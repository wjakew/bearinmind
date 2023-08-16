/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;

/**
 * Object for styling buttons
 */
public class ButtonStyler {

    /**
     * Function for creating simple button
     * @param label
     * @param icon
     * @param width
     * @param height
     * @return Button
     */
    public Button simple_button(Button button,String label, VaadinIcon icon, String width, String height){
        // button creation
        button.setText(label);
        button.setIcon(icon.create());
        button.setSizeFull();button.setWidth(width);button.setHeight(height);
        //button style
        button.getStyle().set("background-color","pink");
        button.getStyle().set("color","#FFFFFF");
        return button;
    }

    public Button secondary_button(Button button,String label, VaadinIcon icon, String width, String height){
        // button creation
        button.setText(label);
        button.setIcon(icon.create());
        button.setSizeFull();button.setWidth(width);button.setHeight(height);
        //button style
        button.getStyle().set("background-color","#B5EAD7");
        button.getStyle().set("color","#000000");
        return button;
    }
}
