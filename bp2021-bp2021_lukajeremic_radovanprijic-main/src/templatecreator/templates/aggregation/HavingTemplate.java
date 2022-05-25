package templatecreator.templates.aggregation;

import templatecreator.templates.AbstractTemplate;
import lombok.Getter;

@Getter

public class HavingTemplate extends AbstractTemplate {
    private String alias;
    private String operator;
    private String criterion;

    public HavingTemplate(String input){
        createTemplateMessage(input);
    }

    @Override
    public void createTemplateMessage(String input) {
        String inputParsing[] = input.split(",");

        for(int i = 0; i < inputParsing.length; i++){
            if (i  < 2) {
                String inputParsing2[] = inputParsing[i].split("\"");
                if (i == 0){
                    alias = inputParsing2[1];
                }
                if (i == 1){
                    operator = inputParsing2[1];
                }
            }
            else{
                String numericCriterion[] = inputParsing[i].split("\\)");
                if (isNumeric(numericCriterion[0])){
                    criterion = numericCriterion[0];
                }
                else{
                    String stringCriterion[] = inputParsing[i].split("\"");
                    criterion = "'" + stringCriterion[1] + "'";
                }
            }
        }
    }
}
