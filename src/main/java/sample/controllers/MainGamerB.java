package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import sample.GameManager;
import sample.ScreenManager;

import java.io.IOException;
import java.util.Map;

public class MainGamerB {


    @FXML
    public Button exitQuestion;

    @FXML
    public Button okQuestion;

    @FXML
    public Button guess;

    @FXML
    public Button exitMain;

    @FXML
    public Button exitPreviousQuestions;

    @FXML
    public Button okPreviousQuestions;

    @FXML
    public Accordion table;

    @FXML
    public AnchorPane previousPane;

    @FXML
    public AnchorPane questionPane;

    @FXML
    public Pane mainPane;

    @FXML
    public AnchorPane askingPane;

    @FXML
    public TextField usersQuestion;

    @FXML
    public AnchorPane wordGuessPane;

    @FXML
    public TextField guessedWord;

    @FXML
    public Button exitFromAskingQuestion;

    @FXML
    public Label askingQuestionPaneHeader;

    @FXML
    public Label life;

    @FXML
    public Label name;

    @FXML
    public Label question;

    @FXML
    public Label answer;

    @FXML
    public AnchorPane winPane;

    @FXML
    public AnchorPane loosePane;

    @FXML
    public AnchorPane connectionErrorPane;

    @FXML
    public AnchorPane waitPane;


    private ScreenManager screenManager = new ScreenManager();

    public void initMainPane(String life, String name) {
        System.out.println("Initializing mainBPane with params: {life: " + life + ", name: " + name + "}");
        this.life.setText(life);
        this.name.setText("Welcome, " + name);
        setAllInvisible();
        this.mainPane.setVisible(true);
    }

    public void goToGuessingWordPane(ActionEvent actionEvent) {
        if (GameManager.getInstance().getUser().getLife() > 0) {
            initGuessingWordPane();
        }
    }

    public void goToShowingPreviousQuestionsPane(ActionEvent actionEvent) {
        initPreviousQuestionsPane(GameManager.getInstance().getQuestionAnswerMap());
    }

    public void goToAskingQuestion(ActionEvent actionEvent) {
        if (GameManager.getInstance().getUser().getLife() > 0) {
            initAskingQuestionPane();
        }
    }

    public void exitToMainMenu(ActionEvent actionEvent) {
        GameManager.getInstance().exitGame();
        screenManager.setScreen("welcome", actionEvent);
    }


    ///////////////////////////////////////////////////////////////////////


    public void initPreviousQuestionsPane(Map<String, String> questionAnswerMap) {
        for (Map.Entry<String, String> row : questionAnswerMap.entrySet()) {
            table.getPanes().add(new TitledPane(row.getKey(), new Text(row.getValue())));
        }
        questionAnswerMap.clear();
        setAllInvisible();
        this.previousPane.setVisible(true);
    }

    public void clearPreviousQuestionsPane() {
        table.getPanes().clear();
        System.out.println("Cleared previous questions");
    }


    /////////////////////////////////////////////////////////////////////////


    public void initShowingQuestionPane(String question, String answer) {
        this.question.setText(question);
        this.answer.setText(answer);
        setAllInvisible();
        this.questionPane.setVisible(true);

    }

    public void okButtonShowingQuestionPane(ActionEvent actionEvent) {
        exitToMainGamerBPanel(actionEvent);
    }

    //////////////////////////////////////////////////////////////////////////////


    public void initAskingQuestionPane() {
        askingQuestionPaneHeader.setText("You can ask question, or you can wait for your turn to collect more "
                + "information about the word");
        setAllInvisible();
        this.exitFromAskingQuestion.setVisible(true);
        this.askingPane.setVisible(true);
    }

    public void initAskingQuestionPaneWhenThereIsUserRound() {
        this.askingQuestionPaneHeader.setText("It's your turn to ask questions!");
        setAllInvisible();
        this.exitFromAskingQuestion.setVisible(false);
        this.askingPane.setVisible(true);
    }


    public void commitYourQuestion(ActionEvent actionEvent) {
        try {
            GameManager.getInstance().sendQuestion(usersQuestion.getText());
            exitToMainGamerBPanel(actionEvent);
        } catch (IOException e) {
            exitToMainMenu(actionEvent);
        }
    }


    ////////////////////////////////////////////////////////////////////////////
    public void initGuessingWordPane() {
        setAllInvisible();
        this.wordGuessPane.setVisible(true);
    }


    public void commitYourGuess(ActionEvent actionEvent) {
        try {
            GameManager.getInstance().sendGuess(guessedWord.getText());
            exitToMainGamerBPanel(actionEvent);
        } catch (IOException e) {
            exitToMainMenu(actionEvent);
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    public void exitToMainGamerBPanel(ActionEvent actionEvent) {
        initMainPane(GameManager.getInstance().getUser().getLife().toString(), GameManager.getInstance()
                .getUser()
                .getName());
    }


    private void setAllInvisible() {
        this.questionPane.setVisible(false);
        this.mainPane.setVisible(false);
        this.previousPane.setVisible(false);
        this.askingPane.setVisible(false);
        this.wordGuessPane.setVisible(false);
        this.winPane.setVisible(false);
        this.loosePane.setVisible(false);
        this.connectionErrorPane.setVisible(false);
        this.waitPane.setVisible(false);
    }

    public void initialize() {
        GameManager.getInstance().setMainGamerB(this);
        initGuessingWordPane();
        clearPreviousQuestionsPane();
        initPreviousQuestionsPane(GameManager.getInstance().getQuestionAnswerMap());
        initAskingQuestionPane();
        initShowingQuestionPane("", "");
        initWaitPane();
        initMainPane(GameManager.getInstance().getUser().getLife().toString(), GameManager.getInstance()
                .getUser()
                .getName());
        if(GameManager.getInstance().isWaiting()){
            initWaitPane();
        }
    }

    public void initWaitPane() {
        setAllInvisible();
        this.waitPane.setVisible(true);
    }

    public void initWinPane() {
        setAllInvisible();
        this.winPane.setVisible(true);
    }

    public void initLoosePane() {
        setAllInvisible();
        this.loosePane.setVisible(true);
    }

    public void win(ActionEvent actionEvent) {
        clearPreviousQuestionsPane();
        screenManager.setScreen("inputWord", actionEvent);
    }

    public void loose(ActionEvent actionEvent) {
        clearPreviousQuestionsPane();
        exitToMainGamerBPanel(actionEvent);
    }

    public void connectionError() {
        setAllInvisible();
        connectionErrorPane.setVisible(true);
    }

    public void error(ActionEvent actionEvent) {
        screenManager.setScreen("error", actionEvent);
    }
}