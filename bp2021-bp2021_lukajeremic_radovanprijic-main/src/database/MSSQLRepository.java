package database;

import database.settings.Settings;
import lombok.AllArgsConstructor;
import lombok.Data;
import repository.DBNode;
import repository.data.Row;
import repository.enums.AttributeType;
import repository.implementation.Attribute;
import repository.implementation.Entity;
import repository.implementation.InformationResource;

import javax.swing.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Data

public class MSSQLRepository implements Repository{

    private Settings settings;
    private Connection connection;

    public MSSQLRepository(Settings settings) {
        this.settings = settings;
    }

    private void initConnection() throws SQLException, ClassNotFoundException{
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        String ip = (String) settings.getParameter("mssql_ip");
        String database = (String) settings.getParameter("mssql_database");
        String username = (String) settings.getParameter("mssql_username");
        String password = (String) settings.getParameter("mssql_password");
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:jtds:sqlserver://"+ip+"/"+database,username,password);
    }

    private void closeConnection(){
        try{
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            connection = null;
        }
    }

    @Override
    public DBNode getSchema() {

        try{
            this.initConnection();

            DatabaseMetaData metaData = connection.getMetaData();
            InformationResource ir = new InformationResource("RAF_BP_Primer");
            String tableType[] = {"TABLE"};
            ResultSet tables = metaData.getTables(connection.getCatalog(), null, null, tableType);

            while (tables.next()){
                String tableName = tables.getString("TABLE_NAME");
                Entity newTable = new Entity(tableName, ir);
                ir.addChild(newTable);
                ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableName, null);

                while (columns.next()){
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    int columnSize = Integer.parseInt(columns.getString("COLUMN_SIZE"));
                    Attribute attribute = new Attribute(columnName, newTable, AttributeType.valueOf(columnType.toUpperCase()), columnSize);
                    newTable.addChild(attribute);
                }
            }
            return ir;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }
        return null;
    }

    @Override
    public List<Row> get(String from) {
        List<Row> rows = new ArrayList<>();

        try{
            this.initConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(from);

            while (rs.next()){
                Row row = new Row();
                row.setName(from);

                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                for (int i = 1; i<=resultSetMetaData.getColumnCount(); i++){

                    switch (resultSetMetaData.getColumnType(i)){
                        case Types.CHAR:
                            row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                            break;
                        case Types.VARCHAR:
                            String text = rs.getString(i);
                            if (text.matches("^\\d+-\\d+-\\d+")){
                                text = dateFormater(text);
                                row.addField(resultSetMetaData.getColumnName(i),text);
                                break;
                            }
                            row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                            break;
                        case Types.DATE://u employees postoji date koji se dobija kao varchar :/
                            row.addField(resultSetMetaData.getColumnName(i),rs.getDate(i));
                            break;
                        case Types.TIMESTAMP://date type se dobija iz baze kao timestamp
                            String[] dateSplit = rs.getString(i).split(" ");
                            String date = dateFormater(dateSplit[0]); //ovde je bio sat
                            row.addField(resultSetMetaData.getColumnName(i),date);
                            break;
                        case Types.FLOAT:
                            row.addField(resultSetMetaData.getColumnName(i), rs.getFloat(i));
                            break;
                        case Types.REAL:
                            row.addField(resultSetMetaData.getColumnName(i), rs.getFloat(i));
                            break;
                        case Types.BIT:
                            row.addField(resultSetMetaData.getColumnName(i), rs.getBoolean(i));
                            break;
                        case Types.BIGINT:
                            row.addField(resultSetMetaData.getColumnName(i), rs.getLong(i));
                            break;
                        case Types.NUMERIC:
                            row.addField(resultSetMetaData.getColumnName(i), rs.getDouble(i));
                            break;
                        case Types.DECIMAL:
                            row.addField(resultSetMetaData.getColumnName(i), rs.getBigDecimal(i));
                            break;
                        case Types.INTEGER:
                            row.addField(resultSetMetaData.getColumnName(i), rs.getInt(i));
                            break;
                     //   case Types.://image
                      //      row.addField(resultSetMetaData.getColumnName(i), rs.getCharacterStream(i));
                     //       break;
                        case Types.SMALLINT:
                            row.addField(resultSetMetaData.getColumnName(i), rs.getShort(i));
                            break;
                        case Types.NVARCHAR:
                            row.addField(resultSetMetaData.getColumnName(i), rs.getNCharacterStream(i));
                            break;
                        default:
                            row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                            break;
                    }
                }
                rows.add(row);
            }
        }
        catch (Exception e) {
            String message = "Imate sintaksnu gresku.\nProverite da li su vase promenljive deklarisane u sledecem formatu:       " +
                    "var <ime_promenljive> = new <skup_upita>;\n" + "Upiti unutar skupa moraju biti razdvojeni tackama.\n" +
                    "Reci u zagradama moraju biti unutar dvojnih navodnika (za brojeve ovo ne vazi) i razdvojene sa zarezima gde sintaksa zahteva.\n" +
                    "Dozvoljeno je raditi samo sa kolonama i tabelama iz HR baze.";
            JOptionPane.showMessageDialog(null, message,"Upozorenje",JOptionPane.WARNING_MESSAGE);
        }
        finally {
            this.closeConnection();
        }
        return rows;
    }


    public String dateFormater(String date){
        String dateSplit[] = date.split("-");
        switch (dateSplit[1]){
            case "01":
                return dateSplit[2] + "/Jan/" + dateSplit[0];
            case "02":
                return dateSplit[2] + "/Feb/" + dateSplit[0];
            case "03":
                return dateSplit[2] + "/Mar/" + dateSplit[0];
            case "04":
                return dateSplit[2] + "/Apr/" + dateSplit[0];
            case "05":
                return dateSplit[2] + "/May/" + dateSplit[0];
            case "06":
                return dateSplit[2] + "/June/" + dateSplit[0];
            case "07":
                return dateSplit[2] + "/Jul/" + dateSplit[0];
            case "08":
                return dateSplit[2] + "/Avg/" + dateSplit[0];
            case "09":
                return dateSplit[2] + "/Sep/" + dateSplit[0];
            case "10":
                return dateSplit[2] + "/Oct/" + dateSplit[0];
            case "11":
                return dateSplit[2] + "/Nov/" + dateSplit[0];
            case "12":
                return dateSplit[2] + "/Dec/" + dateSplit[0];
        }
        return null;
    }
}
