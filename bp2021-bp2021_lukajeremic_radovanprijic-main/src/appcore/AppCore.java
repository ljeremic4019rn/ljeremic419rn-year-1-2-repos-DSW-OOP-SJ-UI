package appcore;

import compiler.Compiler;
import compiler.CompilerImplementation;
import database.Database;
import database.DatabaseImplementation;
import database.MSSQLRepository;
import database.settings.Settings;
import database.settings.SettingsImplementation;
import gui.table.TableModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import observer.Notification;
import observer.Publisher;
import observer.Subscriber;
import observer.enums.NotificationCode;
import observer.implementation.PublisherImplementation;
import repository.implementation.InformationResource;
import templatecreator.TemplateCreator;
import templatecreator.TemplateCreatorImplementation;
import utils.Constants;
import validator.Validator;
import validator.ValidatorImplementation;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AppCore extends PublisherImplementation {
    private static AppCore instance;
    private Database database;
    private Settings settings;
    private TableModel tableModel;
    private Compiler compiler;
    private Validator validator;
    private TemplateCreator templateCreator;

    private AppCore() {

    }

    public static AppCore getInstance(){
        if(instance==null){
            instance = new AppCore();
        }
        return instance;
    }

    public void initialise (){
        this.settings = initSettings();
        this.database = new DatabaseImplementation(new MSSQLRepository(this.settings));
        tableModel = new TableModel();
        this.compiler = new CompilerImplementation();
        this.validator = new ValidatorImplementation();
        this.templateCreator = new TemplateCreatorImplementation();
    }

    private Settings initSettings() {
        Settings settingsImplementation = new SettingsImplementation();
        settingsImplementation.addParameter("mssql_ip", Constants.MSSQL_IP);
        settingsImplementation.addParameter("mssql_database", Constants.MSSQL_DATABASE);
        settingsImplementation.addParameter("mssql_username", Constants.MSSQL_USERNAME);
        settingsImplementation.addParameter("mssql_password", Constants.MSSQL_PASSWORD);
        return settingsImplementation;
    }

    public void loadResource(){
        InformationResource ir = (InformationResource) this.database.loadResource();
        this.notifySubscribers(new Notification(NotificationCode.RESOURCE_LOADED,ir));
    }

    public void readDataFromTable(String fromTable){
        tableModel.setRows(this.database.readDataFromTable(fromTable));
        this.notifySubscribers(new Notification(NotificationCode.DATA_UPDATED, this.getTableModel()));
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(TableModel tableModel) {
        this.tableModel = tableModel;
    }
}
