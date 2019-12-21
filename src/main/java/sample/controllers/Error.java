package sample.controllers;

import javafx.event.ActionEvent;
import sample.GameManager;
import sample.ScreenManager;

public class Error {

    private ScreenManager screenManager = new ScreenManager();

    public void exit(ActionEvent actionEvent) {
        GameManager.getInstance().quitGame();
        screenManager.setScreen("welcome", actionEvent);
    }
}
