/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website.windows;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.StreamResource;
import pl.jakubwawak.bearinmind.BearinmindApplication;
import pl.jakubwawak.bearinmind.website.components.TextFieldStyler;
import pl.jakubwawak.bearinmind.website.views.WelcomeView;
import pl.jakubwawak.maintanance.Password_Validator;


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
        new TextFieldStyler().simple_field(login_field,"user email", VaadinIcon.PENCIL,"350px","75px");

        password_field = new PasswordField();
        password_field.setPrefixComponent(VaadinIcon.LOCK.create());
        password_field.setPlaceholder("password...");
        password_field.setWidth("350px");password_field.setHeight("75px");

        action_button = new Button("",VaadinIcon.ARROW_CIRCLE_RIGHT_O.create(),this::actionbutton_action);
        action_button.getStyle().set("background-color","pink");
        action_button.getStyle().set("color","#FFFFFF");

        prepare_window();
    }

    /**
     * Function for preparing window
     */
    void prepare_window(){
        StreamResource res = new StreamResource("bearinmind_icon.png", () -> {
            return WelcomeView.class.getClassLoader().getResourceAsStream("images/bearinmind_icon.png");
        });

        Image logo = new Image(res,"bear_in_mind logo");
        logo.setHeight("128px");
        logo.setWidth("128px");

        main_layout.add(logo);
        H6 nameheader = new H6("by Jakub Wawak");
        main_layout.add(nameheader);
        main_layout.add(login_field);
        main_layout.add(password_field);
        main_layout.add(action_button);

        password_field.setVisible(false);

        main_layout.setSizeFull();
        main_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        main_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        main_layout.getStyle().set("text-align", "center");

        main_layout.getStyle().set("border-radius","25px");
        main_layout.getStyle().set("background-color","pink");
        main_layout.getStyle().set("--lumo-font-family","Monospace");

        main_dialog.add(main_layout);

        login_field.addKeyPressListener(Key.ENTER, e->{
            keyboard_action_login();
        });

        password_field.addKeyPressListener(Key.ENTER, e->{
            keyboard_action_login();
        });
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
                try{
                    Password_Validator pv = new Password_Validator(password_field.getValue());
                    int ans = BearinmindApplication.database.login_user(login_field.getValue(),pv.hash());
                    if ( ans == 1 ){
                        Notification.show("Welcome back "+BearinmindApplication.logged_user.bim_user_login+"!");
                        // open home page
                        action_button.getUI().ifPresent(ui ->
                                ui.navigate("/home"));
                    }
                    else{
                        Notification.show("Cannot find user with given credentials! code("+ans+")");
                    }
                }catch(Exception e){System.out.println(e.toString());}
            }
        }
    }

    /**
     * Function for triggering when enter is pressed
     */
    private void keyboard_action_login(){
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
                try{
                    Password_Validator pv = new Password_Validator(password_field.getValue());
                    int ans = BearinmindApplication.database.login_user(login_field.getValue(),pv.hash());
                    if ( ans == 1 ){
                        Notification.show("Welcome back "+BearinmindApplication.logged_user.bim_user_login+"!");
                        // open home page
                        action_button.getUI().ifPresent(ui ->
                                ui.navigate("/home"));
                    }
                    else{
                        Notification.show("Cannot find user with given credentials! code("+ans+")");
                    }
                }catch(Exception e){System.out.println(e.toString());}
            }
        }
    }

}
