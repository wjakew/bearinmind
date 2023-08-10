/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.Lumo;
import pl.jakubwawak.bearinmind.BearinmindApplication;
import pl.jakubwawak.bearinmind.website.components.ButtonStyler;
import pl.jakubwawak.maintanance.WelcomeMessages;

/**
 * Object for showing home view
 */
@PageTitle("bear_in_mind")
@Route(value = "home")
public class HomeView extends VerticalLayout {

    Button options_button;
    H3 progress_label;

    /**
     * Constructor
     */
    public HomeView(){
        this.getElement().setAttribute("theme", Lumo.DARK);
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
        options_button = new Button();
        options_button = new ButtonStyler().simple_button(options_button,"Options", VaadinIcon.ARCHIVE,"125px","50px");
        progress_label = new H3("0%");
    }

    /**
     * Function for preparing view and components
     */
    void prepare_view(){
        if (BearinmindApplication.logged_user != null){
            prepare_components();
            // prepare window layout and components
            FlexLayout center_layout = new FlexLayout();
            center_layout.setSizeFull();
            center_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            center_layout.setAlignItems(FlexComponent.Alignment.CENTER);
            center_layout.add(new H3(new WelcomeMessages().getWelcomeMessage(BearinmindApplication.logged_user.bim_user_name)));

            FlexLayout left_layout = new FlexLayout();
            left_layout.setSizeFull();
            left_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
            left_layout.setAlignItems(FlexComponent.Alignment.START);
            left_layout.add(options_button);

            FlexLayout right_layout = new FlexLayout();
            right_layout.setSizeFull();
            right_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
            right_layout.setAlignItems(FlexComponent.Alignment.END);

            right_layout.add(progress_label);

            HorizontalLayout header = new HorizontalLayout(left_layout,center_layout,right_layout);
            header.setWidth("100%");
            header.setMargin(true);
            header.setAlignItems(Alignment.CENTER);
            add(header);

        }
        else{
            // user not logged
            Notification.show("User not logged!");
            getUI().ifPresent(ui -> ui.navigate("/welcome"));
        }
    }

}
