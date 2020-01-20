package sample;

import sample.types.ReceivedMessageType;

import static sample.types.ReceivedMessageType.INVENTOR;
import static sample.types.ReceivedMessageType.GUESSER;

public class UserB extends User {

    public UserB(String name) {
        super(name, 3);
    }

    @Override
    public void retrieveType(ReceivedMessageType msgType, String message) {
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
            case INVENTOR:
                GameManager.getInstance().userALeft(INVENTOR);
                break;
            case GUESSER:
                GameManager.getInstance().userALeft(GUESSER);
                break;
            case GAME_BEGAN:
                GameManager.getInstance().gameBegan();
                break;
            case WAIT:
                GameManager.getInstance().waitForGame();
                break;
            case UNKNOWN:
                break;
        }
    }
}

