/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Classes;


/**
 *
 * @author Nino
 */
public enum ColumnKeyEnum {    
    WEB_SITE(0),
    EMAIL(1),
    USERNAME(2),
    PASSWORD(3),
    NOTE(4),
    UPDATED_AT(5);
    
    private static final String[] columnName = new String [] {
        "Sito web", "Email", "Username", "Password", "Note", "Ultima modifica"
    };

    private final int value;
    private ColumnKeyEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getName(){
        return columnName[value];
    }

    public static String[] getColumnName() {
        return columnName;
    }
    
    public static ColumnKeyEnum fromInt(int value){
        if(value<0 || value > (columnName.length - 1)) return null;
        return values()[value];
    }
}
