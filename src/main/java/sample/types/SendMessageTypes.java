package sample.types;

import lombok.Getter;

public enum SendMessageTypes {
    CLOSE("CL%%"),
    NAME("N%%"),
    NEW_WORD("NW%%"),
    GUESS("G%%"),
    ANSWER("A%%"),
    QUESTION("Q%%");

    @Getter
    private String value;

    SendMessageTypes(String s) {
        this.value = s;
    }
}
