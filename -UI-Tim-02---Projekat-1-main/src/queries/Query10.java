package queries;

import java.sql.*;

public class Query10 extends AbstractQuery{

    @Override
    public void executeQuery() throws SQLException {
        Connection myConnection = null;
        Statement myStatement = null;
        ResultSet myResultSet = null;

        try {
            myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, password);
            myStatement = myConnection.createStatement();
            myResultSet = myStatement.executeQuery("SELECT * FROM adventureworks.model_language");

            while(myResultSet.next()){
                System.out.println(myResultSet.getInt(1) + " -- " +  myResultSet.getString(2) + " -- " + myResultSet.getString("language"));
            }
            System.out.println("COLUMNS: ProductModelID -- Product name -- Language\n");


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (myConnection!= null) myConnection.close();
            if (myStatement != null) myStatement.close();
            if (myResultSet != null) myResultSet.close();
        }
    }
}
