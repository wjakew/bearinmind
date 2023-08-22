/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website.components.bim_components;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import pl.jakubwawak.bearinmind.BearinmindApplication;
import pl.jakubwawak.database_engine.entity.BIM_DailyEntry;

/**
 * Object for showing window components
 */
public class StressEntryWindow {

    public Dialog main_dialog;
    VerticalLayout main_layout;

    IntegerField stress_field;
    BIM_DailyEntry dailyEntry;

    /**
     * Constructor
     */
    public StressEntryWindow(BIM_DailyEntry dailyEntry){
        main_dialog = new Dialog();
        main_layout = new VerticalLayout();
        this.dailyEntry = dailyEntry;
        prepare_window();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        stress_field = new IntegerField();
        stress_field.setStepButtonsVisible(true);
        stress_field.setMin(0);
        try{
            stress_field.setValue(Integer.parseInt(dailyEntry.entry_fearlvl));
        }catch (Exception ex){
            stress_field.setValue(0);
        }
        stress_field.setMax(5);
        stress_field.getStyle().set("font-size","30px");
    }

    /**
     * Function for preparing window
     */
    void prepare_window(){
        // preparing components
        prepare_components();

        // creating layout

        // -- here add your layout
        main_layout.add(new H6("How much did you stress today?"));
        main_layout.add(stress_field);

        // styling window
        main_layout.setSizeFull();
        main_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        main_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        main_layout.getStyle().set("text-align", "center");

        main_layout.getStyle().set("border-radius","25px");
        main_layout.getStyle().set("background-color","#B5EAD7");
        main_layout.getStyle().set("--lumo-font-family","Monospace");
        // adding layout to window
        main_dialog.add(main_layout);
        main_dialog.setWidth("550px");main_dialog.setHeight("550px");

        main_dialog.addDialogCloseActionListener(e -> {
            dailyEntry.entry_fearlvl = Integer.toString(stress_field.getValue());
            BearinmindApplication.database.updateDailyEntry(dailyEntry);
            main_dialog.close();
        });
    }
}
