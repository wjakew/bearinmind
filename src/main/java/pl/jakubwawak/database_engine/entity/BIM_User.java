/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.database_engine.entity;

import org.bson.Document;

/**
 * Collection for storing user data
 */
public class BIM_User {
    public String bim_user_id;
    public String bim_user_name;
    public String bim_user_login;
    public String bim_user_surname;
    public String bim_user_mail;
    public String bim_user_password;
    public int bim_user_sex;


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
        bim_user_sex = 0;
    }

    /**
     * Constructor with database support
     * @param to_add
     */
    public BIM_User(Document to_add){
        bim_user_id = to_add.getObjectId("_id").toString();
        bim_user_name = to_add.getString("bim_user_name");
        bim_user_surname = to_add.getString("bim_user_surname");
        bim_user_mail = to_add.getString("bim_user_mail");
        bim_user_password = to_add.getString("bim_user_password");
        bim_user_login = to_add.getString("bim_user_login");
        bim_user_sex = to_add.getInteger("bim_user_sex");
    }

    /**
     * Function for preparing document for creation
     * @return Document
     */
    public Document prepareDocument(){
        Document bim_user_document = new Document();
        bim_user_document.append("bim_user_login",bim_user_login);
        bim_user_document.append("bim_user_name",bim_user_name);
        bim_user_document.append("bim_user_surname",bim_user_surname);
        bim_user_document.append("bim_user_mail",bim_user_mail);
        bim_user_document.append("bim_user_password",bim_user_password);
        bim_user_document.append("bim_user_sex",bim_user_id);
        return bim_user_document;
    }
}
