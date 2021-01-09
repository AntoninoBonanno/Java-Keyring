/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Exceptions.KeyringException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

/**
 *
 * @author AntoninoBonanno <https://github.com/AntoninoBonanno>
 */
public final class AppSettings{
    public static final double version = 2.0;
    public static final String author = "AntoninoBonanno";
            
    private static final Preferences prefs  = Preferences.userNodeForPackage(AppSettings.class);
    
    public static void exportSettings(String directory) throws KeyringException{
        try {
            prefs.exportNode(new FileOutputStream(directory+File.separator+"kSettings.xml"));
        } catch (FileNotFoundException ex) {
            throw new KeyringException(ex.getMessage(), "File non trovato", KeyringException.INFORMATION_MESSAGE);
        } catch (IOException | BackingStoreException ex) {
            throw new KeyringException(ex.getMessage(), KeyringException.ERROR_MESSAGE);
        }
    }
    
    public static void importSettings(File settings) throws KeyringException{
        try {
           Preferences.importPreferences(new FileInputStream(settings));
        } catch (FileNotFoundException ex) {
            throw new KeyringException(ex.getMessage(), "File non trovato", KeyringException.INFORMATION_MESSAGE);
        } catch (IOException | InvalidPreferencesFormatException ex) {
            throw new KeyringException(ex.getMessage(), KeyringException.ERROR_MESSAGE);
        }
    }
    
    public static boolean isFullscreen() {
        return prefs.getBoolean("Fullscreen", false);
    }
    public static void setFullscreen(boolean fullscreen){
        prefs.putBoolean("Fullscreen", fullscreen);
    }
    
    public static File getOnLoadFile(){
        String pathFile = prefs.get("onLoadFile", "");
        return pathFile.isEmpty() ? null : new File(pathFile);
    } 
    
    public static void setOnLoadFile(File file){
        prefs.put("onLoadFile", file.getPath());
    }

    public static boolean issetOnloadFile() {
        String pathFile = prefs.get("onLoadFile", "");
        return !pathFile.isEmpty();
    }
    
}
