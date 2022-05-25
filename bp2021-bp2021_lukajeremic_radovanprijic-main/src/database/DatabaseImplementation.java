package database;

import lombok.AllArgsConstructor;
import lombok.Data;
import repository.DBNode;
import repository.data.Row;
import java.util.List;

@Data
@AllArgsConstructor
public class DatabaseImplementation implements Database {
    private Repository repository;

    @Override
    public DBNode loadResource() {
        return repository.getSchema();
    }

    @Override
    public List<Row> readDataFromTable(String tableName) {
        return repository.get(tableName);
    }
}
