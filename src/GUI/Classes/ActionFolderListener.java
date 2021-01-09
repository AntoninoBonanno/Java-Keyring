/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Classes;

import Classes.Folder;
import Classes.Directory;
import Classes.Page;

/**
 *
 * @author AntoninoBonanno <https://github.com/AntoninoBonanno>
 */
public interface ActionFolderListener {
    void addFolderAction(Folder parentFolder, Directory currentObject);
    void editFolderAction(Folder folder);
    void deleteFolderAction(Folder parentFolder, Folder folder);
    void addPageAction(Folder parentFolder, Directory currentObject);
    void editPageAction(Page page);
    void deletePageAction(Folder parentFolder, Page page);
}
