package validator.rules.rules;

import compiler.Query;
import templatecreator.templates.AbstractTemplate;
import templatecreator.templates.aggregation.HavingTemplate;
import templatecreator.templates.subqueries.WhereEqQTemplate;
import templatecreator.templates.subqueries.WhereInQTemplate;
import validator.rules.AbstractRule;

import java.util.ArrayList;

public class SubQueryVariableRule extends AbstractRule {
    public SubQueryVariableRule(String message) {
        super(message);
        this.setMessage("Promenljiva koja se koristi za podupit mora biti deklarisana.");
    }

    @Override
    public boolean examineQuery(Query query, ArrayList<Query> queries) {

        for (AbstractTemplate template : query.getTemplates()){
            if (template instanceof WhereInQTemplate){
                for(Query q : queries) {
                    if(((WhereInQTemplate)template).getQueryName().trim().equals(q.getName().trim()))
                        return true;
                }
                return false;
            } else if (template instanceof WhereEqQTemplate){
                for(Query q : queries) {
                    if(((WhereEqQTemplate)template).getQueryName().trim().equals(q.getName().trim()))
                        return true;
                }
                return false;
            }
        }

        return true;
    }
}
