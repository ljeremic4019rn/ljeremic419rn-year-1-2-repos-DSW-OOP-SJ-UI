package queries;

import java.sql.*;

public class Query2 extends AbstractQuery{

    @Override
    public void executeQuery() throws SQLException {
        Connection myConnection = null;
        Statement myStatement = null;
        ResultSet myResultSet = null;

        try {
            myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, password);
            myStatement = myConnection.createStatement();
            myResultSet = myStatement.executeQuery("SELECT * FROM adventureworks.yearly_profit_for_region ");

            while(myResultSet.next()){
                System.out.println(myResultSet.getString("Region name") + " -- " +  myResultSet.getInt("Year")  + " -- " +
                        myResultSet.getBigDecimal("Yearly profit"));
            }
            System.out.println("COLUMNS: Region name -- Year -- Yearly profit\n");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (myConnection!= null) myConnection.close();
            if (myStatement != null) myStatement.close();
            if (myResultSet != null) myResultSet.close();
        }
    }
}
