package dsw.rudok.app.core;

import dsw.rudok.app.gui.swing.tree.RuTree;
import dsw.rudok.app.gui.swing.view.sloteditor.TextSlotFileType;
import dsw.rudok.app.repository.Project;
import dsw.rudok.app.repository.Workspace;
import dsw.rudok.app.repository.slots.Slot;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ISerialization {
    void serializeProject(Project project, File projectFile);
    void serializeWorkspace(Workspace workspace, File workspaceFile);
    void serializeTextSlot(Slot slot, String text, File slotFile);
    void readProject(File projectFile, RuTree tree, Workspace workspace);
    void readWorkspace(File workspaceFile, RuTree tree, Workspace workspace);
    void readTextSlot(File slotFile, Slot slot, JTextPane textPane, TextSlotFileType type);
    void readImageSlot(File selectedFile, File imageFile, BufferedImage image, JLabel picLabel, JPanel panel);
}
