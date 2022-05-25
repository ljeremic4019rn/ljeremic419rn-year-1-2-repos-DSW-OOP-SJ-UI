package templatecreator.templates.filtering;

import templatecreator.templates.AbstractTemplate;
import lombok.Getter;

@Getter

public class OrWhereTemplate extends AbstractTemplate {
    String columnName;
    String operator;
    String criterion;

    public OrWhereTemplate(String input){
        createTemplateMessage(input);
    }

    @Override
    public void createTemplateMessage(String input) {
        String inputParsing[] = input.split(",");

        for(int i = 0; i < inputParsing.length; i++){
            if (i  < 2) {
                String inputParsing2[] = inputParsing[i].split("\"");
                if (i == 0){
                    columnName = inputParsing2[1];
                }
                if (i == 1){
                    operator = inputParsing2[1];
                }
            }
            else{//criterion
                String tmp3[] = inputParsing[i].split("\\)");//sklanjanje zagrade
                if (isNumeric(tmp3[0])){//ako je broj
                    criterion = tmp3[0];
                }
                else{
                    String tmp2[] = inputParsing[i].split("\"");
                    criterion = "'" + tmp2[1] + "'";
                }
            }
        }
    }
}
