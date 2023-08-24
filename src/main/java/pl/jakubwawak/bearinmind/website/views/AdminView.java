/**
 * by Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package pl.jakubwawak.bearinmind.website.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.security.core.parameters.P;
import pl.jakubwawak.bearinmind.BearinmindApplication;
import pl.jakubwawak.bearinmind.website.components.ButtonStyler;
import pl.jakubwawak.maintanance.GridElement;
import pl.jakubwawak.maintanance.WelcomeMessages;

/**
 * Object for showing admin view
 */
@PageTitle("Administration")
@Route(value = "admin-panel")
public class AdminView extends VerticalLayout {

    Button goback_button;
    PasswordField adminpassword_field;
    Button unlock_button;

    Grid<GridElement> userGrid;
    Button removeuser_button, adduser_button;
    Button disableaccount_button, generateraport_button, resetpassword_button, logviewer_button;

    /**
     * Constructor
     */
    public AdminView(){
        this.getElement().setAttribute("theme", Lumo.DARK);
        prepare_view();

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
        getStyle().set("background-image","linear-gradient(#FFC0CB, #FFD4C0)");
        getStyle().set("--lumo-font-family","Monospace");
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){

        goback_button = new Button("",this::gobackbutton_action);
        goback_button = new ButtonStyler().simple_button(goback_button,"", VaadinIcon.ARROW_CIRCLE_LEFT_O,"125px","50px");

        // creating header
        // prepare window layout and components
        FlexLayout center_layout = new FlexLayout();
        center_layout.setSizeFull();
        center_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        center_layout.setAlignItems(FlexComponent.Alignment.CENTER);
        center_layout.add(new H3("Admin Panel"));

        FlexLayout left_layout = new FlexLayout();
        left_layout.setSizeFull();
        left_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        left_layout.setAlignItems(FlexComponent.Alignment.START);
        left_layout.add(goback_button);

        FlexLayout right_layout = new FlexLayout();
        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        right_layout.setAlignItems(FlexComponent.Alignment.END);

        HorizontalLayout header = new HorizontalLayout(left_layout,center_layout,right_layout);
        header.setWidth("100%");
        header.setMargin(true);
        header.setAlignItems(Alignment.CENTER);
        add(header);

        adminpassword_field = new PasswordField();
        adminpassword_field.setPlaceholder("admin password");
        adminpassword_field.setPrefixComponent(VaadinIcon.LOCK.create());
        adminpassword_field.setWidth("25%");

        unlock_button = new Button();
        unlock_button = new ButtonStyler().simple_button(unlock_button,"Unlock",VaadinIcon.LOCK,"125px","50px");

        disableaccount_button = new Button();
        disableaccount_button = new ButtonStyler().simple_button(disableaccount_button,"Disable Account Creation",VaadinIcon.STAR,"100%","50px");

        generateraport_button = new Button();
        generateraport_button = new ButtonStyler().simple_button(generateraport_button,"Generate Raport",VaadinIcon.STAR,"100%","50px");

        resetpassword_button = new Button();
        resetpassword_button = new ButtonStyler().simple_button(resetpassword_button,"Reset Password",VaadinIcon.STAR,"100%","50px");

        logviewer_button = new Button();
        logviewer_button = new ButtonStyler().simple_button(logviewer_button,"Show Application Log",VaadinIcon.STAR ,"100%","50px");

    }

    /**
     * Function for preparing view and components
     */
    void prepare_view(){
        if (BearinmindApplication.logged_user != null){
            prepare_components();
            // prepare window components
            HorizontalLayout passwordLayout = new HorizontalLayout(adminpassword_field,unlock_button);
            passwordLayout.setMargin(true);
            passwordLayout.setAlignItems(Alignment.CENTER);
            passwordLayout.setWidth("80%");
            add(passwordLayout);
            add(prepareAdministrationLayout());
        }
        else{
            // user not logged
            Notification.show("User not logged!");
            getUI().ifPresent(ui -> ui.navigate("/welcome"));
        }
    }

    /**
     * Function for preparing administration layout
     * @return HorizontalLayout
     */
    HorizontalLayout prepareAdministrationLayout(){
        HorizontalLayout adminLayout = new HorizontalLayout();
        // loading components
        userGrid = new Grid(GridElement.class,false);
        userGrid.addColumn(GridElement::getGridelement_text).setHeader("Users");
        userGrid.setItems(BearinmindApplication.database.get_user_list());
        userGrid.setWidth("100%");userGrid.setHeight("100%");

        adduser_button = new Button("Add User");
        adduser_button = new ButtonStyler().simple_button(adduser_button,"Add User",VaadinIcon.USER,"100%","50px");

        removeuser_button = new Button("Remove User");
        removeuser_button = new ButtonStyler().simple_button(removeuser_button,"Remove User",VaadinIcon.USER,"100%","50px");


        VerticalLayout left_layout = new VerticalLayout();
        VerticalLayout right_layout = new VerticalLayout();

        HorizontalLayout userButtonsLayout = new HorizontalLayout();
        userButtonsLayout.add(adduser_button,removeuser_button);

        left_layout.add(userGrid,userButtonsLayout);
        right_layout.add(disableaccount_button,generateraport_button,resetpassword_button,logviewer_button);

        left_layout.setSizeFull();
        left_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        left_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        left_layout.getStyle().set("text-align", "center");

        right_layout.setSizeFull();
        right_layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        right_layout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        right_layout.getStyle().set("text-align", "center");

        adminLayout.add(left_layout,right_layout);

        adminLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        adminLayout.setWidth("80%");
        adminLayout.setHeight("50%");
        adminLayout.getStyle().set("text-align", "center");
        adminLayout.getStyle().set("border-radius","25px");
        adminLayout.getStyle().set("margin","75px");
        adminLayout.getStyle().set("background-color","#7E8D85");
        adminLayout.getStyle().set("--lumo-font-family","Monospace");

        adminLayout.setEnabled(false);
        return adminLayout;
    }

    /**
     * goback_button action
     * @param ex
     */
    private void gobackbutton_action(ClickEvent ex){
        goback_button.getUI().ifPresent(ui ->
                ui.navigate("/home"));
    }

}
