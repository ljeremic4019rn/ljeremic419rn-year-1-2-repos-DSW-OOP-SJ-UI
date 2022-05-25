package compiler;

import templatecreator.templates.AbstractTemplate;
import templatecreator.templates.aggregation.*;
import templatecreator.templates.filtering.*;
import templatecreator.templates.generalquery.NewQueryTemplate;
import templatecreator.templates.joining.JoinTemplate;
import templatecreator.templates.joining.OnTemplate;
import templatecreator.templates.projection.SelectTemplate;
import templatecreator.templates.sorting.OrderByDescTemplate;
import templatecreator.templates.sorting.OrderByTemplate;
import templatecreator.templates.stringoperations.WhereContainsTemplate;
import templatecreator.templates.stringoperations.WhereEndsWithTemplate;
import templatecreator.templates.stringoperations.WhereStartsWithTemplate;
import templatecreator.templates.subqueries.WhereEqQTemplate;
import templatecreator.templates.subqueries.WhereInQTemplate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.AbstractList;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor

public class Query {
    private String name;
    private String queryMessage;
    private ArrayList<AbstractTemplate> templates = new ArrayList<>();


    public void createQueryMessage(ArrayList<Query> queries, ArrayList<AbstractTemplate> templatesToUse){
            templates.addAll(templatesToUse);
            selectionQueries();
            joiningQueries();
            subQueries(queries);
            stringQueries();
            filteringQueries();
            groupByQueries();
            havingQueries();
            orderByQueries();
    }

    public void selectionQueries(){
        NewQueryTemplate newTemplate = null;
        SelectTemplate selectTemplate = null;
        String columnsAndAliases = "";
        boolean indicator = true;

        for(AbstractTemplate template : templates){
            if(template instanceof NewQueryTemplate){
                newTemplate = (NewQueryTemplate) template;//napravimo objekat da izvucemo argumente iz njih
            }
            else if(template instanceof SelectTemplate){
                selectTemplate = (SelectTemplate) template;

                for(String column : selectTemplate.getColumns()){//projemo kroz sve selektovane kolone i proverimo da li ima agregaciju
                    for(AbstractTemplate t : templates){
                        if(t instanceof AvgTemplate){
                            if(((AvgTemplate) t).getAlias() != null && column.trim().equals(((AvgTemplate) t).getAlias().trim()) && !columnsAndAliases.contains(column)){
                                columnsAndAliases = columnsAndAliases + "AVG " + "(" + ((AvgTemplate) t).getColumnName() + ") " + ((AvgTemplate) t).getAlias() + ", ";
                            }
                        } else if(t instanceof CountTemplate){
                            if(((CountTemplate) t).getAlias() != null && column.trim().equals(((CountTemplate) t).getAlias().trim()) && !columnsAndAliases.contains(column)){
                                columnsAndAliases = columnsAndAliases + "COUNT " + "(" + ((CountTemplate) t).getColumnName() + ") " + ((CountTemplate) t).getAlias() + ", ";
                            }
                        } else if(t instanceof MinTemplate){
                            if(((MinTemplate) t).getAlias() != null && column.trim().equals(((MinTemplate) t).getAlias().trim()) && !columnsAndAliases.contains(column)){
                                columnsAndAliases = columnsAndAliases + "MIN " + "(" + ((MinTemplate) t).getColumnName() + ") " + ((MinTemplate) t).getAlias() + ", ";
                            }
                        } else if(t instanceof MaxTemplate){
                            if(((MaxTemplate) t).getAlias() != null && column.trim().equals(((MaxTemplate) t).getAlias().trim()) && !columnsAndAliases.contains(column)){
                                columnsAndAliases = columnsAndAliases + "MAX " + "(" + ((MaxTemplate) t).getColumnName() + ") " + ((MaxTemplate) t).getAlias() + ", ";
                            }
                        } else{
                            if(!columnsAndAliases.contains(column))
                                for(AbstractTemplate tmp : templates){//ovde proveravamo za obicne kolone a ne za funkcije agregacije
                                    if(tmp instanceof AvgTemplate){
                                        if(((AvgTemplate) tmp).getAlias() != null && column.trim().equals(((AvgTemplate) tmp).getAlias().trim())){
                                            indicator = false;
                                        }
                                    } else if(tmp instanceof CountTemplate){
                                        if(((CountTemplate) tmp).getAlias() != null && column.trim().equals(((CountTemplate) tmp).getAlias().trim())){
                                            indicator = false;
                                        }
                                    } else if(tmp instanceof MinTemplate){
                                        if(((MinTemplate) tmp).getAlias() != null && column.trim().equals(((MinTemplate) tmp).getAlias().trim())){
                                            indicator = false;
                                        }
                                    } else if(tmp instanceof MaxTemplate){
                                        if(((MaxTemplate) tmp).getAlias() != null && column.trim().equals(((MaxTemplate) tmp).getAlias().trim())){
                                            indicator = false;
                                        }
                                    }
                                }
                            if(indicator && !columnsAndAliases.contains(column))
                                columnsAndAliases = columnsAndAliases + column + ", ";
                            indicator = true;
                        }
                    }
                }//for 2

                for(AbstractTemplate t : templates) {
                    if (t instanceof AvgTemplate) {
                        if (((AvgTemplate) t).getAlias() == null) {
                            columnsAndAliases = columnsAndAliases + "AVG " + "(" + ((AvgTemplate) t).getColumnName() + "), ";
                        }
                    } else if (t instanceof CountTemplate) {
                        if (((CountTemplate) t).getAlias() == null) {
                            columnsAndAliases = columnsAndAliases + "COUNT " + "(" + ((CountTemplate) t).getColumnName() + "), ";
                        }
                    } else if (t instanceof MinTemplate) {
                        if (((MinTemplate) t).getAlias() == null) {
                            columnsAndAliases = columnsAndAliases + "MIN " + "(" + ((MinTemplate) t).getColumnName() + "), ";
                        }
                    } else if (t instanceof MaxTemplate) {
                        if (((MaxTemplate) t).getAlias() == null) {
                            columnsAndAliases = columnsAndAliases + "MAX " + "(" + ((MaxTemplate) t).getColumnName() + "), ";
                        }
                    }
                }
            }
        }

        this.setQueryMessage("SELECT * FROM " + newTemplate.getTableName() + " ");

        if(!columnsAndAliases.equals("")){//ako nije prazna stavicemo kolone umsesto *
            columnsAndAliases = columnsAndAliases.substring(0, columnsAndAliases.lastIndexOf(","));
            this.setQueryMessage(this.getQueryMessage().replace("*", columnsAndAliases));
        }
    }

