package validator.rules.rules;

import compiler.Query;
import templatecreator.templates.AbstractTemplate;
import templatecreator.templates.filtering.WhereBetweenTemplate;
import templatecreator.templates.filtering.WhereInTemplate;
import templatecreator.templates.filtering.WhereTemplate;
import templatecreator.templates.stringoperations.WhereContainsTemplate;
import templatecreator.templates.stringoperations.WhereEndsWithTemplate;
import templatecreator.templates.stringoperations.WhereStartsWithTemplate;
import templatecreator.templates.subqueries.WhereEqQTemplate;
import templatecreator.templates.subqueries.WhereInQTemplate;
import validator.rules.AbstractRule;

import java.util.ArrayList;

public class SingleWhereRule extends AbstractRule {

    public SingleWhereRule(String message) {
        super(message);
        this.setMessage("Mozete imati samo jedan WHERE upit, ostali WHERE tipovi upita moraju biti AND/OR WHERE upiti.");
    }

    @Override
    public boolean examineQuery(Query query, ArrayList<Query> queries) {
        int whereCounter = 0;

        for (AbstractTemplate template : query.getTemplates()){
            if (template instanceof WhereTemplate || template instanceof WhereBetweenTemplate || template instanceof WhereInTemplate ||
                    template instanceof WhereStartsWithTemplate || template instanceof WhereEndsWithTemplate ||
                        template instanceof WhereContainsTemplate || template instanceof WhereInQTemplate || template instanceof WhereEqQTemplate){
                whereCounter++;
            }
        }

        if (whereCounter > 1){
            return false;
        }
        else{
            return true;
        }
    }
}
