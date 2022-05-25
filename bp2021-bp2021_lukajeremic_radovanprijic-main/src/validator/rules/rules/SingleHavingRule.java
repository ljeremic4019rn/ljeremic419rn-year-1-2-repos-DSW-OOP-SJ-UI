package validator.rules.rules;

import compiler.Query;
import templatecreator.templates.AbstractTemplate;
import templatecreator.templates.aggregation.HavingTemplate;
import templatecreator.templates.filtering.WhereTemplate;
import validator.rules.AbstractRule;

import java.util.ArrayList;

public class SingleHavingRule extends AbstractRule {
    public SingleHavingRule(String message) {
        super(message);
        this.setMessage("Mozete imati samo jedan HAVING upit, ostali HAVING tipovi upita moraju biti AND/OR HAVING upiti.");
    }

    @Override
    public boolean examineQuery(Query query, ArrayList<Query> queries) {
        int havingCounter = 0;

        for (AbstractTemplate template : query.getTemplates()){
            if (template instanceof HavingTemplate){
                havingCounter++;
            }
        }

        if (havingCounter > 1){
            return false;
        }
        else{
            return true;
        }
    }
}
