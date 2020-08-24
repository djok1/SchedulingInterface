/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Utils.DBConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Djok
 */
public class CustomerDB 
{
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static int globalID = 0;
    
    
    
    
    
    
    
    
    public static Customer getCustomer(int id) 
    {
        try 
        {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT * FROM customer WHERE customerId='" + id + "'";
            ResultSet results = statement.executeQuery(query);
            if(results.next()) 
            {
                Customer customer = new Customer();
                customer.setName(results.getString("customerName"));
                statement.close();
                return customer;
            }
        } 
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;
    }

    public static ObservableList<Customer> getAllCustomers() 
    {
       allCustomers.clear();
        try 
        {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = 
                  "SELECT customer.customerId, customer.customerName, address.address, address.phone, address.postalCode, city.city "
                + "FROM customer " 
                + "LEFT JOIN address ON customer.addressId = address.addressId "
                + "LEFT JOIN city ON address.cityId = city.cityId";
            ResultSet results = statement.executeQuery(query);
            while(results.next()) 
            {
                Customer customer = new Customer(results.getInt("customerId"), 
                        results.getString("customerName"), 
                        results.getString("address"), 
                        results.getString("city"), 
                        results.getString("phone"), 
                        results.getString("postalCode"));
                allCustomers.add(customer);
            }
            statement.close();
            return allCustomers;
        } 
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
}
