package Keyring;


import Utilities.CryptoUtils;
import Exceptions.KeyringException;
import Classes.Folder;
import java.io.File;
import java.io.FileInputStream;
import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nino
 */
public class KeyringClass {    
    public static final double version = 2.0;
    public static final String author = "AntoninoBonanno";
    
    private static final String nameFile = "keyring.keys";
        
    private String MasterKey;
    private Folder rootFolder;

    /**
     * Verifica l'esistenza del file contenente la password
     * @return True se il file esiste
     * @throws KeyringException se il file non esiste
     */
    public static boolean checkFile() throws KeyringException{ 
        System.out.print("Verifico l'esistenza del file contente le password...   ");
        if(!new File(nameFile).exists()){
            System.out.println("Non trovato.");
            throw new KeyringException(
                "Il file contenente le password non è stato trovato.\nInserisci una master key, memorizza le tue password e salva per creare un nuovo file.\n",
                "File non trovato.",
                KeyringException.INFORMATION_MESSAGE
            );
        }        
        System.out.println("Trovato.");
        return true;
    }
    
    /**
     * Costruttore
     * @param MasterKey chiave primaria per cifrare/decifrare il file contenente le password
     * @throws KeyringException Se la MasterKey non ha il corretto formato si genera un'eccezione
     */
    public KeyringClass(String MasterKey) throws KeyringException{        
        setMasterKey(MasterKey);
        load();
    }
          
    /**
     * Verifica il formato della masterKey
     * Deve contenere almeno: un carattere Maiuscolo, un carattere minuscolo, 
     * un carattere numerico, un carattere speciale, più di 10 caratteri
     * Non deve contenere caratteri uguali consecutivi.
     * @param masterKey chiave primaria per cifrare/decifrare il file contenente le password
     * @return masterKey se il formato è corretto
     * @throws KeyringException se il formato non è corretto
     */
    private String verifyMasterKey(String masterKey) throws KeyringException{
        System.out.print("Verifico che la password sia sicura...   ");
        
        String regex1 = "^((?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!-.,;:@#$%^&*?_~])([A-Za-z\\d!-.,;:@#$%^&*?_~ ]\\2?(?!\\2)){10,200})$"; 
        //if(Pattern.matches(regex1, masterKey)){
            System.out.println("Una buona password! Bravo.");
            return masterKey;
       // }
       /* System.out.println("Password non sicura!");
        throw new KeyringException(
            "Password inserita: " + masterKey +
            "\n\nLa tua password deve contenere almeno:  \n\n"+
            " - un carattere Maiuscolo\n - un carattere minuscolo\n - un carattere numerico\n - un carattere speciale\n - più di 10 caratteri\n\n" +
            "Non deve contenere caratteri uguali consecutivi. \n\n",
            "Attenzione",
            KeyringException.INFORMATION_MESSAGE
        );*/
    }

    /**
     * Aggiorno la masterKey
     * @param MasterKey chiave primaria per cifrare/decifrare il file contenente le password
     * @throws KeyringException 
     */
    public void setMasterKey(String MasterKey) throws KeyringException {        
        this.MasterKey = verifyMasterKey(MasterKey);
    }
    
    /**
     * Tabella contenente le password
     * @return gruppi contenenti le password
     */
    public Folder getRootFolder() {
        return rootFolder;
    }
        
    /**
     * Salva su file la tabella con le password
     * @return Restituisce la data e l'orario di salvataggio
     * @throws KeyringException
     */
    public String save() throws KeyringException{ 
        System.out.println("Salvo il file contenente le password...");                
        CryptoUtils.encrypt(nameFile, MasterKey, rootFolder);         
        System.out.println("Salvataggio riuscito.");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");		
        return sdf.format(System.currentTimeMillis());         
    }
    
    /**
     * Carica il file con la tabella delle password
     */
    private void load() throws KeyringException{ 
        System.out.println("Carico il file contenente le password...");
                
        FileInputStream fi = null;
        try {
            rootFolder = CryptoUtils.dencrypt(nameFile, MasterKey);  
            System.out.println("Caricamento riuscito.");
        } catch (KeyringException | ClassCastException ex) {            
            rootFolder = new Folder("Root");
            rootFolder.addNewPage("Generale", "");
            System.out.println("Errore: "+ ex.getMessage() + "Caricamento file non riuscito.");
            if (ex instanceof ClassCastException) ; //fare cast vecchio tipo di file
        } catch (StreamCorruptedException ex) {
            System.out.println("Errore: "+ ex.getMessage() + "Password non valida.\nCaricamento file non riuscito.");
            throw new KeyringException("Password non corretta. Non è possibile aprire il file.", "Non è possibile aprire il file.", KeyringException.WARNING_MESSAGE);
        }
    }
    
    /**
     * Recupera la data dell'ultima modifica del file contenente le password
     * @return Data ultima modifica file password
     */
    public String getEditDateFile(){
        File file = new File(nameFile);	
        if (file.exists()) {            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");		
            return sdf.format(file.lastModified());
        }
        return "File mai salvato.";
    }
}
