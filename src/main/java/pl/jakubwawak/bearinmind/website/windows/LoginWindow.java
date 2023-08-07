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
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import pl.jakubwawak.bearinmind.BearinmindApplication;
import pl.jakubwawak.bearinmind.website.components.TextFieldStyler;


/**
 * Object for creating window for log in user to the app
 */
public class LoginWindow {

    public Dialog main_dialog;
    VerticalLayout main_layout;

    TextField login_field;
    PasswordField password_field;

    Button action_button;


    /**
     * Constructor
     */
    public LoginWindow(){
        main_dialog = new Dialog();
        main_layout = new VerticalLayout();

        login_field = new TextField();
        new TextFieldStyler().simple_field(login_field,"user email", VaadinIcon.PENCIL,"200px","75px");

        password_field = new PasswordField();
        password_field.setPrefixComponent(VaadinIcon.LOCK.create());
        password_field.setPlaceholder("password...");
        password_field.setHeight("200px");password_field.setHeight("75px");

        action_button = new Button("",VaadinIcon.ARROW_CIRCLE_RIGHT_O.create(),this::actionbutton_action);
        action_button.addThemeVariants(ButtonVariant.LUMO_SUCCESS,ButtonVariant.LUMO_PRIMARY);

        prepare_window();
    }

    /**
     * Function for preparing window
     */
    void prepare_window(){
        main_layout.add(VaadinIcon.LOCK.create());
        main_layout.add(new H6("bear_in_mind"));
        main_layout.add(login_field);
        main_layout.add(password_field);
        main_layout.add(action_button);

        password_field.setVisible(false);

        main_layout.setSizeFull();
        main_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        main_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        main_layout.getStyle().set("text-align", "center");

        main_dialog.add(main_layout);
    }

    /**
     * action_button function
     * @param ex
     */
    private void actionbutton_action(ClickEvent ex){
        if ( action_button.getText().equals("") ){
            if ( !login_field.getValue().equals("") ){
                password_field.setVisible(true);
                action_button.setText("Login");
            }
            else{
                Notification.show("Login field is empty!");
            }
        }
        else {
            if (password_field.getValue().equals("")){
                //password field is empty
                password_field.setVisible(false);
                action_button.setText("");
            }
            else{
                //password field is not empty - try to login!
                BearinmindApplication.database.login_user(login_field.getValue(),password_field.getValue());
                if ( BearinmindApplication.logged_user != null ){
                    Notification.show("Welcome back "+BearinmindApplication.logged_user.bim_user_login+"!");
                    // open home page
                }
            }
        }
    }
}
