/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Exceptions.KeyringException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import static org.passay.IllegalCharacterRule.ERROR_CODE;
import org.passay.PasswordGenerator;

/**
 *
 * @author AntoninoBonanno <https://github.com/AntoninoBonanno>
 */
public final class PasswordUtils {
     
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
            "La tua password deve contenere almeno:  \n\n"+
            " - un carattere Maiuscolo\n - un carattere minuscolo\n - un carattere numerico\n - un carattere speciale\n - più di 10 caratteri\n\n" +
            "Non deve contenere caratteri uguali consecutivi.",
            KeyringException.WARNING_MESSAGE
        );
    }

    public static String generatePassword(){
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(5);

        CharacterData specialChars = new CharacterData() {
            @Override
            public String getErrorCode() {
                return ERROR_CODE;
            }

            @Override
            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        int lenght = ThreadLocalRandom.current().nextInt(10, 20 + 1);
        String password = gen.generatePassword(lenght, splCharRule, lowerCaseRule, 
          upperCaseRule, digitRule);
        
        return password;
    }
}
