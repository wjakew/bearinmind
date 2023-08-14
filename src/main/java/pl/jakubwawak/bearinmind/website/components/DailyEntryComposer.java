/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import pl.jakubwawak.database_engine.entity.BIM_DailyEntry;

/**
 * Object for creating view for showing components of DailyEntry
 */
public class DailyEntryComposer {
    BIM_DailyEntry dailyEntry;

    public HorizontalLayout main_dailyentry_layout;

    // components
    TextArea entry_quoteoftheday;

    Button emotionlvl_button,fearlvl_button,foodentry_button, waterentry_button, dailygoal_button,dailymends_button;

    TextArea dailyentry_area;


    /**
     * Constructor
     */
    public DailyEntryComposer(BIM_DailyEntry dailyEntry){
        this.dailyEntry = dailyEntry;
        prepareLayout();
        prepareDataOutput();
    }

    /**
     * Function for preparing components for layout
     */
    void prepare_components(){
        entry_quoteoftheday = new TextArea();
        entry_quoteoftheday.setPlaceholder("Quote of the Day");

        fearlvl_button = new Button();
        fearlvl_button.setText("fear_lvl");

        emotionlvl_button = new Button();
        emotionlvl_button.setText("emotion_lvl");

        foodentry_button = new Button();
        foodentry_button.setText("Food");

        waterentry_button = new Button();
        waterentry_button.setText("Water");

        dailygoal_button = new Button();
        dailygoal_button.setText("daily_goal");

        dailymends_button = new Button();
        dailymends_button.setText("daily_meds");

        dailyentry_area = new TextArea();
        dailyentry_area.setPlaceholder("What's on your mind today?");
    }

    /**
     * Function for styling components
     */
    void styleComponents(){
        entry_quoteoftheday.setWidth("100%");entry_quoteoftheday.setHeight("40%");
        foodentry_button.setWidth("45%");foodentry_button.setHeight("40%");
        waterentry_button.setWidth("45%");waterentry_button.setHeight("40%");
        dailygoal_button.setWidth("100%");dailygoal_button.setHeight("10%");
        dailymends_button.setWidth("100%");dailymends_button.setHeight("10%");

        emotionlvl_button.setWidth("100%");emotionlvl_button.setHeight("10%");
        fearlvl_button.setWidth("100%");fearlvl_button.setHeight("10%");
        dailyentry_area.setWidth("100%");dailyentry_area.setHeight("80%");


        // left side
        foodentry_button.getStyle().set("background","#FFC2CD");
        foodentry_button.getStyle().set("color","#FFFFFF");

        waterentry_button.getStyle().set("background-color","#A4B7DE");
        waterentry_button.getStyle().set("color","#FFFFFF");

        dailygoal_button.getStyle().set("background-color","#EAB5B5");
        dailygoal_button.getStyle().set("color","#FFFFFF");

        dailymends_button.getStyle().set("background-color","#FFFAFB");
        dailymends_button.getStyle().set("color","#000000");

        //right side
        emotionlvl_button.getStyle().set("background-color","#87B38D");
        emotionlvl_button.getStyle().set("color","#FFFFFF");

        fearlvl_button.getStyle().set("background-color","#B5EAD7");
        fearlvl_button.getStyle().set("color","#000000");
    }

    /**
     * Function for preparing layout data
     */
    void prepareLayout(){
        prepare_components();
        styleComponents();
        main_dailyentry_layout = new HorizontalLayout();

        VerticalLayout left_layout = new VerticalLayout();
        VerticalLayout right_layout = new VerticalLayout();

        HorizontalLayout foodwater_layout = new HorizontalLayout();

        foodwater_layout.add(foodentry_button,waterentry_button);

        foodwater_layout.setWidth("100%");
        foodwater_layout.setAlignItems(FlexComponent.Alignment.CENTER);
        foodwater_layout.setWidth("40%");

        left_layout.add(entry_quoteoftheday,foodwater_layout,dailygoal_button,dailymends_button);
        right_layout.add(emotionlvl_button,fearlvl_button,dailyentry_area);



        left_layout.setSizeFull();
        left_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        left_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        left_layout.getStyle().set("text-align", "center");

        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        right_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        right_layout.getStyle().set("text-align", "center");

        main_dailyentry_layout.add(left_layout,right_layout);
        //main_dailyentry_layout.setSizeFull();
        main_dailyentry_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        main_dailyentry_layout.setWidth("80%");
        main_dailyentry_layout.setHeight("50%");
        main_dailyentry_layout.getStyle().set("text-align", "center");
        main_dailyentry_layout.getStyle().set("border-radius","25px");
        main_dailyentry_layout.getStyle().set("margin","75px");
        main_dailyentry_layout.getStyle().set("background-color","#7E8D85");
        main_dailyentry_layout.getStyle().set("--lumo-font-family","Monospace");
    }

    /**
     * Function for preparing data visualisation
     */
    void prepareDataOutput(){
        entry_quoteoftheday.setValue(dailyEntry.entry_quoteoftheday);
        dailyentry_area.setValue(dailyEntry.entry_diary);

        if ( !dailyEntry.entry_fearlvl.equals("") ){
            emotionlvl_button.setText(dailyEntry.entry_emotionlvl);
            emotionlvl_button.setEnabled(false);
        }
        else{
            emotionlvl_button.setText("How do you feel today?");
        }

        if ( !dailyEntry.entry_fearlvl.equals("") ){
            fearlvl_button.setText(dailyEntry.entry_fearlvl);
            fearlvl_button.setEnabled(false);
        }
        else{
            fearlvl_button.setText("How much did you stress?");
        }

        if (!dailyEntry.entry_dailygoal.equals("")){
            dailygoal_button.setText(dailyEntry.entry_dailygoal);
            dailygoal_button.setEnabled(false);
        }
        else{
            dailygoal_button.setText("Set Today's Goal");
        }

        dailymends_button.setText("Daily Meds");
    }
}
