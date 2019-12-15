package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.GameManager;
import sample.controllers.ScreenManager;

import java.io.IOException;

public class AName {

    @FXML
    private TextField name;

    private ScreenManager screenManager;

    public void exit(ActionEvent event) {
        screenManager.setScreen("welcome", event);
    }

    public void sendName(ActionEvent event) {
        try {
            GameManager.getInstance().saveName(name.getText());
            screenManager.setScreen("newWord", event);
        } catch (IOException e) {
            exit(event);
        }
    }
}
