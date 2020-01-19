package sample;

import sample.types.ReceivedMessageType;

public class UserA extends User {

    public UserA(String name) {
        super(name, 3);
    }

    @Override
    public void retrieveType(ReceivedMessageType msgType, String message) {
        switch (msgType) {
            case WIN:
                GameManager.getInstance().gamerAWin();
                break;
            case LOOSE:
                GameManager.getInstance().gamerALose();
                break;
            case QUESTION:
                GameManager.getInstance().answerQuestion(message);
                break;
            case UNKNOWN:
                break;
        }
    }
}

