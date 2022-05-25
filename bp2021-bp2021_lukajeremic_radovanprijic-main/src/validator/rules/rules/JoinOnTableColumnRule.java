package validator.rules.rules;

import compiler.Query;
import templatecreator.templates.AbstractTemplate;
import templatecreator.templates.aggregation.HavingTemplate;
import templatecreator.templates.generalquery.NewQueryTemplate;
import templatecreator.templates.joining.OnTemplate;
import validator.rules.AbstractRule;

import java.util.ArrayList;

public class JoinOnTableColumnRule extends AbstractRule {
    public JoinOnTableColumnRule(String message) {
        super(message);
        this.setMessage("Prva navedena tabela u ON upitu mora biti jednaka tabeli iz koje selektujemo, a druga razlicita, i moraju se poklapati po imenu kolone.");
    }

    @Override
    public boolean examineQuery(Query query, ArrayList<Query> queries) {
        boolean correctTableAndColumnNames = false;
        String selectionTableName = null;

        for (AbstractTemplate template : query.getTemplates()){
            if (template instanceof NewQueryTemplate){
                selectionTableName = ((NewQueryTemplate)template).getTableName().trim();
            }
        }

        for (AbstractTemplate template : query.getTemplates()){
            if (template instanceof OnTemplate){

                String parsedColumn1[] = ((OnTemplate)template).getFirstColumnName().trim().split("\\.");
                String parsedColumn2[] = ((OnTemplate)template).getSecondColumnName().trim().split("\\.");

                if(!parsedColumn1[0].equals(parsedColumn2[0]) && parsedColumn1[1].equals(parsedColumn2[1]) &&
                        parsedColumn1[0].equals(selectionTableName) && !parsedColumn2[0].equals(selectionTableName))
                    correctTableAndColumnNames = true;

                if(correctTableAndColumnNames)
                    return true;
                else
                    return false;
            }
        }

        return true;
    }
}
