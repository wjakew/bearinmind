/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website.components;

import com.sun.jna.platform.win32.Variant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import pl.jakubwawak.database_engine.entity.BIM_DailyEntry;
import pl.jakubwawak.maintanance.GridElement;

import java.util.ArrayList;


/**
 * Object for showing daily entry data
 */
public class DailyEntryViewer {

    BIM_DailyEntry dailyEntry;

    public HorizontalLayout main_layout;

    VerticalLayout left_layout,right_layout;

    // left components
    VerticalLayout percentLayout;
    TextArea quoteoftheday_area;
    Grid<GridElement> foodentry_grid;
    VerticalLayout waterLayout;

    TextArea goalofthday_area;
    VerticalLayout fearLayout;
    TextArea diaryEntry_area;

    // right components
    VerticalLayout emotionLayout;
    /**
     * Constructor
     * @param dailyEntry
     */
    public DailyEntryViewer(BIM_DailyEntry dailyEntry){
        this.dailyEntry =dailyEntry;
        main_layout = new HorizontalLayout();
        left_layout = new VerticalLayout();
        right_layout = new VerticalLayout();
        prepareLayout();
    }

    /**
     * Function for preparing components
     */
    void prepareComponents(){
        // percent
        percentLayout = new VerticalLayout();
        H1 percentHeader = new H1(dailyEntry.calculatePercent()+"%");
        percentHeader.getStyle().set("color","#FFFFFF");
        percentHeader.getStyle().set("font-size","100px");
        percentHeader.getStyle().set("align","center");
        percentLayout.add(percentHeader);
        percentLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        percentLayout.getStyle().set("text-align", "center");
        percentLayout.getStyle().set("border-radius","25px");
        percentLayout.getStyle().set("background-color","#3C78AE");
        percentLayout.getStyle().set("--lumo-font-family","Monospace");
        percentLayout.setWidth("100%");
        percentLayout.setHeight("40%");

        //quote of the day
        quoteoftheday_area = new TextArea("Quote Of the Day");
        quoteoftheday_area.setValue(dailyEntry.entry_quoteoftheday);
        quoteoftheday_area.setReadOnly(true);
        quoteoftheday_area.setWidth("100%");
        quoteoftheday_area.setHeight("20%");

        // food_entry grid
        foodentry_grid = new Grid<>(GridElement.class,false);
        foodentry_grid.addColumn(GridElement::getGridelement_text).setHeader("Food Entries");
        ArrayList<GridElement> foodentry_data = new ArrayList<>();
        for(String foodEntry : dailyEntry.entry_food){
            foodentry_data.add(new GridElement(foodEntry));
        }
        foodentry_grid.setItems(foodentry_data);
        foodentry_grid.getStyle().set("border-radius","25px");
        foodentry_grid.setWidth("50%");foodentry_grid.setHeight("100%");

        // water_entry grid
        waterLayout = new VerticalLayout();
        H1 waterAmounHeader = new H1(Integer.toString(dailyEntry.entry_water));
        waterAmounHeader.getStyle().set("color","#000000");
        waterAmounHeader.getStyle().set("font-size","100px");
        waterAmounHeader.getStyle().set("align","center");
        waterLayout.add(waterAmounHeader,new H6("glasses"));
        waterLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        waterLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        waterLayout.getStyle().set("text-align", "center");
        waterLayout.getStyle().set("border-radius","25px");
        waterLayout.getStyle().set("background-color","#E3EAB5");
        waterLayout.getStyle().set("--lumo-font-family","Monospace");
        waterLayout.setWidth("50%");
        waterLayout.setHeight("100%");

        //emotion lvl
        emotionLayout = new VerticalLayout();
        ArrayList<H6> emotionsCollection = new ArrayList<>();
        for(String data : dailyEntry.entry_emotionlvl.split(",")){
            emotionsCollection.add(new H6(data));
        }
        if ( emotionsCollection.size() == 0 ){
            emotionLayout.add(new H6("No Emotions :("));
        }
        else{
            for(H6 header : emotionsCollection){
                emotionLayout.add(header);
            }
        }

        emotionLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        emotionLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        emotionLayout.getStyle().set("text-align", "center");
        emotionLayout.getStyle().set("border-radius","25px");
        emotionLayout.getStyle().set("background-color","#B5EAD7");
        emotionLayout.getStyle().set("--lumo-font-family","Monospace");
        emotionLayout.setWidth("50%");
        emotionLayout.setHeight("100%");

        //fear lvl
        fearLayout = new VerticalLayout();
        H1 fearAmountHeader = new H1(dailyEntry.entry_fearlvl);
        fearAmountHeader.getStyle().set("color","#000000");
        fearAmountHeader.getStyle().set("font-size","100px");
        fearAmountHeader.getStyle().set("align","center");
        fearLayout.add(fearAmountHeader,new H6("Fear Lvl"));
        fearLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        fearLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        fearLayout.getStyle().set("text-align", "center");
        fearLayout.getStyle().set("border-radius","25px");
        fearLayout.getStyle().set("background-color","#B5EAD7");
        fearLayout.getStyle().set("--lumo-font-family","Monospace");
        fearLayout.setWidth("50%");
        fearLayout.setHeight("100%");

        // goal of te day

        goalofthday_area = new TextArea("My Goal for that day");
        goalofthday_area.setValue(dailyEntry.entry_dailygoal);
        goalofthday_area.setReadOnly(true);
        goalofthday_area.setWidth("100%");
        goalofthday_area.setHeight("20%");

        // diary entry
        diaryEntry_area = new TextArea("My Diary");
        diaryEntry_area.setValue(dailyEntry.entry_diary);
        diaryEntry_area.setReadOnly(true);
        diaryEntry_area.setWidth("100%");
        diaryEntry_area.setHeight("100%");
    }

    /**
     * Function for preparing layout
     */
    void prepareLayout(){
        prepareComponents();

        // adding compontents to the left side
        left_layout.add(percentLayout,quoteoftheday_area);
        HorizontalLayout foodwaterLayout = new HorizontalLayout();
        foodwaterLayout.setSizeFull();
        foodwaterLayout.add(foodentry_grid,waterLayout);
        left_layout.add(foodwaterLayout);

        // adding components to the right side
        HorizontalLayout up_half = new HorizontalLayout();
        up_half.setSizeFull();
        up_half.add(emotionLayout,fearLayout);
        right_layout.add(goalofthday_area,up_half,diaryEntry_area);

        // styling layouts
        left_layout.setWidth("50%");
        left_layout.setHeight("100%");
        left_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        left_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        left_layout.getStyle().set("text-align", "center");

        right_layout.setWidth("50%");
        right_layout.setHeight("100%");
        right_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        right_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        right_layout.getStyle().set("text-align", "center");

        main_layout.setSizeFull();
        main_layout.getStyle().set("text-align", "center");
        main_layout.getStyle().set("border-radius","25px");
        main_layout.getStyle().set("background-color","#7E8D85");
        main_layout.getStyle().set("--lumo-font-family","Monospace");

        main_layout.add(left_layout,right_layout);
    }
}
