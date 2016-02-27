package rdb2xml.ui;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DialogHelper {

    public static int GetScreenWorkingWidth() {
        return getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    }

    public static int GetScreenWorkingHeight() {
        return getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    }

    public static File getSelectedFileWithExtension(JFileChooser c) {
        File file = c.getSelectedFile();
        if (c.getFileFilter() instanceof FileNameExtensionFilter) {
            String[] exts = ((FileNameExtensionFilter) c.getFileFilter()).getExtensions();
            String nameLower = file.getName().toLowerCase();
            for (String ext : exts) {
                if (nameLower.endsWith('.' + ext.toLowerCase())) {
                    return file;
                }
            }
            file = new File(file.toString() + '.' + exts[0]);
        }
        return file;
    }
}
