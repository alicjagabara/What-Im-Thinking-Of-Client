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

    public void sendAnswer(String answer) {
        try {
            System.out.println("Answer: " + answer);
            GameManager.getInstance().sendAnswer(this.questionLabel.getText() , answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hideAllPanes();
        waitingPane.setVisible(true);
    }

    public void sendYes() {
        sendAnswer("Yes");
    }

    public void sendNo() {
        sendAnswer("No");
    }

    public void sendDontKnow() {
        sendAnswer("I don't know");
    }

    public void askForAnswer(String question){
        hideAllPanes();
        questionLabel.setText(question);
        questionPane.setVisible(true);
    }


    public void initialize() {
        GameManager.getInstance().setMainGamerA(this);
        hideAllPanes();
        questionLabel.setText("");
        waitingPane.setVisible(true);
    }

    public void exitToMainMenu(ActionEvent actionEvent) {
        GameManager.getInstance().quitGame();
        screenManager.setScreen("welcome", actionEvent);
    }

    public void initLoosePane() {
        hideAllPanes();
        loosePane.setVisible(true);
    }

    public void initWinPane() {
        hideAllPanes();
        winPane.setVisible(true);
    }

    private void hideAllPanes(){
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
        hideAllPanes();
        connectionErrorPane.setVisible(true);
    }

    public void error(ActionEvent actionEvent) {
        screenManager.setScreen("error", actionEvent);
    }
}
