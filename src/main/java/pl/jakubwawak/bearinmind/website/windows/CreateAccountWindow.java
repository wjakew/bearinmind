/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website.windows;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Object for creating window for creating account
 */
public class CreateAccountWindow {

    public Dialog main_dialog;
    FormLayout main_layout;

    TextField firstName, lastName,username;
    PasswordField password, confirmPassword;
    Button create_button;

    /**
     * Constructor
     */
    public CreateAccountWindow(){
        main_dialog = new Dialog();
        main_layout = new FormLayout();
        create_button = new Button("Create", VaadinIcon.PLUS.create());
        create_button.addThemeVariants(ButtonVariant.LUMO_SUCCESS,ButtonVariant.LUMO_PRIMARY);
        prepare_window();
    }

    /**
     * Function for preparing window components
     */
    void prepare_window(){
        firstName = new TextField("First name");
        lastName = new TextField("Last name");
        username = new TextField("Username");
        password = new PasswordField("Password");
        confirmPassword = new PasswordField("Confirm password");

        FormLayout formLayout = new FormLayout();
        formLayout.add(firstName, lastName, username, password,
                confirmPassword);
        formLayout.setResponsiveSteps(
                // Use one column by default
                new FormLayout.ResponsiveStep("0", 1),
                // Use two columns, if layout's width exceeds 500px
                new FormLayout.ResponsiveStep("500px", 2));
        // Stretch the username field over 2 columns
        formLayout.setColspan(username, 2);
        main_layout = formLayout;
        main_dialog.add(new H1("Create Account"));
        main_dialog.add(main_layout);
        main_dialog.add(create_button);

    }
}
