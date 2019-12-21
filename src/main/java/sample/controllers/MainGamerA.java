package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import sample.GameManager;

import java.io.IOException;

public class MainGamerA {

    @FXML
    public Pane waitingPane;
    @FXML
    public Pane questionPane;
    @FXML
    public Text questionField;
    @FXML
    public TextField answer;

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
        questionField.setText(question);
        waitingPane.setVisible(false);
        questionPane.setVisible(true);
    }
}
