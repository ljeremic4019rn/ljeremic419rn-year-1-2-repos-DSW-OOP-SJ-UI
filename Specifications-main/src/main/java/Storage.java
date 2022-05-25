import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Storage { //Kljucna klasa koja predstavlja objektnu reprezentaciju skladista.
    /**
     * Storage initialization component of the storage.
     */
    StorageInitialization storageInitialization;
    /**
     * Storage operations component of the storage.
     */
    StorageOperations storageOperations;
    /**
     * Storage configuration component of the storage.
     */
    StorageConfiguration storageConfiguration;
    /**
     * Object that represents the storage settins of the storage.
     */
    StorageSettings storageSettings;
    /**
     * User that is currently logged in the storage.
     */
    User loggedUser;
    /**
     * Collection of users of the storage.
     */
    Collection<User> users;

    public StorageInitialization getStorageInitialization() {
        return storageInitialization;
    }
    public StorageOperations getStorageOperations() {
        return storageOperations;
    }
    public StorageConfiguration getStorageConfiguration() {
        return storageConfiguration;
    }
    public StorageSettings getStorageSettings() {
        return storageSettings;
    }
    public void setStorageSettings(StorageSettings storageSettings) {
        this.storageSettings = storageSettings;
    }
    public User getLoggedUser() {
        return loggedUser;
    }
    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
    public Collection<User> getUsers() {
        return users;
    }
}
