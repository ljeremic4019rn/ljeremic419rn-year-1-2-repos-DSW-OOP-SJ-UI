import java.io.IOException;
import java.util.Scanner;

public class RemoteStorageConfiguration implements StorageConfiguration{

    RemoteStorage storage;
    Scanner scanner = new Scanner(System.in);
    String home = System.getProperty("user.home");//napravimo tmp storage za json filove

    public void setStorageSizeLimit() {
        if (storage.getLoggedUser().isAdmin()) {
            System.out.println("Unesite maksimalnu velicinu skladista (broj bajtova):");
            int storageSizeLimit = scanner.nextInt();

            try {
                storage.getStorageSettings().setStorageMaxSize(storageSizeLimit);
                storage.writeStorageConfigToJsonFile(home + "\\Downloads\\tmpDir");
                storage.replaceConfigJsonFiles(storage.storageSettings.getRootPath(), "rootConfig.json");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else System.out.println("Konektovani korisnik nije administrator.");
    }

    public void setExtensionBlackList() {
        if (storage.getLoggedUser().isAdmin()) {
            System.out.println("Unesite ekstenzije koje zelite zabraniti u skladistu (pisite ih sa tackom na pocetku i razdvojene razmakom, npr. '.exe .json .txt'");

            String extensionsRaw = scanner.nextLine();
            String[] extensions = extensionsRaw.split(" ");

            try {
                for (String e : extensions) {
                    if (storage.getStorageSettings().getExtensionBlacklist().contains(e)) {
                        System.out.println("Ekstenzija " + e + " je vec medju zabranjenim ekstenzijama.");
                    } else {
                        storage.getStorageSettings().getExtensionBlacklist().add(e);
                    }
                }
                storage.writeStorageConfigToJsonFile(home + "\\Downloads\\tmpDir");
                storage.replaceConfigJsonFiles(storage.storageSettings.getRootPath(), "rootConfig.json");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else System.out.println("Konektovani korisnik nije administrator.");
    }

    public void setDirectoryFileLimit() {
        if (storage.getLoggedUser().isAdmin()) {
            System.out.println("Unesite maksimalan broj fajlova za direktorijum i ime tog direktorijuma (u tom redosledu i u dve linije):");

            String maxFileNum = scanner.nextLine();
            String dirName = scanner.nextLine();

            try {
                //todo namesti ako zelis da moze samo u sub dir da se stavi, trenutno moze na bilo koji dir
                storage.getStorageSettings().getDirectoryMaxFileSize().put(storage.getAllDriveFiles().get(dirName), Integer.parseInt(maxFileNum));
                storage.writeStorageConfigToJsonFile(home + "\\Downloads\\tmpDir");
                storage.replaceConfigJsonFiles(storage.storageSettings.getRootPath(), "rootConfig.json");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else System.out.println("Konektovani korisnik nije administrator.");
    }

    public RemoteStorage getStorage() {
        return storage;
    }
    public void setStorage(RemoteStorage storage) {
        this.storage = storage;
    }
}
