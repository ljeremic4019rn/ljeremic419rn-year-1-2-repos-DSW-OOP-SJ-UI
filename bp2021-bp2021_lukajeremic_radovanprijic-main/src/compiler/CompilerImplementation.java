package compiler;

import appcore.AppCore;
import templatecreator.templates.AbstractTemplate;
import templatecreator.TemplateCreatorImplementation;

import java.util.ArrayList;

public class CompilerImplementation implements Compiler{
    private ArrayList<Query> queries;
    private ArrayList<AbstractTemplate> templates;

    public CompilerImplementation(){
        queries = new ArrayList<>();
        templates = new ArrayList<>();
    }

    @Override
    public void compileQuery(String input) {
        String tmp = removeNewRow(input);
        String inputParsing[] = tmp.split(";");

        for (int i = 0; i < inputParsing.length; i++) {

            String queryParsing[] = inputParsing[i].split(" ", 5);//new query
            queries.add(new Query());
            queries.get(queries.size() - 1).setName(queryParsing[1]);

            String querySyntaxParsing[] = queryParsing[4].split("\\.+(?![^\\(]*\\))");//sa .

            for (int k = 0; k < querySyntaxParsing.length; k++) {
                templates.add(AppCore.getInstance().getTemplateCreator().createTemplate(querySyntaxParsing[k]));
            }
            queries.get(queries.size() - 1).createQueryMessage(queries, templates);
            templates.clear();
        }

        System.out.println("SQL upit poslat bazi:\n" + queries.get(queries.size() - 1).getQueryMessage());

        try {
            AppCore.getInstance().readDataFromTable(queries.get(queries.size() - 1).getQueryMessage());
        }
        catch (Exception e){
            return;
        }
    }

    public String removeNewRow(String input){
        String output = "";
        String subString[] = input.split("\\n");

        for (String s : subString){
            output = output + s;
        }
        return output;
    }
}
