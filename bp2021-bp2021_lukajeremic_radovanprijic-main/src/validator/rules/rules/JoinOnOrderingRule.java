package validator.rules.rules;

import compiler.Query;
import templatecreator.templates.AbstractTemplate;
import templatecreator.templates.aggregation.HavingTemplate;
import templatecreator.templates.joining.JoinTemplate;
import templatecreator.templates.joining.OnTemplate;
import validator.rules.AbstractRule;

import java.util.ArrayList;

public class JoinOnOrderingRule extends AbstractRule {
    public JoinOnOrderingRule(String message) {
        super(message);
        this.setMessage("Posle JOIN upita u sintaksi mora uvek slediti ON upit.");
    }

    @Override
    public boolean examineQuery(Query query, ArrayList<Query> queries) {

        for(int i = 0; i < query.getTemplates().size(); i++){
            if(query.getTemplates().get(i) instanceof JoinTemplate){
                if(query.getTemplates().get(i+1) instanceof OnTemplate)
                    return true;
                else
                    return false;
            }
        }

        return true; //uopste nema join templejta u listi
    }
}
