package repository.implementation;

import lombok.Data;
import lombok.ToString;
import repository.DBNode;
import repository.DBNodeComposite;

@ToString(callSuper = true)

public class Entity extends DBNodeComposite {

    public Entity(String name, DBNode parent) {
        super(name, parent);
    }

    @Override
    public void addChild(DBNode child) {
        if (child != null && child instanceof Attribute){
            Attribute attribute = (Attribute) child;
            this.getChildren().add(attribute);
        }
    }
}
