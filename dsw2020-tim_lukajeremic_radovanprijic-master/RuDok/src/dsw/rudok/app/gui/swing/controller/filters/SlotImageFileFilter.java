package dsw.rudok.app.gui.swing.controller.filters;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class SlotImageFileFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        return (f.isDirectory() || f.getName().toLowerCase().endsWith(".jpg")) || f.getName().toLowerCase().endsWith(".png");
    }

    @Override
    public String getDescription() {
        return "Image Files (*.jpg)";
    }
}
