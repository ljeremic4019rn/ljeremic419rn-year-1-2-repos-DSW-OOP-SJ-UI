package dsw.rudok.app.gui.swing.view;

import com.sun.tools.javac.Main;
import dsw.rudok.app.AppCore;
import dsw.rudok.app.core.Repository;
import dsw.rudok.app.errorhandler.MyError;
import dsw.rudok.app.gui.swing.controller.*;
import dsw.rudok.app.gui.swing.controller.filters.ProjectFileFilter;
import dsw.rudok.app.gui.swing.controller.filters.WorkspaceFileFilter;
import dsw.rudok.app.gui.swing.tree.RuTree;
import dsw.rudok.app.gui.swing.tree.view.RuTreeImplementation;
import dsw.rudok.app.gui.swing.view.observers.ProjectView;
import dsw.rudok.app.observer.ActionType;
import dsw.rudok.app.repository.Project;
import dsw.rudok.app.repository.Workspace;
import dsw.rudok.app.repository.node.RuNode;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.URL;

@Setter
@Getter


public class MainFrame extends JFrame {
    private static MainFrame instance;
    private ActionManager actionManager;
    private Repository documentRepository;
    private RuTree tree;
    private JMenuBar menu;
    private JToolBar toolBar;
    private JTree workspaceTree; // ovo je nas tree koji cemo da izgenerisemo pomocu RuTreeImplementation, a ta klasa vuce sve ostale klase kao lanac
    private AboutFrame aboutFrame;
    private ProjectView projectView = new ProjectView();
    private JSplitPane split = new JSplitPane();
    private int openFrameCounter = 0;
    private int numBull = 0;

    private MainFrame(){ }

    private void initialise() {
        actionManager = new ActionManager();
    }

    public void initialiseWorkspaceTree() { //ovde se napravi nas Tree
        tree = new RuTreeImplementation();
        workspaceTree = tree.generateTree(documentRepository.getWorkspace()); //povucemo sta je workspace i repository i proselimo ga klasi impl. da napravi Tree VVV
        workspaceTree.addMouseListener(new TabOpener());

        initialiseGUI();
    }

    private void initialiseGUI() {//obicno pravljenje GUI elemenata
        Toolkit kit = Toolkit.getDefaultToolkit(); //mislim da je kit samo kako bi uzeli velicinu ekrana
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setSize(screenWidth / 2, screenHeight / 2);
        setLocationRelativeTo(null);
        setTitle("RuDok app");
        menu = new MyMenuBar();//napravi menu
        setJMenuBar(menu);
        toolBar = new Toolbar();//napravi toolBar
        add(toolBar, BorderLayout.NORTH);
        JScrollPane scroll = new JScrollPane(workspaceTree);// ovde stavimo nas Tree koji smo gore napravili   <-------------- Tree placement in GUI
        scroll.setMinimumSize(new Dimension(200,150));
        split.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        split.setLeftComponent(scroll);
        split.setRightComponent(new JPanel(null));
        split.setDividerLocation(250);
        split.setOneTouchExpandable(true);
        getContentPane().add(split,BorderLayout.CENTER);
        URL imageUrl =  getClass().getResource("imm/RuDok.png");
        setIconImage(Toolkit.getDefaultToolkit().getImage(imageUrl));
        startQuestion();
        closeWindow();
    }

    public static MainFrame getInstance(){
        if(instance==null){
            instance = new MainFrame();
            instance.initialise();
        }
        return instance;
    }

