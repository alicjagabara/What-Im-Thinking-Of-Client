package sample;

import com.google.common.util.concurrent.Monitor;
import javafx.application.Platform;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sample.controllers.MainGamerA;
import sample.controllers.MainGamerB;
import sample.types.ReceivedMessageType;
import sample.types.SendMessageTypes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class GameManager {

    private static GameManager instance;

    @Getter @Setter private User user;

    @Getter
    private MessagesRetriever messagesRetriever;

    @Setter
    private MainGamerA mainGamerA;

    @Getter
    private Map<String, String> questionAnswerMap = new HashMap<>();

    @Setter
    private MainGamerB mainGamerB;

    @Getter
    private String currentWord;

    private Monitor mutex;

    public void startGame() {
        this.messagesRetriever = new MessagesRetriever(new ConnectionHandler());
        new Thread(this.messagesRetriever).start();
    }

    void saveQuestionAnswer(String message) {
        String[] msg = message.split("->");
        System.out.println("Question : " + msg[0] + "Answer : " + msg[1]);
        addQuestion(msg[0], msg[1]);
    }

    public void addQuestion(String question, String answer) {
        questionAnswerMap.put(question, answer);
        Platform.runLater(() -> mainGamerB.initShowingQuestionPane(question, answer));
    }

    public static synchronized GameManager getInstance() {
        if (instance == null) instance = new GameManager();
        return instance;
    }

    public void saveName(String name) throws IOException {
        this.user.setName(name);
        this.messagesRetriever.sendMessage(SendMessageTypes.NAME, name);
    }

    public void exitGame() {
        try {
            messagesRetriever.sendMessage(SendMessageTypes.CLOSE, "");
        } catch (IOException ignored){}
        GameManager.getInstance().quitGame();
    }

    public void quitGame() {
        messagesRetriever.closeConnection();
        instance = null;
    }

    public void disconnectGame() {
        if (user instanceof UserA) {
            mainGamerA.connectionError();
        } else {
            mainGamerB.connectionError();
        }
    }


    public void saveWord(String word) throws IOException {
        this.currentWord = word;
        mainGamerA.setWord(word);
        messagesRetriever.sendMessage(SendMessageTypes.NEW_WORD, word);
    }

    public void sendAnswer(String question, String answer) throws IOException {
        question = question.replace("->", " - > ");
        answer = answer.replace("->", " - > ");

        String msg = question + "->" + answer;
        messagesRetriever.sendMessage(SendMessageTypes.ANSWER, msg);
    }

    public void answerQuestion(String question) {
        Platform.runLater(() -> mainGamerA.askForAnswer(question));
    }

    public void askQuestion() {
        Platform.runLater(() -> mainGamerB.initAskingQuestionPaneWhenThereIsUserRound());
    }

    public void sendQuestion(String question) throws IOException {
        messagesRetriever.sendMessage(SendMessageTypes.QUESTION, question);
    }

    public void sendGuess(String guess) throws IOException {
        messagesRetriever.sendMessage(SendMessageTypes.GUESS, guess);
    }

    public void gamerBWin() {
        this.questionAnswerMap.clear();
        this.user = new UserA(this.user.getName());
        mainGamerB.initWinPane();
    }

    public void gamerBLoose() {
        this.questionAnswerMap.clear();
        this.user = new UserB(this.user.getName());
        mainGamerB.initLoosePane();
    }

    public void gamerALose() {
        this.questionAnswerMap.clear();
        this.user = new UserB(this.user.getName());
        mainGamerA.initLoosePane();
    }

    public void userALeft(ReceivedMessageType userType) {
        if (userType == ReceivedMessageType.INVENTOR) {
            this.user = new UserA(this.user.getName());
            mainGamerB.initWinPane();
        } else {
            this.user = new UserB(this.user.getName());
            mainGamerB.initLoosePane();
        }

    }

    public void gamerAWin() {
        this.questionAnswerMap.clear();
        mainGamerA.initWinPane();
    }

    public void lifeEnded() {
        mainGamerA.initLoosePane();
    }


    public void waitForFirstUser(Monitor mutex) {
        this.mutex = mutex;
        if (this.user == null) this.mutex.enter();
    }

    public void setFirstUser(User user) {
        this.user = user;
        if (mutex.isOccupied()) mutex.leave();
    }
}
