/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Exceptions.KeyringException;
import Classes.Folder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
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
 * @author Nino
 */
public class CryptoUtils {
    //Viene utilizzato AES per la cifratura/decifratura
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    
    /**
     * Cifra un Folder e salva l'oggetto su file
     * @param fileName Nome/Percorso del file
     * @param masterKey Chiave master per cifrare l'oggetto
     * @param rootFolder L'oggetto da cifrare
     * @throws KeyringException 
     */
    public static void encrypt(String fileName, String masterKey, Folder rootFolder) throws KeyringException{
        try {
            System.out.print("  -> Cifratura del file in corso... ");
            SecretKeySpec secretKey = transformKey(masterKey);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            SealedObject sealedObject = new SealedObject(rootFolder, cipher);
            CipherOutputStream cipherOutputStream = new CipherOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)), cipher);
            ObjectOutputStream outputStream = new ObjectOutputStream(cipherOutputStream);
            outputStream.writeObject(sealedObject);
            outputStream.close();
            System.out.println("Completato. ");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException  ex) {
            throw new KeyringException(ex.getMessage(), "Error", KeyringException.ERROR_MESSAGE);
        }
    }
    
    /**
     * Carica un file e decifra l'oggetto Folder
     * @param fileName Nome/Percorso del file
     * @param masterKey Chiave master per decifrare l'oggetto
     * @return Folder decifrata
     * @throws java.io.StreamCorruptedException 
     * @throws KeyringException 
     */
    public static Folder dencrypt(String fileName, String masterKey) throws StreamCorruptedException, KeyringException{
        try {
            System.out.print("  -> Decifratura del file in corso... ");
            SecretKeySpec secretKey = transformKey(masterKey);
            
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            CipherInputStream cipherInputStream = new CipherInputStream(new BufferedInputStream(new FileInputStream(fileName)), cipher);
            ObjectInputStream inputStream = new ObjectInputStream(cipherInputStream);
            SealedObject sealedObject = (SealedObject) inputStream.readObject();
            Folder rootFolder = (Folder) sealedObject.getObject(cipher);
            inputStream.close();
            
            System.out.println("Completato. ");
            return rootFolder;
        }catch(StreamCorruptedException ex){
            throw new StreamCorruptedException(ex.getMessage());
        }catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | IOException | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
           throw new KeyringException(ex.getMessage(), "Error", KeyringException.ERROR_MESSAGE);
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
}