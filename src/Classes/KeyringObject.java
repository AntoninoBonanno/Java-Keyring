/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Exceptions.KeyringException;

/**
 *
 * @author Nino
 */
public class KeyringObject {    
    private String name;
    private final String errorMessage;

    public KeyringObject(String name, String errorMessage) throws KeyringException {
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
            System.out.println(errorMessage); 
            throw new KeyringException(errorMessage,"Attenzione",KeyringException.INFORMATION_MESSAGE);
        }
        this.name = name;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
