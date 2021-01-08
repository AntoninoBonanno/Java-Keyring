/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Classes;

import Classes.Key;
import Classes.Page;

/**
 *
 * @author Nino
 */
public interface ActionKeyListener {
    
    void onAddKeyAction(Page page);
    void onEditKeyAction(Key key);
}
