package sample;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import sample.types.ReceivedMessageTypes;

@Getter
@Setter
@AllArgsConstructor
public abstract class User {
    private String name;
    private Integer life;

    public abstract void retrieveType(ReceivedMessageTypes msgType, String message);

    void removeLife(){
        if(life > 0) {
            life--;
        }
        else{
            GameManager.getInstance().lifeEnded();
        }
    }
}
