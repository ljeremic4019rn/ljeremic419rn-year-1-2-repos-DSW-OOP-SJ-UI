package validator.rules;

import compiler.Query;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Setter

public abstract class AbstractRule {
    private String message;

    public abstract boolean examineQuery(Query query, ArrayList<Query> queries);
}
