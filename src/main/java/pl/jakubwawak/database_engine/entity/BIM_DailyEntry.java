/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.database_engine.entity;

import org.bson.Document;
import org.bson.types.ObjectId;
import pl.jakubwawak.bearinmind.BearinmindApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class BIM_DailyEntry {

    public String dailyentry_id;
    public String entry_day;
    public String entry_quoteoftheday;
    public String entry_emotionlvl;
    public String entry_fearlvl;
    public ArrayList<String> entry_food;
    public ArrayList<String> entry_water;

    public String bim_user_hash;

    public String entry_dailygoal;
    public String entry_dailymeds;
    public String entry_diary;

    /**
     * Constructor
     */
    public BIM_DailyEntry(){
        dailyentry_id = "0";
        bim_user_hash = BearinmindApplication.logged_user.hash;
        entry_day = LocalDate.now(ZoneId.of("Europe/Warsaw")).toString();
        entry_quoteoftheday = "";
        entry_emotionlvl = "";
        entry_fearlvl = "";
        entry_food = new ArrayList<>();
        entry_water = new ArrayList<>();
        entry_dailygoal = "";
        entry_dailymeds = "";
        entry_diary = "";
    }

    /**
     * Constructor with support
     * @param to_add
     */
    public BIM_DailyEntry(Document to_add){
        dailyentry_id= to_add.getObjectId("_id").toString();
        bim_user_hash = to_add.getString("bim_user_hash");
        entry_day = to_add.getString("entry_day");
        entry_quoteoftheday = to_add.getString("entry_quoteoftheday");
        entry_emotionlvl = to_add.getString("entry_emotionlvl");
        entry_fearlvl = to_add.getString("entry_fearlvl");
        entry_food = (ArrayList<String>) to_add.getList("entry_food",String.class);
        entry_water = (ArrayList<String>) to_add.getList("entry_water",String.class);
        entry_dailygoal = to_add.getString("entry_dailygoal");;
        entry_dailymeds = to_add.getString("entry_dailymeds");;
        entry_diary = to_add.getString("entry_diary");;
    }

    /**
     * Function for preparing document from object
     * @return Document
     */
    public Document prepareDocument(){
        Document bim_dailyentry_document = new Document();
        bim_dailyentry_document.append("bim_user_hash",bim_user_hash);
        bim_dailyentry_document.append("entry_day",entry_day);
        bim_dailyentry_document.append("entry_quoteoftheday",entry_quoteoftheday);
        bim_dailyentry_document.append("entry_emotionlvl",entry_emotionlvl);
        bim_dailyentry_document.append("entry_food",entry_food);
        bim_dailyentry_document.append("entry_water",entry_water);
        bim_dailyentry_document.append("entry_dailygoal",entry_dailygoal);
        bim_dailyentry_document.append("entry_dailymeds",entry_dailymeds);
        bim_dailyentry_document.append("entry_diary",entry_diary);
        return bim_dailyentry_document;
    }
}
