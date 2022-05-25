package templatecreator.templates.aggregation;

import templatecreator.templates.AbstractTemplate;
import lombok.Getter;

import java.util.ArrayList;

@Getter

public class GroupByTemplate extends AbstractTemplate {
    private ArrayList<String> columns = new ArrayList<>();

    public GroupByTemplate(String input){
        createTemplateMessage(input);
    }

    @Override
    public void createTemplateMessage(String input) {
        String inputParsing[] = input.split(",");
        for(int i = 0; i < inputParsing.length; i++){
            String inputParsing2[] = inputParsing[i].split("\"");
            columns.add(inputParsing2[1]);
        }
    }
}
