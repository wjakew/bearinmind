/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
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
        setstring_button = new Button();
        connectionstring_field = new TextField();

        login_button = new ButtonStyler().simple_button(login_button,"Login", VaadinIcon.KEY,"10rem","5rem");
        setstring_button = new ButtonStyler().simple_button(setstring_button,"",VaadinIcon.PENCIL,"75px","75px");
        connectionstring_field = new TextFieldStyler().simple_field(connectionstring_field,"Connection String...",VaadinIcon.PENCIL,"200px","75px");

    }

    /**
     * Function for preparing view and components
     */
    void prepare_view(){
        prepare_components();

        StreamResource res = new StreamResource("bearinmind_icon.png", () -> {
            return WelcomeView.class.getClassLoader().getResourceAsStream("images/bearinmind_icon.png");
        });

        Image logo = new Image(res,"bear_in_mind logo");
        logo.setHeight("512px");
        logo.setWidth("512px");

        add(new H6("bear_in_mind"));
        add(logo);

        if (BearinmindApplication.database.connected){
            // login screen
            add(login_button);
        }
        else{
            // insert connection string
            HorizontalLayout main_layout = new HorizontalLayout(connectionstring_field,setstring_button);
            main_layout.setSizeFull();
            add(main_layout);
        }
    }

}
