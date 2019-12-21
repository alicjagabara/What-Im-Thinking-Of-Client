package sample;

import com.sun.tools.javadoc.Main;
import lombok.Getter;
import sample.controllers.AlreadyQuestioned;
import sample.controllers.MainGamerA;
import sample.types.SendMessageTypes;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GameManager {
    private static GameManager instance;

    @Getter
    private MessagesRetriever messagesRetriever;

    private AlreadyQuestioned alreadyQuestioned = new AlreadyQuestioned();

    private MainGamerA mainGamerA = new MainGamerA();

    @Getter
    private Map<String, String> questionAnswerMap = new HashMap<>();


    private GameManager(){
        this.messagesRetriever = new MessagesRetriever(new ConnectionHandler());
        new Thread(this.messagesRetriever).start();

    }

    public void addQuestion(String question, String answer){
        questionAnswerMap.put(question, answer);
    }

    public static synchronized GameManager getInstance(){
        if (instance == null) instance = new GameManager();
        return instance;
    }

    public void saveName(String name) throws IOException {
        this.messagesRetriever.getUser().setName(name);
        this.messagesRetriever.sendMessage(SendMessageTypes.NAME, name);
    }

    public void quitGame() {
        messagesRetriever.closeConnection();
        instance = null;
    }

    public void saveWord(String word) throws IOException {
        messagesRetriever.sendMessage(SendMessageTypes.NEW_WORD, word);
    }

    public void showQuestionsAnswers() {
        alreadyQuestioned.init(questionAnswerMap);
    }

    public void sendAnswer(String answer) throws IOException {
        messagesRetriever.sendMessage(SendMessageTypes.ANSWER, answer);
    }

    public void answerQuestion(String question) {
        mainGamerA.askForAnswer(question);
    }
}
