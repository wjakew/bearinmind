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
import pl.jakubwawak.bearinmind.website.windows.CreateAccountWindow;
import pl.jakubwawak.database_engine.entity.BIM_Health;
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

    HorizontalLayout adminLayout;

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

        unlock_button = new Button("",this::unlockbutton_action);
        unlock_button = new ButtonStyler().simple_button(unlock_button,"Unlock",VaadinIcon.LOCK,"125px","50px");

        disableaccount_button = new Button("",this::disableaccountcreationbutton_action);
        disableaccount_button = new ButtonStyler().simple_button(disableaccount_button,"Disable Account Creation",VaadinIcon.STAR,"100%","50px");
        if ( !BearinmindApplication.healthConfiguration.getBim_feature1().equals("createUserOn") ){
            disableaccount_button.setText("Enable Account Creation");
        }

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
            adminLayout = prepareAdministrationLayout();
            add(adminLayout);
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

        adduser_button = new Button("Add User",this::adduserbutton_action);
        adduser_button = new ButtonStyler().simple_button(adduser_button,"Add User",VaadinIcon.USER,"100%","100%");

        removeuser_button = new Button("Remove User",this::removebutton_action);
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

    /**
     * adduser_button action
     * @param ex
     */
    private void adduserbutton_action(ClickEvent ex){
        CreateAccountWindow caw = new CreateAccountWindow();
        add(caw.main_dialog);
        caw.main_dialog.open();
    }

    /**
     * removeuser_button action
     * @param ex
     */
    private void removebutton_action(ClickEvent ex){
        for(GridElement element : userGrid.getSelectedItems()){
            int ans = BearinmindApplication.database.remove_user(element.getGridelement_details());
            if ( ans == 1 ){
                Notification.show("User removed");
            }
            Notification.show("Failed to remove user, check log");
        }
    }

    /**
     * disableaccountcreation_button action
     * @param ex
     */
    private void disableaccountcreationbutton_action(ClickEvent ex){
        if ( disableaccount_button.getText().equals("Disable Account Creation")){
            BearinmindApplication.healthConfiguration.bim_feature1 = "createUserOff";
            disableaccount_button.setText("Enable Account Creation");
            BearinmindApplication.database.updateHealth(BearinmindApplication.healthConfiguration);
            Notification.show("Disabled user creation.");
        }
        else{
            BearinmindApplication.healthConfiguration.bim_feature1 = "createUserOn";
            disableaccount_button.setText("Disable Account Creation");
            BearinmindApplication.database.updateHealth(BearinmindApplication.healthConfiguration);
            Notification.show("Enabled user creation.");
        }
    }

    /**
     * unlock_button action
     * @param ex
     */
    private void unlockbutton_action(ClickEvent ex){
        if ( adminpassword_field.getValue().isEmpty() ){
            Notification.show("Empty password field - ERROR");
        }
        else{
            if ( BearinmindApplication.healthConfiguration.bim_administrator.equals(adminpassword_field.getValue())){
                adminLayout.setEnabled(true);
                Notification.show("Password correct.");
            }
            else{
                Notification.show("Password error.");
            }
        }
    }

}