    public void joiningQueries(){
        OnTemplate onTemplate = null;
        boolean imaOn = false;
        String tableName = null;
        String columnOne[];
        String columnTwo[];

        for(AbstractTemplate template : templates) {
            if (template instanceof JoinTemplate) {
                tableName = ((JoinTemplate)template).getTableName();
            }
            if (template instanceof OnTemplate) {
                imaOn = true;
                onTemplate = (OnTemplate) template;
                columnOne = ((OnTemplate)template).getFirstColumnName().split("\\.");//tabela.kolona (hocemo samo kolonu)
                columnTwo = ((OnTemplate)template).getSecondColumnName().split("\\.");

                if(tableName.trim().equals(columnOne[0].trim()))//uzimamo ime druge tabele
                    tableName = columnTwo[0].trim();
            }
        }
        if(imaOn){
            this.setQueryMessage(this.getQueryMessage() + "JOIN " + tableName + " ON " + onTemplate.getFirstColumnName() + " "
                    + onTemplate.getOperator() + " " + onTemplate.getSecondColumnName() + " ");
        }
    }

    public void subQueries(AbstractList<Query> queries){
        String columnName = null;
        String subqueryName = null;
        String subqueryMessage = null;

        for(AbstractTemplate template : templates) {
            if (template instanceof WhereInQTemplate) {
                columnName = ((WhereInQTemplate)template).getColumnName().trim();
                subqueryName = ((WhereInQTemplate)template).getQueryName().trim();

                for(Query q : queries){//nadjemo query sa istim imenom kao upisano
                    if(subqueryName.equals(q.getName()))
                        subqueryMessage = q.getQueryMessage();
                }
                this.setQueryMessage(this.getQueryMessage() + "WHERE " + columnName + " IN (" + subqueryMessage + ") ");//stavimo njegov message
            }
            else if (template instanceof WhereEqQTemplate) {
                columnName = ((WhereEqQTemplate)template).getColumnName().trim();
                subqueryName = ((WhereEqQTemplate)template).getQueryName().trim();

                for(Query q : queries){
                    if(subqueryName.equals(q.getName()))
                        subqueryMessage = q.getQueryMessage();
                }
                this.setQueryMessage(this.getQueryMessage() + "WHERE " + columnName + " = ALL (" + subqueryMessage + ") ");
            }
        }
    }