    public void closeWindow(){
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) { //ovo zatvara novi pop up
                JFrame f = new JFrame();
                int a = JOptionPane.showConfirmDialog(f, "Da li zelite sacuvati workspace (prvo cete morati sacuvati izmenjene projekte)?");

                if (a == JOptionPane.YES_OPTION) {
                    JFileChooser jfc = new JFileChooser();
                    jfc.setFileFilter(new ProjectFileFilter());

                    for (RuNode project : (documentRepository.getWorkspace().getChildren())) {
                        File projectFile = ((Project) project).getProjectFile();

                        if (((Project) project).getProjectFile() == null) {
                            if (jfc.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                                projectFile = jfc.getSelectedFile();
                            } else {
                                return;
                            }
                        }
                        AppCore.getInstance().getISerialization().serializeProject((Project) project, projectFile);
                    }

                    JFileChooser jfc1 = new JFileChooser();
                    jfc1.setFileFilter(new WorkspaceFileFilter());

                    Workspace workspace = documentRepository.getWorkspace();
                    File workspaceFile;

                    if (jfc1.showSaveDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                        workspaceFile = jfc1.getSelectedFile();
                    } else {
                        return;
                    }
                    AppCore.getInstance().getISerialization().serializeWorkspace(workspace, workspaceFile);
                    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
                }
                else if (a == JOptionPane.NO_OPTION) {
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
                }
                else if (a == JOptionPane.CANCEL_OPTION) {
                    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    public void startQuestion(){
        JFrame f = new JFrame();
        int a = JOptionPane.showConfirmDialog(f, "Da li zelite ucitati prethodno korisceni workspace");
        if (a == JOptionPane.YES_OPTION) {
            JFileChooser jfc = new JFileChooser();//picker
            jfc.setFileFilter(new WorkspaceFileFilter());

            if(jfc.showOpenDialog(MainFrame.getInstance())==JFileChooser.APPROVE_OPTION){
                MainFrame.getInstance().getTree().clearProjects();
                AppCore.getInstance().getISerialization().readWorkspace(jfc.getSelectedFile(), MainFrame.getInstance().getTree(),
                        MainFrame.getInstance().getDocumentRepository().getWorkspace());
            }
        }
        else if (a == JOptionPane.NO_OPTION) {
            setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        }
        else if (a == JOptionPane.CANCEL_OPTION) {
            System.exit(0);
        }
    }

    public ActionManager getActionManager() {
        return actionManager;
    }
    public void setDocumentRepository(Repository documentRepository) {
        this.documentRepository = documentRepository;
    }
    public JTree getWorkspaceTree() {
        return workspaceTree;
    }
    public void setWorkspaceTree(JTree workspaceTree) {
        this.workspaceTree = workspaceTree;
    }

    public RuTree getTree() {
        return tree;
    }

    public void showError (MyError error, ActionType action){
        if (action == ActionType.NO_NODE_SELECTED_ERROR) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, error.getWarningMessage(), "Error message", JOptionPane.ERROR_MESSAGE);
        }
        else if (action == ActionType.WORKSPACE_DELETION_ERROR) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, error.getWarningMessage(), "Error message", JOptionPane.ERROR_MESSAGE);
        }
        else if (action == ActionType.NO_SHAPE_SELECTED_ERROR) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, error.getWarningMessage(), "Error message", JOptionPane.ERROR_MESSAGE);
        }
        else if (action == ActionType.NO_CLICK_ON_SHAPE_ERROR) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, error.getWarningMessage(), "Error message", JOptionPane.ERROR_MESSAGE);
        }
        else if (action == ActionType.NO_FRAME_SELECTED_ERROR) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, error.getWarningMessage(), "Error message", JOptionPane.ERROR_MESSAGE);
        }
        else if (action == ActionType.NO_FRAME_CREATED_ERROR) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, error.getWarningMessage(), "Error message", JOptionPane.ERROR_MESSAGE);
        }
        else if (action == ActionType.NO_PROJECT_SELECTED_ERROR) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, error.getWarningMessage(), "Error message", JOptionPane.ERROR_MESSAGE);
        }
        else if (action == ActionType.NO_DOUMENT_SELECTED_ERROR) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, error.getWarningMessage(), "Error message", JOptionPane.ERROR_MESSAGE);
        }
        else if (action == ActionType.NO_UNDO_REMAINING_ERROR) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, error.getWarningMessage(), "Error message", JOptionPane.ERROR_MESSAGE);
        }
        else if (action == ActionType.NO_REDO_REMAINING_ERROR) {
            JFrame f = new JFrame();
            JOptionPane.showMessageDialog(f, error.getWarningMessage(), "Error message", JOptionPane.ERROR_MESSAGE);
        }
    }
}
