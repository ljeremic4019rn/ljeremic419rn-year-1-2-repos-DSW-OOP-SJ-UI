package validator.rules.rules;

import compiler.Query;
import templatecreator.templates.AbstractTemplate;
import templatecreator.templates.aggregation.AndHavingTemplate;
import templatecreator.templates.aggregation.HavingTemplate;
import templatecreator.templates.aggregation.OrHavingTemplate;
import templatecreator.templates.filtering.*;
import templatecreator.templates.stringoperations.WhereContainsTemplate;
import templatecreator.templates.stringoperations.WhereEndsWithTemplate;
import templatecreator.templates.stringoperations.WhereStartsWithTemplate;
import templatecreator.templates.subqueries.WhereEqQTemplate;
import templatecreator.templates.subqueries.WhereInQTemplate;
import validator.rules.AbstractRule;

import java.util.ArrayList;

public class AndOrRule extends AbstractRule {

    public AndOrRule(String message) {
        super(message);
        this.setMessage("Ne mozete imati AND/OR WHERE/HAVING upit(e) ako nemate i obicne WHERE/HAVING upite.");
    }

    @Override
    public boolean examineQuery(Query query, ArrayList<Query> queries) {
        boolean imaWhere = false;
        boolean imaHaving = false;

        for (AbstractTemplate template : query.getTemplates()){
            if (template instanceof WhereTemplate || template instanceof WhereBetweenTemplate || template instanceof WhereInTemplate ||
                    template instanceof WhereStartsWithTemplate || template instanceof WhereEndsWithTemplate ||
                    template instanceof WhereContainsTemplate || template instanceof WhereInQTemplate || template instanceof WhereEqQTemplate){
                imaWhere = true;
            } else if (template instanceof HavingTemplate)
                imaHaving = true;
        }

        for (AbstractTemplate template : query.getTemplates()){
            if(template instanceof AndWhereTemplate || template instanceof OrWhereTemplate){
                if(!imaWhere)
                    return false;
            } else if (template instanceof AndHavingTemplate || template instanceof OrHavingTemplate){
                if(!imaHaving)
                    return false;
            }
        }
        return true;
    }
}
