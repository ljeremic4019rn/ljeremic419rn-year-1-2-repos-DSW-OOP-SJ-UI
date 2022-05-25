package templatecreator.templates.filtering;

import templatecreator.templates.AbstractTemplate;
import lombok.Getter;

import java.util.ArrayList;

@Getter

public class ParametarListTemplate extends AbstractTemplate {
    private ArrayList<String> parameters = new ArrayList<>();

    public ParametarListTemplate(String input){
        createTemplateMessage(input);
    }

    @Override
    public void createTemplateMessage(String input) {
        String variables[];
        String inputParsing[] = input.split(",");

        for(int i = 0; i < inputParsing.length; i++){
            if(i != inputParsing.length - 1) {
                if (isNumeric(inputParsing[i])) {
                    parameters.add(inputParsing[i]);
                } else {
                   variables = inputParsing[i].split("\"");
                    parameters.add(variables[1]);
                }
            }
            else{
                variables = inputParsing[i].split("\\)");
                if (isNumeric(variables[0])) {
                    parameters.add(variables[0]);
                } else {
                    variables = variables[0].split("\"");
                    parameters.add(variables[1]);
                }
            }
        }
    }
}
