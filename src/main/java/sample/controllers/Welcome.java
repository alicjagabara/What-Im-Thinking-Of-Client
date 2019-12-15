package sample.controllers;

import javafx.event.ActionEvent;
import sample.GameManager;
import sample.UserA;


public class Welcome {

    private ScreenManager screenManager = new ScreenManager();

    public void newGame(ActionEvent event) {
        GameManager.getInstance();
        while(GameManager.getInstance().getMessagesRetriever().getUser() == null);
        if(GameManager.getInstance().getMessagesRetriever().getUser().getClass() == UserA.class){
            screenManager.setScreen("aName", event);
        }
        else{
            screenManager.setScreen("bName", event);
        }
    }

    public void exit(ActionEvent event) {

    }
}
