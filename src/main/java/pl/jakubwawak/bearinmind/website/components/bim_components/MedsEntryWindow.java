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
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import pl.jakubwawak.bearinmind.BearinmindApplication;
import pl.jakubwawak.database_engine.entity.BIM_DailyEntry;


/**
 * Object for adding med entry
 */
public class MedsEntryWindow {

    public Dialog main_dialog;
    VerticalLayout main_layout;

    TextField name_field, amount_field;
    Button add_button;

    BIM_DailyEntry dailyEntry;

    /**
     * Constructor
     */
    public MedsEntryWindow(BIM_DailyEntry dailyEntry){
        main_dialog = new Dialog();
        this.dailyEntry = dailyEntry;
        main_layout = new VerticalLayout();
        prepare_window();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        name_field = new TextField("");
        name_field.setPlaceholder("medicine name");
        name_field.setWidth("60%");

        amount_field = new TextField("");
        amount_field.setPlaceholder("amount");
        amount_field.setWidth("20%");

        add_button = new Button("", VaadinIcon.PLUS.create(),this::addbutton_action);
        add_button.setWidth("20%");

        add_button.getStyle().set("background-color","#87B38D");
        add_button.getStyle().set("color","#FFFFFF");
    }

    /**
     * Function for preparing window
     */
    void prepare_window(){
        // preparing components
        prepare_components();

        // creating layout

        HorizontalLayout button_layout = new HorizontalLayout();
        button_layout.setSizeFull();
        button_layout.add(name_field,amount_field,add_button);

        // -- here add your layout
        main_layout.add(new H6("New medicine entry"));
        main_layout.add(button_layout);

        // styling window
        main_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        main_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        main_layout.getStyle().set("text-align", "center");

        main_layout.getStyle().set("border-radius","25px");
        main_layout.getStyle().set("background-color","pink");
        main_layout.getStyle().set("--lumo-font-family","Monospace");
        // adding layout to window
        main_dialog.add(main_layout);
    }

    /**
     * add_button action
     * @param ex
     */
    private void addbutton_action(ClickEvent ex){
        if ( !name_field.getValue().isEmpty() && !amount_field.getValue().isEmpty() ){
            String medicine = dailyEntry.entry_dailymeds;
            medicine = medicine + name_field.getValue()+"("+amount_field.getValue()+"),";
            dailyEntry.entry_dailymeds = medicine;
            BearinmindApplication.database.updateDailyEntry(dailyEntry);
        }
        else{
            Notification.show("Empty user enter");
        }
    }
}
