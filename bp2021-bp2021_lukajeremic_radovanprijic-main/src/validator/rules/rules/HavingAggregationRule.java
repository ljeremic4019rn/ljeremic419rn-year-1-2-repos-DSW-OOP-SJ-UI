package validator.rules.rules;

import compiler.Query;
import templatecreator.templates.AbstractTemplate;
import templatecreator.templates.aggregation.*;
import validator.rules.AbstractRule;

import java.util.ArrayList;

public class HavingAggregationRule extends AbstractRule {
    public HavingAggregationRule(String message) {
        super(message);
        this.setMessage("U (AND/OR) HAVING upitu mozete raditi samo sa deklarisanom funkcijom agregacije koja takodje ima i isti alias kao pomenuti upit.");
    }

    @Override
    public boolean examineQuery(Query query, ArrayList<Query> queries) {
        boolean HavingIndicator = false;
        boolean AndHavingIndicator = false;
        boolean OrHavingIndicator = false;
        boolean imaHaving = false;
        boolean imaAndHaving = false;
        boolean imaOrHaving = false;

        for (AbstractTemplate template : query.getTemplates()){//trazi having
            if(template instanceof HavingTemplate || template instanceof AndHavingTemplate || template instanceof OrHavingTemplate){

                if (template instanceof HavingTemplate){
                    imaHaving = true;
                    for(AbstractTemplate t : query.getTemplates()){
                        if(t instanceof AvgTemplate){
                            if(((AvgTemplate) t).getAlias() != null && ((HavingTemplate)template).getAlias().trim().equals(((AvgTemplate) t).getAlias().trim())){
                                HavingIndicator = true;
                            }
                        } else if(t instanceof CountTemplate){
                            if(((CountTemplate) t).getAlias() != null && ((HavingTemplate)template).getAlias().trim().equals(((CountTemplate) t).getAlias().trim())){
                                HavingIndicator = true;
                            }
                        } else if(t instanceof MinTemplate){
                            if(((MinTemplate) t).getAlias() != null && ((HavingTemplate)template).getAlias().trim().equals(((MinTemplate) t).getAlias().trim())){
                                HavingIndicator = true;
                            }
                        } else if(t instanceof MaxTemplate){
                            if(((MaxTemplate) t).getAlias() != null && ((HavingTemplate)template).getAlias().trim().equals(((MaxTemplate) t).getAlias().trim())){
                                HavingIndicator = true;
                            }
                        }
                    }

                } else if (template instanceof AndHavingTemplate){
                    imaAndHaving = true;
                    for(AbstractTemplate t : query.getTemplates()){
                        if(t instanceof AvgTemplate){
                            if(((AvgTemplate) t).getAlias() != null && ((AndHavingTemplate)template).getAlias().trim().equals(((AvgTemplate) t).getAlias().trim())){
                                AndHavingIndicator = true;
                            }
                        } else if(t instanceof CountTemplate){
                            if(((CountTemplate) t).getAlias() != null && ((AndHavingTemplate)template).getAlias().trim().equals(((CountTemplate) t).getAlias().trim())){
                                AndHavingIndicator = true;
                            }
                        } else if(t instanceof MinTemplate){
                            if(((MinTemplate) t).getAlias() != null && ((AndHavingTemplate)template).getAlias().trim().equals(((MinTemplate) t).getAlias().trim())){
                                AndHavingIndicator = true;
                            }
                        } else if(t instanceof MaxTemplate){
                            if(((MaxTemplate) t).getAlias() != null && ((AndHavingTemplate)template).getAlias().trim().equals(((MaxTemplate) t).getAlias().trim())){
                                AndHavingIndicator = true;
                            }
                        }
                    }

                } else if (template instanceof OrHavingTemplate){
                    imaOrHaving = true;
                    for(AbstractTemplate t : query.getTemplates()){
                        if(t instanceof AvgTemplate){
                            if(((AvgTemplate) t).getAlias() != null && ((OrHavingTemplate)template).getAlias().trim().equals(((AvgTemplate) t).getAlias().trim())){
                                OrHavingIndicator = true;
                            }
                        } else if(t instanceof CountTemplate){
                            if(((CountTemplate) t).getAlias() != null && ((OrHavingTemplate)template).getAlias().trim().equals(((CountTemplate) t).getAlias().trim())){
                                OrHavingIndicator = true;
                            }
                        } else if(t instanceof MinTemplate){
                            if(((MinTemplate) t).getAlias() != null && ((OrHavingTemplate)template).getAlias().trim().equals(((MinTemplate) t).getAlias().trim())){
                                OrHavingIndicator = true;
                            }
                        } else if(t instanceof MaxTemplate){
                            if(((MaxTemplate) t).getAlias() != null && ((OrHavingTemplate)template).getAlias().trim().equals(((MaxTemplate) t).getAlias().trim())){
                                OrHavingIndicator = true;
                            }
                        }
                    }
                }
            }
        }
        if(imaHaving){
            if(!HavingIndicator)
                return false;
        }
        if(imaAndHaving){
            if(!AndHavingIndicator)
                return false;
        }
        if(imaOrHaving){
            if(!OrHavingIndicator)
                return false;
        }

        return true;
    }
}
