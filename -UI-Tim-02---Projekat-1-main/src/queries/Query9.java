package queries;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

public class Query9 extends AbstractQuery{

    @Override
    public void executeQuery() throws SQLException {
        System.out.println("Unesite ime modela:"); //TODO Ispisati korisniku par imena modela sa kojima se moze ovo testirati.
        System.out.println("Primeri: Classic Vest, Classic Vest, Full-Finger Gloves, Half-Finger Gloves, HL Mountain Frame");
        scanner = new Scanner(System.in);
        String modelName = scanner.nextLine();

        Connection myConnection = null;
        CallableStatement myCall = null;
        ResultSet myResultSet = null;

        try {
            myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, password);
            myCall = myConnection.prepareCall("{call getProductModelBuyersByCountry(?)}");

            myCall.setString(1, modelName);

            myCall.execute();
            myResultSet = myCall.getResultSet();

            while(myResultSet.next()){
                System.out.println(myResultSet.getString("Country Name") + " -- " +  myResultSet.getString("Customer Full Name"));
            }
            System.out.println("COLUMNS: Country name -- Customer full name\n");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (myConnection!= null) myConnection.close();
            if (myCall      != null) myCall.close();
            if (myResultSet != null) myResultSet.close();
        }
    }
}
