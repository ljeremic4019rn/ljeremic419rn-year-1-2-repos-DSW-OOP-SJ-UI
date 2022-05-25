package queries;

import java.sql.*;

public class Query3 extends AbstractQuery{

    @Override
    public void executeQuery() throws SQLException {
        Connection myConnection = null;
        Statement myStatement = null;
        ResultSet myResultSet = null;

        try {
            myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, password);
            myStatement = myConnection.createStatement();
            myResultSet = myStatement.executeQuery("SELECT * FROM adventureworks.yearly_profit_for_region2");

            while(myResultSet.next()){
                System.out.println(myResultSet.getString("Region name") + " -- " +  myResultSet.getInt("Year")  + " -- " +
                        myResultSet.getBigDecimal("Yearly profit") + " -- " +  myResultSet.getString("Procena"));
            }
            System.out.println("COLUMNS: Region name -- Year -- Yearly profit -- Procena\n");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (myConnection!= null) myConnection.close();
            if (myStatement != null) myStatement.close();
            if (myResultSet != null) myResultSet.close();
        }
    }
}
