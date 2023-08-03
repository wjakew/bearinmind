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
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import pl.jakubwawak.database_engine.Database_Connector;
import pl.jakubwawak.maintanance.ConsoleColors;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableVaadin({"pl.jakubwawak"})
public class BearinmindApplication {

	public static int test_flag = 1;

	public static String version = "v0.0.1";
	public static String build = "binmind-02082023POC";

	public static Database_Connector database;

	/**
	 * Main application function
	 * @param args
	 */
	public static void main(String[] args) {
		show_header();
		database = new Database_Connector();
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
