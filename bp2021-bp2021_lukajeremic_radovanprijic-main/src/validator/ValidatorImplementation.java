package validator;

import appcore.AppCore;
import compiler.Query;
import templatecreator.TemplateCreatorImplementation;
import templatecreator.templates.AbstractTemplate;
import validator.rules.AbstractRule;
import validator.rules.RuleInitialisation;

import javax.swing.*;
import java.util.ArrayList;

public class ValidatorImplementation implements Validator{
    private String warning;
    private boolean validation;
    private ArrayList<AbstractRule> rules;
    private ArrayList<Query> queries;
    private ArrayList<AbstractTemplate> templates;
    private RuleInitialisation ruleInitialisation;
    private int counter = 1;

    public ValidatorImplementation(){
        this.warning = "";
        this.validation = true;
        rules = new ArrayList<>();
        queries = new ArrayList<>();
        templates = new ArrayList<>();
        ruleInitialisation = new RuleInitialisation();
        rules.addAll(ruleInitialisation.initialiseRules());
    }

    @Override
    public void validateQuery(String input) {
        String tmp = removeNewRow(input);
        String inputParsing[] = tmp.split(";");

        try {
            for (int i = 0; i < inputParsing.length; i++) {

                String queryParsing[] = inputParsing[i].split(" ", 5);//new query
                queries.add(new Query());
                queries.get(queries.size() - 1).setName(queryParsing[1]);

                String querySyntaxParsing[] = queryParsing[4].split("\\.+(?![^\\(]*\\))");//sa .
                for (int k = 0; k < querySyntaxParsing.length; k++) {
                    if (!(AppCore.getInstance().getTemplateCreator().createTemplate(querySyntaxParsing[k]) == null)){
                        templates.add(AppCore.getInstance().getTemplateCreator().createTemplate(querySyntaxParsing[k]));
                    }
                    else return;
                }
                queries.get(queries.size() - 1).createQueryMessage(queries, templates);
                templates.clear();
            }
        }
        catch (Exception e){
            String message = "Imate sintaksnu gresku.\nProverite da li su vase promenljive deklarisane u sledecem formatu:       " +
                    "var <ime_promenljive> = new <skup_upita>;\n" + "Upiti unutar skupa moraju biti razdvojeni tackama.\n" +
                    "Reci u zagradama moraju biti unutar dvojnih navodnika (za brojeve ovo ne vazi) i razdvojene sa zarezima gde sintaksa zahteva.\n" +
                    "Dozvoljeno je raditi samo sa kolonama i tabelama iz HR baze.";
            JOptionPane.showMessageDialog(null, message,"Upozorenje",JOptionPane.WARNING_MESSAGE);
            return;
        }

        for(Query query : queries){//provera
            for(AbstractRule rule : rules){
                if(!rule.examineQuery(query, queries)){
                    validation = false;
                    warning =  warning + counter++ + ". " + rule.getMessage() + "\n";
                }
            }
        }
        if(validation == true) {
            AppCore.getInstance().getCompiler().compileQuery(input);
        } else {
            JOptionPane.showMessageDialog(null, warning,"Upozorenje",JOptionPane.WARNING_MESSAGE);
        }
        resetAttributes();
    }

    public void resetAttributes(){
        this.warning = "";
        this.validation = true;
        queries.clear();
        counter = 1;
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
