package sample;

import sample.types.ReceivedMessageTypes;

import static sample.types.ReceivedMessageTypes.USER_A;
import static sample.types.ReceivedMessageTypes.USER_B;

public class UserB extends User {

    public UserB(String name) {
        super(name, 3);
    }

    @Override
    public void retrieveType(ReceivedMessageTypes msgType, String message) {
        switch (msgType) {
            case WIN:
                System.out.println("You WON!");
                GameManager.getInstance().gamerBWin();
                break;
            case LOOSE:
                System.out.println("You lost!");
                GameManager.getInstance().gamerBLoose();
                break;
            case QA:
                System.out.println("You received answer : " + message);
                GameManager.getInstance().saveQuestionAnswer(message);
                break;
            case ASK_QUESTION:
                GameManager.getInstance().askQuestion();
                break;
            case WRONG_GUESS:
                this.removeLife();
                break;
            case USER_A:
                GameManager.getInstance().userALeft(USER_A);
                break;
            case USER_B:
                GameManager.getInstance().userALeft(USER_B);
                break;
            case UNKNOWN:
                break;
        }
    }
}

