package sample;

import javafx.application.Platform;
import lombok.Getter;
import lombok.Setter;
import sample.controllers.MainGamerA;
import sample.controllers.MainGamerB;
import sample.types.SendMessageTypes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameManager {

    private static GameManager instance;

    @Getter
    @Setter
    private User user;

    @Getter
    private MessagesRetriever messagesRetriever;

    @Setter
    private MainGamerA mainGamerA;

    @Getter
    private Map<String, String> questionAnswerMap = new HashMap<>();

    @Setter
    private MainGamerB mainGamerB;


    private GameManager(){
        this.messagesRetriever = new MessagesRetriever(new ConnectionHandler());
        new Thread(this.messagesRetriever).start();
    }

    void saveQuestionAnswer(String message) {
        String[] msg = message.split("->");
        System.out.println("Question : " + msg[0] + "Answer : " + msg[1]);
        addQuestion(msg[0], msg[1]);
    }

    public void addQuestion(String question, String answer){
        questionAnswerMap.put(question, answer);
        Platform.runLater(() -> mainGamerB.initShowingQuestionPane(question, answer));
    }

    public static synchronized GameManager getInstance(){
        if (instance == null) instance = new GameManager();
        return instance;
    }

    public void saveName(String name) throws IOException {
        this.user.setName(name);
        this.messagesRetriever.sendMessage(SendMessageTypes.NAME, name);
    }

    public void quitGame() {
        messagesRetriever.closeConnection();
        instance = null;
    }

    public void saveWord(String word) throws IOException {
        messagesRetriever.sendMessage(SendMessageTypes.NEW_WORD, word);
    }

    public void sendAnswer(String question, String answer) throws IOException {
        String msg = question + "->" + answer;
        messagesRetriever.sendMessage(SendMessageTypes.ANSWER, msg);
    }

    public void answerQuestion(String question) {
        Platform.runLater(() -> mainGamerA.askForAnswer(question));
    }

    public void askQuestion(){
        Platform.runLater(() -> mainGamerB.initAskingQuestionPaneWhenThereIsUserRound());
    }

    public void sendQuestion(String question) throws IOException {
        messagesRetriever.sendMessage(SendMessageTypes.QUESTION, question);
    }

    public void sendGuess(String guess) throws IOException {
        messagesRetriever.sendMessage(SendMessageTypes.GUESS, guess);
    }

    public void gamerBWin() {
        this.user = new UserA(this.user.getName());
        mainGamerB.initWinPane();
    }

    public void gamerBLoose() {
        this.user = new UserB(this.user.getName());
        mainGamerB.initLoosePane();
    }



    public void gamerALoose() {
        this.user = new UserB(this.user.getName());
        mainGamerA.initLoosePane();
    }
}
