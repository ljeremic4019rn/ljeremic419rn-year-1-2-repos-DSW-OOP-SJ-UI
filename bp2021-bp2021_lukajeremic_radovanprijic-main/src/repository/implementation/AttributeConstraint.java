package repository.implementation;

import lombok.Data;
import lombok.ToString;
import repository.DBNode;
import repository.enums.ConstraintType;

@Data
@ToString(callSuper = true)

public class AttributeConstraint extends DBNode {

    private ConstraintType constraintType;

    public AttributeConstraint(String name, DBNode parent, ConstraintType constraintType) {
        super(name, parent);
        this.constraintType = constraintType;
    }
}
