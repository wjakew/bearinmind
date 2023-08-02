/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.database_engine;

import com.mongodb.*;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * Object for connecting to database
 */
public class Database_Connector {

    public String database_url;
    public String database_name;

    public boolean connected;

    MongoClient mongoClient;



    /**
     * Constructor
     */
    public Database_Connector(){
        this.database_url = "";
        connected = false;
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
        }catch(MongoException ex){
            // catch error
            connected = false;
        }
    }

}
