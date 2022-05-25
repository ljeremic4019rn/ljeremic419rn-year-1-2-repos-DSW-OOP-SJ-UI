package dsw.rudok.app.errorhandler;

import dsw.rudok.app.core.IErrorHandler;
import dsw.rudok.app.observer.ActionType;
import dsw.rudok.app.observer.ISubscriber;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class ErrorHandler implements IErrorHandler {
    List<ISubscriber> subscribers;

    public void generateError(ErrorType errorType){
        if(errorType == ErrorType.NO_NODE_SELECTED){
            MyError error = new MyError("Niste selektovali cvor.");
            notifySubscribers(error, ActionType.NO_NODE_SELECTED_ERROR);
        }
        else if(errorType == ErrorType.WORKSPACE_DELETION){
            MyError error = new MyError("Ne mozete obrisati Workspace.");
            notifySubscribers(error, ActionType.WORKSPACE_DELETION_ERROR);
        }
        else if(errorType == ErrorType.NO_CLICK_ON_SHAPE){
            MyError error = new MyError("Kliknite na jedan od selektovanih slotova.");
            notifySubscribers(error, ActionType.NO_CLICK_ON_SHAPE_ERROR);
        }
        else if(errorType == ErrorType.NO_FRAME_SELECTED){
            MyError error = new MyError("Niste selektovali prozor.");
            notifySubscribers(error, ActionType.NO_FRAME_SELECTED_ERROR);
        }
        else if(errorType == ErrorType.NO_SHAPE_SELECTED){
            MyError error = new MyError("Niste selektovali slot(ove).");
            notifySubscribers(error, ActionType.NO_SHAPE_SELECTED_ERROR);
        }
        else if(errorType == ErrorType.NO_FRAME_CREATED){
            MyError error = new MyError("Nemate otvorenih prozora (stranica).");
            notifySubscribers(error, ActionType.NO_FRAME_CREATED_ERROR);
        }
        else if(errorType == ErrorType.NO_DOCUMENT_SELECTED){
            MyError error = new MyError("Selektujte dokument koji zelite da podelite.");
            notifySubscribers(error, ActionType.NO_DOUMENT_SELECTED_ERROR);
        }
        else if(errorType == ErrorType.NO_PROJECT_SELECTED){
            MyError error = new MyError("Selektujte projekat.");
            notifySubscribers(error, ActionType.NO_PROJECT_SELECTED_ERROR);
        }
        else if(errorType == ErrorType.NO_UNDO_REMAINING){
            MyError error = new MyError("Nemate vise komandi za 'undo' akciju.");
            notifySubscribers(error, ActionType.NO_UNDO_REMAINING_ERROR);
        }
        else if(errorType == ErrorType.NO_REDO_REMAINING){
            MyError error = new MyError("Nemate vise komandi za 'redo' akciju.");
            notifySubscribers(error, ActionType.NO_REDO_REMAINING_ERROR);
        }
    }

    @Override
    public void addSubscriber(ISubscriber sub) {
        if(sub == null)
            return;
        if(this.subscribers ==null)
            this.subscribers = new ArrayList<>();
        if(this.subscribers.contains(sub))
            return;
        this.subscribers.add(sub);
    }

    @Override
    public void removeSubscriber(ISubscriber sub) {
        if(sub == null || this.subscribers == null || !this.subscribers.contains(sub))
            return;
        this.subscribers.remove(sub);
    }

    @Override
    public void notifySubscribers(Object notification, ActionType action) {
        if(notification == null || this.subscribers == null || this.subscribers.isEmpty())
            return;
        for(ISubscriber observer : subscribers){
            observer.update(notification, action);
        }
    }
}
