import java.util.Collection;
import java.util.Map;

public class User { //Pomocna klasa koja nam sluzi za cuvanje korisnika iz JSON fajla u nekom objektu.

    private String username;
    private String password;
    private boolean isAdmin;
    Collection<String> listOfAdminDirectories;
    Map<String,Privileges> privilegeBlackList;

    /**
     * Constructor for the user object.
     * @param username Username of the user.
     * @param password Password of the user.
     * @param isAdmin Boolean variable that tells us if this user is an administrator.
     * @param listOfAdminDirectories List of directories where the user has administrator role.
     * @param privilegeBlackList Map that contains directories and forbidden privileges in those directories.
     */
    public User(String username, String password, boolean isAdmin, Collection<String> listOfAdminDirectories, Map<String,
            Privileges> privilegeBlackList) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.listOfAdminDirectories = listOfAdminDirectories;
        this.privilegeBlackList = privilegeBlackList;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isAdmin() {
        return isAdmin;
    }
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
    public Collection<String> getListOfAdminDirectories() {
        return listOfAdminDirectories;
    }
    public void setListOfAdminDirectories(Collection<String> listOfAdminDirectories) {
        this.listOfAdminDirectories = listOfAdminDirectories;
    }
    public Map<String, Privileges> getPrivilegeBlackList() {
        return privilegeBlackList;
    }
    public void setPrivilegeBlackList(Map<String, Privileges> privilegeBlackList) {
        this.privilegeBlackList = privilegeBlackList;
    }
}
