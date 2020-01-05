package sample;

import lombok.Getter;
import lombok.Setter;
import sample.types.ReceivedMessageTypes;
import sample.types.SendMessageTypes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
@Setter
public class MessagesRetriever implements Runnable {

    public static final String END_OF_SENTENCE = "//";
    private boolean running = true;
    private ConnectionHandler connectionHandler;
    private List<String> messages = new ArrayList<>();
    private String incompleteMessage = "";
    private User user;
    private ExecutorService executor = Executors.newSingleThreadExecutor();


    public MessagesRetriever(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    public void readMessage() throws IOException {
        while (running) {
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
            messages.addAll(msgList);
        }
    }

    @Override
    public void run() {
        executor.submit(() -> {
            try {
                readMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        while (running) {
            findTypes();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
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
            retrieveType(msgType, message.substring(msgType.getValue().length()));
        }
        messages.clear();
    }

    private void retrieveType(ReceivedMessageTypes msgType, String message) {
        if (user == null) {
            switch (msgType) {
                case USER_A:
                    user = new UserA("");
                    break;
                case USER_B:
                    user = new UserB("");
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
                    System.out.println("You received answer : " + message);
                    saveQuestionAnswer(message);
                    break;
                case QUESTION:
                    GameManager.getInstance().answerQuestion(message);
                    break;
                case ASK_QUESTION:
                    GameManager.getInstance().askQuestion();
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

    private void saveQuestionAnswer(String message) {
        String[] msg = message.split("->");
        System.out.println("Question : " + msg[0] + "Answer : " + msg[1]);
        GameManager.getInstance().addQuestion(msg[0], msg[1]);
    }

    void sendMessage(SendMessageTypes type, String message) throws IOException {
        connectionHandler.sendMessage(type.getValue() + message + "//\n");
    }

    void closeConnection() {
        try {
            sendMessage(SendMessageTypes.CLOSE, "");
            connectionHandler.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.running = false;
        }
    }
}
