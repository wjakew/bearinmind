/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.Lumo;

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
        prepare_components();
    }

}
