package queries;

import java.sql.*;

public class Query8 extends AbstractQuery{

    @Override
    public void executeQuery() throws SQLException {
        Connection myConnection = null;
        Statement myStatement = null;
        ResultSet myResultSet = null;

        try {
            myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, password);
            myStatement = myConnection.createStatement();
            myResultSet = myStatement.executeQuery("SELECT * FROM adventureworks.getProductsBySale");

            while(myResultSet.next()){
                System.out.println(myResultSet.getString(1) + " -- " +  myResultSet.getInt(2) + " -- " + myResultSet.getBigDecimal("Product total profit"));
            }
            System.out.println("COLUMNS: Territory name -- ProductID -- Product total profit\n");


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (myConnection!= null) myConnection.close();
            if (myStatement != null) myStatement.close();
            if (myResultSet != null) myResultSet.close();
        }
    }
}
