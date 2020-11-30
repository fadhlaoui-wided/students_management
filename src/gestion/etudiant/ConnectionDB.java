/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion.etudiant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author unkown
 */
public class ConnectionDB {
    
    private String url = "jdbc:mysql://localhost:3306/gestion_etudiant";
    private String username="root";
    private String password = "";
    
    public  Connection getConnection(){
        
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
