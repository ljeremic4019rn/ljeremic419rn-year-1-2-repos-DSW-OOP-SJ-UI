package queries;

import java.sql.*;

public class Query12 extends AbstractQuery{

    @Override
    public void executeQuery() throws SQLException {
        Connection myConnection = null;
        Statement myStatement = null;
        ResultSet myResultSet = null;

        try {
            myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, password);
            myStatement = myConnection.createStatement();
            myResultSet = myStatement.executeQuery("SELECT * FROM adventureworks.getEmployeesWithDoublePay");

            while(myResultSet.next()){
                System.out.println(myResultSet.getString(1) + " -- " +  myResultSet.getInt("Broj zaposlenih koji se isplacuju 2x mesecno"));
            }
            System.out.println("COLUMNS: Employee name -- Number of employees who are payed 2x a month\n");


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (myConnection!= null) myConnection.close();
            if (myStatement != null) myStatement.close();
            if (myResultSet != null) myResultSet.close();
        }
    }
}
