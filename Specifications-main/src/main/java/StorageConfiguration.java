 public interface StorageConfiguration { //Interfejs koji sadrzi metode za konfiguraciju skladista.

      /**
       * Sets the maximum file size of the root directory
       */
     void setStorageSizeLimit ();

      /**
       * Adds given file extensions to the storage blacklist
       */
     void setExtensionBlackList ();

      /**
       * Sets the maximum numbers of files within a given directory
       */
     void setDirectoryFileLimit ();
}
