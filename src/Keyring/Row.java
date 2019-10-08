package Keyring;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nino
 */
public class Row implements Serializable{
    private final String webSite, username, email, password, note;

    public Row(String webSite, String username, String email, String password, String note) {
        this.webSite = webSite;
        this.username = username;
        this.email = email;
        this.password = password;
        this.note = note;
    }

    public String getWebSite() {
        return webSite;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNote() {
        return note;
    }       
}
