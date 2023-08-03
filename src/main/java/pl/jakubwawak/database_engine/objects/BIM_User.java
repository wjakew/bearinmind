/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.database_engine.objects;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

/**
 * Object for storing user data
 */
public class BIM_User {

    public int bim_user_id;
    public String bim_user_login;
    public String bim_user_password;
    public String bim_user_name;
    public String bim_user_surname;
    public String bim_user_email;
    public String bim_user_sex;

    /**
     * Constructor
     */
    public BIM_User(){
        bim_user_id = -1;
        bim_user_login = "";
        bim_user_password = "";
        bim_user_name = "";
        bim_user_surname = "";
        bim_user_email = "";
        bim_user_sex = "";
    }

    /**
     * Function for getting JSON data from
     * @return String
     */
    public String getJSON(){
        try{
           return new ObjectMapper().writeValueAsString(this);
        }catch(Exception ex){return null;}
    }
}
