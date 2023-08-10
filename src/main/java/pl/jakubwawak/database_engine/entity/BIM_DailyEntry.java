/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.database_engine.entity;

import org.bson.Document;
import pl.jakubwawak.bearinmind.BearinmindApplication;

import java.util.ArrayList;
import java.util.Date;

public class BIM_DailyEntry {

    public String entry_day;
    public String entry_quoteoftheday;
    public String entry_emotionlvl;
    public String entry_fearlvl;
    public ArrayList<String> entry_food;
    public ArrayList<String> entry_water;

    public String bim_user_id;

    public String entry_dailygoal;
    public String entry_dailymeds;
    public String entry_diary;

    /**
     * Constructor
     */
    public BIM_DailyEntry(){
        bim_user_id = BearinmindApplication.logged_user.bim_user_id;
        entry_day = new Date().toString();
        entry_quoteoftheday = "";
        entry_emotionlvl = "";
        entry_fearlvl = "";
        entry_food = new ArrayList<>();
        entry_water = new ArrayList<>();
        entry_dailygoal = "";
        entry_dailymeds = "";
        entry_diary = "";
    }

    Document prepareFoodDocument(){
        Document entry_food_document = new Document();
        for(String food : entry_food){
            entry_food_document.append(Integer.toString(entry_food.indexOf(food)),food);
        }
        return entry_food_document;
    }

    Document prepareWaterDocument(){
        Document entry_water_document = new Document();
        for(String food : entry_water){
            entry_water_document.append(Integer.toString(entry_water.indexOf(food)),food);
        }
        return entry_water_document;
    }

    /**
     * Function for preparing document from object
     * @return Document
     */
    public Document prepareDocument(){
        Document bim_dailyentry_document = new Document();
        bim_dailyentry_document.append("entry_day",entry_day);
        bim_dailyentry_document.append("entry_quoteoftheday",entry_quoteoftheday);
        bim_dailyentry_document.append("entry_emotionlvl",entry_emotionlvl);
        bim_dailyentry_document.append("entry_food",prepareFoodDocument());
        bim_dailyentry_document.append("entry_water",prepareWaterDocument());
        bim_dailyentry_document.append("entry_dailygoal",entry_dailygoal);
        bim_dailyentry_document.append("entry_dailymeds",entry_dailymeds);
        bim_dailyentry_document.append("entry_diary",entry_diary);
        return bim_dailyentry_document;
    }
}
