/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import org.springframework.security.core.parameters.P;
import pl.jakubwawak.bearinmind.BearinmindApplication;
import pl.jakubwawak.bearinmind.website.components.bim_components.*;
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

        fearlvl_button = new Button("",this::fearlvlbutton_action);
        fearlvl_button.setText("fear_lvl");

        emotionlvl_button = new Button("",this::emotionlvlbutton_action);
        emotionlvl_button.setText("emotion_lvl");

        foodentry_button = new Button("",this::foodentrybutton_action);
        foodentry_button.setText("Food");

        waterentry_button = new Button("",this::waterentrybutton_action);
        waterentry_button.setText("Water");

        dailygoal_button = new Button("",this::dailygoalbutton_action);
        dailygoal_button.setText("daily_goal");

        dailymends_button = new Button("",this::dailymendsbutton_action);
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
        emotionlvl_button.getStyle().set("word-wrap","break-word");

        fearlvl_button.getStyle().set("background-color","#B5EAD7");
        fearlvl_button.getStyle().set("color","#FFFFFF");
        fearlvl_button.getStyle().set("word-wrap","break-word");
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

        main_dailyentry_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        main_dailyentry_layout.setWidth("80%");
        main_dailyentry_layout.setHeight("50%");
        main_dailyentry_layout.getStyle().set("text-align", "center");
        main_dailyentry_layout.getStyle().set("border-radius","25px");
        main_dailyentry_layout.getStyle().set("margin","75px");
        main_dailyentry_layout.getStyle().set("background-color","#7E8D85");
        main_dailyentry_layout.getStyle().set("--lumo-font-family","Monospace");

        // action listeners
        entry_quoteoftheday.addBlurListener(e ->
        {
            dailyEntry.entry_quoteoftheday = entry_quoteoftheday.getValue();
            updateDatabase();
        });
        dailyentry_area.addBlurListener(e ->
        {
            dailyEntry.entry_diary = dailyentry_area.getValue();
            updateDatabase();
        });
    }

    /**
     * Function for preparing data visualisation
     */
    public void prepareDataOutput(){
        entry_quoteoftheday.setValue(dailyEntry.entry_quoteoftheday);
        dailyentry_area.setValue(dailyEntry.entry_diary);

        if ( !dailyEntry.entry_emotionlvl.equals("") ){
            emotionlvl_button.setText(dailyEntry.entry_emotionlvl);
            emotionlvl_button.setEnabled(false);
        }
        else{
            emotionlvl_button.setText("How do you feel today?");
        }

        fearlvl_button.setText("How much did you stress today?");

        if (!dailyEntry.entry_dailygoal.equals("")){
            dailygoal_button.setText("Daily goal: " + dailyEntry.entry_dailygoal);
            dailygoal_button.setEnabled(false);
        }
        else{
            dailygoal_button.setText("Set Today's Goal");
        }
        dailymends_button.setText("Daily Meds");
    }

    /**
     * Function for updating object
     */
    public void updateDatabase(){
        int ans = BearinmindApplication.database.updateDailyEntry(dailyEntry);
        switch(ans){
            case 1:
            {
                Notification.show("Updated values on ("+dailyEntry.dailyentry_id+")");
                break;
            }
            case 0:
            {
                Notification.show("Cannot find daily entry! code : 0");
                break;
            }
            default:
            {
                Notification.show("Application error, see log.");
                break;
            }
        }
    }


    // -- button actions
    private void dailygoalbutton_action(ClickEvent ex){
        TodaysGoalWindow tgw = new TodaysGoalWindow(this.dailyEntry);
        main_dailyentry_layout.add(tgw.main_dialog);
        tgw.main_dialog.open();
        prepareDataOutput();
    }
    private void foodentrybutton_action(ClickEvent ex){
        FoodEntryWindow few = new FoodEntryWindow(this.dailyEntry);
        main_dailyentry_layout.add(few.main_dialog);
        few.main_dialog.open();
        prepareDataOutput();
    }
    private void waterentrybutton_action(ClickEvent ex){
        WaterEntryWindow wew = new WaterEntryWindow(this.dailyEntry);
        main_dailyentry_layout.add(wew.main_dialog);
        wew.main_dialog.open();
        prepareDataOutput();
    }
    private void emotionlvlbutton_action(ClickEvent ex){
        FeelingsEntryWindow few = new FeelingsEntryWindow(this.dailyEntry);
        main_dailyentry_layout.add(few.main_dialog);
        few.main_dialog.open();
        prepareDataOutput();
    }
    private void fearlvlbutton_action(ClickEvent ex){
        StressEntryWindow sew = new StressEntryWindow(this.dailyEntry);
        main_dailyentry_layout.add(sew.main_dialog);
        sew.main_dialog.open();
        prepareDataOutput();
    }
    private void dailymendsbutton_action(ClickEvent ex){
        MedsEntryWindow mew = new MedsEntryWindow(this.dailyEntry);
        main_dailyentry_layout.add(mew.main_dialog);
        mew.main_dialog.open();
        prepareDataOutput();
    }
}
