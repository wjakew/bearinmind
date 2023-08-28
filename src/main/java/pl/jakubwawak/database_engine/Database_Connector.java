/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.database_engine;

import com.mongodb.*;


import com.mongodb.client.*;
import com.mongodb.client.model.CountOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.hibernate.dialect.sequence.DB2zSequenceSupport;
import pl.jakubwawak.bearinmind.BearinmindApplication;
import pl.jakubwawak.database_engine.entity.BIM_DailyEntry;
import pl.jakubwawak.database_engine.entity.BIM_Health;
import pl.jakubwawak.database_engine.entity.BIM_Log;
import pl.jakubwawak.database_engine.entity.BIM_User;
import pl.jakubwawak.maintanance.ConsoleColors;
import pl.jakubwawak.maintanance.GridElement;
import pl.jakubwawak.maintanance.Password_Validator;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Object for connecting to database
 */
public class Database_Connector {

    public String database_url;

    public boolean connected;

    MongoClient mongoClient;
    MongoDatabase mongoDatabase;

    ArrayList<String> error_collection;

    /**
     * Constructor
     */
    public Database_Connector(){
        this.database_url = "";
        connected = false;
        error_collection = new ArrayList<>();
    }

    public void setDatabase_url(String database_url){
        this.database_url = database_url;
    }

