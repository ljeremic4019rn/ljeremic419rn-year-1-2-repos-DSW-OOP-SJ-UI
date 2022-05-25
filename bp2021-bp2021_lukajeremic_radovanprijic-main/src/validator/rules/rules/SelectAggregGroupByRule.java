package validator.rules.rules;

import compiler.Query;
import templatecreator.templates.AbstractTemplate;
import templatecreator.templates.aggregation.*;
import templatecreator.templates.projection.SelectTemplate;
import validator.rules.AbstractRule;

import java.util.ArrayList;

public class SelectAggregGroupByRule extends AbstractRule {
    public SelectAggregGroupByRule(String message) {
        super(message);
        this.setMessage("Sve sto je selektovano, a nije pod funkcijom agregacije, mora uci u GROUP BY upit.");
    }

    @Override
    public boolean examineQuery(Query query, ArrayList<Query> queries) {
        ArrayList<String> selectedColumns = new ArrayList<>();

        for (AbstractTemplate template : query.getTemplates()){//uzimamo selektovane kolone
            if (template instanceof SelectTemplate){
                selectedColumns = ((SelectTemplate) template).getColumns();
            }
        }

        for (AbstractTemplate template :query.getTemplates()){//skalnjamo kolone koje se nalaza u agregaciji da nam ostanu samo one koje nisu
            if (template instanceof AvgTemplate){
              selectedColumns.remove(((AvgTemplate) template).getColumnName());
              selectedColumns.remove(((AvgTemplate) template).getAlias());
            }
            if (template instanceof CountTemplate){
                selectedColumns.remove(((CountTemplate) template).getColumnName());
                selectedColumns.remove(((CountTemplate) template).getAlias());
            }
            if (template instanceof MaxTemplate){
                selectedColumns.remove(((MaxTemplate) template).getColumnName());
                selectedColumns.remove(((MaxTemplate) template).getAlias());
            }
            if (template instanceof MinTemplate){
                selectedColumns.remove(((MinTemplate) template).getColumnName());
                selectedColumns.remove(((MinTemplate) template).getAlias());
            }
        }


        for (AbstractTemplate template :query.getTemplates()){
            if (template instanceof GroupByTemplate){
                if (selectedColumns.equals(((GroupByTemplate) template).getColumns())){
                    return true;
                }
                else
                    return false;
            }
        }
        return true;
    }
}
