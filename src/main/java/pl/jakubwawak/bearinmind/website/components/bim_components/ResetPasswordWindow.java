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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import pl.jakubwawak.bearinmind.BearinmindApplication;
import pl.jakubwawak.bearinmind.website.components.ButtonStyler;
import pl.jakubwawak.maintanance.Password_Validator;

/**
 * Object for showing window components
 */
public class ResetPasswordWindow {

    public Dialog main_dialog;
    VerticalLayout main_layout;

    PasswordField oldpassword_field, newpassword_field,confirmpassword_field;

    Button changepassword_button;

    /**
     * Constructor
     */
    public ResetPasswordWindow(){
        main_dialog = new Dialog();
        main_layout = new VerticalLayout();
        prepare_window();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        oldpassword_field = new PasswordField();
        oldpassword_field.setPlaceholder("Current Password");

        newpassword_field = new PasswordField();
        newpassword_field.setPlaceholder("New Password");

        confirmpassword_field = new PasswordField();
        confirmpassword_field.setPlaceholder("Confirm Password");

        changepassword_button = new Button("Change Password",this::changepasswordbutton_action);
        changepassword_button = new ButtonStyler().third_button(changepassword_button,"Change Password", VaadinIcon.USER,"250px","75px");

        oldpassword_field.setWidth("100%");newpassword_field.setWidth("100%");confirmpassword_field.setWidth("100%");


    }

    /**
     * Function for preparing window
     */
    void prepare_window(){
        // preparing components
        prepare_components();

        // creating layout

        // -- here add your layout
        main_layout.add(new H6("Change Password"));
        main_layout.add(oldpassword_field);
        main_layout.add(newpassword_field);
        main_layout.add(confirmpassword_field);
        main_layout.add(changepassword_button);

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
        main_dialog.setWidth("400px"); main_dialog.setHeight("400px");
    }

    /**
     * Function for checking old password
     * @param oldPassword
     * @return boolean
     */
    boolean validateOldPassword(String oldPassword){
        try{
            Password_Validator pv = new Password_Validator(oldPassword);
            return BearinmindApplication.logged_user.bim_user_password.equals(pv.hash());
        }catch(Exception ex){
            Notification.show(ex.toString());
            return false;
        }
    }

    /**
     * Function for comparing new passwords from input
     * @param newPassword1
     * @param newPassword2
     * @return boolean
     */
    boolean compareNewPassword(String newPassword1, String newPassword2){
        return newPassword1.equals(newPassword2);
    }

    /**
     * changepassword_button action
     * @param ex
     */
    private void changepasswordbutton_action(ClickEvent ex){
        if ( validateOldPassword(oldpassword_field.getValue())){
            if ( compareNewPassword(newpassword_field.getValue(),confirmpassword_field.getValue())){
                // password correct
                try {
                    Password_Validator pv = new Password_Validator(newpassword_field.getValue());
                    int ans = BearinmindApplication.database.updatePassword(pv.hash());
                    if (ans > 0){
                        Notification.show("Password changed!");
                        main_dialog.close();
                    }
                }catch (Exception e){
                    Notification.show(e.toString());
                }
            }
            else{
                // passwords not match
                Notification.show("Passwords didnt match!");
                newpassword_field.setValue("");
                confirmpassword_field.setValue("");
            }
        }
        else{
            Notification.show("Wrong password validation!");
            oldpassword_field.setValue("");
            newpassword_field.setValue("");
            confirmpassword_field.setValue("");
        }
    }
}
