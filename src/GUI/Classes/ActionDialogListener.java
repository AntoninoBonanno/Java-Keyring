/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Classes;

import Classes.Keyring;

/**
 *
 * @author AntoninoBonanno <https://github.com/AntoninoBonanno>
 */
public interface ActionDialogListener {

    /**
     *
     * @param keyring
     */
    void onConfirmAction(Keyring keyring);
}
