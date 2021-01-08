/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Classes;

import Classes.Folder;
import Classes.KeyringObject;
import Classes.Page;

/**
 *
 * @author Nino
 */
public interface ActionFolderListener {
    void addFolderAction(Folder parentFolder, KeyringObject currentObject);
    void editFolderAction(Folder folder);
    void deleteFolderAction(Folder parentFolder, Folder folder);
    void addPageAction(Folder parentFolder, KeyringObject currentObject);
    void editPageAction(Page page);
    void deletePageAction(Folder parentFolder, Page page);
}
