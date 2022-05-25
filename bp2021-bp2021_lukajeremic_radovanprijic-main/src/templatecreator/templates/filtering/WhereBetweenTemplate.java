package templatecreator.templates.filtering;

import templatecreator.templates.AbstractTemplate;
import lombok.Getter;

@Getter

public class WhereBetweenTemplate extends AbstractTemplate {
    String columnName;
    String lowerBoundary;
    String upperBoundary;

    public WhereBetweenTemplate(String input){
        createTemplateMessage(input);
    }

    @Override
    public void createTemplateMessage(String input) {
        String inputParsing[] = input.split(",");
        String variables[];

        variables = inputParsing[0].split("\"");
        columnName = variables[1];
        lowerBoundary = inputParsing[1];
        variables = inputParsing[2].split("\\)");
        upperBoundary = variables[0];
    }
}
