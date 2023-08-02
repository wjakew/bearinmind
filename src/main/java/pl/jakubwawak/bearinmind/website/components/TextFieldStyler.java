/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website.components;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Object for styling text fields
 */
public class TextFieldStyler {

    /**
     * Function for styling field as simple field
     * @param field
     * @param placeholder
     * @param icon
     * @param width
     * @param height
     * @return TextField
     */
    public TextField simple_field(TextField field, String placeholder, VaadinIcon icon, String width,String height){
        field.setPlaceholder(placeholder);
        field.setPrefixComponent(icon.create());
        field.setWidth(width);field.setHeight(height);

        //field.getStyle().set("background-image","linear-gradient(pink, white)");
        field.getStyle().set("color","#000000");
        return field;
    }
}
