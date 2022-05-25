package repository.implementation;

import lombok.Data;
import lombok.ToString;
import repository.DBNode;
import repository.DBNodeComposite;

@ToString(callSuper = true)

public class InformationResource extends DBNodeComposite {

    public InformationResource(String name) {
        super(name, null);
    }

    @Override
    public void addChild(DBNode child) {
        if (child != null && child instanceof Entity){
            Entity entity = (Entity) child;
            this.getChildren().add(entity);
        }
    }
}

