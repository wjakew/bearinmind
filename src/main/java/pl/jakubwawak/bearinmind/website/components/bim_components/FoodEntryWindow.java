/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website.components.bim_components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import pl.jakubwawak.bearinmind.BearinmindApplication;
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

    TextField foodName_field, foodkcal_field;
    Button add_button;

    /**
     * Constructor
     */
    public FoodEntryWindow(BIM_DailyEntry dailyEntry){
        this.dailyEntry = dailyEntry;
        main_dialog = new Dialog();
        main_layout = new VerticalLayout();

        main_dialog.setModal(true);

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
        grid.setWidth("500px"); grid.setHeight("400px");

        foodName_field = new TextField();
        foodName_field.setPlaceholder("Food Name");
        foodName_field.setPrefixComponent(VaadinIcon.COFFEE.create());
        foodName_field.setWidth("50%");

        foodkcal_field = new TextField();
        foodkcal_field.setPlaceholder("kcal");
        foodkcal_field.setPrefixComponent(VaadinIcon.CALC.create());
        foodkcal_field.setWidth("30%");

        add_button = new Button("",VaadinIcon.PLUS.create(),this::addbutton_action);
        add_button.setWidth("20%");
        add_button.addThemeVariants(ButtonVariant.LUMO_SUCCESS,ButtonVariant.LUMO_PRIMARY);

    }

    /**
     * Function for preparing window
     */
    void prepare_window(){
        // preparing components
        prepare_components();

        // creating layout
        HorizontalLayout hl_buttons =new HorizontalLayout();
        hl_buttons.add(foodName_field,foodkcal_field,add_button);
        //hl_buttons.setSizeFull();

        // -- here add your layout
        main_layout.add(new H6("What did you eat today?"));
        main_layout.add(hl_buttons);
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

    /**
     * add_button action
     * @param ex
     */
    private void addbutton_action(ClickEvent ex){
        if ( !foodName_field.getValue().equals("")&&!foodkcal_field.getValue().equals("")){
            // add record
            dailyEntry.entry_food.add(foodName_field.getValue()+"("+foodkcal_field.getValue()+"kcal)");
        }
        content.clear();
        for(String food_entry : dailyEntry.entry_food){
            content.add(new GridElement(food_entry));
        }
        grid.getDataProvider().refreshAll();
        BearinmindApplication.database.updateDailyEntry(this.dailyEntry);
        foodName_field.setValue(""); foodkcal_field.setValue("");
    }
}
