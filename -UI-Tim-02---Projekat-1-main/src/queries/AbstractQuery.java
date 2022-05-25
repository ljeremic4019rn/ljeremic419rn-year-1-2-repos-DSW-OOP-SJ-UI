package queries;

import java.sql.SQLException;
import java.util.Scanner;

public abstract class AbstractQuery {
    String database = "adventureworks";
    String username = "student";
    String password = "student";
    Scanner scanner;

    public abstract void executeQuery() throws SQLException;
}
