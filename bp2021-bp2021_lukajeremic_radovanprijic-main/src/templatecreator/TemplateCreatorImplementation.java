package templatecreator;

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

import javax.swing.*;

public class TemplateCreatorImplementation implements TemplateCreator{

    public AbstractTemplate createTemplate(String input){
        AbstractTemplate template = null;

        String inputParsing[] = input.split("\\(", 2);

        //System.out.println("pravimo -> " + inputParsing[0]);

        switch (inputParsing[0]){
            case "Query":
                template = new NewQueryTemplate(inputParsing[1]);
                break;

            case "Select":
                template = new SelectTemplate(inputParsing[1]);
                break;

            case "OrderBy":
                template = new OrderByTemplate(inputParsing[1]);
                break;

            case "OrderByDesc":
                template = new OrderByDescTemplate(inputParsing[1]);
                break;

            case "Where":
                template = new WhereTemplate(inputParsing[1]);
                break;

            case "OrWhere":
                template = new OrWhereTemplate(inputParsing[1]);
                break;

            case "AndWhere":
                template = new AndWhereTemplate(inputParsing[1]);
                break;

            case "WhereBetween":
                template = new WhereBetweenTemplate(inputParsing[1]);
                break;

            case "WhereIn":
                template = new WhereInTemplate(inputParsing[1]);
                break;

            case "ParametarList":
                template = new ParametarListTemplate(inputParsing[1]);
                break;

            case "Join":
                template = new JoinTemplate(inputParsing[1]);
                break;

            case "On":
                template = new OnTemplate(inputParsing[1]);
                break;

            case "WhereEndsWith":
                template = new WhereEndsWithTemplate(inputParsing[1]);
                break;

            case "WhereStartsWith":
                template = new WhereStartsWithTemplate(inputParsing[1]);
                break;

            case "WhereContains":
                template = new WhereContainsTemplate(inputParsing[1]);
                break;

            case "Avg":
                template = new AvgTemplate(inputParsing[1]);
                break;

            case "Count":
                template = new CountTemplate(inputParsing[1]);
                break;

            case "Min":
                template = new MinTemplate(inputParsing[1]);
                break;

            case "Max":
                template = new MaxTemplate(inputParsing[1]);
                break;

            case "GroupBy":
                template = new GroupByTemplate(inputParsing[1]);
                break;

            case "Having":
                template = new HavingTemplate(inputParsing[1]);
                break;

            case "AndHaving":
                template = new AndHavingTemplate(inputParsing[1]);
                break;

            case "OrHaving":
                template = new OrHavingTemplate(inputParsing[1]);
                break;

            case "WhereInQ":
                template = new WhereInQTemplate(inputParsing[1]);
                break;

            case "WhereEqQ":
                template = new WhereEqQTemplate(inputParsing[1]);
                break;
            default:
                String message = "Imate sintaksnu gresku. Jedan ili vise upita je nepravilno napisanih (misljen je deo posle tacke i pre zagrade).";
                JOptionPane.showMessageDialog(null, message,"Upozorenje",JOptionPane.WARNING_MESSAGE);
                return null;
        }

        return template;
    }
}
