/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Utils.DBConnection;
import Utils.Log;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Djok
 */
public class UserDB 
{
    private static User currentUser;
    public static User getCurrentUser() 
    {
        return currentUser;
    }
    
    public static Boolean login(String username, String password) {
        try 
        {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT userName, password FROM user WHERE userName='" + username + "' AND password='" + password + "'";
            ResultSet results = statement.executeQuery(query);
            if(results.next()) 
            {
                currentUser = new User();
                currentUser.setUsername(results.getString("userName"));
                statement.close();
                Log.log(username, true);
                return true;
            } 
            else 
            {
                Log.log(username, false);
                return false;
            }
        } 
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }
    
    public static ObservableList<String> getUsers()
    {
        ObservableList<String> Users = FXCollections.observableArrayList();
        try
        {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT userName FROM user WHERE active = 1";
            ResultSet results = statement.executeQuery(query);
            while(results.next())
            {
                Users.add(results.getString("userName"));
            }
        }
        catch (SQLException e)
        {
            
        }
        
        
        
        return Users;
    }
}
