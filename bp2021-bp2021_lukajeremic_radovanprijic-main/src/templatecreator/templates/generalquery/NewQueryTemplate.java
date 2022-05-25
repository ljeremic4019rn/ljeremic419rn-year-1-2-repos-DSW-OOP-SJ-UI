package templatecreator.templates.generalquery;

import templatecreator.templates.AbstractTemplate;
import lombok.Getter;

@Getter

public class NewQueryTemplate extends AbstractTemplate {
    private String tableName;

    public NewQueryTemplate(String input){
        createTemplateMessage(input);
    }

    @Override
    public void createTemplateMessage(String input) {
        String inputParsing[] = input.split("[\")]");
        tableName = inputParsing[1];
    }
}
