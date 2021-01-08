package Keyring;

import Exceptions.KeyringException;
import GUI.KeyringMain;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nino
 */
public class Main {        
    public static void main(String[] args) {
        
        System.out.println("<----- KEYRING ----->");
        System.out.println("Versione: " + KeyringClass.version);    
        System.out.println("Autore: " + KeyringClass.author + "\n");
         
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            
            KeyringMain keyringMain = new KeyringMain();
            keyringMain.setLocationRelativeTo(null);
            keyringMain.setVisible(true);
            keyringMain.setExtendedState(JFrame.MAXIMIZED_BOTH);
            
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            System.out.println("Errore tema: " + ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore tema", JOptionPane.ERROR_MESSAGE);
        } catch (KeyringException ex) {
           JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getTitleMsg(), ex.getTypeMessage());
        }
    }
}
