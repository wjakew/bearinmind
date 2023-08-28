/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import pl.jakubwawak.bearinmind.website.views.HomeView;
import pl.jakubwawak.database_engine.Database_Connector;
import pl.jakubwawak.database_engine.entity.BIM_Health;
import pl.jakubwawak.database_engine.entity.BIM_User;
import pl.jakubwawak.maintanance.ConsoleColors;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class })
@EnableVaadin({"pl.jakubwawak"})
public class BearinmindApplication {
	public static int test_flag = 0;        // flag enables testing
	public static int log_database_flag = 1;// flag for setting if log is storing on database

	public static String version = "v1.0.0";
	public static String build = "binmind-28082023POC";

	public static Database_Connector database;

	public static BIM_Health healthConfiguration;

	public static BIM_User logged_user;
	public static HomeView currentLayout;

	/**
	 * Main application function
	 * @param args
	 */
	public static void main(String[] args) {
		show_header();
		database = new Database_Connector();
		healthConfiguration = null;
		logged_user = null;
		currentLayout = null;
		if ( test_flag == 0 ){
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
