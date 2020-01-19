package sample.controllers;

import com.google.common.util.concurrent.Monitor;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import sample.GameManager;
import sample.ScreenManager;
import sample.UserA;

import java.time.Duration;

import static com.google.common.util.concurrent.Monitor.Guard;


public class Welcome {

    private static long TIMEOUT = 1500;

    private ScreenManager screenManager = new ScreenManager();

    private Monitor mutex = new Monitor();
    private Guard userIsNotNull = new Guard(mutex) {
        @Override
        public boolean isSatisfied() {
            return GameManager.getInstance().getUser() != null;
        }
    };

    @SneakyThrows
    public void newGame(ActionEvent event) {
        GameManager.getInstance().startGame();
        GameManager.getInstance().waitForFirstUser(mutex);
        mutex.waitFor(userIsNotNull, Duration.ofMillis(100));
        if (GameManager.getInstance().getUser() == null) {
            screenManager.setScreen("error", event);
        } else if (GameManager.getInstance().getUser() instanceof UserA) {
            screenManager.setScreen("aName", event);
        } else {
            screenManager.setScreen("bName", event);
        }
        if (mutex.isOccupied()) mutex.leave();
    }

    public void exit(ActionEvent event) {
        Stage stage = screenManager.getCurrentScene(event);
        stage.close();
        System.exit(0);
    }

}
