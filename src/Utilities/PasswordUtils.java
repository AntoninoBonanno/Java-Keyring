/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Exceptions.KeyringException;
import java.util.regex.Pattern;

/**
 *
 * @author Nino
 */
public class PasswordUtils {
    
    private String masterKey;

    public PasswordUtils(String masterKey) {
        this.masterKey = masterKey;
    }

    public String getMasterKey() {
        return masterKey;
    }

    public void setMasterKey(String masterKey) {
        this.masterKey = masterKey;
    }    
        
    /**
     * Verifica il formato della password inserita
     * Deve contenere almeno: un carattere Maiuscolo, un carattere minuscolo, 
     * un carattere numerico, un carattere speciale, più di 10 caratteri
     * Non deve contenere caratteri uguali consecutivi.
     * 
     * @param password password da verificare
     * @return True se è una buona password
     * @throws KeyringException se la password inserita non è una buona password
     */
    public static boolean verifyKeyStrength(String password) throws KeyringException{
        System.out.print("Verifico che la password sia sicura...   ");
        
        String regex1 = "^((?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!-.,;:@#$%^&*?_~])([A-Za-z\\d!-.,;:@#$%^&*?_~ ]\\2?(?!\\2)){10,200})$"; 
        if(Pattern.matches(regex1, password)){
            System.out.println("Una buona password! Bravo.");
            return true;
        }
        System.out.println("Password non sicura!");
        throw new KeyringException(
            "Password inserita: " + password +
            "\n\nLa tua password deve contenere almeno:  \n\n"+
            " - un carattere Maiuscolo\n - un carattere minuscolo\n - un carattere numerico\n - un carattere speciale\n - più di 10 caratteri\n\n" +
            "Non deve contenere caratteri uguali consecutivi. \n\n",
            "Attenzione",
            KeyringException.INFORMATION_MESSAGE
        );
    }

    public static String generatePassword(){
        return "";
    }
}
