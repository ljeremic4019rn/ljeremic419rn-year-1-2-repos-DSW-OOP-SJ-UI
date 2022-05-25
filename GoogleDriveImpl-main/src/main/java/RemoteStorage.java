import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class RemoteStorage extends Storage {

    Drive drive = RemoteStorage.getDriveService();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    FileList result;
    List<File> files;
    Map<String,String> allDriveFiles = new HashMap<>();//name fileId

    public RemoteStorage() throws IOException {
        this.storageInitialization = new RemoteStorageInitialization();
        this.storageOperations = new RemoteStorageOperations();
        this.storageConfiguration = new RemoteStorageConfiguration();

        ((RemoteStorageInitialization)this.storageInitialization).setStorage(this);
        ((RemoteStorageOperations)this.storageOperations).setStorage(this);
        ((RemoteStorageConfiguration)this.storageConfiguration).setStorage(this);

        this.storageSettings = new StorageSettings(false,"", 1000000, new ArrayList<>(), new HashMap<>());
        this.users = new ArrayList<>();
        loadAllDriveFiles();//napunimo mapu
        loadDriveFilesAndDirs(null, "all");
    }


    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "My project";

    /**
     * Global instance of the {@link FileDataStoreFactory}.
     */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /**
     * Global instance of the JSON factory.
     */
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Global instance of the HTTP transport.
     */
    private static HttpTransport HTTP_TRANSPORT;

    /**
     * Global instance of the scopes required by this quickstart.
     * <p>
     * If modifying these scopes, delete your previously saved credentials at
     * ~/.credentials/calendar-java-quickstart
     */
    private static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     *
     * @return an authorized Credential object.
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = RemoteStorage.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
                clientSecrets, SCOPES).setAccessType("offline").build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     *
     * @return an authorized Calendar client service
     */
    public static Drive getDriveService() throws IOException {
        Credential credential = authorize(); //ovde dobijamo cred za povezivanje sa nasim google drive
        // System.out.println(credential.getAccessToken());
        // System.out.println(credential.getRefreshToken());
        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public void loadAllDriveFiles () throws IOException {
        this.result = drive.files().list() //1vrYJ13WOELh0XmOsN1GhbeqGlHml7mla
                .setQ("trashed = false")
                .setSpaces("drive")
                .setFields("nextPageToken, files(id, name)")
                .execute();
        this.files = result.getFiles();

        for (File file : files){
            allDriveFiles.put(file.getName(), file.getId());//napunimo mapu sa svim filovima i folderima sa drivea, kako bi lakse nalazili id kasnije
        }
    }

    public void loadDriveFilesAndDirs (String parent, String mimeType) throws IOException {//OVO JE REDUNDANT, SAMO SE PROSLEDI NULL I ALL
       // String pageToken = null;
        String mimeTypeStr = switch (mimeType) {
            case "folder" -> "mimeType = 'application/vnd.google-apps.folder' and trashed = false";
            case "file" -> "mimeType =! 'application/vnd.google-apps.folder' and trashed = false";
            case "all" -> "";
            default -> null;//"mimeType = 'application/vnd.google-apps.folder' //.setQ("'root' in parents and mimeType != 'application/vnd.google-apps.folder' and trashed = false")
        };
        String parentStr = "";

        if (parent != null){
            parentStr= "'" + parent + "' in parents and ";
        }

        System.out.println(parentStr  + mimeTypeStr);


        this.result = drive.files().list()
                .setQ(parentStr  + mimeTypeStr)
                .setSpaces("drive")
                .setFields("nextPageToken, files(id, name, parents, mimeType, fileExtension, createdTime, modifiedTime)")
                //  .setPageToken(pageToken)
                .execute();
        this.files = result.getFiles();

/*
        for (File file : files) {
            System.out.println(file.getName() + " (" + file.getId() + ") parents:" + file.getParents());
            //System.out.printf("%s (%s)\n", file.getName(), file.getId());
        }
*/
    }

    public void folderCreator(String parentDirId, String folderName) throws IOException {
        File folderMetadata = new File();
        folderMetadata.setName(folderName);
        folderMetadata.setParents(Collections.singletonList(parentDirId));
        folderMetadata.setMimeType("application/vnd.google-apps.folder");

        File file = drive.files().create(folderMetadata)
                .setFields("id")
                .execute();

        // System.out.println(folderName + " " + file.getId());
        file.setName(folderName);
        file.setParents(Collections.singletonList(parentDirId));
        file.setMimeType("application/vnd.google-apps.folder");
        allDriveFiles.put(file.getName(), file.getId());
        files.add(file);

        System.out.println("Novi " + folderName + " dir je kreiran id: " + file.getId());
    }

    public void uploadFile (String parentDirId, String localPathToFile, String fileName/*, String fileFormat*/) throws IOException {
        File fileMetadata = new File();                     //"C:\\Users\\Shus\\Desktop\\text.txt"
        fileMetadata.setName(fileName);
        fileMetadata.setParents(Collections.singletonList(parentDirId));
       // fileMetadata.setMimeType("application/vnd.google-apps.document");
        java.io.File filePath = new java.io.File(localPathToFile);
        FileContent mediaContent = new FileContent(null, filePath);
        File file = drive.files().create(fileMetadata, mediaContent)
                .setFields("id, parents")
                .execute();


        file.setParents(Collections.singletonList(parentDirId));
        file.setName(fileName);
        allDriveFiles.put(file.getName(), file.getId());
        files.add(file);

        System.out.println("uploaded file: " + fileName );
    }

    public void downloadFile (String fileId, String fileName, String downloadPath) throws IOException {
        String home = System.getProperty("user.home");
        String downPath = home + "\\Downloads\\" + fileName;

        if (downloadPath != null){
            downPath = downloadPath + "\\" + fileName;
        }

        OutputStream outputStream = new FileOutputStream(downPath);
        drive.files().get(fileId).executeMediaAndDownloadTo(outputStream);
        outputStream.close();
        System.out.println("downloaded file: " + fileName );
    }



    public void deleteFile (String fileId) throws IOException {
        Void file = drive.files().delete(fileId).execute();
        int i = 0;
        String fileName = null;
        for (File f : files){
            if (f.getId().equals(fileId)) {
                fileName = f.getName();
                break;
            }
            i++;
        }

        files.remove(i);
        allDriveFiles.remove(fileName);

        System.out.println( fileName + " deleted" );
    }

    public void fileCreator (String parentDirId, String fileName) throws IOException {
        File fileMetadata = new File();
        fileMetadata.setName(fileName);
        fileMetadata.setParents(Collections.singletonList(parentDirId));
        //fileMetadata.setMimeType("application/vnd.google-apps.document");

        File file = drive.files().create(fileMetadata)
                .setFields("id, parents")
                .execute();

        file.setParents(Collections.singletonList(parentDirId));
        file.setName(fileName);
        allDriveFiles.put(file.getName(), file.getId());
        files.add(file);

        System.out.println("File "+ file.getName() + " created id: " + file.getId() );
    }

    public void parseUsersFromJsonFile (String path, List<User> users){ //Pomocni metod za citanje korisnika iz JSON fajla.
        try {
            InputStream inputStream = Files.newInputStream(Path.of(path  + java.io.File.separator + "users.json"));
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream));

            reader.beginArray();
            users.clear();
            while (reader.hasNext()) {
                User user =  gson.fromJson(reader, User.class);
                //  System.out.println("USER: " + user.getUsername() + " " + user.getPassword());
                users.add(user);
            }

            reader.endArray();
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void writeUsersToJsonFile(String path, ArrayList<User> users) { //Pomocni metod koji se koristi za pisanje registrovanih korisnika u JSON fajl.
        try{
            Writer writer = Files.newBufferedWriter(Paths.get(path + java.io.File.separator + "users.json"));
            gson.toJson(users, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseStorageConfigFromJsonFile (String path, StorageSettings storageSettings){ //Pomocni metod za stavljanje konfiguracija skladista iz JSON fajla u objekat StorageSettings.
        try {
            InputStream inputStream = Files.newInputStream(Path.of(path + java.io.File.separator + "rootConfig.json"));
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream));

            StorageSettings storageSettingsTmp = gson.fromJson(reader, StorageSettings.class);
            storageSettings.setAllStorageSettings(storageSettingsTmp);//copy constructor

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeStorageConfigToJsonFile (String path){ //Pomocni metod koji se koristi za pisanje konfiguracija skladista u JSON fajl.
        try {
            Writer writer = Files.newBufferedWriter(Paths.get(path + java.io.File.separator + "rootConfig.json"));
            gson.toJson(this.getStorageSettings(), writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replaceConfigJsonFiles (String rootId, String fileName) throws IOException { //users.json rootConfig.json
        String home = System.getProperty("user.home");//napravimo tmp storage za json filove
        String jsonFileId = findFileIdByParent(fileName, rootId);//pronadjemo id od nasih configa, specificno u root folderu
        //System.out.println("deleting " + jsonFileId);
        deleteFile(jsonFileId);
         uploadFile(rootId, home + "\\Downloads\\tmpDir\\" + fileName, fileName);
       // uploadFile(rootId, home + "\\Downloads\\tmpDir\\" + fileName, "rootConfig.json");
    }

    public String findFileIdByParent(String fileName, String parentId){
        String fileId = null;
        for (File file : getFiles()){
            //System.out.println(file.getName() + " parent "+file.getParents());
            if (file.getName().equals(fileName) && file.getParents().contains(parentId))
                fileId = file.getId();
        }
        return fileId;
    }


    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public Map<String, String> getAllDriveFiles() {
        return allDriveFiles;
    }

    public Drive getDrive() {
        return drive;
    }

    static {
        try {
            StorageManager.registerStorage(new RemoteStorage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
