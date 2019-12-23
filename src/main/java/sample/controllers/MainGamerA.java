package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import sample.GameManager;
import sample.ScreenManager;

import java.io.IOException;

public class MainGamerA {

    @FXML
    public Pane waitingPane;
    @FXML
    public Pane questionPane;
    @FXML
    public TextField answer;
    @FXML
    public Label questionLabel;

    private ScreenManager screenManager = new ScreenManager();

    public void sendAnswer(ActionEvent event) {
        try {
            GameManager.getInstance().sendAnswer(this.answer.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        questionPane.setVisible(false);
        waitingPane.setVisible(true);
    }

    public void askForAnswer(String question){
        questionLabel.setText(question);
        waitingPane.setVisible(false);
        questionPane.setVisible(true);
    }


    public void initialize() {
        GameManager.getInstance().setMainGamerA(this);
        questionLabel.setText("");
        questionPane.setVisible(false);
        waitingPane.setVisible(true);
    }

    public void exitToMainMenu(ActionEvent actionEvent) {
        GameManager.getInstance().quitGame();
        screenManager.setScreen("welcome", actionEvent);
    }
}
