/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Exceptions.KeyringException;
import Classes.Folder;
import Classes.KeyringObject;
import Classes.Page;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Nino
 */
public class UpsertPage extends javax.swing.JDialog {

    /**
     * Creates new form UpsertPage
     * @param parent
     * @param modal
     * @param parentFolder Cartella padre all'oggetto dove si vuole aggiungere la pagina, NULL se <code>kObj</code> è una pagina da modificare
     * @param kObj Oggetto dove si vuole aggiungere la pagina, se <code>parentFolder</code> è NULL && <code>kObj</code> è una pagina, <code>kObj</code> viene modificato
     * @throws Exceptions.KeyringException
     */
    public UpsertPage(java.awt.Frame parent, boolean modal, Folder parentFolder, KeyringObject kObj) throws KeyringException {
        super(parent, modal);
        initComponents();
        
        if(kObj == null) throw new KeyringException("Errore.", "Errore", KeyringException.INFORMATION_MESSAGE);
        
        this.parentFolder = parentFolder;
        this.kObj = kObj;
        
        
        if(kObj instanceof Page && parentFolder == null){
            jTextField_name.setText(((Page)kObj).getName());
            jTextArea_info.setText(((Page)kObj).getInfo());
            setTitle("Modifica pagina");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField_name = new javax.swing.JTextField();
        jButton_confirm = new javax.swing.JButton();
        jButton_abort = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea_info = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Aggiungi pagina");

        jLabel1.setText("Nome:");

        jTextField_name.setNextFocusableComponent(jTextArea_info);
        jTextField_name.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField_nameKeyPressed(evt);
            }
        });

        jButton_confirm.setText("Conferma");
        jButton_confirm.setNextFocusableComponent(jButton_abort);
        jButton_confirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_confirmActionPerformed(evt);
            }
        });

        jButton_abort.setText("Annulla");
        jButton_abort.setNextFocusableComponent(jTextField_name);
        jButton_abort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_abortActionPerformed(evt);
            }
        });

        jTextArea_info.setColumns(20);
        jTextArea_info.setRows(5);
        jTextArea_info.setNextFocusableComponent(jButton_confirm);
        jTextArea_info.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea_infoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTextArea_info);

        jLabel5.setText("Informazioni:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton_confirm)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_abort))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jTextField_name, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(0, 246, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_confirm)
                    .addComponent(jButton_abort))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_confirmActionPerformed
        String name = jTextField_name.getText();
        String info = jTextArea_info.getText();
        try {
            if(kObj instanceof Page){
                if(parentFolder != null){                
                    Folder newFolder = parentFolder.convertPageToFolder((Page) kObj);
                    newFolder.addNewPage(name, info);
                } else ((Page) kObj).edit(name, info);
            } else {
                ((Folder) kObj).addNewPage(name, info);
            }
            
            this.dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_jButton_confirmActionPerformed

    private void jButton_abortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_abortActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton_abortActionPerformed

    private void jTextField_nameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField_nameKeyPressed
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            jButton_confirm.doClick();
        }
    }//GEN-LAST:event_jTextField_nameKeyPressed

    private void jTextArea_infoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea_infoKeyPressed
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            jButton_confirm.doClick();
        }
    }//GEN-LAST:event_jTextArea_infoKeyPressed

    
    private final Folder parentFolder;
    private final KeyringObject kObj;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton_abort;
    private javax.swing.JButton jButton_confirm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea_info;
    private javax.swing.JTextField jTextField_name;
    // End of variables declaration//GEN-END:variables
}
