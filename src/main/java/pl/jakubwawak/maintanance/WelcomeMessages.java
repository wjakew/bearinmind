package pl.jakubwawak.maintanance;

import java.util.ArrayList;

public class WelcomeMessages {

    public ArrayList<String> welcomeCollection;

    /**
     * Constructor
     */
    public WelcomeMessages(){
        welcomeCollection = new ArrayList<>();
        welcomeCollection.add("Have splendid day X");
        welcomeCollection.add("Have a good one X");
        welcomeCollection.add("Have a wonderful day X");
        welcomeCollection.add("Take care X");
        welcomeCollection.add("Enjoy your day X");
        welcomeCollection.add("Have good day X");
    }

    /**
     * Function for getting random welcome message
     * @return String
     */
    public String getWelcomeMessage(String login){
        int index = (int)(Math.random() * ((welcomeCollection.size()) + 1));
        return welcomeCollection.get(index).replace("X",login);
    }
}