    /**
     * Function for connecting to database
     * @return boolean
     */
    public void connect(){
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(database_url))
                .serverApi(serverApi)
                .build();
        try{
            mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("admin");
            // Send a ping to confirm a successful connection
            Bson command = new BsonDocument("ping", new BsonInt64(1));
            Document commandResult = database.runCommand(command);
            connected = true;
            mongoDatabase = mongoClient.getDatabase("db_bear_in_mind");
            BearinmindApplication.healthConfiguration = create_health_configuration();
        }catch(MongoException ex){
            // catch error
            log("DB-CONNECTION-ERROR", "Failed to connect to database ("+ex.toString()+")");
            connected = false;
        }
    }

    /**
     * BIM_Health
     * @return BIM_Health
     */
    BIM_Health create_health_configuration(){
        BIM_Health bim_health = new BIM_Health();
        MongoCollection<Document> health_collection = get_data_collection("bim_health");
        if ( health_collection.countDocuments() == 0){
            health_collection.insertOne(bim_health.prepareDocument());
        }
        Document document = health_collection.find().first();
        String adm_password = document.getString("bim_administrator");
        if ( adm_password != null )
            log("HEALTH-CONFIGURATION","Loaded configuration, adm password: "+adm_password);
        return new BIM_Health(document);
    }

    /**
     * Function for loading collections
     * @param collection_name
     * @return MongoCollection<Document>
     */
    MongoCollection<Document> get_data_collection(String collection_name){
        return mongoDatabase.getCollection(collection_name);
    }

    /**
     * Function for adding user
     * @param user_to_add
     * @return BIM_User
     */
    public int insert_user(BIM_User user_to_add){
        try{
            MongoCollection<Document> user_collection = get_data_collection("bim_user");
            user_collection.insertOne(user_to_add.prepareDocument());
            log("DB-INSERT-USER","New user created! "+user_to_add.bim_user_login);
            return 1;
        }catch(Exception ex){
            log("DB-INSERT-USER-FAILED","Failed to insert user ("+ex.toString()+")");
            return 0;
        }
    }

    /**
     * Function for resetting user password
     * @param user_hash
     * @return String
     */
    public String reset_user_password(String user_hash){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 15;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String newPassword = buffer.toString();
        try{
            Password_Validator pv = new Password_Validator(newPassword);
            MongoCollection<Document> user_collection = get_data_collection("bim_user");
            Document userDocument = user_collection.find(new Document("bim_user_hash",user_hash)).first();
            if ( userDocument != null ) {
                Bson updates = Updates.combine(
                        Updates.set("bim_user_password", pv.hash())
                );
                UpdateResult result = user_collection.updateOne(userDocument, updates);
                log("DB-USER-PWRESET","User password reset! User Hash ("+user_hash+")");
                return newPassword;
            }
            log("DB-USER-PWRESET","Cannot find user with given hash! ("+user_hash+")");
            return "";
        }catch(Exception ex){
            log("DB-USER-PASSWORD-FAILED","Failed to reset user password ("+ex.toString()+")");
            return "";
        }
    }

    /**
     * Function for updating user password
     * @param passwordHash
     * @return Integer
     */
    public int updatePassword(String passwordHash){
        try{
            MongoCollection<Document> user_collection = get_data_collection("bim_user");
            Document userDocument = user_collection.find(new Document("bim_user_hash",BearinmindApplication.logged_user.hash)).first();
            if ( userDocument != null ) {
                Bson updates = Updates.combine(
                        Updates.set("bim_user_password", passwordHash)
                );
                UpdateResult result = user_collection.updateOne(userDocument, updates);
                log("DB-USER-PWRESET","User password reset! User Hash ("+BearinmindApplication.logged_user.hash+")");
                return 1;
            }
            log("DB-USER-PWRESET","Cannot find user with given hash! ("+BearinmindApplication.logged_user.hash+")");
            return 0;
        }catch(Exception ex){
            log("DB-USER-PASSWORD-FAILED","Failed to reset user password ("+ex.toString()+")");
            return -1;
        }
    }

    /**
     * Function for removing user from database
     * @param user_hash
     * @return Integer
     */
    public int remove_user(String user_hash){
        try{
            MongoCollection<Document> dailyEntry_collection = get_data_collection("bim_dailyentry");
            Bson query = Filters.eq("bim_dailyentry", user_hash);
            DeleteResult result = dailyEntry_collection.deleteMany(query);
            log("DB-DAILYENTRY-REMOVE","Removed "+result.getDeletedCount()+" documents from bim_dailyentry");
            MongoCollection<Document> user_collection = get_data_collection("bim_user");
            Bson user_query = Filters.eq("bim_user_hash",user_hash);
            DeleteResult user_result = user_collection.deleteMany(user_query);
            log("DB-USER-REMOVE","Removed user ("+user_hash+") from database");
            return 1;
        }catch(Exception ex){
            log("DB-DAILYENTRY-REMOVE-FAILED","Failed for removing user ("+ex.toString()+")");
            return -1;
        }
    }

    /**
     * Function for inserting log
     * @param log_to_add
     * @return Integer
     */
    int insert_log(BIM_Log log_to_add){
        try{
            MongoCollection<Document> log_collection = get_data_collection("bim_log");
            log_collection.insertOne(log_to_add.prepareDocument());
            return 1;
        }catch(Exception ex){
            System.out.println(ex.toString());
            return -1;
        }
    }

    /**
     * Function for loading user from database
     * @param email
     * @return BIM_User
     */
    public BIM_User get_user(String email){
        try{
            MongoCollection<Document> user_collection = get_data_collection("bim_user");
            Document document = user_collection.find(new Document("bim_user_mail",email)).first();
            if (document != null) {
                log("DB-GETUSER","Found user email: "+email);
                return new BIM_User(document);
            }
            log("DB-GETUSER","Cannot find user with email: "+email);
            return null;
        }catch(Exception ex){
            log("DB-GETUSER-FAILED","Failed to get user data ("+ex.toString()+")");
            return null;
        }
    }

    /**
     * Function for loading user from database using _id
     * @param _id
     * @return BIM_User
     */
    public BIM_User get_user_byid(String _id){
        try{
            MongoCollection<Document> user_collection = get_data_collection("bim_user");
            Bson filter = Filters.eq("_id", new ObjectId(_id));
            Document document =user_collection.find(filter).first();
            if ( document != null ){
                log("DB-GETUSER-ID","Found user _id: "+_id);
                return new BIM_User(document);
            }
            log("DB-GETUSER-ID","Cannot find user with id: "+_id);
            return null;
        }catch(Exception ex){
            log("DB-GETUSER-ID-FAILED","Failed to get user data ("+ex.toString()+")");
            return null;
        }
    }

    /**
     * Function for loading user daily entry data
     * @return BIM_DailyEntry
     */
    public BIM_DailyEntry get_user_dailyentry(){
        try{
            MongoCollection<Document> dailyentry_collection = get_data_collection("bim_dailyentry");
            BIM_DailyEntry dailyEntry = new BIM_DailyEntry();
            FindIterable<Document> daily_entry_collection =dailyentry_collection.find(new Document("bim_user_hash",BearinmindApplication.logged_user.hash));
            if ( daily_entry_collection != null ){
                for(Document document : daily_entry_collection){
                    if (document.getString("entry_day").equals(dailyEntry.entry_day)){
                        log("DB-GETDE","Found daily entry for user _id: "+BearinmindApplication.logged_user.bim_user_id+ "("+document.getObjectId("_id").toString()+")");
                        return new BIM_DailyEntry(document);
                    }
                }
            }
            log("DB-GETDE","Cannot find daily entry with user id: "+BearinmindApplication.logged_user.bim_user_id);
            BIM_DailyEntry user_daily_entry = new BIM_DailyEntry();
            dailyentry_collection.insertOne(user_daily_entry.prepareDocument());
            log("DB-GETDE","Created new daily entry for user: "+BearinmindApplication.logged_user.bim_user_id);
            return user_daily_entry;
        }catch(Exception ex){
            log("DB-GETDE-FAILED","Failed to get user daily entry data ("+ex.toString()+")");
            return null;
        }
    }

    /**
     * Function for counting user daily entries
     * @return Long
     */
    public long count_user_dailyentries(){
        try{
            MongoCollection<Document> dailyentry_collection = get_data_collection("bim_dailyentry");
            CountOptions opts = new CountOptions().hintString("_id_");
            return dailyentry_collection.countDocuments(new BsonDocument(), opts);
        }catch(Exception ex){
            log("DB-DE-COUNT","Failed to count daily entries ("+ex.toString()+")");
            return -1;
        }
    }

    /**
     * Function for loading list of users
     * @return ArrayList
     */
    public ArrayList<GridElement> get_user_list(){
        ArrayList<GridElement> data = new ArrayList<>();
        try{
            MongoCollection<Document> dailyentry_collection = get_data_collection("bim_user");
            FindIterable<Document> daily_entry_collection =dailyentry_collection.find();
            for(Document user_document : daily_entry_collection){
                data.add(new GridElement(user_document.getString("bim_user_mail"),0,user_document.getString("bim_user_hash")));
            }
            log("DB-LIST-USER","Loaded "+data.size()+" users from database");
        }catch(Exception ex){
            log("DB-LIST-USER-FAILED","Failed to list users ("+ex.toString()+")");
        }
        return data;
    }

    /**
     * Function for loading dailyentries
     * @return ArrayList
     */
    public ArrayList<BIM_DailyEntry> get_user_dailyentries(){
        ArrayList<BIM_DailyEntry> data = new ArrayList<>();
        try{
            MongoCollection<Document> dailyentry_collection = get_data_collection("bim_dailyentry");
            BIM_DailyEntry dailyEntry = new BIM_DailyEntry();
            FindIterable<Document> daily_entry_collection =dailyentry_collection.find(new Document("bim_user_hash",BearinmindApplication.logged_user.hash)).sort(new Document("_id",-1)).limit(10);
            for (Document document : daily_entry_collection) {
                data.add(new BIM_DailyEntry(document));
            }
            log("DB-GETDE-COLLECTION", "Loaded "+data.size()+" user documents!");
        }catch(Exception ex){
            log("DB-GETDE-COLLECTION-FAILED", "Failed to load user documents ("+ex.toString()+")");
        }
        return  data;
    }

    /**
     * Function for getting application log from database
     * @return ArrayList
     */
    public ArrayList<GridElement> get_application_log(){
        ArrayList<GridElement> data = new ArrayList<>();
        try{
            MongoCollection<Document> bim_log_collection = get_data_collection("bim_log");
            FindIterable<Document> daily_entry_documents = bim_log_collection.find().sort(new Document("_id",-1)).limit(100);
            for(Document document : daily_entry_documents){
                data.add(new GridElement(document.getString("log_desc"),document.getString("log_time"),document.getString("log_code")));
            }
        }catch(Exception ex){
            log("DB-GETLOG-FAILED","Failed to get application log information ("+ex.toString()+")");
        }
        return data;
    }

    /**
     * Function for updating daily entry
     * @param to_update
     * @return Integer
     */
    public int updateDailyEntry(BIM_DailyEntry to_update){
        try{
            MongoCollection<Document> bim_collection = get_data_collection("bim_dailyentry");
            FindIterable<Document> user_dailyentries = bim_collection.find(new Document("bim_user_hash",to_update.bim_user_hash));
            // find document on database
            Document document = null;
            for(Document dailyEntry : user_dailyentries){
                if ( dailyEntry.getString("entry_day").equals(to_update.entry_day)){
                    document = dailyEntry;
                    break;
                }
            }

            if ( document != null ){
                Bson updates = Updates.combine(
                        Updates.set("entry_quoteoftheday", to_update.entry_quoteoftheday),
                        Updates.set("entry_emotionlvl", to_update.entry_emotionlvl),
                        Updates.set("entry_fearlvl", to_update.entry_fearlvl),
                        Updates.set("entry_dailygoal", to_update.entry_dailygoal),
                        Updates.set("entry_diary", to_update.entry_diary),
                        Updates.set("entry_food", to_update.entry_food),
                        Updates.set("entry_water", to_update.entry_water),
                        Updates.set("entry_dailymeds",to_update.entry_dailymeds)
                );
                UpdateResult result = bim_collection.updateOne(document, updates);
                log("DB-BIM-UPDATE","Modified document ("+to_update.dailyentry_id+") count "+result.getModifiedCount());
                try{
                    if ( result.getModifiedCount() > 0 ){
                        Notification noti = Notification.show("Updated Daily Entry object! Modified document ("+to_update.dailyentry_id+") count "+result.getModifiedCount());
                        noti.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    }
                }catch(Exception ex){}
                return 1;
            }
            return 0;
        }catch(Exception ex){
            log("DB-BIM-UPDATE-FAILED","Failed to update bim ("+ex.toString()+")");
            return -1;
        }
    }

    /**
     * Function for updating health data
     * @param health_update
     * @return Integer
     */
    public int updateHealth(BIM_Health health_update){
        try{
            MongoCollection<Document> bim_collection = get_data_collection("bim_health");
            Document health_document = bim_collection.find().first();
            Bson updates = Updates.combine(
                    Updates.set("bim_administrator",health_update.bim_administrator),
                    Updates.set("bim_feature1",health_update.bim_feature1),
                    Updates.set("bim_feature2",health_update.bim_feature2),
                    Updates.set("bim_feature3",health_update.bim_feature3),
                    Updates.set("bim_feature4",health_update.bim_feature4),
                    Updates.set("bim_feature5",health_update.bim_feature5)
            );
            UpdateResult result = bim_collection.updateOne(health_document, updates);
            log("HEALTH-UPDATE","Modified health document, updated: "+result.getModifiedCount());
            return 1;
        }catch(Exception ex){
            log("HEALTH-UPDATE-FAILED","Failed to update ("+ex.toString()+")");
            return -1;
        }

    }

    /**
     * User login to the app
     * @param login
     * @param hash_password
     * @return Integer
     * return codes:
     * 1 - user successfull
     * 0 - wrong password
     * -2 - cannot find user
     * -1 - database error
     */
    public int login_user(String login, String hash_password){
        try{
            MongoCollection<Document> user_collection = get_data_collection("bim_user");
            Document document = user_collection.find(new Document("bim_user_mail",login)).first();
            if (document != null){
                BIM_User user = new BIM_User(document);
                if ( user.bim_user_password.equals(hash_password) ){
                    // login successfull
                    BearinmindApplication.logged_user = user;
                    log("DB-LOGIN-USER","Logged new user to the instance ("+login+"/"+BearinmindApplication.logged_user.hash+")");
                    return 1;
                }
                else{
                    // wrong password
                    log("DB-LOGIN-USER-WRPASS","Wrong password for ("+login+") hash ("+hash_password+")");
                    return 0;
                }
            }
            // cannot find user
            log("DB-LOGIN-USER-CNFIND","Cannot find user ("+login+")");
            return -2;
        }catch(Exception ex){
            log("DB-LOGIN-USER-FAILED","Failed to login user ("+ex.toString()+")");
            return -1;
        }
    }

    /**
     * Function for story log data
     * @param log_category
     * @param log_text
     */
    public void log(String log_category, String log_text){
        error_collection.add(log_category+"("+ LocalDateTime.now(ZoneId.of("Europe/Warsaw")).toString()+") - "+log_text);
        BIM_Log log_obj = new BIM_Log();
        log_obj.log_code = log_category;
        log_obj.log_desc =log_text;
        if ( log_category.contains("FAILED") || log_category.contains("ERROR")){
            System.out.println(ConsoleColors.RED_BRIGHT+log_category+"["+ LocalDateTime.now(ZoneId.of("Europe/Warsaw")).toString()+") - "+log_text+"]"+ConsoleColors.RESET);
            try{
                Notification noti = Notification.show(log_text);
                noti.addThemeVariants(NotificationVariant.LUMO_ERROR);
                log_obj.std_category = "ERR";
            }catch(Exception ex){}
        }
        else{
            System.out.println(ConsoleColors.GREEN_BRIGHT+log_category+"["+ LocalDateTime.now(ZoneId.of("Europe/Warsaw")).toString()+") - "+log_text+"]"+ConsoleColors.RESET);
            log_obj.std_category = "NRL";
        }
        if ( BearinmindApplication.logged_user != null ){
            log_obj.log_user_login = BearinmindApplication.logged_user.bim_user_mail;
        }
        else{
            log_obj.log_user_login = "none";
        }
        if ( BearinmindApplication.log_database_flag == 1){
            // create log entry on database
            if(BearinmindApplication.database.connected){
                BearinmindApplication.database.insert_log(log_obj);
            }
        }
    }
}
