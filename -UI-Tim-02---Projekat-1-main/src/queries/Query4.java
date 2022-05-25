package queries;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

public class Query4 extends AbstractQuery{

    @Override
    public void executeQuery() throws SQLException {
        System.out.println("Unesite ime prodavnice:"); //TODO Ispisati korisniku par imena prodavnica sa kojima se moze ovo testirati.
        System.out.println("Primeri: A Bike Store, Progressive Sports, Advanced Bike Components, Modular Cycle Systems");
        scanner = new Scanner(System.in);
        String storeName = scanner.nextLine();

        Connection myConnection = null;
        CallableStatement myCall = null;
        ResultSet myResultSet = null;

        try {
            myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, password);
            myCall = myConnection.prepareCall("{call prodavnica_info(?,?,?,?)}");

            myCall.setString(1,storeName);
            myCall.registerOutParameter(2, Types.INTEGER);
            myCall.registerOutParameter(3, Types.DECIMAL);
            myCall.registerOutParameter(4, Types.DECIMAL);

            myCall.execute();

            int brojNarudzbina = myCall.getInt(2);
            BigDecimal ukupnaZarada = myCall.getBigDecimal(3);
            BigDecimal prosecnoProizvodaPoNarudzbini = myCall.getBigDecimal(4);

            System.out.println("Number of orders since opening = " + brojNarudzbina);
            System.out.println("Total profit = " + ukupnaZarada);
            System.out.println("Avg. qty. of products by order = " + prosecnoProizvodaPoNarudzbini);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (myConnection!= null) myConnection.close();
            if (myCall      != null) myCall.close();
            if (myResultSet != null) myResultSet.close();
        }
    }
}
