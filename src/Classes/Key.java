package Classes;

import Exceptions.KeyringException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nino
 */
public final class Key implements Serializable{
      
    private static final long serialVersionUID = 1999L;
    private String webSite, username, email, password, note;
    private Date updatedAt;

    public Key(String webSite, String username, String email, String password, String note) throws KeyringException {
        this.edit(webSite, username, email, password, note);
    }
    
    public String getWebSite() {
        return webSite;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNote() {
        return note;
    }     

    public String getNotePreview(){
        if(note.length() > 30) return note.substring(0, 20) + "...";
        return note;
    }
    
    public Date getUpdatedAt() {
        return updatedAt;
    }

    
    /**
     * Modifica la key
     * @param webSite Sito web di riferimento
     * @param username Username utilizzato
     * @param email Email utilizzata per la registrazione
     * @param password Password utilizzata
     * @param note Eventuali note
     * @throws KeyringException Se non sono stati inseriti webSite, username, email, password
     */
    public void edit(String webSite, String username, String email, String password, String note) throws KeyringException{
        System.out.print("Modifico la key...   ");       
        if (webSite.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()){
            System.out.println("Inserisci il Sito web, l'email utilizzata, l'username utilizzato e la password."); 
            throw new KeyringException("Inserisci il Sito web, l'email utilizzata, l'username utilizzato e la password.","Attenzione",KeyringException.INFORMATION_MESSAGE);
        }
        
        this.webSite = webSite;
        this.username = username;
        this.email = email;
        this.password = password;
        this.note = note;
        this.updatedAt = Calendar.getInstance().getTime();        
        
        System.out.println("Completato.");
    }
    
}
