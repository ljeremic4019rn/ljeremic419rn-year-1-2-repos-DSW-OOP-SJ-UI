package queries;

import java.sql.*;
import java.util.Scanner;

public class Query11 extends AbstractQuery{

    @Override
    public void executeQuery() throws SQLException {
        System.out.println("Unesite ime jezika:"); //TODO Ispisati korisniku par imena jezika sa kojima se moze ovo testirati.
        System.out.println("Moguci jezici: Arabic, English, Spanish, French, Hebrew,Thai, Chinese");
        scanner = new Scanner(System.in);
        String languageName = scanner.nextLine();

        Connection myConnection = null;
        CallableStatement myCall = null;
        ResultSet myResultSet = null;

        try {
            myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database, username, password);
            myCall = myConnection.prepareCall("{call getProductInfoByLanguage(?,?)}");

            myCall.setString(1, languageName);
            myCall.registerOutParameter(2, Types.INTEGER);

            myCall.execute();

            int flag = myCall.getInt(2);

            if(flag == 1){
                System.out.println("Na datom jeziku nije napisano nijedno uputstvo.");
                return;
            }
            myResultSet = myCall.getResultSet();

            while(myResultSet.next()){
                System.out.println(myResultSet.getInt(1) + " -- " +  myResultSet.getString(2) + " -- " +
                        myResultSet.getBigDecimal(3));

            }
                System.out.println("COLUMNS: Product ID -- Product name -- Product price\n");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (myConnection!= null) myConnection.close();
            if (myCall      != null) myCall.close();
            if (myResultSet != null) myResultSet.close();
        }
    }
}
