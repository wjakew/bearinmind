/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.database_engine;

import com.mongodb.*;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.hibernate.dialect.sequence.DB2zSequenceSupport;
import pl.jakubwawak.bearinmind.BearinmindApplication;
import pl.jakubwawak.database_engine.entity.BIM_User;
import pl.jakubwawak.maintanance.ConsoleColors;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

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
        }catch(MongoException ex){
            // catch error
            log("DB-CONNECTION-ERROR", "Failed to connect to database ("+ex.toString()+")");
            connected = false;
        }
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
            Document document = user_collection.find(new Document("bim_user_login",login)).first();
            if (document != null){
                BIM_User user = new BIM_User(document);
                if ( user.bim_user_password.equals(hash_password) ){
                    // login successfull
                    BearinmindApplication.logged_user = user;
                    return 1;
                }
                else{
                    // wrong password
                    return 0;
                }
            }
            // cannot find user
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
        if ( log_category.contains("FAILED") || log_category.contains("ERROR")){
            System.out.println(ConsoleColors.RED_BRIGHT+log_category+"["+ LocalDateTime.now(ZoneId.of("Europe/Warsaw")).toString()+") - "+log_text+"]"+ConsoleColors.RESET);
            try{
                Notification noti = Notification.show(log_text);
                noti.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }catch(Exception ex){}
        }
        else{
            System.out.println(ConsoleColors.GREEN_BRIGHT+log_category+"["+ LocalDateTime.now(ZoneId.of("Europe/Warsaw")).toString()+") - "+log_text+"]"+ConsoleColors.RESET);
        }
    }
}
