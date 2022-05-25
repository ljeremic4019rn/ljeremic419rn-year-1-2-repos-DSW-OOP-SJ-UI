package templatecreator.templates.subqueries;

import templatecreator.templates.AbstractTemplate;
import lombok.Getter;

@Getter

public class WhereEqQTemplate extends AbstractTemplate {
    private String columnName;
    private String queryName;

    public WhereEqQTemplate(String input){
        createTemplateMessage(input);
    }

    @Override
    public void createTemplateMessage(String input) {
        String inputParsing[] = input.split(",");

        String inputParsing2[] = inputParsing[0].split("\"");
        columnName = inputParsing2[1];

        String inputParsing3[] = inputParsing[1].split("\\)");
        queryName = inputParsing3[0].replace(" ", "");
    }
}
