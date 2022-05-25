package templatecreator.templates.aggregation;

import templatecreator.templates.AbstractTemplate;
import lombok.Getter;

@Getter

public class MinTemplate extends AbstractTemplate {
    private String columnName;
    private String alias;

    public MinTemplate(String input){
        createTemplateMessage(input);
    }

    @Override
    public void createTemplateMessage(String input) {
        if(input.contains(",")){
            String inputParsing[] = input.split(",");

            String inputParsing2[] = inputParsing[0].split("\"");
            columnName = inputParsing2[1];

            String inputParsing3[] = inputParsing[1].split("\"");
            alias = inputParsing3[1];
        } else {
            String inputParsing4[] = input.split("\"");
            columnName = inputParsing4[1];
        }
    }
}
