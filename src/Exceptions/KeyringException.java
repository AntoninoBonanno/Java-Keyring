/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exceptions;

import javax.swing.JOptionPane;

/**
 *
 * @author AntoninoBonanno <https://github.com/AntoninoBonanno>
 */
public class KeyringException extends Exception {
    
    public static final int ERROR_MESSAGE = JOptionPane.ERROR_MESSAGE;
    public static final int INFORMATION_MESSAGE = JOptionPane.INFORMATION_MESSAGE;
    public static final int WARNING_MESSAGE = JOptionPane.WARNING_MESSAGE;
    public static final int QUESTION_MESSAGE = JOptionPane.QUESTION_MESSAGE;
    
    private final String title; 
    private final int messageType;    
    
    /**
     * Constructs an instance of <code>KeyringException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     * @param title Titolo del messaggio
     * @param messageType 0 -> ERROR_MESSAGE, 1 -> INFORMATION_MESSAGE, 2 -> WARNING_MESSAGE, 3 -> QUESTION_MESSAGE
     */
    public KeyringException(String msg, String title, int messageType) {
        super(msg);
        this.messageType=messageType;
        this.title=title;   
        
        System.err.println("Errore: " + msg);
    }
    
    public KeyringException(String msg, int messageType){
        super(msg);
        this.messageType=messageType;
        switch(messageType){
            case ERROR_MESSAGE:
                title = "Errore";
                break;
            case INFORMATION_MESSAGE:
                title = "Informazione";
                break;
            case WARNING_MESSAGE:
                title = "Attenzione";
                break;
            default:
                title = "Domanda";
                break;
        }     
        System.err.println("Errore: " + msg);
    }
    
    public String getTitle() {
        return title;
    }

    public int getMessageType() {
        return messageType;
    }
}
