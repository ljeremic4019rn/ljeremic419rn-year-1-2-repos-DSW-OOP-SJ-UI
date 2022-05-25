import com.google.api.services.drive.model.File;
import org.apache.commons.io.FileUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class RemoteStorageInitialization implements StorageInitialization{
    private String rootName;
    RemoteStorage storage;
    private boolean loggedIn = false;
    Scanner scanner = new Scanner(System.in);

    String home = System.getProperty("user.home");//napravimo tmp storage za json filove
    java.io.File tmpFile = new java.io.File(home + "\\Downloads\\tmpDir");


    public void initializeStorage(String parentId) {
        try {
            storage.folderCreator(parentId, rootName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logIn() {
        if (!loggedIn) {

            System.out.println("Unesite ime zeljenog root foldera i ime parent direktorijuma od root foldera (u tom redosledu i u dve linije):");
            rootName = scanner.nextLine();
            String parentDirName = scanner.nextLine();
            String parentId = storage.getAllDriveFiles().get(parentDirName);
            String rootId;

            if (storage.getAllDriveFiles().containsKey(rootName) && storage.getAllDriveFiles().containsKey(parentDirName)) {//da li mapa sadrzi root koji trazimo
                for (File file : storage.getFiles()) {//prodjemo kroz listu filova, nadjemo nas root
                    if (file.getName().equals(rootName) && file.getParents() != null && file.getParents().contains(parentId)) {//ako ima isti id parenta kao nas ukucani parent, onda root postoji i nadjen je
                        rootId = storage.getAllDriveFiles().get(rootName);

                        String usersJsonId = findFileIdByParent("users.json", rootId);//pronadjemo id od nasih configa, specificno u root folderu
                        String rootConfigJsonId = findFileIdByParent("rootConfig.json", rootId);

                        if (usersJsonId != null && rootConfigJsonId != null) {//root ima json filove, znaci jeste zapravo root directory
                            tmpFile.mkdir(); //napravimo tmp storage za json filove
                            try {
                                storage.downloadFile(usersJsonId, "users.json", home + "\\Downloads\\tmpDir");//skinemo u tmp storage
                                storage.downloadFile(rootConfigJsonId, "rootConfig.json", home + "\\Downloads\\tmpDir");

                                storage.parseStorageConfigFromJsonFile(home + "\\Downloads\\tmpDir", storage.getStorageSettings());//ucitamo skinute json filove
                                storage.parseUsersFromJsonFile(home + "\\Downloads\\tmpDir", (List<User>) storage.getUsers());

                                System.out.println("Upisite vas username i password (u tom redosledu i u dve linije)");
                                String username = scanner.nextLine();
                                String password = scanner.nextLine();

                                for (User user : storage.getUsers()) {
                                    if (user.getUsername().equals(username) && user.getPassword().equals(password)) {//proverimo da li user postoji

                                        if (storage.getStorageSettings().isInUse()) {//neko je vec ulogovan
                                            System.out.println("Neko je vec konektovan na skladiste.");
                                            return;
                                        } else {
                                            storage.getStorageSettings().setInUse(true);//namestimo sve vezano za storage settings
                                            storage.writeStorageConfigToJsonFile(home + "\\Downloads\\tmpDir");//stavaljmo da je in use
                                            storage.setLoggedUser(user); //kada se ulogujemo stavimo ovde trenutnog usera
                                            storage.replaceConfigJsonFiles(rootId, "rootConfig.json");//stavimo da je neko ulogovan
                                            loggedIn = true;
                                            System.out.println("Konektovanje na skladiste je bilo uspesno.");
                                            return;
                                        }
                                    }
                                }
                                System.err.println("Pogresno uneti username ili password");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else System.err.println("Uneti direktorijum nije root skladista");
                    }
                }
                System.err.println("root nije na unetoj lokaciji");
            } else if (storage.getAllDriveFiles().containsKey(parentDirName)) {//ako root ne postoji, ali postoji parent dir
                System.out.println("Nepostojeci root folder, kreira se novi u izabranom direktorijumu");

                initializeStorage(parentId);//napravimo prazan root folder

                rootId = storage.getAllDriveFiles().get(rootName);
                ArrayList<String> adminDirectories = new ArrayList<>();
                adminDirectories.add(rootId);
                System.out.println("Unesite username i password admina, (u tom redosledu i u dve linije)");
                User admin = new User(scanner.nextLine(), scanner.nextLine(), true, adminDirectories, new HashMap<>());

                storage.getUsers().add(admin);//pravimo prvog admina
                storage.setLoggedUser(admin);

                StorageSettings storageSettingsAdmin = new StorageSettings(true, rootId, 1000000, new ArrayList<>(), new HashMap<>());
                storage.setStorageSettings(storageSettingsAdmin);//updatujemo storage config

                tmpFile.mkdir();

                storage.writeUsersToJsonFile(home + "\\Downloads\\tmpDir", (ArrayList<User>) storage.getUsers());//napisemo sve u loacl json
                storage.writeStorageConfigToJsonFile(home + "\\Downloads\\tmpDir");

                try {
                    storage.uploadFile(rootId, home + "\\Downloads\\tmpDir\\users.json", "users.json");//upladujemo local json
                    storage.uploadFile(rootId, home + "\\Downloads\\tmpDir\\rootConfig.json", "rootConfig.json");

                } catch (IOException e) {
                    e.printStackTrace();
                }

                loggedIn = true;
                System.out.println("Konektovanje na skladiste je bilo uspesno.");
            } else {//ako ne postoji ni root folder ni parent folder
                System.err.println("Nepostojeci root parent direktorijum i root direktorijum");
            }
        }else System.err.println("Molimo vas da se islogujete pre ponovnog logovanja");

    }


    public void logOut() {
        if (storage.getStorageSettings().isInUse()) {//ako je neko loginovan setujemo storageConfig na false i zapisemo ga
            storage.getStorageSettings().setInUse(false);
            try {
                storage.writeStorageConfigToJsonFile(home + "\\Downloads\\tmpDir");
                System.out.println("Uspesno ste se diskonektovali sa skladista.");
                storage.replaceConfigJsonFiles(storage.storageSettings.getRootPath(), "rootConfig.json");
                System.out.println("flushing system of old files");
                FileUtils.deleteDirectory(tmpFile);
                loggedIn = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Diskonektovanje sa skladista je bilo neuspesno (jer niko nije bio ni konektovan).");
        }
    }


    public void addUser() {
        try {
            if(storage.getLoggedUser().isAdmin()){
                storage.parseUsersFromJsonFile(home + "\\Downloads\\tmpDir", (List<User>) storage.getUsers());
                System.out.println("Unesite korisnicko ime i lozinku za novog korisnika (u tom redosledu i u dve linije):");
                User newUser = new User(scanner.nextLine(), scanner.nextLine(),false,new ArrayList<>(), new HashMap<>());//pravimo nogog usera
                storage.getUsers().add(newUser);
                storage.writeUsersToJsonFile(home + "\\Downloads\\tmpDir", (ArrayList<User>) storage.getUsers());//zapisujemo sve usere + novog
                storage.replaceConfigJsonFiles(storage.getStorageSettings().getRootPath(), "users.json");
            }
            else System.out.println("Konektovani korisnik nije administrator.");
        } catch (Exception e) {
             System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }

    }

    public void removeUser() {
        try {
            if(storage.getLoggedUser().isAdmin()){
                System.out.println("Unesite korisnicko ime za korisnika koga zelite da obrisete:");
                String username = scanner.nextLine();

                storage.parseUsersFromJsonFile(home + "\\Downloads\\tmpDir", (List<User>) storage.getUsers());

                int userPosition = 0;

                for (User u : storage.getUsers()){//trazimo usera koji ima username koji zelimo
                    if (u.getUsername().equals(username)){
                        break;
                    }
                    userPosition++;
                }
                ((List<User>) storage.getUsers()).remove(userPosition);//sklomino ga iz liste
                storage.writeUsersToJsonFile(home + "\\Downloads\\tmpDir", (ArrayList<User>) storage.getUsers());//napisemo usere opet u json
                storage.replaceConfigJsonFiles(storage.getStorageSettings().getRootPath(), "users.json");
            }
            else System.out.println("Konektovani korisnik nije administrator.");
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public void editPrivilege() {
        try {
            if(storage.getLoggedUser().isAdmin()){
                System.out.println("Unesite korisnicko ime za korisnika kome zelite da promenite privilegije:");
                String username = scanner.nextLine();

                storage.parseUsersFromJsonFile(home + "\\Downloads\\tmpDir", (List<User>) storage.getUsers());
                Privileges privileges = new Privileges(true, true, true, true);

                for (User u : storage.getUsers()){//nadjemo usera koga zelimo
                    if (u.getUsername().equals(username)){
                        System.out.println("Unesite direktorijuma u kom zelite da menjate korisnikove privilegije:");
                        String privDirPath = scanner.nextLine(); //upisemo directory za koji zelimo da promenimo privilage

                        System.out.println("Unesite promene za privilegije u zadatom direktorijumu:");
                        System.out.println("Dodatna napomena 1: privilegije se unose u sledecem redosledu - save download remove view");
                        System.out.println("Dodatna napomena 2: za svaku morate napisati true ili false (true znaci da je omogucena, false da je zabranjena");

                        String tmp = scanner.nextLine();
                        String []privilegesArr = tmp.split(" ");

                        if (privilegesArr[0].equals("false")){ //pisemo za sta zelimo da promenimo privilage
                            privileges.setSavePrivilege(false);
                        }
                        else if (privilegesArr[1].equals("false")){
                            privileges.setDownloadPrivilege(false);
                        }
                        else if (privilegesArr[2].equals("false")){
                            privileges.setRemovePrivilege(false);
                        }
                        else if (privilegesArr[3].equals("false")){
                            privileges.setViewPrivilege(false);
                        }
                        u.getPrivilegeBlackList().put(privDirPath, privileges);//stavljamo nove priv u user black list
                        storage.writeUsersToJsonFile(home + "\\Downloads\\tmpDir", (ArrayList<User>) storage.getUsers());
                        storage.replaceConfigJsonFiles(storage.getStorageSettings().getRootPath(), "users.json");
                    }
                }
            }
            else System.out.println("Konektovani korisnik nije administrator.");

        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public String findFileIdByParent(String fileName, String parentId){
        String fileId = null;

        for (File file : storage.getFiles()){
            if (file.getName().equals(fileName) && file.getParents().contains(parentId))
                fileId = file.getId();
        }
        return fileId;
    }

    public RemoteStorage getStorage() {
        return storage;
    }
    public void setStorage(RemoteStorage storage) {
        this.storage = storage;
    }
}
