package dsw.rudok.app.repository.slots;

import dsw.rudok.app.repository.node.RuNode;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;

@Getter
@Setter
public abstract class Slot extends RuNode {
    protected SlotType slotType;
    protected File slotFile;
    protected Dimension size;
    protected Point position;
    private double angle = 0;
    protected Boolean isGreen = false;
    protected Point2D.Double pointA; //leva gornja tacka
    protected Point2D.Double pointB; //desna gornja tacka
    protected Point2D.Double pointC; //desna donja tacka
    protected Point2D.Double pointD; //leva donja tacka
    protected Point2D.Double rotationPoint; //tacka u sredini oko koje se vrsi rotacija

    public Slot(String name, RuNode parent, Dimension size, Point position) {
        super(name, parent);
        this.size = size;
        this.position = position;
        this.slotType = null;
        this.slotFile = null;
        position.setLocation(position.getX()-size.getWidth()/2,position.getY()-size.getHeight()/2); //slotovi se prave u centru
        pointA = new Point2D.Double();
        this.pointA.setLocation(position.getX(), position.getY());
        pointB = new Point2D.Double();
        this.pointB.setLocation(position.getX()+size.getWidth(), position.getY());
        pointC = new Point2D.Double();
        this.pointC.setLocation(position.getX()+size.getWidth(), position.getY()+size.getHeight());
        pointD = new Point2D.Double();
        this.pointD.setLocation(position.getX(), position.getY()+size.getHeight());
        rotationPoint = new Point2D.Double();
        this.rotationPoint.setLocation(position.getX()+size.getWidth()/2,position.getY()+size.getHeight()/2);
    }

    public void updatePoints(Point position, Dimension size){
        this.pointA.setLocation(position.getX(), position.getY());
        this.pointB.setLocation(position.getX()+size.getWidth(), position.getY());
        this.pointC.setLocation(position.getX()+size.getWidth(), position.getY()+size.getHeight());
        this.pointD.setLocation(position.getX(), position.getY()+size.getHeight());
        this.rotationPoint.setLocation(position.getX()+size.getWidth()/2,position.getY()+size.getHeight()/2);
    }
}
