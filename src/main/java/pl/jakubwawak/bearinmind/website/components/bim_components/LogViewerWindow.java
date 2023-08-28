/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website.components.bim_components;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import pl.jakubwawak.bearinmind.BearinmindApplication;
import pl.jakubwawak.maintanance.GridElement;

/**
 * Object for showing window components
 */
public class LogViewerWindow {

    public Dialog main_dialog;
    VerticalLayout main_layout;

    Grid<GridElement> log_grid;

    /**
     * Constructor
     */
    public LogViewerWindow(){
        main_dialog = new Dialog();
        main_layout = new VerticalLayout();
        prepare_window();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        log_grid = new Grid(GridElement.class, false);
        log_grid.addColumn(GridElement::getGridelement_details2).setHeader("LOG CODE");
        log_grid.addColumn(GridElement::getGridelement_text);
        log_grid.setItems(BearinmindApplication.database.get_application_log());
        log_grid.setSizeFull();
    }

    /**
     * Function for preparing window
     */
    void prepare_window(){
        // preparing components
        prepare_components();

        // creating layout

        // -- here add your layout
        main_layout.add(new H6("Log Messages"));
        main_layout.add(log_grid);

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
        main_dialog.setWidth("800px");main_dialog.setHeight("550px");
    }
}
