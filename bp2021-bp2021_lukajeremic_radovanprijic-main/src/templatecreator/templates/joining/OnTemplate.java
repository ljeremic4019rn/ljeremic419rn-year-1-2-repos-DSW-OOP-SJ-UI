package templatecreator.templates.joining;

import templatecreator.templates.AbstractTemplate;
import lombok.Getter;

@Getter

public class OnTemplate extends AbstractTemplate {
    private String firstColumnName;
    private String secondColumnName;
    private String operator;

    public OnTemplate(String input){
        createTemplateMessage(input);
    }

    @Override
    public void createTemplateMessage(String input) {
        String inputParsing[] = input.split(",");

        for(int i = 0; i < inputParsing.length; i++){
            String inputParsing2[] = inputParsing[i].split("\"");
            if(i == 0){
                firstColumnName = inputParsing2[1];
            } else if (i == 1) {
                operator = inputParsing2[1];
            } else {
                secondColumnName = inputParsing2[1];
            }
        }
    }
}