    public void stringQueries(){
        for(AbstractTemplate template : templates) {
            if (template instanceof WhereStartsWithTemplate) {
                this.setQueryMessage(this.getQueryMessage() + "WHERE " + ((WhereStartsWithTemplate)template).getColumnName() +
                        " LIKE '%' + " + ((WhereStartsWithTemplate)template).getPattern() + " ");
            }
            else if (template instanceof WhereEndsWithTemplate) {
                this.setQueryMessage(this.getQueryMessage() + "WHERE " + ((WhereEndsWithTemplate)template).getColumnName() +
                        " LIKE " + ((WhereEndsWithTemplate)template).getPattern() + " + '%' ");
            }
            else if (template instanceof WhereContainsTemplate) {
                this.setQueryMessage(this.getQueryMessage() + "WHERE " + ((WhereContainsTemplate)template).getColumnName() +
                        " LIKE " + ((WhereContainsTemplate)template).getPattern() + " ");
            }
        }
    }

    public void filteringQueries(){
        for(AbstractTemplate template : templates) {
            if (template instanceof WhereTemplate) {
                this.setQueryMessage(this.getQueryMessage() + "WHERE " + ((WhereTemplate)template).getColumnName() + " " +
                    ((WhereTemplate)template).getOperator() + " " + ((WhereTemplate)template).getCriterion() + " ");
            }
            else if (template instanceof WhereBetweenTemplate) {
                this.setQueryMessage(this.getQueryMessage() + "WHERE " + ((WhereBetweenTemplate)template).getColumnName() + " BETWEEN " +
                        ((WhereBetweenTemplate)template).getLowerBoundary() + " AND " + ((WhereBetweenTemplate)template).getUpperBoundary() + " ");
            }
            else if (template instanceof WhereInTemplate) {
                this.setQueryMessage(this.getQueryMessage() + "WHERE " + ((WhereInTemplate)template).getColumnName() + " IN ");
            }
            else if (template instanceof ParametarListTemplate) {
                this.setQueryMessage(this.getQueryMessage() + "(");
                for(int i = 0; i < ((ParametarListTemplate)template).getParameters().size(); i++){
                    if(i != ((ParametarListTemplate)template).getParameters().size()-1)//ovo zbog zareza i zagrade
                        this.setQueryMessage(this.getQueryMessage() + ((ParametarListTemplate)template).getParameters().get(i) + ", ");
                    else
                        this.setQueryMessage(this.getQueryMessage() + ((ParametarListTemplate)template).getParameters().get(i));
                }
                this.setQueryMessage(this.getQueryMessage() + ") ");
            }
        }
        for(AbstractTemplate template : templates) {
            if (template instanceof AndWhereTemplate) {
                this.setQueryMessage(this.getQueryMessage() + "AND " + ((AndWhereTemplate)template).getColumnName() + " " +
                        ((AndWhereTemplate)template).getOperator() + " " + ((AndWhereTemplate)template).getCriterion() + " ");
            }
            else if (template instanceof OrWhereTemplate) {
                this.setQueryMessage(this.getQueryMessage() + "OR " + ((OrWhereTemplate)template).getColumnName() + " " +
                        ((OrWhereTemplate)template).getOperator() + " " + ((OrWhereTemplate)template).getCriterion() + " ");
            }
        }
    }

    public void groupByQueries(){
        for(AbstractTemplate template : templates) {
            if (template instanceof GroupByTemplate){
                this.setQueryMessage(this.getQueryMessage() + "GROUP BY ");

                for(int i = 0; i < ((GroupByTemplate)template).getColumns().size(); i++){
                    if(i != ((GroupByTemplate)template).getColumns().size() - 1)//ovo zbog zareza i zagrade
                        this.setQueryMessage(this.getQueryMessage() + ((GroupByTemplate)template).getColumns().get(i) + ", ");
                    else
                        this.setQueryMessage(this.getQueryMessage() + ((GroupByTemplate)template).getColumns().get(i));
                }
                this.setQueryMessage(this.getQueryMessage() + " ");
            }
        }
    }

