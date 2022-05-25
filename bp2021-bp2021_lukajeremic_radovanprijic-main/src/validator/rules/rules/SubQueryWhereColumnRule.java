package validator.rules.rules;

import compiler.Query;
import templatecreator.templates.AbstractTemplate;
import templatecreator.templates.projection.SelectTemplate;
import templatecreator.templates.subqueries.WhereEqQTemplate;
import templatecreator.templates.subqueries.WhereInQTemplate;
import validator.rules.AbstractRule;

import java.util.ArrayList;

public class SubQueryWhereColumnRule extends AbstractRule {
    public SubQueryWhereColumnRule(String message) {
        super(message);
        this.setMessage("Podupit mora raditi sa istom kolonom kao i upit u WhereInQ/WhereEqQ upitu.");
    }

    public String findSubQColumnName(Query subQuery){
        for (AbstractTemplate template : subQuery.getTemplates()){
            if (template instanceof SelectTemplate){
                return ((SelectTemplate) template).getColumns().get(0);//ako subQuery ima samo jednu kolonu uzimamo 0
            }
        }
        return null;
    }

    @Override
    public boolean examineQuery(Query query, ArrayList<Query> queries) {
        String whereQColumn;

        for (AbstractTemplate template : query.getTemplates()){//trazimo subQuery
            if (template instanceof WhereInQTemplate){
                whereQColumn = ((WhereInQTemplate) template).getColumnName();
                for(Query q : queries) {
                    if (((WhereInQTemplate) template).getQueryName().trim().equals(q.getName().trim())) {
                        if (whereQColumn.equals(findSubQColumnName(q))) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            } else if (template instanceof WhereEqQTemplate){
                whereQColumn = ((WhereEqQTemplate) template).getColumnName();
                for(Query q : queries) {
                    if (((WhereEqQTemplate) template).getQueryName().trim().equals(q.getName().trim())) {
                        return whereQColumn.equals(findSubQColumnName(q));
                    }
                }
            }
        }
        return true;
    }
}
