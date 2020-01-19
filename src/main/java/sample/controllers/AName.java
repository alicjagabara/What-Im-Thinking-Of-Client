package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.GameManager;
import sample.ScreenManager;

import java.io.IOException;

public class AName {

    @FXML
    private TextField name;

    private ScreenManager screenManager = new ScreenManager();

    public void exit(ActionEvent event) {
        GameManager.getInstance().exitGame();
        screenManager.setScreen("welcome", event);
    }

    public void sendName(ActionEvent event) {
        try {
            GameManager.getInstance().saveName(name.getText());
            screenManager.setScreen("inputWord", event);
        } catch (IOException e) {
            screenManager.setScreen("error", event);
        }
    }
}
