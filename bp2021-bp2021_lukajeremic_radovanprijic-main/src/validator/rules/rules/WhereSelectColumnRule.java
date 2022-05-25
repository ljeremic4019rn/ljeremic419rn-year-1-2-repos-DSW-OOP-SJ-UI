package validator.rules.rules;

import compiler.Query;
import templatecreator.templates.AbstractTemplate;
import templatecreator.templates.filtering.*;
import templatecreator.templates.projection.SelectTemplate;
import templatecreator.templates.stringoperations.WhereContainsTemplate;
import templatecreator.templates.stringoperations.WhereEndsWithTemplate;
import templatecreator.templates.stringoperations.WhereStartsWithTemplate;
import templatecreator.templates.subqueries.WhereEqQTemplate;
import templatecreator.templates.subqueries.WhereInQTemplate;
import validator.rules.AbstractRule;

import java.util.ArrayList;

public class WhereSelectColumnRule extends AbstractRule {
    public WhereSelectColumnRule(String message) {
        super(message);
        this.setMessage("Kolona u WHERE upitu mora biti selektovana.");
    }

    @Override
    public boolean examineQuery(Query query, ArrayList<Query> queries) {
        ArrayList<String> selectedColumns = new ArrayList<>();
        boolean selectedFullTable = true;
        boolean hasWhere = false;
        boolean validator = true;


        for (AbstractTemplate template : query.getTemplates()){//uzimamo selektovane kolone
            if (template instanceof SelectTemplate){
                selectedFullTable = false;
                selectedColumns = ((SelectTemplate) template).getColumns();
            }
        }

        if (!selectedFullTable){
            for (AbstractTemplate template : query.getTemplates()){
                if (template instanceof WhereTemplate) {
                    if (!selectedColumns.contains(((WhereTemplate) template).getColumnName())){
                        validator = false;
                    }
                }
                else if (template instanceof WhereBetweenTemplate){
                    if (!selectedColumns.contains(((WhereBetweenTemplate) template).getColumnName())){
                        validator = false;
                    }
                }
                else if (template instanceof WhereInTemplate){
                    if (!selectedColumns.contains(((WhereInTemplate) template).getColumnName())){
                        validator = false;
                    }
                }
                else if (template instanceof WhereEndsWithTemplate){
                    if (!selectedColumns.contains(((WhereEndsWithTemplate) template).getColumnName())){
                        validator = false;
                    }
                }
                else if (template instanceof WhereStartsWithTemplate){
                    if (!selectedColumns.contains(((WhereStartsWithTemplate) template).getColumnName())){
                        validator = false;
                    }
                }
                else if (template instanceof WhereContainsTemplate){
                    if (!selectedColumns.contains(((WhereContainsTemplate) template).getColumnName())){
                        validator = false;
                    }
                }/*
                else if (template instanceof WhereInQTemplate){
                    if (!selectedColumns.contains(((WhereInQTemplate) template).getColumnName())){
                        System.out.println("whereInQ");
                        validator = false;
                    }
                }
                else if (template instanceof WhereEqQTemplate){
                    if (!selectedColumns.contains(((WhereEqQTemplate) template).getColumnName())){
                        System.out.println("whereEqQ");
                        validator = false;
                    }
                }*/
                else if (template instanceof AndWhereTemplate){
                    if (!selectedColumns.contains(((AndWhereTemplate) template).getColumnName())){
                        validator = false;
                    }
                }
                else if (template instanceof OrWhereTemplate){
                    if (!selectedColumns.contains(((OrWhereTemplate) template).getColumnName())){
                        validator = false;
                    }
                }
            }

            if (!validator){
                return false;
            }
            return true;

        }
        else{//ako je *
            return true;
        }


    }
}
