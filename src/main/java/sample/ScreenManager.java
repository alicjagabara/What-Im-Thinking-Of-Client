package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenManager {

    private double x, y;

    public void setScreen(String screen, ActionEvent actionEvent) {
        Stage current = getCurrentScene(actionEvent);
        setScene(screen,current);
    }

    private void setScene(String nextScene, Stage current) {
        Parent newScene = null;
        try {
            newScene = FXMLLoader.load(getClass().getResource(String.format("/%s.fxml", nextScene)));
            newScene.setOnMousePressed(mouseEvent -> {
                x = mouseEvent.getSceneX();
                y = mouseEvent.getSceneY();
            });
            newScene.setOnMouseDragged(mouseEvent -> {
                current.setX(mouseEvent.getScreenX() - x);
                current.setY(mouseEvent.getScreenY() - y);
            });
        } catch(IOException e) {
            e.printStackTrace();
        }

        Parent finalNewScene = newScene;
            Platform.runLater(() -> {
                assert finalNewScene != null;
                current.setScene(new Scene(finalNewScene));
            });

    }

    public Stage getCurrentScene(ActionEvent actionEvent){
        return (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    }
}
