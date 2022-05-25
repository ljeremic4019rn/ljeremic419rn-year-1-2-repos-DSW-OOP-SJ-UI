package database;

import repository.DBNode;
import repository.data.Row;
import java.util.List;

public interface Database{
    DBNode loadResource();
    List<Row> readDataFromTable(String tableName);
}
