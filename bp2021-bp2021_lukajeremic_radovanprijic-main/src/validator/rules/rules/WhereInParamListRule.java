package validator.rules.rules;

import compiler.Query;
import templatecreator.templates.filtering.ParametarListTemplate;
import templatecreator.templates.filtering.WhereInTemplate;
import templatecreator.templates.joining.JoinTemplate;
import templatecreator.templates.joining.OnTemplate;
import validator.rules.AbstractRule;

import java.util.ArrayList;

public class WhereInParamListRule extends AbstractRule {
    public WhereInParamListRule(String message) {
        super(message);
        this.setMessage("Posle WHERE IN upita u sintaksi mora uvek slediti PARAMETAR LIST upit.");
    }

    @Override
    public boolean examineQuery(Query query, ArrayList<Query> queries) {

        for(int i = 0; i < query.getTemplates().size(); i++){
            if(query.getTemplates().get(i) instanceof WhereInTemplate){
                if(query.getTemplates().get(i+1) instanceof ParametarListTemplate)
                    return true;
                else
                    return false;
            }
        }

        return true;
    }
}
