package sample;

import lombok.Getter;
import lombok.Setter;
import sample.types.ReceivedMessageTypes;
import sample.types.SendMessageTypes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class MessagesRetriever implements Runnable {

    public static final String END_OF_SENTENCE = "//";
    private ConnectionHandler connectionHandler;
    private List<String> messages = new ArrayList<>();
    private String incompleteMessage = "";
    private User user;

    public MessagesRetriever(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public void readMessage() {
        try {
            boolean incomplete = false;
            String message = connectionHandler.receiveMessage();
            if (message.length() >= 2) {
                if (!message.substring(message.length() - 2).equals(END_OF_SENTENCE)) {
                    incomplete = true;
                }
            } else {
                incomplete = true;
            }
            List<String> msgList = new ArrayList<>(Arrays.asList(message.split(END_OF_SENTENCE)));
            if (!incompleteMessage.isEmpty()) {
                this.messages.add(incompleteMessage + msgList.remove(0));
                incompleteMessage = "";
            }
            if (incomplete) {
                incompleteMessage = msgList.remove(msgList.size() - 1);
            }
            messages.addAll(msgList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            readMessage();
            findTypes();
        }
    }

    void findTypes() {
        for (String message : this.messages) {
            ReceivedMessageTypes msgType = ReceivedMessageTypes.UNKNOWN;
            for (ReceivedMessageTypes type : ReceivedMessageTypes.values()) {
                if (message.contains(type.getValue())) {
                    msgType = type;
                }
            }
            retrieveType(msgType, message);
        }
    }

    private void retrieveType(ReceivedMessageTypes msgType, String message) {
        if (user == null) {
            switch (msgType) {
                case USER_A:
                    user = new UserA();
                    break;
                case USER_B:
                    user = new UserB();
                    break;
            }
        }
        else {
            switch (msgType) {
                case WIN:

                    break;
                case LOOSE:

                    break;
                case NEW_WORD:

                    break;
                case QA:

                    break;
                case QUESTION:

                    break;
                case ASK_QUESTION:

                    break;
                case GAME_BEGAN:

                    break;
                case WRONG_GUESS:

                    break;
                case UNKNOWN:
                    break;
            }
        }
    }

    void sendMessage(SendMessageTypes type, String message) throws IOException {
        connectionHandler.sendMessage(type.getValue() + message);
    }

    void closeConnection() {
        try {
            connectionHandler.sendMessage(SendMessageTypes.CLOSE.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
