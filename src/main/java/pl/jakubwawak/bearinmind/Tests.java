/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind;

import pl.jakubwawak.database_engine.entity.BIM_DailyEntry;
import pl.jakubwawak.database_engine.entity.BIM_User;

/**
 * Function for testing
 */
public class Tests {

    /**
     * Contructor
     */
    public Tests(){
        System.out.println("Running tests...");
    }

    /**
     * Function for running tests
     */
    public void run(){
        BearinmindApplication.database.setDatabase_url("mongodb+srv://kubawawak:Vigor2710Vn@jwmdbinstance.uswe95e.mongodb.net/?retryWrites=true&w=majority");
        BearinmindApplication.database.connect();
//        BIM_User add = new BIM_User();
//        add.bim_user_mail = "test@gmail.com";
//        add.bim_user_password = "adfasdfsafasf";
//        add.bim_user_sex = 1;
//        add.bim_user_name = "Test";
//        add.bim_user_surname = "Testowy";
//        add.bim_user_login="t.testowy";
//        BearinmindApplication.database.insert_user(add);

        BIM_User search2 = BearinmindApplication.database.get_user("a.testowa@gmail.com");
        System.out.println(search2.bim_user_mail);
        BearinmindApplication.logged_user = search2;
        BIM_DailyEntry bde= BearinmindApplication.database.get_user_dailyentry();
        System.out.println(bde.entry_day);
    }
}
