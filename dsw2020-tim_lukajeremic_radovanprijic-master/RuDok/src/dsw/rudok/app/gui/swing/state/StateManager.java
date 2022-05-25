package dsw.rudok.app.gui.swing.state;

import dsw.rudok.app.gui.swing.view.observers.PageView;
import dsw.rudok.app.repository.Page;
import lombok.Getter;

@Getter
public class StateManager {

    private State currentState;

    CircleState circleState;
    RectangleState rectangleState;
    TriangleState triangleState;
    SelectState selectState;
    DeleteState deleteState;
    RotateState rotateState;
    ScaleState scaleState;
    MoveState moveState;

    public StateManager(PageView mediator){
        circleState = new CircleState(mediator);
        rectangleState = new RectangleState(mediator);
        triangleState = new TriangleState(mediator);
        selectState = new SelectState(mediator);
        deleteState = new DeleteState(mediator);
        rotateState = new RotateState(mediator);
        scaleState = new ScaleState(mediator);
        moveState = new MoveState(mediator);
        currentState = selectState;
    }

    public void setCircleState(){
        currentState = circleState;
    }
    public void setRectangleState(){
        currentState = rectangleState;
    }
    public void setTriangleState(){
        currentState = triangleState;
    }
    public void setSelectState(){
        currentState = selectState;
    }
    public void setDeleteState(){
        currentState = deleteState;
    }
    public void setRotateState(){
        currentState = rotateState;
    }
    public void setScaleState(){
        currentState = scaleState;
    }
    public void setMoveState(){
        currentState = moveState;
    }
    public State getCurrentState() {
        return currentState;
    }
}

