
import java.util.Scanner;
public class LocalStorageConfiguration implements StorageConfiguration{

    private LocalStorage storage;
    Scanner scanner = new Scanner(System.in);

    public void setStorageSizeLimit() { //Metod za promenu maksimalne velicine skladista (u bajtovima).
        System.out.println("Unesite maksimalnu velicinu skladista (broj bajtova):");

        try {
            String storageSizeLimit = scanner.nextLine();

            if (storage.getLoggedUser().isAdmin()){
                storage.parseStorageConfigFromJsonFile(storage.getStorageSettings().getRootPath(), storage.getStorageSettings());
                storage.getStorageSettings().setStorageMaxSize(Integer.parseInt(storageSizeLimit));
                storage.writeStorageConfigToJsonFile(storage.getStorageSettings().getRootPath());
            }
            else {
                System.out.println("Konektovani korisnik nije administrator.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public void setExtensionBlackList() { //Metod s kojim se zabranjuju odredjene ekstenzije.
        System.out.println("Unesite ekstenzije koje zelite zabraniti u skladistu (pisite ih sa tackom na pocetku i razdvojene razmakom, npr. '.exe .json .txt'");

        try {
            String extensionsRaw = scanner.nextLine();

            if (storage.getLoggedUser().isAdmin()){
                storage.parseStorageConfigFromJsonFile(storage.getStorageSettings().getRootPath(), storage.getStorageSettings());
                String []extensions = extensionsRaw.split(" ");

                for (String e : extensions) {
                    if (storage.getStorageSettings().getExtensionBlacklist().contains(e)) {
                        System.out.println("Ekstenzija " + e + " je vec medju zabranjenim ekstenzijama.");
                    }
                    else {
                        storage.getStorageSettings().getExtensionBlacklist().add(e);
                    }
                }
                storage.writeStorageConfigToJsonFile(storage.getStorageSettings().getRootPath());
            }
            else {
                System.out.println("Konektovani korisnik nije administrator.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public void setDirectoryFileLimit() { //Metod za ogranicavanje broja fajlova u odredjenom direktorijumu.
        System.out.println("Unesite maksimalan broj fajlova za direktorijum i putanju do tog direktorijuma (u tom redosledu i u dve linije):");

        try {
            String maxFileNum = scanner.nextLine();
            String path = scanner.nextLine();

            if (storage.getLoggedUser().isAdmin()){
                storage.parseStorageConfigFromJsonFile(storage.getStorageSettings().getRootPath(), storage.getStorageSettings());
                storage.getStorageSettings().getDirectoryMaxFileSize().put(path,Integer.parseInt(maxFileNum));
                storage.writeStorageConfigToJsonFile(storage.getStorageSettings().getRootPath());

            }
            else {
                System.out.println("Konektovani korisnik nije administrator.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public void setStorage(LocalStorage storage) {
        this.storage = storage;
    }
}
