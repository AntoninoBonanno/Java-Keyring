/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Exceptions.KeyringException;
import GUI.Classes.KeyringFileFilter;
import Utilities.CryptoUtils;
import Utilities.PasswordUtils;
import java.io.File;
import java.text.SimpleDateFormat;

/**
 *
 * @author AntoninoBonanno <https://github.com/AntoninoBonanno>
 */
public final class Keyring {        
    private final File saveFile;
    
    private String masterKey;
    private final Folder rootFolder;
    
    /**
     * Crea un nuovo keyring, salvando su file
     * 
     * @param masterKey chiave primaria per cifrare/decifrare il file 
     * @param pathFile path della directory di destinazione del file
     * @param nameFile nome del file
     * @param overwrite True se voglio sovrascrivere il file esistente, False se non voglio sovrascriverlo
     * @throws KeyringException 
     */
    public Keyring(String masterKey, String pathFile, String nameFile, boolean overwrite) throws KeyringException {
        saveFile = new File(pathFile + File.separator + nameFile + KeyringFileFilter.extension); 
        if(saveFile.exists() && !overwrite) throw new KeyringException("File già esistente. Vuoi sovrascriverlo?", "File esistente", KeyringException.QUESTION_MESSAGE);
            
        setMasterKey(masterKey);
        
        rootFolder = new Folder(nameFile);
        rootFolder.addNewPage("Generale", "");
        save();
    }
    
    /**
     * Crea un keyring partendo da dati già esistenti
     * 
     * @param masterKey chiave primaria per cifrare/decifrare il file 
     * @param saveFile file utilizzato per salvare il keyring
     * @param rootFolder rootFolder del keyring
     */
    private Keyring(String masterKey, File saveFile, Folder rootFolder){
        this.masterKey = masterKey;
        this.saveFile = saveFile;
        this.rootFolder = rootFolder;
    }

    private void setMasterKey(String masterKey) throws KeyringException{
        if(masterKey.isEmpty()) throw new KeyringException("La Master Key non può essere vuota", KeyringException.WARNING_MESSAGE);
        PasswordUtils.verifyKeyStrength(masterKey);
        
        this.masterKey = masterKey;
    }
    
    public void setMasterKey(String oldMasterKey, String newMasterKey) throws KeyringException{
        if(oldMasterKey == null ? masterKey != null : !oldMasterKey.equals(masterKey)) throw new KeyringException("La Master Key inserita non corrisponde con quella attuale", KeyringException.WARNING_MESSAGE);
        setMasterKey(newMasterKey);
        save();
    }
    
    public Folder getRootFolder() {
        return rootFolder;
    }
    
    /**
     * Salva su file il keyring
     * 
     * @return Restituisce la data e l'orario di salvataggio
     * @throws KeyringException
     */
    public String save() throws KeyringException{ 
        CryptoUtils.encrypt(saveFile, masterKey, rootFolder);         
        System.out.println("Salvataggio riuscito.");
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");		
        return sdf.format(System.currentTimeMillis());         
    } 
    
    /**
     * Carica da file il keyring
     * 
     * @param file file da caricare
     * @param masterKey chiave primaria per cifrare/decifrare il file 
     * @return Keyring il keyring caricato
     * @throws Exceptions.KeyringException
     */
    public static Keyring load(File file, String masterKey) throws KeyringException {
        if(masterKey.isEmpty()) throw new KeyringException("La Master Key non può essere vuota", KeyringException.WARNING_MESSAGE);
        
        Folder rootFolder = CryptoUtils.dencrypt(file, masterKey); 
        return new Keyring(masterKey, file, rootFolder);
    }
}
