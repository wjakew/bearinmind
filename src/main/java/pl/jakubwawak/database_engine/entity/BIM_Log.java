/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.database_engine.entity;

import org.bson.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Object for storing log data on database
 */
public class BIM_Log {
    public String log_time;
    public String log_code;
    public String log_desc;

    public String log_user_login;

    public String std_category;

    /**
     * Constructor
     */
    public BIM_Log(){
        log_time = LocalDateTime.now(ZoneId.of("Europe/Warsaw")).toString();
        log_code = "";
        log_desc = "";
        log_user_login = "";
        std_category = "";
    }

    /**
     * Constructor with params
     */
    public BIM_Log(String log_code,String log_desc,String log_user_login){
        this.log_code = log_code;
        this.log_desc = log_desc;
        this.log_user_login = log_user_login;
        log_time = LocalDateTime.now(ZoneId.of("Europe/Warsaw")).toString();
        if ( log_code.contains("ERROR") || log_code.contains("FAILED")){
            std_category = "ERR";
        }
        else{
            std_category = "NRL";
        }
    }

    /**
     * Function for preparing document for creation on database
     * @return Document
     */
    public Document prepareDocument(){
        Document bim_log_document = new Document();
        bim_log_document.append("log_time",log_time);
        bim_log_document.append("log_code",log_code);
        bim_log_document.append("log_desc",log_desc);
        bim_log_document.append("log_user_login",log_user_login);
        bim_log_document.append("std_category",std_category);
        return bim_log_document;
    }
}
