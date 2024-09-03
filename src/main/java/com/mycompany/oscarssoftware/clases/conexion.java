
package com.mycompany.oscarssoftware.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class conexion {
    
    private String db;
    private String host;
    private String user;
    private String password = "";
    Connection con;

    public conexion() {
        this.db = "oscar_db";
        this.host = "localhost";
        this.user = "root";
        this.password = "";
    }
        public Connection getCon() {
            try {
                String url = "jdbc:mysql://" + host + "/" + db;
                con = DriverManager.getConnection(url, user, password);
                System.out.println("se conecto!");
                return con;
            } catch (SQLException ex) {
                Logger.getLogger(conexion.class.getName()).log(Level.SEVERE, null, ex); 
                System.out.println("no se conecto, flipo: \n" + ex);
            }
        return null;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection conectar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   


    
    
    
 
    
}
