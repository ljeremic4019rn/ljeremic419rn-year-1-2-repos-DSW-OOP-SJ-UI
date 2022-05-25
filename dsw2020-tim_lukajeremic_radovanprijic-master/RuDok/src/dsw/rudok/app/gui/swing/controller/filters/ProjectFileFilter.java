package dsw.rudok.app.gui.swing.controller.filters;

import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.io.File;

public class ProjectFileFilter extends FileFilter {//ovo filtrira koje filove ce da pop-up prikaze za save/load

    @Override
    public boolean accept(File f) {
        return (f.isDirectory() ||
                f.getName().toLowerCase().endsWith(".rpf"));
    }

    @Override
    public String getDescription() {
        return "RuDok Project Files (*.rpf)";
    }
}
