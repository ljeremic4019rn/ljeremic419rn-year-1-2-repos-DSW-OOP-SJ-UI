package validator.rules.rules;

import compiler.Query;
import templatecreator.templates.AbstractTemplate;
import templatecreator.templates.aggregation.HavingTemplate;
import templatecreator.templates.generalquery.NewQueryTemplate;
import templatecreator.templates.joining.JoinTemplate;
import validator.rules.AbstractRule;

import java.util.ArrayList;

public class JoinTableRule extends AbstractRule {
    public JoinTableRule(String message) {
        super(message);
        this.setMessage("Tabela koja je navedena u JOIN upitu mora biti jednaka tabeli iz koje selektujemo.");
    }

    @Override
    public boolean examineQuery(Query query, ArrayList<Query> queries) {
        String joinTableName = "";
        String tableName = "";
        boolean hasJoin = false;

        for (AbstractTemplate template : query.getTemplates()){
            if (template instanceof NewQueryTemplate){
                tableName = ((NewQueryTemplate) template).getTableName().trim();
            }
            else if (template instanceof JoinTemplate){
                joinTableName = ((JoinTemplate) template).getTableName().trim();
                hasJoin = true;
            }
        }

        if (!tableName.equals(joinTableName) && hasJoin)
            return false;
        return true;
    }
}
