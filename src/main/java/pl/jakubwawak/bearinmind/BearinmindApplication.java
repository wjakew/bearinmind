/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import pl.jakubwawak.database_engine.Database_Connector;
import pl.jakubwawak.database_engine.entity.BIM_Health;
import pl.jakubwawak.database_engine.entity.BIM_User;
import pl.jakubwawak.maintanance.ConsoleColors;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class })
@EnableVaadin({"pl.jakubwawak"})
public class BearinmindApplication {

	public static int debug_flag = 1;       // set automatic connection to test database - dev setting only
	public static int test_flag = 1;        // flag enables testing
	public static int log_database_flag = 0;// flag for setting if log is storing on database

	public static String version = "v0.0.1";
	public static String build = "binmind-11082023POC";

	public static Database_Connector database;

	public static BIM_Health healthConfiguration;

	public static BIM_User logged_user;

	/**
	 * Main application function
	 * @param args
	 */
	public static void main(String[] args) {
		show_header();
		database = new Database_Connector();
		healthConfiguration = null;
		logged_user = null;
		if ( test_flag == 0 ){

			if ( debug_flag == 1 ){
				String url = "mongodb+srv://kubawawak:Vigor2710Vn@jwmdbinstance.uswe95e.mongodb.net/?retryWrites=true&w=majority";
				database.setDatabase_url(url);
				database.connect();
			}
			SpringApplication.run(BearinmindApplication.class, args);
		}
		else{
			// create tests
			Tests test = new Tests();
			test.run();
		}
	}

	/**
	 * Function for showing header data
	 */
	static void show_header(){
		String header = " __         __\n" +
				"/  \\.-\"\"\"-./  \\\n" +
				"\\    -   -    /\n" +
				" |   o   o   |\n" +
				" \\  .-'''-.  /\n" +
				"  '-\\__Y__/-'\n" +
				"     `---`";
		header = header +"\n" + "bear_in_mind " + version + "/" + build;
		System.out.println(ConsoleColors.YELLOW_BRIGHT+header+ConsoleColors.RESET);
	}

}
