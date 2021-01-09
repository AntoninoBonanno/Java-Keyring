/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Classes;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author AntoninoBonanno <https://github.com/AntoninoBonanno>
 */
public class KeyringFileFilter extends FileFilter{
    public static String extension = ".keys";

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) return false;
        return file.getName().toLowerCase().endsWith(extension);
    }

    @Override
    public String getDescription() {
        return "Keyring files" + String.format(" (*%s)", extension);
    }
    
}
