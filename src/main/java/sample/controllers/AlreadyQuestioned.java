package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;

import java.util.Map;

public class AlreadyQuestioned {

    @FXML
    public Accordion table;

    public void init(Map<String, String> questionAnswerMap){
        for( Map.Entry<String, String> row : questionAnswerMap.entrySet()){
            table.getPanes().add(new TitledPane(row.getKey(), new Text(row.getValue())));
        }
    }

    public void next(ActionEvent actionEvent) {

    }
}
