/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Exceptions.KeyringException;
import Classes.Folder;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.prefs.Preferences;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author AntoninoBonanno <https://github.com/AntoninoBonanno>
 */
public class CryptoUtils {
    //Viene utilizzato AES per la cifratura/decifratura
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    private static final int MAX_ATTEMPT = 3;
    
    /**
     * Cifra un Folder e salva l'oggetto su file
     * 
     * @param file File destinazione della cifratura
     * @param masterKey Chiave master per cifrare l'oggetto
     * @param rootFolder L'oggetto da cifrare
     * @throws KeyringException 
     */
    public static void encrypt(File file, String masterKey, Folder rootFolder) throws KeyringException{
        try {
            System.out.print("  -> Cifratura del file in corso... ");
            SecretKeySpec secretKey = transformKey(masterKey);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            SealedObject sealedObject = new SealedObject(rootFolder, cipher);
            CipherOutputStream cipherOutputStream = new CipherOutputStream(new BufferedOutputStream(new FileOutputStream(file)), cipher);
            ObjectOutputStream outputStream = new ObjectOutputStream(cipherOutputStream);
            outputStream.writeObject(sealedObject);
            outputStream.close();
            
            System.out.println("Completato. ");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException  ex) {
            throw new KeyringException(ex.getMessage(), KeyringException.ERROR_MESSAGE);
        }
    }
    
    /**
     * Carica un file e decifra l'oggetto Folder
     * 
     * @param file File da decifrare
     * @param masterKey Chiave master per decifrare l'oggetto
     * @return Folder decifrata
     * @throws KeyringException 
     */
    public static Folder dencrypt(File file, String masterKey) throws KeyringException{
        int attempt = getFileAttempt(file);
        if(attempt > MAX_ATTEMPT) throw new KeyringException("Troppi tentativi di accesso!!\nRiprova tra 5 minuti", KeyringException.WARNING_MESSAGE);
        
        try {
            System.out.print("  -> Decifratura del file in corso... ");
            SecretKeySpec secretKey = transformKey(masterKey);
            
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            CipherInputStream cipherInputStream = new CipherInputStream(new BufferedInputStream(new FileInputStream(file)), cipher);
            ObjectInputStream inputStream = new ObjectInputStream(cipherInputStream);
            SealedObject sealedObject = (SealedObject) inputStream.readObject();
            Folder rootFolder = (Folder) sealedObject.getObject(cipher);
            inputStream.close();
            
            removeFileAttempt(file);
            System.out.println("Completato. ");
            return rootFolder;
        }catch(StreamCorruptedException ex){
            throw new KeyringException("Password non valida. \nTentativo " + attempt + " di " + MAX_ATTEMPT, KeyringException.WARNING_MESSAGE);
        }catch(InvalidClassException ex){
        throw new KeyringException("File non copatibile con questa versione o non riconosciuto", KeyringException.WARNING_MESSAGE);
        }catch (ClassNotFoundException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IOException |  IllegalBlockSizeException | BadPaddingException ex) {
           throw new KeyringException(ex.getMessage() + ex.getClass().getCanonicalName(), KeyringException.ERROR_MESSAGE);
        }
    }    
    
    /**
     * Trasforma la stringa inserita in una combinazione di SHA256 e MD5 
     * @param masterKey Stringa master
     * @return Password key 
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException 
     */
    private static SecretKeySpec transformKey(String masterKey) throws NoSuchAlgorithmException, InvalidKeySpecException{        
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(masterKey.getBytes());
                
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(masterKey.toCharArray(), md.digest(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
                
        return new SecretKeySpec(tmp.getEncoded(), ALGORITHM);
    }

    /**
     * Recupera i tentativi di accesso su uno specifico file
     * 
     * @param file File da controllere
     * @return il numero di tentativi effettuati
     */
    private static int getFileAttempt(File file) {
        String key = file.getPath();        
        Preferences attempts  = Preferences.userNodeForPackage(CryptoUtils.class);
        int attempt = attempts.getInt(key, 0);
        
        long currentMillis = System.currentTimeMillis();        
        long attemptMillis = attempts.getLong(key+"time", System.currentTimeMillis());
        
        //se l'ultimo tentativo di accesso è maggiore di 5 minuti, azzero l'attempt
        if (currentMillis - attemptMillis > 300000) attempt = 1;
        else attempt++;
        
        attempts.putInt(key, attempt);
        attempts.putLong(key+"time", currentMillis);
        
        return attempt;        
    }
    
    /**
     * Rimuove i tentativi di accesso su uno specifico file
     * 
     * @param file File da controllere
     */
    private static void removeFileAttempt(File file) {
        String key = file.getPath();        
        Preferences attempts  = Preferences.userNodeForPackage(CryptoUtils.class);
        attempts.remove(key);
        attempts.remove(key+"time");
    }
}
