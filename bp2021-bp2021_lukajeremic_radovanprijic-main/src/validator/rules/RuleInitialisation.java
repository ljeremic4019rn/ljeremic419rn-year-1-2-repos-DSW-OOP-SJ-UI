package validator.rules;


import validator.rules.rules.*;

import java.util.ArrayList;

public class RuleInitialisation {
    private ArrayList<AbstractRule> rules = new ArrayList<>();
    String msg;

    public ArrayList initialiseRules(){
        rules.add(new AndOrRule(msg));
        rules.add(new HavingAggregationRule(msg));
        rules.add(new HavingGroupByRule(msg));
        rules.add(new JoinOnOrderingRule(msg));
        rules.add(new JoinOnTableColumnRule(msg));
        rules.add(new JoinTableRule(msg));
        rules.add(new SelectAggregGroupByRule(msg));
        rules.add(new SingleHavingRule(msg));
        rules.add(new SingleWhereRule(msg));
        rules.add(new SubQueryVariableRule(msg));
        rules.add(new SubQueryWhereColumnRule(msg));
        rules.add(new WhereInParamListRule(msg));
        rules.add(new WhereSelectColumnRule(msg));

        return rules;
    }



}
