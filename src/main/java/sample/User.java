package sample;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sample.types.ReceivedMessageType;

@Getter
@Setter
@AllArgsConstructor
public abstract class User {
    private String name;
    private Integer life;

    public abstract void retrieveType(ReceivedMessageType msgType, String message);

    void removeLife(){
        if(life > 0) {
            life--;
        }
        else{
            GameManager.getInstance().lifeEnded();
        }
    }
}
