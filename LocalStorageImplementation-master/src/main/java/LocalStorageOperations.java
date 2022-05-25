import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class LocalStorageOperations implements StorageOperations{

    private LocalStorage storage;
    Scanner scanner = new Scanner(System.in);

    public void createDirectory() { //Metod za kreiranje direktorijuma.
        System.out.println("Unesite ime i putanju za direktorijum (u tom redosledu i u dve linije):");

        try {
            String name = scanner.nextLine();
            String path = scanner.nextLine();

            if(checkAllConditions(path, 1, name, 0, "save")){
                File file = new File(path + File.separator + name);
                file.mkdir();
            }
            else {
                System.out.println("Zadata putanja nije korektna ili ste prekrsili jedno/vise ogranicenja nad skladistem/direktorijumom.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }


    public void createMultipleDirectories() { //Metod za kreiranje vise direktorijuma odjednom, prosledjuje se u ovom formatu: name(number1:number2).
        System.out.println("Unesite putanju i patern (u formatu: ime(broj1:broj2) ) za direktorijume (u tom redosledu i u dve linije):");

        try {
            String path = scanner.nextLine();
            String pattern = scanner.nextLine();

            String []patternParsed = pattern.split("[():]");

            if (checkAllConditions(path,1, patternParsed[0], 0, "save")){
                for(int i = Integer.parseInt(patternParsed[1]); i <= Integer.parseInt(patternParsed[2]); i++){
                    File file = new File(path + File.separator + patternParsed[0] + i);
                    file.mkdir();
                }
            }
            else {
                System.out.println("Zadata putanja nije korektna ili ste prekrsili jedno/vise ogranicenja nad skladistem/direktorijumom.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }


    public void createFile() { //Metod za kreiranje fajla.
        System.out.println("Unesite ime i putanju za fajl (u tom redosledu i u dve linije):");

        try {
            String name = scanner.nextLine();
            String path = scanner.nextLine();

            if (checkAllConditions(path,1, name, 0, "save")){
                try {
                    File f = new File(path + File.separator + name);
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Zadata putanja nije korektna ili ste prekrsili jedno/vise ogranicenja nad skladistem/direktorijumom.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }


    public void createMultipleFiles() { //Metod za kreiranje vise fajlova odjednom, prosledjuje se u ovom formatu: name(number1:number2).
        System.out.println("Unesite putanju i patern (u formatu: ime(broj1:broj2) ) za fajlove (u tom redosledu i u dve linije):");

        try {
            String path = scanner.nextLine();
            String pattern = scanner.nextLine();

            String []patternParsed = pattern.split("[():]");
            String []nameParsed = patternParsed[0].split("\\.");

            if (checkAllConditions(path,1, patternParsed[0], 0, "save")){

                if (patternParsed[0].contains(".")){
                    nameParsed[1] = "." + nameParsed[1];
                }
                else {
                    nameParsed[0] = patternParsed[0];
                    //nameParsed[1] = "";
                }

                try {
                    for(int i = Integer.parseInt(patternParsed[1]); i <= Integer.parseInt(patternParsed[2]); i++){
                        if (patternParsed[0].contains(".")){
                            File f = new File(path + File.separator + nameParsed[0] + i + nameParsed[1]);
                            f.createNewFile();
                        }
                        else {
                            nameParsed[0] = patternParsed[0];
                            File f = new File(path + File.separator + nameParsed[0] + i);
                            f.createNewFile();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Zadata putanja nije korektna ili ste prekrsili jedno/vise ogranicenja nad skladistem/direktorijumom.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }


    public void copyFiles() { //Metod za kopiranje jednog ili vise fajlova sa odredjene lokacije u direktorijum koji je u skladistu.
        System.out.println("Unesite putanju do direktorijuma gde hocete da kopirate fajlove (za koje u sledecoj liniji morate takodje uneti putanje, razdvojene razmakom):");
        try {
            String directoryPath = scanner.nextLine();
            String filePathsToParse = scanner.nextLine();
            String []filePathsParsed = filePathsToParse.split(" ");
            ArrayList<String> filePaths = new ArrayList<>();

            for(String s : filePathsParsed){
                filePaths.add(s);
            }

            int fileSize = 0;
            String []nameParsedByDot;
            String extension;

            for (String s: filePaths){
                if (s.contains(".")){
                    nameParsedByDot = s.split("\\.");
                    extension = "." + nameParsedByDot[1];//.exe
                }
                else {
                    extension = "";
                }
                fileSize += getDirectorySize(Paths.get(s));
                if(checkStorageConfigurations(directoryPath, filePaths.size(), fileSize, extension) != 0){
                    System.out.println("File " + s + " nije mogao da se kopira u zadati direktorijum. Prekrsili ste jedno ili vise ogranicenja nad skladistem/direktorijumom.");
                    return;
                }
            }
            if(checkIfDirExists(directoryPath) && checkUserPrivileges(directoryPath, "save") == 0){
                for (String sourcePath: filePaths){
                    try {
                        File sourceFile = new File(sourcePath);
                        File destinationFile = new File(directoryPath);
                        FileUtils.copyFileToDirectory(sourceFile,destinationFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                System.out.println("Zadata putanja nije korektna ili nemate privilegiju za snimanje.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public void removeDirectory() { //Metod za brisanje direktorijuma.
        System.out.println("Unesite putanju do direktorijuma koji zelite da izbrisete:");

        try {
            String path = scanner.nextLine();

            if(checkIfDirExists(path) && checkUserPrivileges(path.substring(0, path.lastIndexOf("\\")), "remove") == 0) {
                try {
                    File directoryToRemove = new File(path);
                    FileUtils.deleteDirectory(directoryToRemove);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Zadata putanja nije korektna ili nemate privilegiju za brisanje.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public void removeFile() { //Metod za brisanje fajla.
        System.out.println("Unesite putanju do fajla koji zelite da izbrisete:");

        try {
            String path = scanner.nextLine();

            if(checkIfDirExists(path) && checkUserPrivileges(path.substring(0, path.lastIndexOf("\\")), "remove") == 0) {
                File fileToRemove = new File(path);
                fileToRemove.delete();
            }
            else {
                System.out.println("Zadata putanja nije korektna ili nemate privilegiju za brisanje.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public void findAllFilesInDirectory() { //Metod koji vraca nazive svih fajlova u direktorijumu.
        System.out.println("Unesite putanju do direktorijuma:");

        try {
            String path = scanner.nextLine();

            if (checkIfDirExists(path) && checkUserPrivileges(path, "view") == 0){
                File dir = new File(path);
                File[] directoryListing = dir.listFiles();
                if (directoryListing != null) {
                    for (File file : directoryListing) {
                        if (file.isFile()){
                            System.out.println(file.getName());
                        }
                    }
                } else {
                    System.out.println("Zadati direktorijum je prazan.");
                }
            }
            else {
                System.out.println("Zadata putanja nije korektna ili nemate privilegiju za gledanje.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public void findAllDirectoriesInDirectory() { //Metod koji vraca nazive svih direktorijuma u direktorijumu.
        System.out.println("Unesite putanju do direktorijuma:");

        try {
            String path = scanner.nextLine();

            if (checkIfDirExists(path) && checkUserPrivileges(path, "view") == 0){
                File dir = new File(path);
                File[] directoryListing = dir.listFiles();
                if (directoryListing != null) {
                    for (File file : directoryListing) {
                        if (file.isDirectory()){
                            System.out.println(file.getName());
                        }
                    }
                } else {
                    System.out.println("Zadati direktorijum je prazan.");
                }
            }
            else {
                System.out.println("Zadata putanja nije korektna ili nemate privilegiju za gledanje.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public void findFilesByNameInDirAndSubdirs() { //Metod koji vraca nazive svih fajlova u direktorijumu i njegovim subdirektorijumima.
        System.out.println("Unesite putanju do direktorijuma i naziv fajla kome ce se vrsiti pretraga (u tom redosledu i u dve linije):");

        try {
            String path = scanner.nextLine();
            String fileName = scanner.nextLine();

            if (checkIfDirExists(path) && checkUserPrivileges(path, "view") == 0){
                File dir = new File(path);
                showFiles(Objects.requireNonNull(dir.listFiles()),fileName);
            }
            else {
                System.out.println("Zadata putanja nije korektna ili nemate privilegiju za gledanje.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public void findFilesByExtension() { //Metod koji vraca nazive svih fajlova sa nekom ekstenzijom, u direktorijumu.
        System.out.println("Unesite putanju do direktorijuma i ekstenziju (sa tackom na pocetku) po kojoj ce se vrsiti pretraga (u tom redosledu i u dve linije):");

        try {
            String path = scanner.nextLine();
            String extension = scanner.nextLine();

            if (checkIfDirExists(path) && checkUserPrivileges(path, "view") == 0){
                File dir = new File(path);
                File[] directoryListing = dir.listFiles();
                if (directoryListing != null) {
                    for (File file : directoryListing) {
                        if (file.isFile() && FilenameUtils.getExtension(file.getName()).equals(extension)){
                            System.out.println(file.getName());
                        }
                    }
                } else {
                    System.out.println("Zadati direktorijum ne postoji.");
                }
            }
            else {
                System.out.println("Zadata putanja nije korektna ili nemate privilegiju za gledanje.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public void findFilesByNameSorted() { //Metod koji vraca nazive svih fajlova u direktorijumu, sortirane po nazivu.
        System.out.println("Unesite putanju do direktorijuma:");

        try {
            String path = scanner.nextLine();

            if (checkIfDirExists(path) && checkUserPrivileges(path, "view") == 0){
                File dir = new File(path);
                File[] directoryListing = dir.listFiles();
                ArrayList<String> fileNames = new ArrayList<>();
                if (directoryListing != null) {
                    for (File file : directoryListing) {
                        if (file.isFile()){
                            fileNames.add(file.getName());
                        }
                    }
                } else {
                    System.out.println("Zadati direktorijum ne postoji.");
                }
                fileNames.sort(null);
                for(String s : fileNames){
                    System.out.println(s);
                }
            }
            else {
                System.out.println("Zadata putanja nije korektna ili nemate privilegiju za gledanje.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public void findFilesByDateSorted() { //Metod koji vraca nazive svih fajlova u direktorijumu, sortirane po datumu kreacije.
        System.out.println("Unesite putanju do direktorijuma:");

        try {
            String path = scanner.nextLine();

            File dir = new File(path);
            BasicFileAttributes attr;
            Map<FileTime, String> map = new HashMap<>();

            if (checkIfDirExists(path) && checkUserPrivileges(path, "view") == 0){
                File[] directoryListing = dir.listFiles();
                if (directoryListing != null) {
                    for (File file : directoryListing) {
                        if (file.isFile()){
                            try {
                                attr = Files.readAttributes(Path.of(file.getAbsolutePath()), BasicFileAttributes.class);
                                map.put(attr.creationTime(), file.getName());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    System.out.println("Zadati direktorijum ne postoji.");
                    return;
                }
                TreeMap<FileTime, String> sorted = new TreeMap<>(map);
                for (Map.Entry<FileTime, String> entry : sorted.entrySet()) {
                    System.out.println(entry.getValue() + "     " + entry.getKey());
                }
            }
            else {
                System.out.println("Zadata putanja nije korektna ili nemate privilegiju za gledanje.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public void findFilesByDateModifiedSorted() { //Metod koji vraca nazive svih fajlova u direktorijumu, sortirane po datumu modifikacije.
        System.out.println("Unesite putanju do direktorijuma:");

        try {
            String path = scanner.nextLine();

            File dir = new File(path);
            BasicFileAttributes attr;
            Map<FileTime, String> map = new HashMap<>();

            if (checkIfDirExists(path) && checkUserPrivileges(path, "view") == 0){
                File[] directoryListing = dir.listFiles();
                if (directoryListing != null) {
                    for (File file : directoryListing) {
                        if (file.isFile()){
                            try {
                                attr = Files.readAttributes(Path.of(file.getAbsolutePath()), BasicFileAttributes.class);
                                map.put(attr.lastModifiedTime(), file.getName());

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    System.out.println("Zadati direktorijum je prazan.");
                }
                TreeMap<FileTime, String> sorted = new TreeMap<>(map);
                for (Map.Entry<FileTime, String> entry : sorted.entrySet()) {
                    System.out.println(entry.getValue() + "     " + entry.getKey());
                }
            }
            else {
                System.out.println("Zadata putanja nije korektna ili nemate privilegiju za gledanje.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public void findFilesBySpecificCreatedModifiedTime() { //Metod koji vraca nazive svih fajlova u direktorijumu, koji su bili kreirani/modifikovani u datom periodu.
        System.out.println("Unesite putanju do direktorijuma:");
        String path = scanner.nextLine();
        System.out.println("Unesite pocetni datum (format: dd-MM-yyyy HH:mm:ss):");
        String dateStart = scanner.nextLine();
        System.out.println("Unesite krajnji datum (format: dd-MM-yyyy HH:mm:ss):");
        String dateEnd = scanner.nextLine();
        System.out.println("Unesite da li zelite ispis po datumu kreacije ili modifikacije (unosi se jedna od kljucnih reci 'created' i 'modified':");
        String createdOrModified = scanner.nextLine();

        try {
            BasicFileAttributes attr;
            Date  startDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(dateStart);
            Date  endDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(dateEnd);
            Date date;

            if (checkIfDirExists(path) && checkUserPrivileges(path, "view") == 0){
                File dir = new File(path);
                File[] directoryListing = dir.listFiles();
                if (directoryListing != null) {
                    for (File file : directoryListing) {
                        if (file.isFile()){
                            attr = Files.readAttributes(Path.of(file.getAbsolutePath()), BasicFileAttributes.class);

                            if(createdOrModified.equals("created")){
                                date = new Date(attr.creationTime().toMillis());
                                if (date.after(startDate) && date.before(endDate)){
                                    System.out.println(file.getName());
                                }
                            }
                            else if (createdOrModified.equals("modified")){
                                date = new Date(attr.lastModifiedTime().toMillis());
                                if (date.after(startDate) && date.before(endDate)){
                                    System.out.println(file.getName());
                                }
                            }
                        }
                    }
                } else {
                    System.out.println("Zadati direktorijum je prazan.");
                }
            }
            else {
                System.out.println("Zadata putanja nije korektna ili nemate privilegiju za gledanje.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public void moveFiles() { //Metod s kojim se pomera jedan ili vise fajlova iz jednog direktorijuma u skladistu u drugi.
        System.out.println("Unesite putanju za direktorijum gde zelite pomeriti fajlove (u prvoj liniji), i putanje za fajlove koje zelite pomeriti (u drugoj liniji i razdvojene razmakom):");

        try {
            String directoryPath = scanner.nextLine();
            String filePathsToParse = scanner.nextLine();
            String []parsedFilePaths = filePathsToParse.split(" ");
            ArrayList<String> filePaths = new ArrayList<>();

            for(String s : parsedFilePaths){
                filePaths.add(s);
            }
            int fileSize = 0;
            String []nameParsedByDot;
            String extension;

            for (String s: filePaths){
                if (s.contains(".")){
                    nameParsedByDot = s.split("\\.");
                    extension = "." + nameParsedByDot[1];//.exe
                }
                else {
                    extension = "";
                }
                fileSize += getDirectorySize(Paths.get(s));
                if(checkStorageConfigurations(directoryPath, filePaths.size(), fileSize, extension) != 0){
                    System.out.println("Fajl " + s + " nije mogao da se pomeri u zadati direktorijum. Prekrsili ste jedno ili vise ogranicenja nad skladistem/direktorijumom.");
                    return;
                }
            }
            if(checkIfDirExists(directoryPath) && checkUserPrivileges(directoryPath, "save") == 0){
                for (String sourcePath: filePaths){
                    try {
                        File sourceFile = new File(sourcePath);
                        File destinationFile = new File(directoryPath);
                        FileUtils.moveFileToDirectory(sourceFile,destinationFile, false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                System.out.println("Zadata putanja nije korektna ili nemate privilegiju za snimanje.");
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public void downloadFiles() { //Metod za preuzimanje jednog ili vise fajlova iz skladista u Downloads direktorijum.
        System.out.println("Unesite putanje do fajlova koje hocete da preuzmete (odvojene razmakom):");

        try {
            String filePathsToParse = scanner.nextLine();
            String []parsedFilePaths = filePathsToParse.split(" ");
            ArrayList<String> fileLocations = new ArrayList<>();

            for(String s : parsedFilePaths){
                fileLocations.add(s);
            }
            String home = System.getProperty("user.home");
            File downloadFile = new File(home+"/Downloads");

            for(String s : fileLocations){
                if(!(checkIfDirExists(s) && checkUserPrivileges(s.substring(0, s.lastIndexOf("\\")), "download") == 0)){
                    System.out.println("Neke od zadatih putanja nisu korektne ili nemate privilegiju za preuzimanje.");
                    return;
                }
            }

            for(String sourcePath : fileLocations) {
                try {
                    File sourceFile = new File(sourcePath);
                    FileUtils.copyFileToDirectory(sourceFile, downloadFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("Pogresili ste u unosenju trazenih podataka.");
        }
    }

    public static void showFiles(File[] files, String findFileName) { //Pomocni metod koji se koristi za ispisivanje naziva fajlova.
        for (File file : files) {
            if (file.isDirectory()) {
                showFiles(Objects.requireNonNull(file.listFiles()), findFileName);
            } else {
                if(file.getName().equals(findFileName)){
                    System.out.println(file.getName() + "      " + file.getAbsolutePath());
                }
            }
        }
    }

    public int checkUserPrivileges(String path, String privilegeToCheck) { //Metod za proveru ogranicenja vezanih za korisnicke privilegije.
        int privilegeErrorNum = 0;

        if (storage.getLoggedUser().getPrivilegeBlackList().containsKey(path)){
            privilegeErrorNum = checkPrivilege(path,privilegeToCheck);
        }
        return privilegeErrorNum;
    }

    public int checkStorageConfigurations(String dirPathForMaxFileSIzeCheck, int fileAddedNumber, int fileSize, String fileExtension) { //Metod za proveru ogranicenja vezanih za konfiguracije skladista.
        storage.parseStorageConfigFromJsonFile(storage.getStorageSettings().getRootPath(), storage.getStorageSettings());
        Path path = Paths.get(storage.getStorageSettings().getRootPath());
        long storageSize = getDirectorySize(path);

        if ((storageSize + fileSize) > storage.getStorageSettings().getStorageMaxSize()){
            System.out.println("Predjena je maksimalna velicina skladista (u bajtovima).");
            return 1;
        }
        else if (storage.getStorageSettings().getExtensionBlacklist().contains(fileExtension)){
            System.out.println("Ekstenzija " + fileExtension + " nije dozvoljena u direktorijumu.");
            return 1;
        }
        else if(storage.getStorageSettings().getDirectoryMaxFileSize().containsKey(dirPathForMaxFileSIzeCheck)){
            long fileCount = countFilesInDir(dirPathForMaxFileSIzeCheck);
            if(fileCount + fileAddedNumber >  storage.getStorageSettings().getDirectoryMaxFileSize().get(dirPathForMaxFileSIzeCheck)){
                System.out.println("Predjen je maksimalni broj fajlova u direktorijumu.");
                return 1;
            }
        }
        //System.out.println("all good");
        return 0;
    }

    public boolean checkAllConditions (String path,int fileAddedNumber, String name, int fileSize, String action){ //Metod za proveru svih relevantnih ogranicenja nad operacijama.
        String []nameParsedByDot;
        String extension;

        if (name.contains(".")){
            nameParsedByDot = name.split("\\.");
            extension = "." + nameParsedByDot[1];//.exe
        }
        else {
            extension = "";
        }

        if(checkIfDirExists(path) &&
                checkUserPrivileges(path, action) == 0 &&
                    checkStorageConfigurations(path,fileAddedNumber, fileSize, extension) == 0){
            return true;
        }
        return false;
    }

    public int checkPrivilege (String path, String privilegeToCheck){ //Pomocni metod za proveru korisnickih privilegija.
        switch (privilegeToCheck){
            case "view" :
                if(!storage.getLoggedUser().getPrivilegeBlackList().get(path).isViewPrivilege()){
                    System.out.println("Korisnik nema privilegiju za gledanje.");
                    return 3;
                }
                break;
            case "save" :
                if(!storage.getLoggedUser().getPrivilegeBlackList().get(path).isSavePrivilege()){
                    System.out.println("Korisnik nema privilegiju za snimanje.");
                    return 3;
                }
                break;
            case "download" :
                if(!storage.getLoggedUser().getPrivilegeBlackList().get(path).isDownloadPrivilege()){
                    System.out.println("Korisnik nema privilegiju za preuzimanje.");
                    return 3;
                }
                break;
            case "remove" :
                if(!storage.getLoggedUser().getPrivilegeBlackList().get(path).isRemovePrivilege()){
                    System.out.println("Korisnik nema privilegiju za brisanje.");
                    return 3;
                }
                break;
        }
        return 0;
    }

    public boolean checkIfDirExists (String path){ //Pomocni metod koji proverava da li direktorijum postoji.
        if (path.contains(storage.getStorageSettings().getRootPath())){
            //String pathChecker = path.substring(path.lastIndexOf(storage.getStorageSettings().getRootPath())+1, path.lastIndexOf(path));
            /* if (!path.endsWith("Root")){
                String []pathParsedByRoot = path.split("Root");//C:\Users\Shus\Desktop\        \dir\dir1
                String []rightSideOfPath = pathParsedByRoot[1].split("\\\\");//dir-dir1
                String pathBuilder = pathParsedByRoot[0] + "Root"; //C:\Users\Shus\Desktop\+Root

                for (int i = 1; i < rightSideOfPath.length; i++){
                    pathBuilder = pathBuilder + File.separator + rightSideOfPath[i];
                    File file = new File(pathBuilder);

                    if (!file.exists())
                        return false;
                }
            } */
            File file = new File(path);
            if(file.exists())
                return true;
        }
        //System.out.println("path is good");
        return false;
    }

    public static long getDirectorySize(Path path) { //Pomocni metod koji vraca velicinu direktorijuma u bajtovima.
        AtomicLong size = new AtomicLong(0);
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<>() {

                
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    // sum size of all visit file
                    size.addAndGet(attrs.size());
                    return FileVisitResult.CONTINUE;
                }

                
                public FileVisitResult visitFileFailed(Path file, IOException e) {
                    System.out.printf("Failed to get size of %s%n%s", file, e);
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (IOException e) {
            System.out.printf("%s", e);
        }
        return size.get();
    }

    public long countFilesInDir(String path){ //Pomocni metod koji broji broj fajlova u direktorijumu.
        long count = 0;
        try (Stream<Path> files = Files.list(Paths.get(path))) {
             count = files.count();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    public void setStorage(LocalStorage storage) {
        this.storage = storage;
    }
}