    public void havingQueries(){
        HavingTemplate havingTemplate;
        AndHavingTemplate andHavingTemplate;
        OrHavingTemplate orHavingTemplate;

        for (AbstractTemplate template : templates){
            if(template instanceof HavingTemplate){
                havingTemplate = (HavingTemplate) template;
                this.setQueryMessage(this.getQueryMessage() +  "HAVING ");

                for(AbstractTemplate t : templates) {
                    if (t instanceof AvgTemplate) {
                        if (((AvgTemplate) t).getAlias() != null && havingTemplate.getAlias().trim().equals(((AvgTemplate) t).getAlias().trim())) {
                           this.setQueryMessage(this.getQueryMessage() + "AVG " + " (" + ((AvgTemplate) t).getColumnName() + ") " + havingTemplate.getOperator() + " "
                                   + havingTemplate.getCriterion() + " ");
                        }
                    }else if (t instanceof CountTemplate) {
                        if (((CountTemplate) t).getAlias() != null && havingTemplate.getAlias().trim().equals(((CountTemplate) t).getAlias().trim())) {
                            this.setQueryMessage(this.getQueryMessage() + "COUNT " + " (" + ((CountTemplate) t).getColumnName() + ") " + havingTemplate.getOperator() + " "
                                    + havingTemplate.getCriterion() + " ");
                        }
                    }else if (t instanceof MinTemplate) {
                        if (((MinTemplate) t).getAlias() != null && havingTemplate.getAlias().trim().equals(((MinTemplate) t).getAlias().trim())) {
                            this.setQueryMessage(this.getQueryMessage() + "MIN " + " (" + ((MinTemplate) t).getColumnName() + ") " + havingTemplate.getOperator() + " "
                                    + havingTemplate.getCriterion() + " ");
                        }
                    }else if (t instanceof MaxTemplate) {
                        if (((MaxTemplate) t).getAlias() != null && havingTemplate.getAlias().trim().equals(((MaxTemplate) t).getAlias().trim())) {
                            this.setQueryMessage(this.getQueryMessage() + "MAX " + " (" + ((MaxTemplate) t).getColumnName() + ") " + havingTemplate.getOperator() + " "
                                    + havingTemplate.getCriterion() + " ");
                        }
                    }
                }
            }
        }

        for (AbstractTemplate template : templates){
            if(template instanceof AndHavingTemplate){
                andHavingTemplate = (AndHavingTemplate) template;
                this.setQueryMessage(this.getQueryMessage() +  "AND ");

                for(AbstractTemplate t : templates) {
                    if (t instanceof AvgTemplate) {
                        if (((AvgTemplate) t).getAlias() != null && andHavingTemplate.getAlias().trim().equals(((AvgTemplate) t).getAlias().trim())) {
                            this.setQueryMessage(this.getQueryMessage() + "AVG " + " (" + ((AvgTemplate) t).getColumnName() + ") " + andHavingTemplate.getOperator() + " "
                                    + andHavingTemplate.getCriterion() + " ");
                        }
                    }else if (t instanceof CountTemplate) {
                        if (((CountTemplate) t).getAlias() != null && andHavingTemplate.getAlias().trim().equals(((CountTemplate) t).getAlias().trim())) {
                            this.setQueryMessage(this.getQueryMessage() + "COUNT " + " (" + ((CountTemplate) t).getColumnName() + ") " + andHavingTemplate.getOperator() + " "
                                    + andHavingTemplate.getCriterion() + " ");
                        }
                    }else if (t instanceof MinTemplate) {
                        if (((MinTemplate) t).getAlias() != null && andHavingTemplate.getAlias().trim().equals(((MinTemplate) t).getAlias().trim())) {
                            this.setQueryMessage(this.getQueryMessage() + "MIN " + " (" + ((MinTemplate) t).getColumnName() + ") " + andHavingTemplate.getOperator() + " "
                                    + andHavingTemplate.getCriterion() + " ");
                        }
                    }else if (t instanceof MaxTemplate) {
                        if (((MaxTemplate) t).getAlias() != null && andHavingTemplate.getAlias().trim().equals(((MaxTemplate) t).getAlias().trim())) {
                            this.setQueryMessage(this.getQueryMessage() + "MAX " + " (" + ((MaxTemplate) t).getColumnName() + ") " + andHavingTemplate.getOperator() + " "
                                    + andHavingTemplate.getCriterion() + " ");
                        }
                    }
                }
            }
            else if (template instanceof OrHavingTemplate){
                orHavingTemplate = (OrHavingTemplate) template;
                this.setQueryMessage(this.getQueryMessage() +  "OR ");

                for(AbstractTemplate t : templates) {
                    if (t instanceof AvgTemplate) {
                        if (((AvgTemplate) t).getAlias() != null && orHavingTemplate.getAlias().trim().equals(((AvgTemplate) t).getAlias().trim())) {
                            this.setQueryMessage(this.getQueryMessage() + "AVG " + " (" + ((AvgTemplate) t).getColumnName() + ") " + orHavingTemplate.getOperator() + " "
                                    + orHavingTemplate.getCriterion() + " ");
                        }
                    }else if (t instanceof CountTemplate) {
                        if (((CountTemplate) t).getAlias() != null && orHavingTemplate.getAlias().trim().equals(((CountTemplate) t).getAlias().trim())) {
                            this.setQueryMessage(this.getQueryMessage() + "COUNT " + " (" + ((CountTemplate) t).getColumnName() + ") " + orHavingTemplate.getOperator() + " "
                                    + orHavingTemplate.getCriterion() + " ");
                        }
                    }else if (t instanceof MinTemplate) {
                        if (((MinTemplate) t).getAlias() != null && orHavingTemplate.getAlias().trim().equals(((MinTemplate) t).getAlias().trim())) {
                            this.setQueryMessage(this.getQueryMessage() + "MIN " + " (" + ((MinTemplate) t).getColumnName() + ") " + orHavingTemplate.getOperator() + " "
                                    + orHavingTemplate.getCriterion() + " ");
                        }
                    }else if (t instanceof MaxTemplate) {
                        if (((MaxTemplate) t).getAlias() != null && orHavingTemplate.getAlias().trim().equals(((MaxTemplate) t).getAlias().trim())) {
                            this.setQueryMessage(this.getQueryMessage() + "MAX " + " (" + ((MaxTemplate) t).getColumnName() + ") " + orHavingTemplate.getOperator() + " "
                                    + orHavingTemplate.getCriterion() + " ");
                        }
                    }
                }
            }
        }
    }

