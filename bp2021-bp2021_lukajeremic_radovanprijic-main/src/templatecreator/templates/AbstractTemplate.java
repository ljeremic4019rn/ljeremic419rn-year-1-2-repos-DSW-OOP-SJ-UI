package templatecreator.templates;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public abstract class AbstractTemplate {

    public abstract void createTemplateMessage(String input);

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
