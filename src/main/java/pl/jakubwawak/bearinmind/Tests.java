/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind;

import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import pl.jakubwawak.database_engine.objects.BIM_User;

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
        BIM_User bimUser = new BIM_User();
        bimUser.bim_user_login = "j.wawak";
        bimUser.bim_user_email = "j.wawak@usp.pl";
        System.out.println(bimUser.getJSON());
    }
}
