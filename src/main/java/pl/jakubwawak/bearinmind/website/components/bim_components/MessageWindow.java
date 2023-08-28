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
import com.vaadin.flow.component.textfield.TextArea;


/**
 * Object for showing window components
 */
public class MessageWindow {

    public Dialog main_dialog;
    VerticalLayout main_layout;
    String messageText;

    TextArea messageArea;

    /**
     * Constructor
     */
    public MessageWindow(String messageText){
        main_dialog = new Dialog();
        main_layout = new VerticalLayout();
        this.messageText = messageText;
        prepare_window();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        messageArea = new TextArea();
        messageArea.setSizeFull();
        messageArea.setReadOnly(true);
        messageArea.setValue(messageText);
    }

    /**
     * Function for preparing window
     */
    void prepare_window(){
        // preparing components
        prepare_components();

        // creating layout

        // -- here add your layout
        main_layout.add(new H6("BearInMind Message"));
        main_layout.add(messageArea);

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
        main_dialog.setWidth("450px");
        main_dialog.setHeight("250px");
    }
}
