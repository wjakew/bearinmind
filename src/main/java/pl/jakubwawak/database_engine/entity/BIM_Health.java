/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.database_engine.entity;

import org.bson.Document;
import pl.jakubwawak.bearinmind.BearinmindApplication;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class BIM_Health {
    public String bim_version = BearinmindApplication.version;
    public String bim_administrator;
    public String bim_startuptime;
    public String bim_feature1, bim_feature2,bim_feature3,bim_feature4,bim_feature5;

    /**
     * Constructor
     */
    public BIM_Health(){
        bim_administrator = randomStringGenerator(15);
        bim_startuptime = LocalDateTime.now(ZoneId.of("Europe/Warsaw")).toString();
        bim_feature1 = "createUserOn"; // enable user creation, createUserOff to disable | createUserOn/createUserOff
        bim_feature2 = "";
        bim_feature3 = "";
        bim_feature4 = "";
        bim_feature5 = "";
    }

    /**
     * Constructor with database support
     * @param health_document
     */
    public BIM_Health(Document health_document){
        bim_administrator = health_document.getString("bim_administrator");
        bim_startuptime = health_document.getString("bim_startuptime");
        bim_feature1 = health_document.getString("bim_feature1");
        bim_feature2 = health_document.getString("bim_feature2");
        bim_feature3 = health_document.getString("bim_feature3");
        bim_feature4 = health_document.getString("bim_feature4");
        bim_feature5 = health_document.getString("bim_feature5");
    }

    /**
     * Function for creating random string generator
     * @param n
     * @return String
     */
    String randomStringGenerator(int n){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }
        return sb.toString();
    }

    /**
     * Function for creating documents for database insert
     * @return Document
     */
    public Document prepareDocument(){
        Document bim_health_document = new Document();
        bim_health_document.append("bim_startuptime",bim_startuptime);
        bim_health_document.append("bim_administrator",bim_administrator);
        bim_health_document.append("bim_feature1",bim_feature1);
        bim_health_document.append("bim_feature2",bim_feature1);
        bim_health_document.append("bim_feature3",bim_feature1);
        bim_health_document.append("bim_feature4",bim_feature1);
        bim_health_document.append("bim_feature5",bim_feature1);
        return bim_health_document;
    }

    public String getBim_feature1(){return bim_feature1;}
}
