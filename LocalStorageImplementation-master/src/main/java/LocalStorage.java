import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LocalStorage extends Storage{

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    static {
        StorageManager.registerStorage(new LocalStorage());
    }

    public LocalStorage() {
        this.storageInitialization = new LocalStorageInitialization();
        this.storageOperations = new LocalStorageOperations();
        this.storageConfiguration = new LocalStorageConfiguration();

        ((LocalStorageInitialization)this.storageInitialization).setStorage(this);
        ((LocalStorageOperations)this.storageOperations).setStorage(this);
        ((LocalStorageConfiguration)this.storageConfiguration).setStorage(this);

        this.storageSettings = new StorageSettings(false,"", 1000000, new ArrayList<>(), new HashMap<>());
        this.users = new ArrayList<>();
    }

    public void parseStorageConfigFromJsonFile (String path, StorageSettings storageSettings){ //Pomocni metod za stavljanje konfiguracija skladista iz JSON fajla u objekat StorageSettings.
        try {
            InputStream inputStream = Files.newInputStream(Path.of(path + File.separator + "rootConfig.json"));
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream));

            StorageSettings storageSettingsTmp = gson.fromJson(reader, StorageSettings.class);
            storageSettings.setAllStorageSettings(storageSettingsTmp);//copy constructor

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseUsersFromJsonFile (String path, List<User> users){ //Pomocni metod za citanje korisnika iz JSON fajla.
        try {
            InputStream inputStream = Files.newInputStream(Path.of(path  + File.separator + "users.json"));
            JsonReader reader = new JsonReader(new InputStreamReader(inputStream));

            reader.beginArray();
            users.clear();
            while (reader.hasNext()) {
                User user =  gson.fromJson(reader, User.class);
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
            Writer writer = Files.newBufferedWriter(Paths.get(path + File.separator + "users.json"));
            gson.toJson(users, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeStorageConfigToJsonFile (String path){ //Pomocni metod koji se koristi za pisanje konfiguracija skladista u JSON fajl.
        try {
            Writer writer = Files.newBufferedWriter(Paths.get(path + File.separator + "rootConfig.json"));
            gson.toJson(this.getStorageSettings(), writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
