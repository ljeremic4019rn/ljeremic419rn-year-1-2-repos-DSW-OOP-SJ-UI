import java.util.Collection;
import java.util.List;
import java.util.Map;

public class StorageSettings { //Pomocna klasa koja nam sluzi za cuvanje podataka o trenutnom stanju skladista (da li je neko vec ulogovan + konfiguracije).

    private boolean isInUse;
    private String rootPath;
    private int storageMaxSize;
    private Collection<String> extensionBlacklist;
    private Map<String,Integer> directoryMaxFileSize;

    /**
     * Constructor for the StorageSettings object.
     * @param isInUse Boolean variable that tells us if somebody is already logged in the storage.
     * @param rootPath Path to the root directory of the storage.
     * @param storageMaxSize Maximum size of the storage.
     * @param extensionBlacklist List of extensions that are forbidden in the storage.
     * @param directoryMaxFileSize Map of directories and their maximum file number limits.
     */
    public StorageSettings(boolean isInUse, String rootPath, int storageMaxSize, Collection<String> extensionBlacklist,
                           Map<String, Integer> directoryMaxFileSize) {
        this.isInUse = isInUse;
        this.rootPath = rootPath;
        this.storageMaxSize = storageMaxSize;
        this.extensionBlacklist = extensionBlacklist;
        this.directoryMaxFileSize = directoryMaxFileSize;
    }

    public boolean isInUse() {
        return isInUse;
    }
    public void setInUse(boolean inUse) {
        isInUse = inUse;
    }
    public String getRootPath() {
        return rootPath;
    }
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }
    public int getStorageMaxSize() {
        return storageMaxSize;
    }
    public void setStorageMaxSize(int storageMaxSize) {
        this.storageMaxSize = storageMaxSize;
    }
    public Collection<String> getExtensionBlacklist() {
        return extensionBlacklist;
    }
    public void setExtensionBlacklist(List<String> extensionBlacklist) {
        this.extensionBlacklist = extensionBlacklist;
    }
    public Map<String, Integer> getDirectoryMaxFileSize() {
        return directoryMaxFileSize;
    }
    public void setDirectoryMaxFileSize(Map<String, Integer> directoryMaxFileSize) {
        this.directoryMaxFileSize = directoryMaxFileSize;
    }

    public void setAllStorageSettings(StorageSettings storageSettingsTmp){
        this.isInUse = storageSettingsTmp.isInUse();
        this.rootPath = storageSettingsTmp.getRootPath();
        this.storageMaxSize = storageSettingsTmp.getStorageMaxSize();
        this.extensionBlacklist = storageSettingsTmp.getExtensionBlacklist();
        this.directoryMaxFileSize = storageSettingsTmp.getDirectoryMaxFileSize();
    }
}
