/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Exceptions.KeyringException;
import java.io.Serializable;

/**
 *
 * @author AntoninoBonanno <https://github.com/AntoninoBonanno>
 */
public class Directory implements Serializable{      
    private static final long serialVersionUID = 1999L;
    
    private String name;
    private final String errorMessage;

    public Directory(String name, String errorMessage) throws KeyringException {
       this.errorMessage = errorMessage;
       edit(name);
    }

    public String getName() {
        return name;
    }

    /**
     * Modifica i campi
     * @param name Nome
     * @throws KeyringException Se non è stato inserito il nome
     */
    protected void edit(String name) throws KeyringException {
        if (name.isEmpty()){
            throw new KeyringException(errorMessage,KeyringException.WARNING_MESSAGE);
        }
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
