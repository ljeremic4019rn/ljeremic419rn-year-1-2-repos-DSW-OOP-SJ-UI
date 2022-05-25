package templatecreator.templates.joining;

import templatecreator.templates.AbstractTemplate;
import lombok.Getter;

@Getter

public class JoinTemplate extends AbstractTemplate {
    private String tableName;

    public JoinTemplate(String input){
        createTemplateMessage(input);
    }

    @Override
    public void createTemplateMessage(String input) {
        String inputParsing[] = input.split("\"");
        tableName = inputParsing[1];
    }
}
