package sample.types;

import lombok.Getter;

public enum ReceivedMessageTypes {
    USER_A("A||"),
    USER_B("B||"),
    WIN("W||"),
    LOOSE("L||"),
    NEW_WORD("NW||"),
    QA("QA||"),
    QUESTION("Q||"),
    ASK_QUESTION("AQ||"),
    GAME_BEGAN("GB||"),
    WRONG_GUESS("WG||"),
    UNKNOWN("UNKNOWN");

    @Getter
    private String value;

    ReceivedMessageTypes(String s) {
        this.value = s;
    }

}
