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
    private boolean running = true;
    private ConnectionHandler connectionHandler;
    private List<String> messages = new ArrayList<>();
    private String incompleteMessage = "";
    private User user;

    public MessagesRetriever(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public void readMessage() throws IOException {

        boolean incomplete = false;
        String message = connectionHandler.receiveMessage();
        String completeMessage = incompleteMessage + message;
        incompleteMessage = "";
        if (completeMessage.length() >= 2) {
            if (!completeMessage.substring(completeMessage.length() - 2).equals(END_OF_SENTENCE)) {
                incomplete = true;
            }
        } else {
            incomplete = true;
        }
        List<String> msgList = new ArrayList<>(Arrays.asList(completeMessage.split(END_OF_SENTENCE)));
        if (incomplete) {
            incompleteMessage = msgList.remove(msgList.size() - 1);
        }
        System.out.println("Separated messages : " + msgList);
        System.out.println("Incomplete message : " + incompleteMessage);
        messages.addAll(msgList);
    }

    @Override
    public void run() {
        while (running) {
            try {
                readMessage();
                findTypes();
            } catch (IOException e) {
                e.printStackTrace();
                closeConnection();
            }
        }
    }

    private void findTypes() {
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
        System.out.println("retrieving");
        if (user == null) {
            System.out.println(msgType);
            switch (msgType) {
                case USER_A:
                    user = new UserA();
                    break;
                case USER_B:
                    user = new UserB();
                    break;
            }
        } else {
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
        connectionHandler.sendMessage(type.getValue() + message + "//\n");
    }

    void closeConnection() {
        try {
            connectionHandler.sendMessage(SendMessageTypes.CLOSE.getValue());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.running = false;
        }
    }
}
