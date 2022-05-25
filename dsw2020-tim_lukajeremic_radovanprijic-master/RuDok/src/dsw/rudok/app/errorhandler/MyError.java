package dsw.rudok.app.errorhandler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyError {
    private String warningMessage;

    public MyError (String warningMessage){
        this.warningMessage = warningMessage;
    }
}
