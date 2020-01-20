package sample.types;

import lombok.Getter;

public enum ReceivedMessageType {
    INVENTOR("A||"),
    GUESSER("B||"),
    WIN("W||"),
    LOOSE("L||"),
    NEW_WORD("NW||"),
    QA("QA||"),
    QUESTION("Q||"),
    ASK_QUESTION("AQ||"),
    GAME_BEGAN("BG||"),
    WAIT("WAIT||"),
    WRONG_GUESS("WG||"),
    QA_END("QAE||"),
    UNKNOWN("UNKNOWN");

    @Getter
    private String value;

    ReceivedMessageType(String s) {
        this.value = s;
    }

}
