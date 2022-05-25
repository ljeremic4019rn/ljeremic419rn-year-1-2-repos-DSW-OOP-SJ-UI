public class Privileges { //Pomocna klasa koja nam sluzi za laksu manipulaciju nad korisnickim privilegijama.

    private boolean savePrivilege;
    private boolean downloadPrivilege;
    private boolean removePrivilege;
    private boolean viewPrivilege;

    /**
     * Consturctor for the Privileges object.
     * @param savePrivilege Boolean variable that represents user's save privilege.
     * @param downloadPrivilege Boolean variable that represents user's download privilege.
     * @param removePrivilege Boolean variable that represents user's remove privilege.
     * @param viewPrivilege Boolean variable that represents user's view privilege.
     */
    public Privileges(boolean savePrivilege, boolean downloadPrivilege, boolean removePrivilege, boolean viewPrivilege) {
        this.savePrivilege = savePrivilege;
        this.downloadPrivilege = downloadPrivilege;
        this.removePrivilege = removePrivilege;
        this.viewPrivilege = viewPrivilege;
    }

    public boolean isSavePrivilege() {
        return savePrivilege;
    }
    public void setSavePrivilege(boolean savePrivilege) {
        this.savePrivilege = savePrivilege;
    }
    public boolean isDownloadPrivilege() {
        return downloadPrivilege;
    }
    public void setDownloadPrivilege(boolean downloadPrivilege) {
        this.downloadPrivilege = downloadPrivilege;
    }
    public boolean isRemovePrivilege() {
        return removePrivilege;
    }
    public void setRemovePrivilege(boolean removePrivilege) {
        this.removePrivilege = removePrivilege;
    }
    public boolean isViewPrivilege() {
        return viewPrivilege;
    }
    public void setViewPrivilege(boolean viewPrivilege) {
        this.viewPrivilege = viewPrivilege;
    }
}
