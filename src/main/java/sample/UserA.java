package sample;

import sample.types.ReceivedMessageTypes;

public class UserA extends User {

    public UserA(String name) {
        super(name, 3);
    }

    @Override
    public void retrieveType(ReceivedMessageTypes msgType, String message) {
        switch (msgType) {
            case WIN:
                break;
            case LOOSE:
                GameManager.getInstance().gamerALoose();
                break;
            case QUESTION:
                GameManager.getInstance().answerQuestion(message);
                break;
            case GAME_BEGAN:

                break;
            case UNKNOWN:
                break;
        }
    }
}

