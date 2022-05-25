import com.google.api.client.util.DateTime;
import com.google.api.services.drive.model.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

public class RemoteStorageOperations implements StorageOperations{

    RemoteStorage storage;
    Scanner scanner = new Scanner(System.in);

    public void createDirectory() {
        System.out.println("Unesite ime parent direktorijuma gde zelite da napravite direktorijum i ime direktorijum koji pravite (u tom redosledu i u dve linije)");
        String parentDirName = scanner.nextLine();
        String name = scanner.nextLine();

        if (checkAllConditions(parentDirName,1, name,0, "save")){
            try {
                storage.folderCreator(storage.getAllDriveFiles().get(parentDirName), name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createMultipleDirectories() {
        System.out.println("Unesite parent directory ime i pattern (u formatu: ime(broj1:broj2) ) za direktorijume (u tom redosledu i u dve linije):");
        try {
            String parentDirName = scanner.nextLine();
            String pattern = scanner.nextLine();
            String []patternParsed = pattern.split("[():]");

            if (checkAllConditions(parentDirName,1, patternParsed[0],0, "save")) {
                for(int i = Integer.parseInt(patternParsed[1]); i <= Integer.parseInt(patternParsed[2]); i++){
                    storage.folderCreator(storage.getAllDriveFiles().get(parentDirName), patternParsed[0] + i);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createFile() {
        System.out.println("Unesite ime parent direktorijuma gde zelite da napravite file i ime (u tom redosledu i u dve linije)");
        String parentDirName = scanner.nextLine();
        String name = scanner.nextLine();

        try {
            if (checkAllConditions(parentDirName,1, name, 0, "save")) {
                storage.fileCreator(storage.getAllDriveFiles().get(parentDirName), name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createMultipleFiles() {
        System.out.println("Unesite parent directory ime i pattern (u formatu: ime(broj1:broj2) ) za direktorijume (u tom redosledu i u dve linije):");

        try {
            String parentDirName = scanner.nextLine();
            String pattern = scanner.nextLine();
            String []patternParsed = pattern.split("[():]");
            String []nameParsed = patternParsed[0].split("\\.");

            if (patternParsed[0].contains(".")){
                nameParsed[1] = "." + nameParsed[1];
            }
            else {
                nameParsed[0] = patternParsed[0];
                //nameParsed[1] = "";
            }

            if (checkAllConditions(parentDirName,1, patternParsed[0], 0, "save")) {
                for(int i = Integer.parseInt(patternParsed[1]); i <= Integer.parseInt(patternParsed[2]); i++){
                    if (patternParsed[0].contains(".")){
                        storage.fileCreator(storage.getAllDriveFiles().get(parentDirName), nameParsed[0] + i + nameParsed[1]);
                    }
                    else {
                        nameParsed[0] = patternParsed[0];
                        storage.fileCreator(storage.getAllDriveFiles().get(parentDirName), nameParsed[0] + i);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyFiles() {
        System.out.println("Unesite ime direktorijuma gde hocete da kopirate fajlove (za koje u sledecoj liniji morate takodje uneti putanje, razdvojene razmakom):");
        String dirName = scanner.nextLine();
        String filePathsToParse = scanner.nextLine();
        String[] filePathsParsed = filePathsToParse.split(" "); //C:\Users\Shus\Desktop\ttt1.txt C:\Users\Shus\Desktop\ttt2.txt C:\Users\Shus\Desktop\ttt3.txt
        String fileName;

        int fileSize = 0;
        String []nameParsedByDot;
        String extension;


        for (String s: filePathsParsed){
            if (s.contains(".")){
                nameParsedByDot = s.split("\\.");
                extension = "." + nameParsedByDot[1];//.exe
            }
            else {
                extension = "";
            }
            fileSize += getDirectorySize(Paths.get(s));
            if(checkStorageConfigurations(dirName, filePathsParsed.length, fileSize, extension) != 0){
                System.out.println("File " + s + " nije mogao da se kopira u zadati direktorijum. Prekrsili ste jedno ili vise ogranicenja nad skladistem/direktorijumom.");
                return;
            }
        }

        if(fileDirExists(dirName) && checkUserPrivileges(storage.getAllDriveFiles().get(dirName), "save") == 0) {
            try {
                for (String fileLocalPath : filePathsParsed) {
                    fileName = fileLocalPath.substring(fileLocalPath.lastIndexOf('\\') + 1);
                    storage.uploadFile(storage.getAllDriveFiles().get(dirName), fileLocalPath, fileName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeDirectory() {
        System.out.println("Unesite ime direktorijuma koji zelite da izbrisete i ime njegovog parent direktorijuma (taj redosled u dve linije):");
        String dirName = scanner.nextLine();
        String parentDirName = scanner.nextLine();
        String dirId;
        try {
            if (fileDirExists(dirName) && checkUserPrivileges(storage.getAllDriveFiles().get(dirName), "delete") == 0){

                dirId = storage.findFileIdByParent(dirName, storage.getAllDriveFiles().get(parentDirName));
                storage.deleteFile(dirId);
            }
            else System.err.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeFile() {
        System.out.println("Unesite ime fajla koji zelite da izbrisete i ime njegovog parent direktorijuma (taj redosled u dve linije):");
        String fileName = scanner.nextLine();
        String parentDirName = scanner.nextLine();
        try {
            if (fileDirExists(fileName) && checkUserPrivileges(storage.getAllDriveFiles().get(parentDirName), "delete") == 0) {
                storage.deleteFile(storage.getAllDriveFiles().get(fileName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findAllFilesInDirectory() {
        System.out.println("Unesite ime direktorijuma:");
        String dirName = scanner.nextLine();
        String parentDirId = storage.getAllDriveFiles().get(dirName);

        if (fileDirExists(dirName) && checkUserPrivileges(parentDirId, "view") == 0) {

            for (File file : storage.getFiles()) {
                if (file.getParents() != null && file.getParents().contains(parentDirId) && !(file.getMimeType().equals("application/vnd.google-apps.folder"))) {
                    System.out.println("File name: " + file.getName() + " id: " + file.getId());
                }
            }
        }
    }

    public void findAllDirectoriesInDirectory() {
        System.out.println("Unesite ime direktorijuma:");
        String dirName = scanner.nextLine();
        String parentDirId = storage.getAllDriveFiles().get(dirName);

        if (fileDirExists(dirName) && checkUserPrivileges(parentDirId, "view") == 0) {

            for (File file : storage.getFiles()) {
                if (file.getParents() != null && file.getParents().contains(parentDirId) && file.getMimeType().equals("application/vnd.google-apps.folder")) {
                    System.out.println("File name: " + file.getName() + " id: " + file.getId());
                }
            }
        }
    }

    public void findFilesByNameInDirAndSubdirs() {
        System.out.println("Unesite ime direktorijuma i naziv fajla kome ce se vrsiti pretraha (u tom redosledu i u dve linije):");
        String dirName = scanner.nextLine();
        String parentDirId = storage.getAllDriveFiles().get(dirName);
        String fileName = scanner.nextLine();

        if (fileDirExists(dirName) && checkUserPrivileges(parentDirId, "view") == 0) {
            showFiles(parentDirId, fileName);
        }
    }

    public void findFilesByExtension() {
        System.out.println("Unesite ime direktorijuma i ekstenziju >(BEZ! tacke na pocetku)< po kojoj ce se vrsiti pretraha (u tom redosledu i u dve linije):");
        String dirName = scanner.nextLine();
        String parentDirId = storage.getAllDriveFiles().get(dirName);
        String extension = scanner.nextLine();

        if (fileDirExists(dirName) && checkUserPrivileges(parentDirId, "view") == 0) {

            for (File file : storage.getFiles()) {
                if (file.getParents() != null &&  file.getParents().contains(parentDirId)  && file.getName().contains(extension)) {
                    System.out.println("File name: " + file.getName() + " id: " + file.getId());
                }
            }
        }
    }

    public void findFilesByNameSorted() {
        System.out.println("Unesite ime direktorijuma:");
        String dirName = scanner.nextLine();
        String parentDirId = storage.getAllDriveFiles().get(dirName);
        ArrayList<File> sortedFileList = new ArrayList<>();
        File [] sortedFileArr;

        if (fileDirExists(dirName) && checkUserPrivileges(parentDirId, "view") == 0) {

            for (File file : storage.getFiles()) {//napunimo listu sa filovima koje zelimo da sortiramo
                if (file.getParents() != null && file.getParents().contains(parentDirId) && !(file.getMimeType().equals("application/vnd.google-apps.folder"))) {
                    sortedFileList.add(file);
                }
            }

            sortedFileArr = sortedFileList.toArray(new File[0]);
            Arrays.sort(sortedFileArr, Comparator.comparing(File::getName));//stavimo ih u arr i srotiramo

            for (File f : sortedFileArr){
                System.out.println("File name: " + f.getName() + " id: " + f.getId());
            }
        }
    }

    public void findFilesByDateSorted() {
        System.out.println("Unesite ime direktorijuma:");
        String dirName = scanner.nextLine();
        String parentDirId = storage.getAllDriveFiles().get(dirName);
        ArrayList<File> sortedFileList = new ArrayList<>();

        if (fileDirExists(dirName) && checkUserPrivileges(parentDirId, "view") == 0) {

            for (File file : storage.getFiles()) {//napunimo listu sa filovima koje zelimo da sortiramo
                if (file.getParents() != null && file.getParents().contains(parentDirId) && !(file.getMimeType().equals("application/vnd.google-apps.folder"))) {
                    sortedFileList.add(file);
                }
            }

            Comparator<File> fileNameComparator = new Comparator<>() {
                Date date1;
                Date date2;
                @Override
                public int compare(File o1, File o2) {
                    try {
                        date1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(parseDate(o1.getCreatedTime()));
                        date2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(parseDate(o2.getCreatedTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return date1.compareTo(date2);
                }
            };

            sortedFileList.sort(fileNameComparator);

            for (File f : sortedFileList){
                System.out.println("File name: " + f.getName() + " id: " + f.getId());
            }
        }
    }

    public void findFilesByDateModifiedSorted() {
        System.out.println("Unesite ime direktorijuma: ");
        String dirName = scanner.nextLine();
        String parentDirId = storage.getAllDriveFiles().get(dirName);
        ArrayList<File> sortedFileList = new ArrayList<>();

        if (fileDirExists(dirName) && checkUserPrivileges(parentDirId, "view") == 0) {

            for (File file : storage.getFiles()) {//napunimo listu sa filovima koje zelimo da sortiramo
                if (file.getParents() != null && file.getParents().contains(parentDirId) && !(file.getMimeType().equals("application/vnd.google-apps.folder"))) {
                    sortedFileList.add(file);
                }
            }

            Comparator<File> fileNameComparator = new Comparator<>() {
                Date date1;
                Date date2;
                @Override
                public int compare(File o1, File o2) {
                    try {
                        date1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(parseDate(o1.getModifiedTime()));
                        date2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(parseDate(o2.getModifiedTime()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return date1.compareTo(date2);
                }
            };

            sortedFileList.sort(fileNameComparator);

            for (File f : sortedFileList){
                System.out.println("File name: " + f.getName() + " id: " + f.getId());
            }
        }
    }

    public void findFilesBySpecificCreatedModifiedTime() {
        System.out.println("Unesite putanju do direktorijuma:");
        String dirName = scanner.nextLine();
        String parentDirId = storage.getAllDriveFiles().get(dirName);
        System.out.println("Unesite pocetni datum (format: dd-MM-yyyy HH:mm:ss):");
        String dateStart = scanner.nextLine();
        System.out.println("Unesite krajnji datum (format: dd-MM-yyyy HH:mm:ss):");
        String dateEnd = scanner.nextLine();
        System.out.println("Unesite da li zelite ispis po datumu kreacije ili modifikacije (unosi se jedna od kljucnih reci 'created' i 'modified':");
        String createdOrModified = scanner.nextLine();
        Date date;

        if (fileDirExists(dirName) && checkUserPrivileges(parentDirId, "view") == 0) {
            try {
                Date startDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(dateStart);
                Date endDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(dateEnd);

                for (File file : storage.getFiles()) {//napunimo listu sa filovima koje zelimo da sortiramo
                    if (file.getParents() != null && file.getParents().contains(parentDirId) && !(file.getMimeType().equals("application/vnd.google-apps.folder"))) {

                        if(createdOrModified.equals("created")){
                            date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(parseDate(file.getCreatedTime()));//todo DOBRO RADI ALI ATM PRIKAZUJE 1H MANJE ZBOG NASEG VREMENA GLUPOG
                            if (date.after(startDate) && date.before(endDate)){
                                System.out.println(file.getName() + " " + file.getCreatedTime());
                            }
                        }
                        else if (createdOrModified.equals("modified")){
                            date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(parseDate(file.getModifiedTime()));
                            if (date.after(startDate) && date.before(endDate)){
                                System.out.println(file.getName() + " " + file.getCreatedTime());
                            }
                        }
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }



    public void moveFiles() {
        System.out.println("Unesite ime direktorijuma gde zelite pomeriti fajlove (u prvoj liniji), i imena fajlova koje zelite pomeriti (u drugoj liniji i razdvojene razmakom):");
        String dirName = scanner.nextLine();
        String newParentDirId = storage.getAllDriveFiles().get(dirName);
        String []fileNames = scanner.nextLine().split(" ");
        ArrayList<String> fileIds = new ArrayList<>();

       for (String name : fileNames){
           if (fileDirExists(name)){
               fileIds.add(storage.getAllDriveFiles().get(name));
           }
           else System.out.println("file " + name + " ne postoji");
       }

       if (checkUserPrivileges(newParentDirId, "save") == 0){
           for (String id : fileIds){
               try {
                   File file = storage.getDrive().files().get(id)
                           .setFields("parents")
                           .execute();
                   StringBuilder previousParents = new StringBuilder();
                   for (String parent : file.getParents()) {
                       previousParents.append(parent);
                       previousParents.append(',');
                   }
                   // Move the file to the new folder
                   file = storage.getDrive().files().update(id, null)
                           .setAddParents(newParentDirId)
                           .setRemoveParents(previousParents.toString())
                           .setFields("id, parents")
                           .execute();

                   System.out.println("Moving file " + file.getName() + " to new directory");
               }
               catch (Exception e){
                   e.printStackTrace();
               }
           }
       }
    }

    public void downloadFiles() {
        System.out.println("Unesite imena fajlova koje hocete da preuzmete(odvojene razmakom) (u tom redosledu u dve linije):");
        String []parentDirIdSplit;
        String [] fileNames = scanner.nextLine().split(" ");

       try {//todo baca error ako je file prazan (0 byta)
           for (String fileName : fileNames){
               for (File file : storage.getFiles()){
                   if (file.getName().equals(fileName) && file.getParents() != null){
                       parentDirIdSplit = file.getParents().toArray(new String[0]);
                       if(checkUserPrivileges(storage.getAllDriveFiles().get(parentDirIdSplit[0]), "download") == 0) {
                           storage.downloadFile(storage.getAllDriveFiles().get(fileName), fileName, null);
                       }
                   }
               }
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }


    //helper functions below


    public void showFiles(String parentDirId, String findFileName) {
        for (File file : storage.getFiles()) {
            if (file.getParents() != null && file.getParents().contains(parentDirId) && !(file.getMimeType().equals("application/vnd.google-apps.folder"))
                    && file.getName().equals(findFileName)) {//ako nije dir
                System.out.println("File name: " + file.getName() + " id: " + file.getId());
            }
            else if (file.getParents() != null && file.getParents().contains(parentDirId) && file.getMimeType().equals("application/vnd.google-apps.folder")){//ako jeste dir
                showFiles(file.getId() ,findFileName);
            }
        }
    }

    public boolean checkAllConditions (String dirName, int fileAddedNumber, String name, int fileSize, String action){ //Metod za proveru svih relevantnih ogranicenja nad operacijama.
        String []nameParsedByDot;
        String extension;

        if (name.contains(".")){
            nameParsedByDot = name.split("\\.");
            extension = "." + nameParsedByDot[1];//.exe
        }
        else {
            extension = "";
        }

        if(fileDirExists(dirName) && //gde stavljamo da li postoji
                checkUserPrivileges(storage.getAllDriveFiles().get(dirName), action) == 0 &&//gde stavljamo da li ima priv za akciju
                checkStorageConfigurations(storage.getAllDriveFiles().get(dirName), fileAddedNumber, fileSize, extension) == 0){ //da li se krsi rootConfig limit
            return true;
        }
        return false;
    }


    public int checkUserPrivileges(String dirId, String privilegeToCheck) { //Metod za proveru ogranicenja vezanih za korisnicke privilegije.
        int privilegeErrorNum = 0;

        if (storage.getLoggedUser().getPrivilegeBlackList().containsKey(dirId)){
            privilegeErrorNum = checkPrivilege(dirId,privilegeToCheck);
        }
        return privilegeErrorNum;
    }

    public int checkPrivilege (String dirId, String privilegeToCheck){ //Pomocni metod za proveru korisnickih privilegija.
        switch (privilegeToCheck){
            case "view" :
                if(storage.getLoggedUser().getPrivilegeBlackList() != null || !storage.getLoggedUser().getPrivilegeBlackList().get(dirId).isViewPrivilege()){
                    System.out.println("Korisnik nema privilegiju za gledanje.");
                    return 3;
                }
                break;
            case "save" :
                if(storage.getLoggedUser().getPrivilegeBlackList() != null || !storage.getLoggedUser().getPrivilegeBlackList().get(dirId).isSavePrivilege()){
                    System.out.println("Korisnik nema privilegiju za snimanje.");
                    return 3;
                }
                break;
            case "download" :
                if(storage.getLoggedUser().getPrivilegeBlackList() != null || !storage.getLoggedUser().getPrivilegeBlackList().get(dirId).isDownloadPrivilege()){
                    System.out.println("Korisnik nema privilegiju za preuzimanje.");
                    return 3;
                }
                break;
            case "remove" :
                if(storage.getLoggedUser().getPrivilegeBlackList() != null || !storage.getLoggedUser().getPrivilegeBlackList().get(dirId).isRemovePrivilege()){
                    System.out.println("Korisnik nema privilegiju za brisanje.");
                    return 3;
                }
                break;
        }
        return 0;
    }

    public int checkStorageConfigurations(String dirIdForMaxFileSIzeCheck, int fileAddedNumber, int fileSize, String fileExtension) { //Metod za proveru ogranicenja vezanih za konfiguracije skladista.
        long storageSize = 0;

        for (File file : storage.getFiles()){
            if (file.getId().equals(storage.getStorageSettings().getRootPath()) && !file.getMimeType().equals("application/vnd.google-apps.folder")){
                storageSize = file.getSize();
                break;
            }
        }

        if ((storageSize + fileSize) > storage.getStorageSettings().getStorageMaxSize()) {
            System.out.println("Predjena je maksimalna velicina skladista (u bajtovima).");
            return 1;
        } else if (fileExtension != "" && storage.getStorageSettings().getExtensionBlacklist().contains(fileExtension)) {
            System.out.println("Ekstenzija " + fileExtension + " nije dozvoljena u direktorijumu.");
            return 1;
        } else if (storage.getStorageSettings().getDirectoryMaxFileSize().containsKey(dirIdForMaxFileSIzeCheck)) {
            int fileCount = countFilesInDir(dirIdForMaxFileSIzeCheck);
            if (fileCount + fileAddedNumber > storage.getStorageSettings().getDirectoryMaxFileSize().get(dirIdForMaxFileSIzeCheck)) {
                System.out.println("Predjen je maksimalni broj fajlova u direktorijumu.");
                return 1;
            }
        }
        return 0;
    }



    public int countFilesInDir(String dirId){ //Pomocni metod koji broji broj fajlova u direktorijumu.
        int i = 0;
        for (File file : storage.getFiles()){
            if (file.getParents().contains(dirId)){
                i++;
            }
        }
        return i;
    }

    public boolean fileDirExists (String dirName){
        if (!storage.getAllDriveFiles().containsKey(dirName)){
            System.out.println("file/direktorijum ne postoji");
            return false;
        }
        return true;
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

    public String parseDate (DateTime dateTime){
        String goodDate = "";

        String []dateSplit = dateTime.toString().split("T"); //2021-11-03   T   17:18:03.677Z
        String []timeSplit = dateSplit[1].split("\\."); //17:18:03  .   677Z
        dateSplit = dateSplit[0].split("-"); //2021 -   11  -   03

        goodDate = dateSplit[2] + "-" + dateSplit[1] + "-" + dateSplit[0] + " " + timeSplit[0];
        return goodDate;
    }

    public RemoteStorage getStorage() {
        return storage;
    }
    public void setStorage(RemoteStorage storage) {
        this.storage = storage;
    }
}
