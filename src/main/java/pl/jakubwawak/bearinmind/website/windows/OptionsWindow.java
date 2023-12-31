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
import pl.jakubwawak.bearinmind.BearinmindApplication;
import pl.jakubwawak.bearinmind.website.components.ButtonStyler;
import pl.jakubwawak.bearinmind.website.components.bim_components.ResetPasswordWindow;
import pl.jakubwawak.bearinmind.website.components.bim_components.ViewerWindow;

/**
 * Object for showing window components
 */
public class OptionsWindow {

    public Dialog main_dialog;
    VerticalLayout main_layout;

    Button viewer_button;
    Button adminpanel_button;

    Button changepassword_button;
    Button logout_button;

    /**
     * Constructor
     */
    public OptionsWindow(){
        main_dialog = new Dialog();
        main_layout = new VerticalLayout();
        prepare_window();
    }

    /**
     * Function for preparing components
     */
    void prepare_components(){
        logout_button= new Button("",this::logoutbutton_action);
        logout_button  = new ButtonStyler().third_button(logout_button,"Logout",VaadinIcon.EXIT,"100%","20%");

        adminpanel_button = new Button("Admin Panel",VaadinIcon.LOCK.create(),this::adminpanelbutton_action);
        adminpanel_button = new ButtonStyler().third_button(adminpanel_button,"Admin Panel",VaadinIcon.LOCK,"100%","20%");

        viewer_button = new Button("Mental Viewer",VaadinIcon.LOCK.create(),this::viewerbutton_action);
        viewer_button = new ButtonStyler().third_button(viewer_button,"Your Emotion History",VaadinIcon.MAGIC,"100%","20%");

        changepassword_button = new Button("Mental Viewer",VaadinIcon.LOCK.create(),this::changepasswordbutton_action);
        changepassword_button = new ButtonStyler().third_button(changepassword_button,"Change Password",VaadinIcon.USER,"100%","20%");

    }

    /**
     * Function for preparing window
     */
    void prepare_window(){
        // preparing components
        prepare_components();

        // creating layout

            // -- here add your layout

        main_layout.add(new H6("Options"));
        main_layout.add(viewer_button);
        main_layout.add(adminpanel_button);
        main_layout.add(changepassword_button);
        main_layout.add(logout_button);

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
        main_dialog.setWidth("600px");main_dialog.setHeight("600px");
    }

    /**
     * viewer_button action
     * @param e
     */
    private void viewerbutton_action(ClickEvent e){
        if ( BearinmindApplication.database.count_user_dailyentries() > 0 ){
            ViewerWindow vw = new ViewerWindow();
            main_layout.add(vw.main_dialog);
            vw.main_dialog.open();
            main_dialog.close();
        }
        else{
            Notification.show("No emotion entries! Check back tomorrow!");
        }
    }

    /**
     * changepassword_button action
     * @param ex
     */
    private void changepasswordbutton_action(ClickEvent ex){
        ResetPasswordWindow rpw = new ResetPasswordWindow();
        main_layout.add(rpw.main_dialog);
        rpw.main_dialog.open();
        main_dialog.close();
    }

    /**
     * Function for logging out
     * @param e
     */
    private void logoutbutton_action(ClickEvent e){
        BearinmindApplication.database.log("DB-LOGOUT","User "+BearinmindApplication.logged_user.bim_user_mail+" logged out!");
        BearinmindApplication.logged_user = null;
        logout_button.getUI().ifPresent(ui ->
                ui.navigate("/welcome"));
        Notification.show("User logged out!");
    }

    private void adminpanelbutton_action(ClickEvent e){
        adminpanel_button.getUI().ifPresent(ui ->
                ui.navigate("/admin-panel"));
    }
}
