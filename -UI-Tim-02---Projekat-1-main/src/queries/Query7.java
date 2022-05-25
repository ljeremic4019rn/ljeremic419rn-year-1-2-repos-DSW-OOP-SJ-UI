package queries;

import java.sql.*;

public class Query7 extends AbstractQuery{

    @Override
    public void executeQuery() throws SQLException {
        Connection myConnection = null;
        Statement myStatement = null;
        ResultSet myResultSet = null;

        try {
            myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, password);
            myStatement = myConnection.createStatement();
            myResultSet = myStatement.executeQuery("SELECT * FROM adventureworks.region_customercount_moneyavg_ordersavg");

            while(myResultSet.next()){
                System.out.println(myResultSet.getString(1) + " -- " +  myResultSet.getInt(2)
                        + " -- " + myResultSet.getBigDecimal(3) + " -- " +  myResultSet.getFloat(4));
            }
            System.out.println("COLUMNS: Region name -- Customer number -- average money spent -- average objects bought\n");


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (myConnection!= null) myConnection.close();
            if (myStatement != null) myStatement.close();
            if (myResultSet != null) myResultSet.close();
        }
    }
}
