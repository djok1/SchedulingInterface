/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Djok
 */
public class DBConnection 
{
    private static final String protocall = "jdbc";
    private static final String vendorName = ":mySQL:";
    private static final String serverName = "//wgudb.ucertify.com/U05qOE";
    private static final String jdbcURL = protocall + vendorName + serverName;
    private static final String mySQLjdbcDriver =  "com.mysql.jdbc.Driver";
    private static Connection conn = null;
    private static final String userName = "U05qOE";
    private static final String password = "53688580498";
    
    
    public static Connection startConnection()
    {
        try 
        {
            Class.forName(mySQLjdbcDriver);
            conn = (Connection) DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection successfull");
        } 
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return conn;
    }
    public static void closeConnection()
    {
        try 
        {
            conn.close();
            System.out.println("Connection Clsoed");
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }
    
    public static Connection getConnection()
    {
        return conn;
    }
    
}
