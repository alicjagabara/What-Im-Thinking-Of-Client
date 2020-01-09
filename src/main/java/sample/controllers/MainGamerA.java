package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    @FXML
    public AnchorPane loosePane;
    @FXML
    public AnchorPane winPane;
    @FXML
    public AnchorPane connectionErrorPane;

    private ScreenManager screenManager = new ScreenManager();

    public void sendAnswer(ActionEvent event) {
        try {
            System.out.println("Answer text: " + this.answer.getText());
            GameManager.getInstance().sendAnswer(this.questionLabel.getText() ,this.answer.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        invisibleAll();
        waitingPane.setVisible(true);
    }

    public void askForAnswer(String question){
        invisibleAll();
        questionLabel.setText(question);
        questionPane.setVisible(true);
    }


    public void initialize() {
        GameManager.getInstance().setMainGamerA(this);
        invisibleAll();
        questionLabel.setText("");
        waitingPane.setVisible(true);
    }

    public void exitToMainMenu(ActionEvent actionEvent) {
        GameManager.getInstance().quitGame();
        screenManager.setScreen("welcome", actionEvent);
    }

    public void initLoosePane() {
        invisibleAll();
        loosePane.setVisible(true);
    }

    public void initWinPane() {
        invisibleAll();
        winPane.setVisible(true);
    }

    private void invisibleAll(){
        waitingPane.setVisible(false);
        questionPane.setVisible(false);
        loosePane.setVisible(false);
        winPane.setVisible(false);
        connectionErrorPane.setVisible(false);
    }

    public void loose(ActionEvent actionEvent) {
        screenManager.setScreen("mainGamerB", actionEvent);
    }

    public void win(ActionEvent actionEvent) {
        screenManager.setScreen("inputWord", actionEvent);
    }

    public void connectionError() {
        invisibleAll();
        connectionErrorPane.setVisible(true);
    }

    public void error(ActionEvent actionEvent) {
        screenManager.setScreen("error", actionEvent);
    }
}
