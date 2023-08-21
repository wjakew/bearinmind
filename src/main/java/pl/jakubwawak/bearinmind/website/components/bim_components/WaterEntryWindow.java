/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website.components.bim_components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import pl.jakubwawak.bearinmind.BearinmindApplication;
import pl.jakubwawak.database_engine.entity.BIM_DailyEntry;

/**
 * Object for showing water entry data
 */
public class WaterEntryWindow{

    public Dialog main_dialog;
    VerticalLayout main_layout;

    Button wateramount_button;
    BIM_DailyEntry dailyEntry;

    /**
     * Constructor
     */
    public WaterEntryWindow(BIM_DailyEntry dailyEntry){
        main_dialog = new Dialog();
        main_layout = new VerticalLayout();
        this.dailyEntry = dailyEntry;
        prepare_window();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        wateramount_button = new Button("0",this::wateramountbutton_action);
        wateramount_button.getStyle().set("background","#FFC2CD");
        wateramount_button.getStyle().set("color","#FFFFFF");
        wateramount_button.getStyle().set("font-size","100px");
        wateramount_button.setWidth("250px");
        wateramount_button.setHeight("250px");
        wateramount_button.setText(Integer.toString(dailyEntry.entry_water));
    }

    /**
     * Function for preparing window
     */
    void prepare_window(){
        // preparing components
        prepare_components();

        // creating layout

        // -- here add your layout
        main_layout.add(new H6("how many glasses did you drink today?"));
        main_layout.add(wateramount_button);

        // styling window
        main_layout.setSizeFull();
        main_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        main_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        main_layout.getStyle().set("text-align", "center");

        main_layout.getStyle().set("border-radius","25px");
        main_layout.getStyle().set("background-color","#A4B7DE");
        main_layout.getStyle().set("--lumo-font-family","Monospace");
        // adding layout to window
        main_dialog.add(main_layout);
    }

    /**
     * wateramount_button action
     * @param ex
     */
    private void wateramountbutton_action(ClickEvent ex){
        try{
            int water_entry = Integer.parseInt(wateramount_button.getText());
            water_entry++;
            dailyEntry.entry_water = water_entry;
            BearinmindApplication.database.updateDailyEntry(dailyEntry);
            wateramount_button.setText(Integer.toString(water_entry));
        }catch(Exception e){}
    }
}
