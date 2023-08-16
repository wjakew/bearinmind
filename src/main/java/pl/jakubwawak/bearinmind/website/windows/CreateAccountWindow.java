/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website.windows;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.security.core.parameters.P;
import pl.jakubwawak.bearinmind.BearinmindApplication;
import pl.jakubwawak.database_engine.entity.BIM_User;
import pl.jakubwawak.maintanance.Password_Validator;

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
        create_button = new Button("Create", VaadinIcon.PLUS.create(),this::createbutton_action);
        create_button.addThemeVariants(ButtonVariant.LUMO_SUCCESS,ButtonVariant.LUMO_PRIMARY);
        prepare_window();
    }

    /**
     * Function for preparing window components
     */
    void prepare_window(){
        firstName = new TextField("First name");
        lastName = new TextField("Last name");
        username = new TextField("E-Mail");
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
        main_layout.getStyle().set("--lumo-font-family","Monospace");
        main_dialog.add(new H1("Create Account"));
        main_dialog.add(main_layout);
        main_dialog.add(create_button);
    }

    /**
     * Function for validating fields
     * @return boolean
     */
    private boolean validate_fields(){
        return !firstName.getValue().equals("") && !lastName.getValue().equals("") && !username.getValue().equals("") &&
                !password.getValue().equals("") && !confirmPassword.getValue().equals("");
    }

    /**
     * Function for validating length
     * @return boolean
     */
    private boolean validate_length(){
        return firstName.getValue().length() < 30 && lastName.getValue().length() < 30 && username.getValue().length() < 50 &&
                password.getValue().length() < 30 && confirmPassword.getValue().length() < 30;
    }

    /**
     * Function for validating passwords
     * @return String
     */
    private boolean validate_passwords(){
        return password.getValue().equals(confirmPassword.getValue());
    }

    /**
     * create_button action
     * @param ex
     */
    private void createbutton_action(ClickEvent ex){
        if ( validate_fields() ){
            if ( validate_length() ){
                if (validate_passwords()){
                    // fields correct - check email and add user
                    BIM_User user = BearinmindApplication.database.get_user(username.getValue());
                    if ( user != null ){
                        Notification.show("User "+username.getValue()+" exists!");
                    }
                    else{
                        // fields correct and user didnt exists
                        BIM_User add = new BIM_User();
                        add.bim_user_mail = username.getValue();
                        try{
                            if ( add.bim_user_mail.contains("@") ){
                                Password_Validator pv = new Password_Validator(password.getValue());
                                add.bim_user_password = pv.hash();
                                add.bim_user_name = firstName.getValue();
                                add.bim_user_surname = lastName.getValue();
                                add.bim_user_login=username.getValue().split("@")[0];
                                int ans = BearinmindApplication.database.insert_user(add);
                                if ( ans == 1 ){
                                    Notification.show("User created!");
                                    main_dialog.close();
                                }
                                else{
                                    Notification.show("Failed to create user, check application log!");
                                }
                            }
                            else{
                                Notification.show("E-Mail address is wrong!");
                            }

                        }catch(Exception e){
                            Notification.show("Error ("+e.toString()+")");
                        }
                    }
                }
                else{
                    Notification.show("Passwords didn't match!");
                }
            }
            else{
                Notification.show("Wrong input lenght!");
            }
        }
        else{
            Notification.show("Wrong user input!");
        }
    }
}
