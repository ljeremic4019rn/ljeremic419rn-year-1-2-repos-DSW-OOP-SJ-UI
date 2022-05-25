import queries.*;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        AbstractQuery query;
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nDobrodosli u program za izvrsavanje SQL upita!");
        System.out.println("Odaberite jedan od upita, tako sto cete uneti odgovarajuci broj od 1 do 13.\nAko zelite izaci iz programa, unesite kljucnu rec \"exit\".");
        String userInput = scanner.nextLine();

        while (!"exit".equals(userInput)) {
            switch (userInput){
                case "1":
                    query = new Query1();
                    query.executeQuery();
                    break;
                case "2":
                    query = new Query2();
                    query.executeQuery();
                    break;
                case "3":
                    query = new Query3();
                    query.executeQuery();
                    break;
                case "4":
                    query = new Query4();
                    query.executeQuery();
                    break;
                case "5":
                    query = new Query5();
                    query.executeQuery();
                    break;
                case "6":
                    query = new Query6();
                    query.executeQuery();
                    break;
                case "7":
                    query = new Query7();
                    query.executeQuery();
                    break;
                case "8":
                    query = new Query8();
                    query.executeQuery();
                    break;
                case "9":
                    query = new Query9();
                    query.executeQuery();
                    break;
                case "10":
                    query = new Query10();
                    query.executeQuery();
                    break;
                case "11":
                    query = new Query11();
                    query.executeQuery();
                    break;
                case "12":
                    query = new Query12();
                    query.executeQuery();
                    break;
                case "13":
                    query = new Query13();
                    query.executeQuery();
                    break;
                default:
                    System.out.println("Uneli ste nedozvoljenu vrednost.\n");
            }
            System.out.println("Odaberite sledeci upit:");
            userInput = scanner.nextLine();
        }
        System.out.println("Zbogom, korisnice!");
    }
}
