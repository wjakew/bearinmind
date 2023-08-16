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
import pl.jakubwawak.database_engine.entity.BIM_DailyEntry;
import pl.jakubwawak.maintanance.GridElement;

import java.util.ArrayList;

/**
 * Object for showing window components
 */
public class FoodEntryWindow {

    public Dialog main_dialog;
    VerticalLayout main_layout;

    BIM_DailyEntry dailyEntry;

    Grid<GridElement> grid;
    ArrayList<GridElement> content;

    /**
     * Constructor
     */
    public FoodEntryWindow(BIM_DailyEntry dailyEntry){
        this.dailyEntry = dailyEntry;
        main_dialog = new Dialog();
        main_layout = new VerticalLayout();

        main_dialog.setModal(true);
        main_dialog.setResizable(true);

        prepare_window();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        grid = new Grid<>(GridElement.class,false);
        content =  new ArrayList<>();
        for(String food_entry : dailyEntry.entry_food){
            content.add(new GridElement(food_entry));
        }
        grid.addColumn(GridElement::getGridelement_text).setHeader("Food Entries");
        grid.setItems(content);

        grid.getStyle().set("border-radius","25px");
        grid.getStyle().set("background-color","pink");
        grid.setSizeFull(); grid.setWidth("250px"); grid.setHeight("100px");

    }

    /**
     * Function for preparing window
     */
    void prepare_window(){
        // preparing components
        prepare_components();

        // creating layout

        // -- here add your layout
        main_layout.add(new H6("What did you eat today?"));
        main_layout.add(grid);


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
    }
}
