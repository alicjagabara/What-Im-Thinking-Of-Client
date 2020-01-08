package sample.controllers;

import javafx.event.ActionEvent;
import javafx.stage.Stage;
import sample.GameManager;
import sample.ScreenManager;
import sample.UserA;


public class Welcome {

    private ScreenManager screenManager = new ScreenManager();

    public void newGame(ActionEvent event) {
        GameManager.getInstance();
        while(GameManager.getInstance().getUser() == null);
        if(GameManager.getInstance().getUser().getClass() == UserA.class){
            screenManager.setScreen("aName", event);
        }
        else{
            screenManager.setScreen("bName", event);
        }
    }

    public void exit(ActionEvent event) {
        Stage stage = screenManager.getCurrentScene(event);
        stage.close();
    }
}
