package templatecreator.templates.sorting;

import templatecreator.templates.AbstractTemplate;
import lombok.Getter;

import java.util.ArrayList;
@Getter
public class OrderByTemplate extends AbstractTemplate {
    private ArrayList<String> columns = new ArrayList<>();

    public OrderByTemplate (String input){
        createTemplateMessage(input);
    }


    @Override
    public void createTemplateMessage(String input) {
        String inputParsing[] = input.split(",");
        String tmp = "";

        for(int i = 0; i < inputParsing.length; i++){
            String inputParsing2[] = inputParsing[i].split("\"");
            columns.add(inputParsing2[1]);
        }
    }
}
