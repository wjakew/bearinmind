/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.Lumo;
import pl.jakubwawak.bearinmind.BearinmindApplication;

/**
 * Object for showing welcome view
 */
@PageTitle("Template bear_in_mind")
@Route(value = "template")
public class TemplateView extends VerticalLayout {


    /**
     * Constructor
     */
    public TemplateView(){
        this.getElement().setAttribute("theme", Lumo.DARK);
        prepare_view();

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
        getStyle().set("background-color","#000000");
        getStyle().set("--lumo-font-family","Monospace");
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){

    }

    /**
     * Function for preparing view and components
     */
    void prepare_view(){
        if (BearinmindApplication.logged_user != null){
            prepare_components();
            // prepare window components


        }
        else{
            // user not logged
            Notification.show("User not logged!");
            getUI().ifPresent(ui -> ui.navigate("/welcome"));
        }
    }

}
