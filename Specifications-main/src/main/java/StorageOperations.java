import java.util.Collection;
import java.util.List;

 public interface StorageOperations { //Interfejs koji sadrzi metode za operacije nad skladistem.

     /**
      * Creates a new empty directory at given path with given name
      */
     void createDirectory ();

     /**
      * Creates multiple new empty directories at given path given names
      */
     void createMultipleDirectories ();

     /**
      * Creates a new empty file at given path given name
      */
     void createFile ();

     /**
      * Creates multiple new empty files at given path given names
      */
     void createMultipleFiles ();

     /**
      * Copies a list of files from one directory to another
      */
     void copyFiles ();

     /**
      * Removes a directory at given path
      */
     void removeDirectory ();

     /**
      *  Removes a directory at given path
      */
     void removeFile ();

     /**
      * Displays all files in given directory
      */
     void findAllFilesInDirectory ();

     /**
      * Displays directories files in given directory
      */
     void findAllDirectoriesInDirectory ();

     /**
      * Displays all files with a given name in given directory and its subdirectories
      */
     void findFilesByNameInDirAndSubdirs ();

     /**
      * Displays all files with a given extension in given directory
      */
     void findFilesByExtension ();

     /**
      * Displays all files in given directory sorted alphabetically
      */
     void findFilesByNameSorted ();

     /**
      * Finds and prints out all files in a directory, sorted by their creation date.
      */
     void findFilesByDateSorted ();

     /**
      * Finds and prints out all files in a directory, sorted by their date of last modification.
      */
     void findFilesByDateModifiedSorted ();

     /**
      * Finds and prints out all files in a directory that were either created or modified in a certain time period.
      */
     void findFilesBySpecificCreatedModifiedTime ();

     /**
      * Moves certain files from one directory to another (inside the storage).
      */
     void moveFiles ();

     /**
      * Downloads wanted files into the user's 'Downloads' directory.
      */
     void downloadFiles ();

     /**
      * Method that checks user's privileges in a given directory.
      * @param path Path to a directory in the storage.
      * @param privilegeToCheck Privilege that has to be checked for a given user.
      * @return
      */
     int checkUserPrivileges (String path, String privilegeToCheck);

     /**
      * Checks if all storage configuration limitations are satisfied for a given storage or directory.
      * @param path Path to a directory in the storage.
      * @param filesAddedNumber Number of files that were added to the directory.
      * @param fileSize Size of the added files.
      * @param extension Extension of the added files.
      * @return
      */
     int checkStorageConfigurations (String path, int filesAddedNumber, int fileSize, String extension);

}

