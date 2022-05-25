package templatecreator.templates.filtering;

import templatecreator.templates.AbstractTemplate;
import lombok.Getter;

@Getter

public class WhereInTemplate extends AbstractTemplate {
    private String columnName;

    public WhereInTemplate(String input){
        createTemplateMessage(input);
    }

    @Override
    public void createTemplateMessage(String input) {
        String inputParsing[] =input.split("\"");
        columnName = inputParsing[1];
    }
}
