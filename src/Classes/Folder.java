/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Exceptions.KeyringException;
import java.io.Serializable;
import java.text.Collator;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Nino
 */
public final class Folder extends KeyringObject implements Serializable, Comparable<Folder>{    
    private static final long serialVersionUID = 1998L;
    
    private final LinkedList<Page> pages;
    private LinkedList<Folder> subfolders;

    public Folder() throws KeyringException {
        this("Senza nome");
    }
    
    public Folder(String name) throws KeyringException {
        super(name, "Inserisci il nome della cartella.");
        this.pages = new LinkedList();
        this.subfolders = new LinkedList();
    }

    /**
     * Modifica i campi della cartella
     * @param name Nome della cartella
     * @throws KeyringException Se non è stato inserito il nome della cartella     
     */
    @Override
    public void edit(String name) throws KeyringException{
        System.out.print("Modifico la cartella...   ");
        super.edit(name);
        
        System.out.println("Completato.");
    }
    
    /**
     * Sottocartelle contenute in questa cartella
     * @return Sottocartelle
     */
    public LinkedList<Folder> getSubfolders() {        
        return subfolders;
    }
        
    /**
     * Verifica se esistono sottocartelle contenute in questa cartella
     * @return True se esistono sottocartelle, False se non esistono
     */
    public boolean hasSubfolders(){
        return !subfolders.isEmpty();
    }

    /**
     * Aggiunge una nuova sottocartella
     * @param name Nome della sottocartella
     * @return folder Cartella appena creata
     * @throws KeyringException Se non è stato inserito il nome della cartella
     */
    public Folder addSubfolder(String name) throws KeyringException {
        System.out.print("Aggiungo una nuova sottocartella...   ");
        
        Folder f = new Folder(name);
        f.addNewPage("Generale", "");
        subfolders.add(f);
        Collections.sort(subfolders);
        
        System.out.println("Completato.");
        return f;
    }

    /**
     * Elimina una sottocartella
     * @param folder Sottocartella da eliminare
     * @throws KeyringException Se non è stata trovata la sottocartella
     */
    public void removeSubfolder(Folder folder) throws KeyringException{
        System.out.print("Rimuovo la sottocartella '" + folder.getName() + "...   ");        
        
        if(!subfolders.remove(folder)){
            System.out.println("Sottocartella non presente nella cartella."); 
            throw new KeyringException("Sottocartella non presente nella cartella.","Attenzione",KeyringException.INFORMATION_MESSAGE);
        } 
        
        System.out.println("Completato.");
    }

    //---
    
    /**
     * Pagine contenute in questa cartella
     * @return Pagine contenute in questa cartella
     */
    public LinkedList<Page> getPages() {        
        return pages;
    }
    
    /**
     * Aggiunge una nuova pagina alla cartella corrente
     * @param name Nome della pagina
     * @param info Eventuali informazioni della pagina
     * @return page La pagina appena creata
     * @throws KeyringException Se non sono stati inseriti name e info
     */
    public Page addNewPage(String name, String info) throws KeyringException{
        System.out.print("Aggiungo una nuova pagina...   ");
        if (name.isEmpty()){
            System.out.println("Inserisci il nome della pagina."); 
            throw new KeyringException("Inserisci il nome della pagina.","Attenzione",KeyringException.INFORMATION_MESSAGE);
        }
        
        Page p = new Page(name, info);
        pages.add(p); 
        Collections.sort(pages);
        
        System.out.println("Completato.");
        return p;
    }
    
    /**
     * Aggiunge una pagina alla cartella corrente
     * @param page Pagina da inserire
     * @throws KeyringException Se non sono stati inseriti name e info
     */
    public void addPage(Page page) throws KeyringException{
        System.out.print("Aggiungo una pagina...   ");
        if (page.getName().isEmpty()){
            System.out.println("Pagina non valida."); 
            throw new KeyringException("Pagina non valida.","Attenzione",KeyringException.INFORMATION_MESSAGE);
        }
        
        pages.add(page); 
        Collections.sort(pages);
        
        System.out.println("Completato.");
    }
    
    /**
     * Converte una pagina in una cartella
     * @param page Pagina da convertire
     * @return cartella appena creata
     * @throws KeyringException Se non è stata trovata la cartella
     */
    public Folder convertPageToFolder(Page page) throws KeyringException{
        System.out.print("Converto la pagina '" + page.getName() + "' in cartella...   ");        
        
        removePage(page);
        
        Folder f = new Folder();
        f.addPage(page);
        
        if(subfolders == null) subfolders = new LinkedList();
        subfolders.add(f);
        Collections.sort(subfolders);
        
        System.out.println("Completato.");
        return f;
    }
    
    /**
     * Elimina una pagina
     * @param page Pagina da eliminare
     * @throws KeyringException Se non è stata trovata la cartella
     */
    public void removePage(Page page) throws KeyringException{
        System.out.print("Rimuovo la pagina '" + page.getName() + "'...   ");        
        
        if(!pages.remove(page)){
            System.out.println("Pagina non presente nella cartella."); 
            throw new KeyringException("Pagina non presente nella cartella.","Attenzione",KeyringException.INFORMATION_MESSAGE);
        } 
        
        System.out.println("Completato.");
        
    }

    /**
     * Restituisce il numero di elementi presenti nella cartella
     * @return numero di elementi presenti nella cartella
     */
    public int size(){
        return pages.size() + (hasSubfolders() ? subfolders.size() : 0);
    }
    
    @Override
    public int compareTo(Folder o) {
        return Collator.getInstance().compare(getName(), o.getName());
    }

    
}
