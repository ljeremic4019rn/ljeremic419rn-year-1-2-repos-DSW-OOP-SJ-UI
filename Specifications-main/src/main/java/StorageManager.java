public class StorageManager {

    /**
     * Abstract storage object that is used for storing concrete storage implementation objects.
     */
    private static Storage storage;

    /**
     * Method that is used for integrating storage implementations as runtiem dependencies in the main program.
     * @param storageImplementation The concrete storage implementation object that will be used in the main program.
     */
    public static void registerStorage(Storage storageImplementation) {
        storage = storageImplementation;
    }

    /**
     * Method that returns the currently used storage implementation.
     * @return The concrete storage implementation that is used in the main program.
     */
    public static Storage getStorage() {
        return storage;
    }
}
