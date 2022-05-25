public interface StorageInitialization { //Interfejs koji sadrzi metode vezane za inicijalizaciju skladista i korisnike.


    /**
     * Creates a new root directory at given path
     *
     * @param path location for creation of new root directory
     */
    void initializeStorage (String path);

    /**
     * User provides path to the root directory, his username and password
     * if the root directory exists user will be logged in and root json files loaded
     * if the root directory doesn't exist a new one will be created at the given path
     */
    void logIn ();

    /**
     * Logs user out of the system and lets him log into a new storage
     * clearing all logged data about the user or storage configuration at the end
     */
    void logOut ();

    /**
     * Adds a new user to the users json file locates in the storage root directory
     */
    void addUser ();

    /**
     * Removes a given user from the users json file locates in the storage root directory
     */
    void removeUser ();

    /**
     * Edits the load/download/save/remove privileges of the given user
     */
    void editPrivilege ();

}
