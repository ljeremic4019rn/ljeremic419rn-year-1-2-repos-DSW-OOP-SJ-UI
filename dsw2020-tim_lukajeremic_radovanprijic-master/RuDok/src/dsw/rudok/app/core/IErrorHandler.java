package dsw.rudok.app.core;

import dsw.rudok.app.errorhandler.ErrorType;
import dsw.rudok.app.observer.IPublisher;

public interface IErrorHandler extends IPublisher {
    void generateError(ErrorType errorType);
}
