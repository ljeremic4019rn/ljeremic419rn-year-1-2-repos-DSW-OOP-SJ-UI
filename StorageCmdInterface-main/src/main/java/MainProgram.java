import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MainProgram {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Class.forName("LocalStorage");//RemoteStorage
        Storage storage = StorageManager.getStorage();

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nDobrodosli u program za upravljanje skladistima!");
        System.out.println("Odaberite jednu od akcija (tako sto cete uneti rec koja stoji pored akcije):");
        System.out.println("------------------------------------------------\nAKCIJE VEZANE ZA INICIJALIZACIJU I KORISNIKE:");
        System.out.println("login - Inicijalizacija skladista/konektovanje na skladiste\nlogout - Diskonektovanje sa skladista\nadduser - Dodavanje korisnika (dostupno samo administratoru skladista)\nrmvuser - Brisanje korisnika (dostupno samo administratoru skladista)\neditpriv - Modifikacija korisnickih privilegija (dostupno samo administratoru skladista)");
        System.out.println("------------------------------------------------\nAKCIJE VEZANE ZA KONFIGURACIJU SKLADISTA:");
        System.out.println("setstrgsize - Promena maksimalne velicine skladista (dostupno samo administratoru skladista)\nsetstrgext - Zabranjivanje odredjene ekstenzije za skladiste (dostupno samo administratoru skladista)\nsetdirfilelim - Ogranicenje broja fajlova za direktorijum (dostupno samo administratoru skladista)");
        System.out.println("------------------------------------------------\nAKCIJE VEZANE ZA OPERACIJE NAD SKLADISTEM:");
        System.out.println("mkdir - Kreiranje direktorijuma\nmkmuldirs - Kreiranje vise direktorijuma odjednom\nmkfile - Kreiranje fajla\nmkmulfiles - Kreiranje vise fajlova odjednom\ncpfiles - Kopiranje fajlova u odredjeni direktorijum\nrmvdir - Brisanje direktorijuma\nrmvfile - Brisanje fajla\nfindfilesname - Ispis svih fajlova u direktorijumu\nfinddirs - Ispis svih direktorijuma u direktorijumu\nfindfilesdirssubdirs - Ispis svih fajlova u direktorijumu i subdirektorijumima\nfindfilesext - Ispis svih fajlova (u direktorijumu) sa odredjenom ekstenzijom\nfindfilesnamesrtd - Ispis svih fajlova u direktorijumu, sortiranih po imenu\nfindfilescdatesrtd - Ispis svih fajlova u direktorijumu, sortiranih po datumu kreacije\nfindfilesmdatesrtd - Ispis svih fajlova u direktorijumu, sortiranih po datumu modifikacije\nfindfilesfromtime - Ispis svih fajlova u direktorijumu, kreiranih/modifikovanih u odredjenom vremenskom periodu\nmvfiles - Pomeranje fajlova iz jednog direktorijuma u drugi\ndlfiles - Preuzimanje fajlova");
        System.out.println("------------------------------------------------\nOSTALE AKCIJE:");
        System.out.println("exit - Izlazak iz programa\nhelp - Ponovni ispis svih mogucih akcija\n");
        String userInput = scanner.nextLine();

        while (!"exit".equals(userInput)) {
            switch (userInput){
                case "login":
                    storage.getStorageInitialization().logIn();
                    break;
                case "logout":
                    storage.getStorageInitialization().logOut();
                    break;
                case "adduser":
                    storage.getStorageInitialization().addUser();
                    break;
                case "rmvuser":
                    storage.getStorageInitialization().removeUser();
                    break;
                case "editpriv":
                    storage.getStorageInitialization().editPrivilege();
                    break;
                case "setstrgsize":
                    storage.getStorageConfiguration().setStorageSizeLimit();
                    break;
                case "setstrgext":
                    storage.getStorageConfiguration().setExtensionBlackList();
                    break;
                case "setdirfilelim":
                    storage.getStorageConfiguration().setDirectoryFileLimit();
                    break;
                case "mkdir":
                    storage.getStorageOperations().createDirectory();
                    break;
                case "mkmuldirs":
                    storage.getStorageOperations().createMultipleDirectories();
                    break;
                case "mkfile":
                    storage.getStorageOperations().createFile();
                    break;
                case "mkmulfiles":
                    storage.getStorageOperations().createMultipleFiles();
                    break;
                case "cpfiles":
                    storage.getStorageOperations().copyFiles();
                    break;
                case "rmvdir":
                    storage.getStorageOperations().removeDirectory();
                    break;
                case "rmvfile":
                    storage.getStorageOperations().removeFile();
                    break;
                case "findfilesname":
                    storage.getStorageOperations().findAllFilesInDirectory();
                    break;
                case "finddirs":
                    storage.getStorageOperations().findAllDirectoriesInDirectory();
                    break;
                case "findfilesdirssubdirs":
                    storage.getStorageOperations().findFilesByNameInDirAndSubdirs();
                    break;
                case "findfilesext":
                    storage.getStorageOperations().findFilesByExtension();
                    break;
                case "findfilesnamesrtd":
                    storage.getStorageOperations().findFilesByNameSorted();
                    break;
                case "findfilescdatesrtd":
                    storage.getStorageOperations().findFilesByDateSorted();
                    break;
                case "findfilesmdatesrtd":
                    storage.getStorageOperations().findFilesByDateModifiedSorted();
                    break;
                case "findfilesfromtime":
                    storage.getStorageOperations().findFilesBySpecificCreatedModifiedTime();
                    break;
                case "mvfiles":
                    storage.getStorageOperations().moveFiles();
                    break;
                case "dlfiles":
                    storage.getStorageOperations().downloadFiles();
                    break;
                case "help":
                    System.out.println("Odaberite jednu od akcija (tako sto cete uneti rec koja stoji pored akcije):");
                    System.out.println("------------------------------------------------\nAKCIJE VEZANE ZA INICIJALIZACIJU I KORISNIKE:");
                    System.out.println("login - Inicijalizacija skladista/konektovanje na skladiste\nlogout - Diskonektovanje sa skladista\nadduser - Dodavanje korisnika (dostupno samo administratoru skladista)\nrmvuser - Brisanje korisnika (dostupno samo administratoru skladista)\neditpriv - Modifikacija korisnickih privilegija (dostupno samo administratoru skladista)");
                    System.out.println("------------------------------------------------\nAKCIJE VEZANE ZA KONFIGURACIJU SKLADISTA:");
                    System.out.println("setstrgsize - Promena maksimalne velicine skladista (dostupno samo administratoru skladista)\nsetstrgext - Zabranjivanje odredjene ekstenzije za skladiste (dostupno samo administratoru skladista)\nsetdirfilelim - Ogranicenje broja fajlova za direktorijum (dostupno samo administratoru skladista)");
                    System.out.println("------------------------------------------------\nAKCIJE VEZANE ZA OPERACIJE NAD SKLADISTEM:");
                    System.out.println("mkdir - Kreiranje direktorijuma\nmkmuldirs - Kreiranje vise direktorijuma odjednom\nmkfile - Kreiranje fajla\nmkmulfiles - Kreiranje vise fajlova odjednom\ncpfiles - Kopiranje fajlova u odredjeni direktorijum\nrmvdir - Brisanje direktorijuma\nrmvfile - Brisanje fajla\nfindfilesname - Ispis svih fajlova u direktorijumu\nfinddirs - Ispis svih direktorijuma u direktorijumu\nfindfilesdirssubdirs - Ispis svih fajlova u direktorijumu i subdirektorijumima\nfindfilesext - Ispis svih fajlova (u direktorijumu) sa odredjenom ekstenzijom\nfindfilesnamesrtd - Ispis svih fajlova u direktorijumu, sortiranih po imenu\nfindfilescdatesrtd - Ispis svih fajlova u direktorijumu, sortiranih po datumu kreacije\nfindfilesmdatesrtd - Ispis svih fajlova u direktorijumu, sortiranih po datumu modifikacije\nfindfilesfromtime - Ispis svih fajlova u direktorijumu, kreiranih/modifikovanih u odredjenom vremenskom periodu\nmvfiles - Pomeranje fajlova iz jednog direktorijuma u drugi\ndlfiles - Preuzimanje fajlova");
                    System.out.println("------------------------------------------------\nOSTALE AKCIJE:");
                    System.out.println("exit - Izlazak iz programa\nhelp - Ponovni ispis svih mogucih akcija\n");
                    break;
                default:
                    System.out.println("Uneli ste nedozvoljenu vrednost.\n");
            }
            System.out.println("Odaberite sledecu akciju:");
            userInput = scanner.nextLine();
        }
        storage.getStorageInitialization().logOut();
        System.out.println("Zbogom, korisnice!");
    }
}
