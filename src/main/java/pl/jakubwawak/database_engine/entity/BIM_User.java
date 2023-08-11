/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.database_engine.entity;

import org.bson.Document;
import org.bson.types.ObjectId;
import pl.jakubwawak.bearinmind.BearinmindApplication;

/**
 * Collection for storing user data
 */
public class BIM_User {

    public ObjectId _id;
    public String bim_user_id;
    public String bim_user_name;
    public String bim_user_login;
    public String bim_user_surname;
    public String bim_user_mail;
    public String bim_user_password;

    public String hash;


    /**
     * Constructor
     */
    public BIM_User(){
        bim_user_id = "";
        bim_user_name = "";
        bim_user_login = "";
        bim_user_surname = "";
        bim_user_mail = "";
        bim_user_password = "";
        hash = "-1";
    }

    /**
     * Constructor with database support
     * @param to_add
     */
    public BIM_User(Document to_add){
        try{
            _id = to_add.getObjectId("_id");
            bim_user_id = to_add.getObjectId("_id").toString();
            bim_user_name = to_add.getString("bim_user_name");
            bim_user_surname = to_add.getString("bim_user_surname");
            bim_user_mail = to_add.getString("bim_user_mail");
            bim_user_password = to_add.getString("bim_user_password");
            bim_user_login = to_add.getString("bim_user_login");
            hash = to_add.getString("bim_user_hash");
        }catch(Exception ex){
            BearinmindApplication.database.log("BIM-USER-PARSE-FAILED","Failed to parse bim user data ("+ex.toString()+") - "+ex.getStackTrace().toString());
        }

    }

    /**
     * Function for preparing document for creation
     * @return Document
     */
    public Document prepareDocument(){
        hash = Integer.toString(bim_user_mail.hashCode());
        Document bim_user_document = new Document();
        bim_user_document.append("bim_user_hash",hash);
        bim_user_document.append("bim_user_login",bim_user_login);
        bim_user_document.append("bim_user_name",bim_user_name);
        bim_user_document.append("bim_user_surname",bim_user_surname);
        bim_user_document.append("bim_user_mail",bim_user_mail);
        bim_user_document.append("bim_user_password",bim_user_password);
        return bim_user_document;
    }
}
