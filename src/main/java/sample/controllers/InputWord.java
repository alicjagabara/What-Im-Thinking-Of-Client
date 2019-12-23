package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sample.GameManager;
import sample.ScreenManager;

import java.io.IOException;

public class InputWord {

    private ScreenManager screenManager = new ScreenManager();

    @FXML
    public TextField word;

    public void exit(ActionEvent event) {
        GameManager.getInstance().quitGame();
        screenManager.setScreen("welcome", event);
    }

    public void sendWord(ActionEvent event) {

        try {
            screenManager.setScreen("mainGamerA", event);
            GameManager.getInstance().saveWord(word.getText());
        } catch (IOException e) {
            screenManager.setScreen("error", event);
        }

    }
}
