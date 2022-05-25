package dsw.rudok.app.observer;

public interface ISubscriber {
    void update(Object notification, ActionType action);
}
