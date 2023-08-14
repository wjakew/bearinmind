/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Object for showing window components
 */
public class TemplateWindow {

    public Dialog main_dialog;
    VerticalLayout main_layout;

    /**
     * Constructor
     */
    public TemplateWindow(){
        main_dialog = new Dialog();
        main_layout = new VerticalLayout();
        prepare_window();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){

    }

    /**
     * Function for preparing window
     */
    void prepare_window(){
        // preparing components
        prepare_components();

        // creating layout

        // -- here add your layout



        // styling window
        main_layout.setSizeFull();
        main_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        main_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        main_layout.getStyle().set("text-align", "center");

        main_layout.getStyle().set("border-radius","25px");
        main_layout.getStyle().set("background-color","pink");

        // adding layout to window
        main_dialog.add(main_layout);
    }
}
