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
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import pl.jakubwawak.bearinmind.BearinmindApplication;
import pl.jakubwawak.bearinmind.website.components.ButtonStyler;
import pl.jakubwawak.database_engine.entity.BIM_DailyEntry;

/**
 * Object for adding today's goal
 */
public class TodaysGoalWindow {

    public Dialog main_dialog;
    VerticalLayout main_layout;

    TextField todaysgoal_field;
    Button set_button;

    BIM_DailyEntry dailyEntry;

    /**
     * Constructor
     */
    public TodaysGoalWindow(BIM_DailyEntry dailyEntry){
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
        todaysgoal_field = new TextField();
        todaysgoal_field.setPlaceholder("?");
        todaysgoal_field.setPrefixComponent(VaadinIcon.STAR.create());
        todaysgoal_field.setWidth("150px");

        set_button = new Button("",this::setbutton_action);
        new ButtonStyler().secondary_button(set_button,"Set Goal",VaadinIcon.STAR,"100%","20%");
    }

    /**
     * Function for preparing window
     */
    void prepare_window(){
        // preparing components
        prepare_components();

        // creating layout

        // -- here add your layout
        main_layout.add(new H6("what do you want to achieve today?"));
        main_layout.add(todaysgoal_field);
        main_layout.add(set_button);

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

    private void setbutton_action(ClickEvent ex){
        dailyEntry.entry_dailygoal = todaysgoal_field.getValue();
        BearinmindApplication.database.updateDailyEntry(dailyEntry);
        main_dialog.close();
    }
}