    public void orderByQueries(){
        for(AbstractTemplate template : templates) {
            if (template instanceof OrderByTemplate){
                if (!this.getQueryMessage().contains("ORDER BY")) {
                    this.setQueryMessage(this.getQueryMessage() + "ORDER BY ");
                }
                for(int i = 0; i < ((OrderByTemplate)template).getColumns().size(); i++){
                    if(i != ((OrderByTemplate)template).getColumns().size() - 1)
                        this.setQueryMessage(this.getQueryMessage() + ((OrderByTemplate)template).getColumns().get(i) + " ASC, ");
                    else
                        this.setQueryMessage(this.getQueryMessage() + ((OrderByTemplate)template).getColumns().get(i));
                }
                this.setQueryMessage(this.getQueryMessage() + " ASC ");
            }
            else if (template instanceof OrderByDescTemplate){
                if (!this.getQueryMessage().contains("ORDER BY")) {
                    this.setQueryMessage(this.getQueryMessage() + "ORDER BY ");
                }

                for(int i = 0; i < ((OrderByDescTemplate)template).getColumns().size(); i++){
                    if(i != ((OrderByDescTemplate)template).getColumns().size() - 1)
                        this.setQueryMessage(this.getQueryMessage() + ((OrderByDescTemplate)template).getColumns().get(i) + " DESC, ");
                    else
                        this.setQueryMessage(this.getQueryMessage() + ((OrderByDescTemplate)template).getColumns().get(i));
                }
                this.setQueryMessage(this.getQueryMessage() + " DESC ");
            }
        }
    }
}
