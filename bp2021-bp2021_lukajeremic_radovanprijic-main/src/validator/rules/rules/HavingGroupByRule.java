package validator.rules.rules;

import compiler.Query;
import templatecreator.templates.AbstractTemplate;
import templatecreator.templates.aggregation.GroupByTemplate;
import templatecreator.templates.aggregation.HavingTemplate;
import templatecreator.templates.filtering.WhereTemplate;
import validator.rules.AbstractRule;

import java.util.ArrayList;

public class HavingGroupByRule extends AbstractRule {
    public HavingGroupByRule(String message) {
        super(message);
        this.setMessage("Ako vas konacni SQL upit sadrzi HAVING upit, onda mora takodje sadrzati i GROUP BY upit.");
    }

    @Override
    public boolean examineQuery(Query query, ArrayList<Query> queries) {
        boolean hasHave = false;
        boolean hasGroupBy = false;

        for (AbstractTemplate template : query.getTemplates()){//trazi having
            if (template instanceof HavingTemplate){
                hasHave = true;
            }
        }

        if (hasHave){
            for (AbstractTemplate template : query.getTemplates()){//ako nadjemo having razimo group by
                if (template instanceof GroupByTemplate){
                    hasGroupBy = true;//ako ga nadjemo
                }
            }
            if (hasGroupBy){//ima
                return true;
            }
            else{
                return false;//nema group
            }
        }
        else{
            return true;//nije imalo having
        }
    }
}
