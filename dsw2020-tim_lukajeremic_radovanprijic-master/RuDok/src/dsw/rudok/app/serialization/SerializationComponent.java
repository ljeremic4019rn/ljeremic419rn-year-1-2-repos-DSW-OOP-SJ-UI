package dsw.rudok.app.serialization;

import dsw.rudok.app.core.ISerialization;
import dsw.rudok.app.gui.swing.tree.RuTree;
import dsw.rudok.app.gui.swing.view.sloteditor.TextSlotFileType;
import dsw.rudok.app.repository.Project;
import dsw.rudok.app.repository.Workspace;
import dsw.rudok.app.repository.slots.Slot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class SerializationComponent implements ISerialization {

    @Override
    public void serializeProject(Project project, File projectFile) {//cuvamo projekat u file
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(projectFile));
            os.writeObject(project);
            project.setProjectFile(projectFile);
            project.setChanged(false);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void serializeWorkspace(Workspace workspace, File workspaceFile) {//cuvamo file path do svih  project filova i txt file
            try{
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(workspaceFile));//uzima gde pisemo
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));//bw na lokaciji gde pisemo
                for (int i = 0; i < workspace.getChildren().size(); i++) {
                    bw.write(((Project)workspace.getChildren().get(i)).getProjectFile().getAbsolutePath());
                    bw.newLine();
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public void serializeTextSlot(Slot slot, String text, File slotFile) {//pisemo text slota u txt file
        slot.setSlotFile(slotFile);
        try{
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(slotFile));//uzima gde pisemo
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));//bw na lokaciji gde pisemo
            bw.write(text);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readProject(File projectFile, RuTree tree, Workspace workspace) {
        try {
            ObjectInputStream os = new ObjectInputStream(new FileInputStream(projectFile));
            Project p=null;
            try {
                p = (Project) os.readObject();
            } catch (ClassNotFoundException e0) {
                e0.printStackTrace();
            }
            tree.addProject(p);
            p.setParent(workspace);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void readWorkspace(File workspaceFile, RuTree tree, Workspace workspace) {
        try {
            ObjectInputStream os = new ObjectInputStream(new FileInputStream(workspaceFile));
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(os));
                String line;
                FileInputStream fileIn;
                ObjectInputStream objectIn;

                while ((line = br.readLine()) != null) {
                    fileIn = new FileInputStream(line);
                    objectIn = new ObjectInputStream(fileIn);

                    Project project = (Project) objectIn.readObject();
                    tree.addProject(project);
                    project.setParent(workspace);
                    objectIn.close();
                }
                br.close();
            } catch (ClassNotFoundException e0) {
                e0.printStackTrace();
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void readTextSlot(File slotFile, Slot slot, JTextPane textPane, TextSlotFileType type) {
        if(type == TextSlotFileType.TYPE_ONE){
            try {
                ObjectInputStream os = new ObjectInputStream(new FileInputStream(slotFile));
                BufferedReader br = new BufferedReader(new InputStreamReader(os));
                slot.setSlotFile(slotFile);
                String line;
                StringBuilder sb = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                textPane.setText(sb.toString());
                br.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        else if (type == TextSlotFileType.TYPE_TWO){
            try {
                ObjectInputStream os = new ObjectInputStream(new FileInputStream(slot.getSlotFile()));
                BufferedReader br = new BufferedReader(new InputStreamReader(os));
                String line;
                StringBuilder sb = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                textPane.setText(sb.toString());
                br.close();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void readImageSlot(File selectedFile, File imageFile, BufferedImage image, JLabel picLabel, JPanel panel) {
        try {
            imageFile = selectedFile;
            image = ImageIO.read(new File(imageFile.toString()));
            picLabel = new JLabel(new ImageIcon(image));
            panel.add(picLabel);
            panel.revalidate();
            panel.repaint();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
