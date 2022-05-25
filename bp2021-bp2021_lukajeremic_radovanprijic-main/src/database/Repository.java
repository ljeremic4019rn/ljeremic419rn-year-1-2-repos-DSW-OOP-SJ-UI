package database;

import repository.DBNode;
import repository.data.Row;
import java.util.List;

public interface Repository {
    DBNode getSchema();
    List<Row> get(String from);
}
