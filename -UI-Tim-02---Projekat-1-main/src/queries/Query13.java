package queries;

import java.sql.*;

public class Query13 extends AbstractQuery{

    @Override
    public void executeQuery() throws SQLException {
        Connection myConnection = null;
        Statement myStatement = null;
        ResultSet myResultSet = null;

        try {
            myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, password);
            myStatement = myConnection.createStatement();
            myResultSet = myStatement.executeQuery("SELECT * FROM adventureworks.department_analysis");

            while(myResultSet.next()){
                System.out.println(myResultSet.getString("Ime odseka") + " -- " +  myResultSet.getFloat("Prosecna satnica")
                        + " -- " +  myResultSet.getString("Procenat zaposlenih u braku") + " -- " +  myResultSet.getString("Procenat zena")
                        + " -- " +  myResultSet.getFloat("Prosecna starost")+ " -- " +  myResultSet.getFloat("Prosecno trajanje zaposljenja"));
            }
            System.out.println("COLUMNS: Ime odseka -- Prosecna satnica -- Procenat zaposlenih u braku -- Procenat zena -- Prosecna starost -- Prosecno trajanje zaposljenja\n");


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (myConnection!= null) myConnection.close();
            if (myStatement != null) myStatement.close();
            if (myResultSet != null) myResultSet.close();
        }
    }
}
