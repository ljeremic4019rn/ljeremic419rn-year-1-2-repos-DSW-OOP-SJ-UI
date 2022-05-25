package dsw.rudok.app.gui.swing.controller.filters;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class WorkspaceFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        return (f.isDirectory() ||
                f.getName().toLowerCase().endsWith(".rwf"));
    }

    @Override
    public String getDescription() {
        return "RuDok Workspace Files (*.rwf)";
    }
}
