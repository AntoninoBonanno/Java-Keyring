/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Exceptions.KeyringException;
import java.io.Serializable;
import java.text.Collator;
import java.util.LinkedList;


/**
 *
 * @author AntoninoBonanno <https://github.com/AntoninoBonanno>
 */
public class Page extends Directory implements Serializable, Comparable<Page>{
    private static final long serialVersionUID = 1997L;
    
    private String info;    
    private final LinkedList<Key> tableKeys;

    public Page() throws KeyringException {
        this("Generale", "");
    }
    
    public Page(String name, String info) throws KeyringException {
        super(name, "Inserisci il nome della pagina.");
        this.info = info;
        this.tableKeys = new LinkedList();
    }

    public String getInfo() {
        return info;
    }
    
    /**
     * Modifica la pagina
     * @param name Nome della pagina
     * @param info Eventuali informazioni della pagina
     * @throws KeyringException Se non sono stati inseriti name e info
     */
    public void edit(String name, String info) throws KeyringException{
        System.out.print("Modifico la pagina...   ");
        
        super.edit(name);
        this.info = info;
        
        System.out.println("Completato.");
    }
    
    /**
     * Recupera la tabella contenente le password
     * @return righe della tabella
     */
    public LinkedList<Key> getTableKeys() {
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
    public void addKey(String webSite, String username, String email, String password, String note) throws KeyringException{
        System.out.print("Aggiungo una nuova riga...   ");
        if (webSite.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()){
            System.out.println("Inserisci il Sito web, l'email utilizzata, l'username utilizzato e la password."); 
            throw new KeyringException("Inserisci il Sito web, l'email utilizzata, l'username utilizzato e la password.","Attenzione",KeyringException.INFORMATION_MESSAGE);
        }
        Key r = new Key(webSite, username, email, password, note);
        tableKeys.add(r); 
        System.out.println("Completato.");
    }
        
    /**
     * Elimina una key
     * 
     * @param key Key da eliminare
     * @throws KeyringException Se non è stata trovata la key
     */
    public void removeKey(Key key) throws KeyringException{
        System.out.print("Rimuovo la key...   ");        
        
        if(!tableKeys.remove(key)){
            System.out.println("Key non presente nella pagina."); 
            throw new KeyringException("Key non presente nella pagina.","Attenzione",KeyringException.INFORMATION_MESSAGE);
        } 
        
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
            throw new KeyringException("Seleziona una riga della tabella.",KeyringException.INFORMATION_MESSAGE);
        } 
        if (currentIndex == 0){
            throw new KeyringException("La riga è in cima.",KeyringException.INFORMATION_MESSAGE);
        }
        
        Key r = tableKeys.get(currentIndex);
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
            throw new KeyringException("Seleziona una riga della tabella.",KeyringException.INFORMATION_MESSAGE);
        } 
        if (currentIndex == tableKeys.size()-1){                 
            throw new KeyringException("La riga è alla base.",KeyringException.INFORMATION_MESSAGE);
        }
        
        Key r = tableKeys.get(currentIndex);
        tableKeys.set(currentIndex, tableKeys.get(currentIndex+1));    
        tableKeys.set(currentIndex+1,r); 
        System.out.println("Completato.");
    }
    

    @Override
    public int compareTo(Page o) {
        return Collator.getInstance().compare(getName(), o.getName());
    }

}
