/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website.components.bim_components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import pl.jakubwawak.bearinmind.BearinmindApplication;
import pl.jakubwawak.database_engine.entity.BIM_DailyEntry;
import pl.jakubwawak.maintanance.GridElement;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Object for showing window components
 */
public class FeelingsEntryWindow {

    public Dialog main_dialog;
    VerticalLayout main_layout;

    ArrayList<String> emotions_collection;

    BIM_DailyEntry dailyEntry;

    MultiSelectComboBox<GridElement> emotions_combobox;

    TextArea emotions_area;

    /**
     * Constructor
     */
    public FeelingsEntryWindow(BIM_DailyEntry dailyEntry){
        main_dialog = new Dialog();
        main_layout = new VerticalLayout();
        this.dailyEntry = dailyEntry;
        emotions_collection = new ArrayList<>();

        emotions_collection.add("Excited");
        emotions_collection.add("Happy");
        emotions_collection.add("Sad");
        emotions_collection.add("Disappointed");
        emotions_collection.add("Calm");
        emotions_collection.add("Anxious");
        emotions_collection.add("Jealous");
        emotions_collection.add("Energetic");
        emotions_collection.add("Loved");
        emotions_collection.add("Creative");
        emotions_collection.add("Alone");
        emotions_collection.add("Cranky");
        emotions_collection.add("Frustrated");
        emotions_collection.add("Lost");
        emotions_collection.add("Tired");
        emotions_collection.add("Grateful");

        prepare_window();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        emotions_combobox = new MultiSelectComboBox();
        ArrayList<GridElement> data = new ArrayList<>();
        for(String emotion : emotions_collection){
            data.add(new GridElement(emotion));
        }
        emotions_combobox.setItems(data);
        emotions_combobox.setItemLabelGenerator(GridElement::getGridelement_text);
        emotions_combobox.setWidth("100%");
        emotions_combobox.setHeight("20%");

        emotions_area = new TextArea();
        emotions_area.setPlaceholder("Your feelings and emotions...");
        emotions_area.setWidth("100%");
        emotions_area.setHeight("80%");
        emotions_area.setReadOnly(true);

        emotions_combobox.addValueChangeListener(e -> {
            String selectedEmotions = e.getValue().stream()
                    .map(GridElement::getGridelement_text).collect(Collectors.joining(", "));
            emotions_area.setValue(selectedEmotions);
        });

    }

    /**
     * Function for preparing window
     */
    void prepare_window(){
        // preparing components
        prepare_components();

        // creating layout

        // -- here add your layout
        main_layout.add(new H6("How do you feel today?"));
        main_layout.add(emotions_area);
        main_layout.add(emotions_combobox);
        // styling window
        main_layout.setSizeFull();
        main_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        main_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        main_layout.getStyle().set("text-align", "center");

        main_layout.getStyle().set("border-radius","25px");
        main_layout.getStyle().set("background-color","#87B38D");
        main_layout.getStyle().set("--lumo-font-family","Monospace");
        // adding layout to window
        main_dialog.add(main_layout);
        main_dialog.setHeight("500px"); main_dialog.setWidth("500px");

        main_dialog.addDialogCloseActionListener(e -> {
            dailyEntry.entry_emotionlvl = emotions_area.getValue();
            BearinmindApplication.database.updateDailyEntry(dailyEntry);
            main_dialog.close();
        });
    }
}
