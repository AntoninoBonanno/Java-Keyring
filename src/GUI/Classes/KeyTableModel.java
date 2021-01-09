/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Classes;

import Classes.Key;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author AntoninoBonanno <https://github.com/AntoninoBonanno>
 */
public class KeyTableModel extends AbstractTableModel {
    
    private LinkedList<Key> keys;
    private boolean showPassword;
    private final DateFormat dateFormat;
    protected static final String[] columns = ColumnKeyEnum.getColumnName();
    
    public KeyTableModel() {
        this.keys = new LinkedList<>();
        this.showPassword = false;
        this.dateFormat = new SimpleDateFormat("dd/mm/yyyy hh:mm:ss");  
    }
    
    public boolean isShowPassword() {
        return showPassword;
    }

    public void setShowPassword(boolean showPassword) {
        this.showPassword = showPassword;
        reload();
    }

    public LinkedList<Key> getKeys() {
        return keys;
    }

    public void setKeys(LinkedList<Key> keys) {
        this.keys = (keys == null) ? new LinkedList<>() : keys;
        reload();
    }
    
    public Key getKey(int index){
        if(index < 0 || index > getRowCount()) return null;
        return keys.get(index);
    }
    
    public void lastRowInserted(){
        int size = getRowCount()-1;
        if (size <= 0) reload();
        else fireTableRowsInserted(size, size);
    }
    
    public void reload(){
        fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        return keys.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return java.lang.String.class;
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ColumnKeyEnum keyEnum = ColumnKeyEnum.fromInt(columnIndex);
        return getColumn(rowIndex, keyEnum);
    }
    
    public String getValueAt(int rowIndex, ColumnKeyEnum keyEnum){
        if(rowIndex < 0 || rowIndex > getRowCount()) return null;
        return getColumn(rowIndex, keyEnum);
    }
    
    private String getColumn(int rowIndex, ColumnKeyEnum keyEnum) {
        Key key = keys.get(rowIndex);
        switch(keyEnum){
            case WEB_SITE:
                return key.getWebSite();
            case EMAIL:
                return key.getEmail();
            case USERNAME:
                return key.getUsername();
            case PASSWORD:
                return isShowPassword() ? key.getPassword() : "********";
            case NOTE:
                return key.getNotePreview();
            case UPDATED_AT:
                return this.dateFormat.format(key.getUpdatedAt());
            default:
                return null;
        }
    }
}
