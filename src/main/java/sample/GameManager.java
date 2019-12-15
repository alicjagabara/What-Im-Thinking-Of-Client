package sample;

import lombok.Getter;
import sample.types.SendMessageTypes;
import java.io.IOException;

public class GameManager {
    private static GameManager instance;
    @Getter
    private MessagesRetriever messagesRetriever;

    private GameManager(){
        this.messagesRetriever = new MessagesRetriever(new ConnectionHandler());
        new Thread(this.messagesRetriever).start();

    }

    public static synchronized GameManager getInstance(){
        if (instance == null) instance = new GameManager();
        return instance;
    }

    public void saveName(String name) throws IOException {
        this.messagesRetriever.getUser().setName(name);
        this.messagesRetriever.sendMessage(SendMessageTypes.NAME, name);
    }
}
