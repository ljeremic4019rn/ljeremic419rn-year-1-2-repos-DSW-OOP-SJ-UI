import com.google.gson.Gson;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class LocalStorageInitialization implements StorageInitialization{

    private LocalStorage storage;
    Scanner scanner = new Scanner(System.in);
    Gson gson = new Gson();

    public void initializeStorage(String path) { //Metod za inicijalizaciju skladista.
        ArrayList<String> adminDirectories = new ArrayList<>();
        adminDirectories.add(path);


        System.out.println("Unesite svoje korisnicko ime i lozinku (u tom redosledu i u dve linije):");
        User admin = new User(scanner.nextLine(),scanner.nextLine(),true, adminDirectories,new HashMap<>());
        storage.getUsers().clear();
        storage.getUsers().add(admin);//pravimo prvog admina

        storage.setLoggedUser(admin);

        // StorageSettings storageSettingsAdmin = new StorageSettings(true,path + File.separator + "Root");
        StorageSettings storageSettingsAdmin = new StorageSettings(true, path, 1000000,
                new ArrayList<>(), new HashMap<>());
        storage.setStorageSettings(storageSettingsAdmin);//updatujemo storage config

        storage.writeUsersToJsonFile(storageSettingsAdmin.getRootPath(), (ArrayList<User>) storage.getUsers());
        storage.writeStorageConfigToJsonFile(storage.getStorageSettings().getRootPath());
    }

    public void logIn() { //Metod za konektovanje na skladiste.
        System.out.println("Unesite putanju do skladista koje hocete da napravite ili na koje hocete da se konektujete:");

        try {
            String path = scanner.nextLine();

            File file = new File(path);
            File usersJson = new File(path + File.separator + "users.json");
            File configJson = new File(path + File.separator + "rootConfig.json");

            if (file.exists() && usersJson.exists() && configJson.exists()){ //ako root postoji
                storage.parseUsersFromJsonFile(path, (List<User>) storage.getUsers());//pokupi usere is user json
                boolean successfulLogin = false;

                System.out.println("Unesite svoje korisnicko ime i lozinku (u tom redosledu i u dve linije):");

                String username = scanner.nextLine();//username za logovanje
                String password = scanner.nextLine();//pass za logovanje

                for (User u : storage.getUsers()){
                    if (u.getUsername().equals(username) && u.getPassword().equals(password)){//proverimo da li se pass i username matchuju

                        storage.parseStorageConfigFromJsonFile(path,storage.getStorageSettings());//logovali smo se, citamo config
                        successfulLogin = true;

                        if (storage.getStorageSettings().isInUse()){//neko je vec ulogovan
                            System.out.println("Neko je vec konektovan na skladiste.");
                            return;
                        }
                        else{
                            storage.getStorageSettings().setInUse(true);
                            storage.writeStorageConfigToJsonFile(storage.getStorageSettings().getRootPath());//stavaljmo da je in use
                            storage.setLoggedUser(u); //kada se ulogujemo stavimo ovde trenutnog usera

                            System.out.println("Konektovanje na skladiste je bilo uspesno.");
                            return;
                        }
                    }
                }
                if(!successfulLogin){
                    System.out.println("Uneli ste pogresno korisnicko ime i/ili lozinku.");
                }
            }
            else{//ovde je ako nepostoji root, vec ga prvi put pravimo
                initializeStorage(path);
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }


    public void logOut() { //Metod za diskonektovanje sa skladista.

        if (storage.getStorageSettings().isInUse()) {//ako je neko loginovan setujemo storageConfig na false i zapisemo ga
            storage.getStorageSettings().setInUse(false);
            try {
                Writer writer = Files.newBufferedWriter(Paths.get(storage.getStorageSettings().getRootPath() + File.separator + "rootConfig.json"));
                gson.toJson(storage.getStorageSettings(), writer);
                System.out.println("Uspesno ste se diskonektovali sa skladista.");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Diskonektovanje sa skladista je bilo neuspesno (jer niko nije bio ni konektovan).");
        }
    }

    public void addUser() { //Metod za dodavanje korisnika (dostupan samo administratoru).
        try {
            if(storage.getLoggedUser().isAdmin()){
                storage.parseUsersFromJsonFile(storage.getStorageSettings().getRootPath(), (List<User>) storage.getUsers());
                System.out.println("Unesite korisnicko ime i lozinku za novog korisnika (u tom redosledu i u dve linije):");
                User newUser = new User(scanner.nextLine(), scanner.nextLine(),false,new ArrayList<>(), new HashMap<>());//pravimo nogog usera
                storage.getUsers().add(newUser);
                storage.writeUsersToJsonFile(storage.getStorageSettings().getRootPath(), (ArrayList<User>) storage.getUsers());//zapisujemo sve usere + novog
            }
            else {
                System.out.println("Konektovani korisnik nije administrator.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public void removeUser() { //Metod za brisanje korisnika (dostupan samo administratoru).
        try {
            if(storage.getLoggedUser().isAdmin()){
                System.out.println("Unesite korisnicko ime za korisnika koga zelite da obrisete:");
                String username = scanner.nextLine();

                storage.parseUsersFromJsonFile(storage.getStorageSettings().getRootPath(), (List<User>) storage.getUsers());

                int userPosition = 0;

                for (User u : storage.getUsers()){//trazimo usera koji ima username koji zelimo
                    if (u.getUsername().equals(username)){
                        break;
                    }
                    userPosition++;
                }
                ((List<User>) storage.getUsers()).remove(userPosition);//sklomino ga iz liste
                storage.writeUsersToJsonFile(storage.getStorageSettings().getRootPath(), (ArrayList<User>) storage.getUsers());//napisemo usere opet u json
            }
            else {
                System.out.println("Konektovani korisnik nije administrator.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public void editPrivilege() { //Metod za modifikovanje korisnickih privilegija (dostupan samo administratoru).
        try {
            if(storage.getLoggedUser().isAdmin()){
                System.out.println("Unesite korisnicko ime za korisnika kome zelite da promenite privilegije:");
                String username = scanner.nextLine();

                storage.parseUsersFromJsonFile(storage.getStorageSettings().getRootPath(), (List<User>) storage.getUsers());
                Privileges privileges = new Privileges(true, true, true, true);

                for (User u : storage.getUsers()){//nadjemo usera koga zelimo
                    if (u.getUsername().equals(username)){
                        System.out.println("Unesite putanju do direktorijuma u kom zelite da menjate korisnikove privilegije:");
                        String privDirPath = scanner.nextLine(); //upisemo directory za koji zelimo da promenimo privilage

                        System.out.println("Unesite promene za privilegije u zadatom direktorijumu:");
                        System.out.println("Dodatna napomena 1: privilegije se unose u sledecem redosledu - save download remove view");
                        System.out.println("Dodatna napomena 2: za svaku morate napisati true ili false (true znaci da je omogucena, false da je zabranjena");

                        String tmp = scanner.nextLine();
                        String []privilegesArr = tmp.split(" ");

                        if (privilegesArr[0].equals("false")){ //pisemo za sta zelimo da promenimo privilage
                            privileges.setSavePrivilege(false);
                        }
                        if (privilegesArr[1].equals("false")){
                            privileges.setDownloadPrivilege(false);
                        }
                        if (privilegesArr[2].equals("false")){
                            privileges.setRemovePrivilege(false);
                        }
                        if (privilegesArr[3].equals("false")){
                            privileges.setViewPrivilege(false);
                        }
                        u.getPrivilegeBlackList().put(privDirPath, privileges);//stavljamo nove priv u user black list
                        storage.writeUsersToJsonFile(storage.getStorageSettings().getRootPath(), (ArrayList<User>) storage.getUsers());
                    }
                }
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
