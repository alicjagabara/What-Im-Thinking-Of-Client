package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import sample.GameManager;
import sample.ScreenManager;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class MainGamerB implements Initializable {

    @FXML
    public Text question;

    @FXML
    public Button exitQuestion;

    @FXML
    public Button okQuestion;

    @FXML
    public Text answer;

    @FXML
    public Text name;

    @FXML
    public Text life;

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
    public Text askingQuestionPaneHeader;

    private ScreenManager screenManager = new ScreenManager();

    public void initMainPane(String life, String name){
        this.life.setText("life left: " + life);
        this.name.setText(name);
        setAllInvisible();
        this.mainPane.setVisible(true);
    }

    public void goToGuessingWordPane(ActionEvent actionEvent) {
        initGuessingWordPane();
    }

    public void goToShowingPreviousQuestionsPane(ActionEvent actionEvent) {
        initPreviousQuestionsPane(GameManager.getInstance().getQuestionAnswerMap());
    }

    public void goToAskingQuestion(ActionEvent actionEvent) {
        initAskingQuestionPaneWhenThereIsUserRound();
    }

    public void exitToMainMenu(ActionEvent actionEvent) {
        GameManager.getInstance().quitGame();
        screenManager.setScreen("welcome", actionEvent);
    }


    ///////////////////////////////////////////////////////////////////////


    public void initPreviousQuestionsPane(Map<String, String> questionAnswerMap) {
        for (Map.Entry<String, String> row : questionAnswerMap.entrySet()) {
            table.getPanes().add(new TitledPane(row.getKey(), new Text(row.getValue())));
        }
        setAllInvisible();
        this.previousPane.setVisible(true);
    }


    /////////////////////////////////////////////////////////////////////////



    public void initShowingQuestionPane(String question, String answer){
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
        askingQuestionPaneHeader.setText("You can ask question. You can also wait for your turn to collect more " +
                "information about word");
        setAllInvisible();
        this.exitFromAskingQuestion.setVisible(true);
        this.askingPane.setVisible(true);
    }

    public void initAskingQuestionPaneWhenThereIsUserRound() {
        askingQuestionPaneHeader.setText("It's your turn, you must ask question");
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
    public void initGuessingWordPane(){
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
        setAllInvisible();
        mainPane.setVisible(true);
    }



    private void setAllInvisible(){
        this.questionPane.setVisible(false);
        this.mainPane.setVisible(false);
        this.previousPane.setVisible(false);
        this.askingPane.setVisible(false);
        this.wordGuessPane.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initMainPane(GameManager.getInstance().getMessagesRetriever().getUser().getLife().toString()
                ,GameManager.getInstance().getMessagesRetriever().getUser().getName());
    }
}