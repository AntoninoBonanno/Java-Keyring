package Keyring;


import Exceptions.KeyringException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.StreamCorruptedException;
import java.util.LinkedList;
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
public class Keyring {    
    public static final double version = 1.1;
    public static final String author = "NinoBon";
    
    private static final String nameFile = "keyring";
        
    private final String MasterKey;
    private LinkedList<Row> tableKeys;

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
    public Keyring(String MasterKey) throws KeyringException{
        verifyMasterKey(MasterKey);
        this.MasterKey = MasterKey;
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
        if(Pattern.matches(regex1, masterKey)){
            System.out.println("Una buona password! Bravo.");
            return masterKey;
        }
        System.out.println("Password non sicura!");
        throw new KeyringException(
            "Password inserita: " + masterKey +
            "\n\nLa tua password deve contenere almeno:  \n\n"+
            " - un carattere Maiuscolo\n - un carattere minuscolo\n - un carattere numerico\n - un carattere speciale\n - più di 10 caratteri\n\n" +
            "Non deve contenere caratteri uguali consecutivi. \n\n",
            "Attenzione",
            KeyringException.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Tabella contenente le password
     * @return tabella contenente le password
     */
    public LinkedList<Row> getTableKeys() {
        return tableKeys;
    }
    
    /**
     * Aggiunge una riga alla tabella contenente le password
     * @param webSite Sito web di riferimento
     * @param username Username utilizzato
     * @param email Email utilizzata per la registrazione
     * @param password Password utilizzata
     * @param note Eventuali note
     * @throws KeyringException Se non sono stati inseriti webSite, username, email, password
     */
    public void addRow(String webSite, String username, String email, String password, String note) throws Exception{
        System.out.print("Aggiungo una nuova riga...   ");
        if (webSite.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()){
            System.out.println("Inserisci il Sito web, l'email utilizzata, l'username utilizzato e la password."); 
            throw new KeyringException("Inserisci il Sito web, l'email utilizzata, l'username utilizzato e la password.","Attenzione",KeyringException.INFORMATION_MESSAGE);
        }
        Row r = new Row(webSite, username, email, password, note);
        tableKeys.add(r); 
        System.out.println("Completato.");
    }
    
    /**
     * Modifica una riga della tabella contenente le password
     * @param currentIndex Indice della riga da modificare
     * @param webSite Sito web di riferimento
     * @param username Username utilizzato
     * @param email Email utilizzata per la registrazione
     * @param password Password utilizzata
     * @param note Eventuali note
     * @throws KeyringException Se non sono stati inseriti webSite, username, email, password
     */
    public void editRow(int currentIndex, String webSite, String username, String email, String password, String note) throws Exception{
        System.out.print("Modifico la riga " + currentIndex + "...   ");
        if (currentIndex < 0 || currentIndex > tableKeys.size()-1){
            System.out.println("Seleziona una riga nella tabella."); 
            throw new KeyringException("Seleziona una riga nella tabella.","Attenzione",KeyringException.INFORMATION_MESSAGE);
        }
        if (webSite.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()){
            System.out.println("Inserisci il Sito web, l'email utilizzata, l'username utilizzato e la password."); 
            throw new KeyringException("Inserisci il Sito web, l'email utilizzata, l'username utilizzato e la password.","Attenzione",KeyringException.INFORMATION_MESSAGE);
        }
        Row r = tableKeys.get(currentIndex); 
        r.setWebSite(webSite);
        r.setUsername(username);
        r.setEmail(email);
        r.setPassword(password);
        r.setNote(note);
        
        System.out.println("Completato.");
    }

    /**
     * Elimina una riga della tabella contenente le password
     * @param currentIndex Indice della riga da eliminare
     * @throws KeyringException Se l'indice non è valido
     */
    public void removeRow(int currentIndex) throws KeyringException{
        System.out.print("Rimuovo la riga " + currentIndex + "...   ");        
        if (currentIndex < 0 || currentIndex > tableKeys.size()-1){
            System.out.println("Seleziona una riga nella tabella."); 
            throw new KeyringException("Seleziona una riga nella tabella.","Attenzione",KeyringException.INFORMATION_MESSAGE);
        } 
        tableKeys.remove(currentIndex);
        System.out.println("Completato.");
    }
    
    /**
     * Sposta di una posizione in alto una riga della tabella contenente le password
     * @param currentIndex Indice della riga da spostare
     * @throws KeyringException Se l'indice non è valido
     */
    public void moveUpRow(int currentIndex) throws KeyringException{
        System.out.print("Porto sù la riga " + currentIndex + "...   ");      
        if (currentIndex < 0 || currentIndex > tableKeys.size()-1){
            System.out.println("Seleziona una riga nella tabella."); 
            throw new KeyringException("Seleziona una riga nella tabella.","Attenzione",KeyringException.INFORMATION_MESSAGE);
        } 
        if (currentIndex == 0){
            System.out.println("La riga è in cima.");      
            throw new KeyringException("La riga è in cima.","Attenzione",KeyringException.INFORMATION_MESSAGE);
        }
        
        Row r = tableKeys.get(currentIndex);
        tableKeys.set(currentIndex, tableKeys.get(currentIndex-1));        
        tableKeys.set(currentIndex-1, r);
        System.out.println("Completato.");
    }
    
    /**
     * Sposta di una posizione in basso una riga della tabella contenente le password
     * @param currentIndex Indice della riga da spostare
     * @throws KeyringException Se l'indice non è valido
     */
    public void moveDownRow(int currentIndex) throws KeyringException{
        System.out.print("Porto giù la riga " + currentIndex + "...   ");      
        if (currentIndex < 0 || currentIndex > tableKeys.size()-1){
            System.out.println("Seleziona una riga nella tabella."); 
            throw new KeyringException("Seleziona una riga nella tabella.","Attenzione",KeyringException.INFORMATION_MESSAGE);
        } 
        if (currentIndex == tableKeys.size()-1){
            System.out.println("La riga è alla base.");                    
            throw new KeyringException("La riga è alla base.","Attenzione",KeyringException.INFORMATION_MESSAGE);
        }
        
        Row r = tableKeys.get(currentIndex);
        tableKeys.set(currentIndex, tableKeys.get(currentIndex+1));    
        tableKeys.set(currentIndex+1,r); 
        System.out.println("Completato.");
    }
    
    /**
     * Copia negli appunti l'elemento selezionato
     * @param currentIndex Riga selezionata
     * @param rowElement Row.ELEMENT_WEBSITE, Row.ELEMENT_USERNAME, Row.ELEMENT_EMAIL, Row.ELEMENT_PASSWORD, Row.ELEMENT_NOTE
     * @throws Exceptions.KeyringException
     */
    public void copyToClipboard(int currentIndex, int rowElement) throws KeyringException{
        System.out.print("Copio la cella (" + currentIndex + ", " + rowElement + ")...   ");      
        if (currentIndex < 0 || currentIndex > tableKeys.size()-1){
            System.out.println("Seleziona una riga nella tabella."); 
            throw new KeyringException("Seleziona una riga nella tabella.","Attenzione",KeyringException.INFORMATION_MESSAGE);
        } 
        String element = tableKeys.get(currentIndex).getElement(rowElement);
        if(element == null){
            System.out.println("Scelta elemento non valido."); 
            throw new KeyringException("Scelta elemento non valido.","Errore",KeyringException.ERROR_MESSAGE);
        }
        StringSelection stringSelection = new StringSelection(element);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        System.out.println("Copiato.");
    }    
    
    /**
     * Salva su file la tabella con le password
     * @throws KeyringException
     */
    public void save() throws KeyringException{ 
        System.out.println("Salvo il file contenente le password...");                
        CryptoUtils.encrypt(nameFile, MasterKey, tableKeys);         
        System.out.println("Salvataggio riuscito.");
    }
    
    /**
     * Carica il file con la tabella delle password
     */
    private void load() throws KeyringException{ 
        System.out.println("Carico il file contenente le password...");
                
        FileInputStream fi = null;
        try {
            tableKeys = CryptoUtils.dencrypt(nameFile, MasterKey);  
            System.out.println("Caricamento riuscito.");
        } catch (KeyringException ex) {            
            tableKeys=new LinkedList();
            System.out.println("Errore: "+ ex.getMessage() + "Caricamento file non riuscito.");
        } catch (StreamCorruptedException ex) {
            System.out.println("Errore: "+ ex.getMessage() + "Password non valida.\nCaricamento file non riuscito.");
            throw new KeyringException("Password non corretta. Non è possibile aprire il file.", "Non è possibile aprire il file.", KeyringException.WARNING_MESSAGE);
        }
    }
}
