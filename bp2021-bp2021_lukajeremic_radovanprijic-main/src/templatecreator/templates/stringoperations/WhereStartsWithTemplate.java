package templatecreator.templates.stringoperations;

import templatecreator.templates.AbstractTemplate;
import lombok.Getter;

@Getter

public class WhereStartsWithTemplate extends AbstractTemplate {
    private String columnName;
    private String pattern;

    public WhereStartsWithTemplate(String input){
        createTemplateMessage(input);
    }

    @Override
    public void createTemplateMessage(String input) {
        String inputParsing[] = input.split(",");
        String parsedColumn[];
        String parsedPattern[];

        parsedColumn = inputParsing[0].split("\"");
        parsedPattern = inputParsing[1].split("\"");
        columnName = parsedColumn[1];
        pattern = "'" + parsedPattern[1] + "'";
    }
}
