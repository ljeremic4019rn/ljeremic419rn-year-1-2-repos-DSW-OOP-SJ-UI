package queries;

import java.sql.*;

public class Query1 extends AbstractQuery{

    @Override
    public void executeQuery() throws SQLException {
        Connection myConnection = null;
        Statement myStatement = null;
        ResultSet myResultSet = null;

        try {
            myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, password);
            myStatement = myConnection.createStatement();
            myResultSet = myStatement.executeQuery("SELECT * FROM adventureworks.product_category");

            while(myResultSet.next()){
                System.out.println(myResultSet.getString("Product name") + " -- " +  myResultSet.getString("Category name"));
            }
            System.out.println("COLUMNS: Product name -- Category name\n");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (myConnection!= null) myConnection.close();
            if (myStatement != null) myStatement.close();
            if (myResultSet != null) myResultSet.close();
        }
    }
}
