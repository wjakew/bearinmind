/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website.components.bim_components;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import pl.jakubwawak.bearinmind.BearinmindApplication;
import pl.jakubwawak.bearinmind.website.components.DailyEntryViewer;
import pl.jakubwawak.database_engine.entity.BIM_DailyEntry;

import java.util.ArrayList;

/**
 * Object for creating viewer
 */
public class ViewerWindow {

    public Dialog main_dialog;
    VerticalLayout main_layout;

    ArrayList<BIM_DailyEntry> dailyEntries;

    TabSheet tabSheet;

    /**
     * Constructor
     */
    public ViewerWindow(){
        main_dialog = new Dialog();
        main_layout = new VerticalLayout();
        dailyEntries = BearinmindApplication.database.get_user_dailyentries();
        prepare_window();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        tabSheet = new TabSheet();
        for(BIM_DailyEntry dailyEntry : dailyEntries){
            DailyEntryViewer dev = new DailyEntryViewer(dailyEntry);
            tabSheet.add(dailyEntry.entry_day,dev.main_layout);
        }
        tabSheet.setSizeFull();
    }

    /**
     * Function for preparing window
     */
    void prepare_window(){
        // preparing components
        prepare_components();

        // creating layout

        // -- here add your layout
        main_layout.add(tabSheet);

        // styling window
        main_layout.setSizeFull();
        main_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        main_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        main_layout.getStyle().set("text-align", "center");

        main_layout.getStyle().set("border-radius","25px");
        main_layout.getStyle().set("background-color","pink");
        main_layout.getStyle().set("--lumo-font-family","Monospace");
        // adding layout to window
        main_dialog.add(main_layout);
        main_dialog.setWidth("80%");main_dialog.setHeight("80%");
    }
}
