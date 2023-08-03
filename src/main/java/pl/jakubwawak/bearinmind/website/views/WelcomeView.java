/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.component.html.Image;
import pl.jakubwawak.bearinmind.BearinmindApplication;
import pl.jakubwawak.bearinmind.website.components.ButtonStyler;
import pl.jakubwawak.bearinmind.website.components.TextFieldStyler;

/**
 * Object for showing welcome view
 */
@PageTitle("bear_in_mind")
@Route(value = "/welcome")
@RouteAlias(value = "/")
public class WelcomeView extends VerticalLayout {

    Button login_button, setstring_button;
    TextField connectionstring_field;


    /**
     * Constructor
     */
    public WelcomeView(){
        this.getElement().setAttribute("theme", Lumo.LIGHT);
        prepare_view();

        setSizeFull();
        setSpacing(true);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
        getStyle().set("background-image","linear-gradient(pink, white)");
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){

        login_button = new Button();
        setstring_button = new Button("",this::setstringbutton_action);
        connectionstring_field = new TextField();

        login_button = new ButtonStyler().simple_button(login_button,"Login", VaadinIcon.KEY,"150px","50px");
        setstring_button = new ButtonStyler().simple_button(setstring_button,"Connect to Database!",VaadinIcon.PENCIL,"512px","75px");
        connectionstring_field = new TextFieldStyler().simple_field(connectionstring_field,"Connection String...",VaadinIcon.PENCIL,"512px","75px");

    }

    /**
     * Function for preparing view and components
     */
    void prepare_view(){
        prepare_components();

        if (BearinmindApplication.database.connected){
            // login screen
            prepare_loginview();
        }
        else{
            // insert connection string
            prepare_connectionview();
        }
    }

    /**
     * Function for showing login view
     */
    void prepare_loginview(){
        this.removeAll();
        StreamResource res = new StreamResource("bearinmind_icon.png", () -> {
            return WelcomeView.class.getClassLoader().getResourceAsStream("images/bearinmind_icon.png");
        });

        Image logo = new Image(res,"bear_in_mind logo");
        logo.setHeight("512px");
        logo.setWidth("512px");

        add(new H6("bear_in_mind"));
        add(logo);
        add(login_button);
    }

    /**
     * Function for showing connection view
     */
    void prepare_connectionview(){
        StreamResource res = new StreamResource("bearinmind_icon.png", () -> {
            return WelcomeView.class.getClassLoader().getResourceAsStream("images/bearinmind_icon.png");
        });

        Image logo = new Image(res,"bear_in_mind logo");
        logo.setHeight("512px");
        logo.setWidth("512px");

        add(new H6("bear_in_mind"));
        add(logo);

        // insert connection string
        VerticalLayout vl = new VerticalLayout(connectionstring_field,setstring_button);
        vl.setJustifyContentMode(JustifyContentMode.CENTER);
        vl.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        vl.getStyle().set("text-align", "center");

        add(vl);
    }

    /**
     * setstring_button action
     * @param ex
     */
    private void setstringbutton_action(ClickEvent ex){
        if ( !connectionstring_field.getValue().equals("") ){
            BearinmindApplication.database.database_url = connectionstring_field.getValue();
            BearinmindApplication.database.connect();
            if ( BearinmindApplication.database.connected ){
                // set to login screen
                prepare_loginview();
                Notification.show("Connected to database!");
            }
            else{
                // connection failed
                Notification.show("Connection error!");
            }
        }
        else{
            Notification.show("Connection string is empty!");
        }
    }

    /**
     * login_button action
     * @param ex
     */
    private void loginbutton_action(ClickEvent ex){

    }

}
