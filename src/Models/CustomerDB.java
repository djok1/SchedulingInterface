/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Utils.DBConnection;
import Utils.Log;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

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
                  "SELECT customer.customerId, customer.customerName, address.address, address.phone, address.postalCode, city.city, country.country "
                + "FROM customer " 
                + "LEFT JOIN address ON customer.addressId = address.addressId "
                + "LEFT JOIN city ON address.cityId = city.cityId "
                + "LEFT JOIN country ON city.countryId = country.countryId";
            ResultSet results = statement.executeQuery(query);
            while(results.next()) 
            {
                Customer customer = new Customer(
                        results.getInt("customerId"), 
                        results.getString("customerName"), 
                        results.getString("address"), 
                        results.getString("city"), 
                        results.getString("postalCode"), 
                        results.getString("phone"),
                        results.getString("country"));
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

    public static boolean saveCustomer(String nameTXT, String addressTXT, String cityTXT, String postalTXT, String phoneTXT, String countryTXT) 
    {
        try 
        {
            String tempUser = Log.fingCurrentUser();
            Statement statement = DBConnection.getConnection().createStatement();
            int CountryID = getCountryID(statement, countryTXT, tempUser);
            int CityID = getCityID(statement, CountryID,cityTXT, tempUser);
            int AddressID = getAddressID(statement, CityID,addressTXT,postalTXT, tempUser , phoneTXT);
            String query = 
                    "INSERT INTO customer "
                    + "SET addressid = '" + AddressID  + "' "
                    + ", customerName = '" + nameTXT + "' "
                    + ", active = 1"
                    + ", createDate=NOW() " 
                    + ", createdBy='" + tempUser
                    + "', lastUpdate=NOW() "
                    + ", lastUpdateBy='"+tempUser+"'";
            statement.executeUpdate(query);
        } 
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }

    private static int getCountryID(Statement statement, String countryTXT, String tempUser) 
    {
        try 
        {
            String query =
                    "SELECT countryId "
                    + "FROM country "
                    + "WHERE country = '" + countryTXT + "' ";
            ResultSet results = statement.executeQuery(query);
            while(results.next())
            {
                return results.getInt("countryID");
            }
            
        } 
        catch (SQLException ex) 
        {
            System.out.println("SQLExcpection: " + ex.getMessage());

        }
        try 
        {

            String query0 =
                    "INSERT INTO country "
                    + "SET country = '" + countryTXT + "' "
                    + ", createDate=NOW() " 
                    + ", createdBy='" + tempUser
                    + "', lastUpdate=NOW() "
                    + ", lastUpdateBy='"+tempUser+"'";
            String query1 =
                    "SELECT MAX(countryID) AS countryID "
                    + "FROM country";

            statement.executeUpdate(query0);
            ResultSet results = statement.executeQuery(query1);
            if(results.next() == false)
            {
            
            }
            else
            {
            return results.getInt(1);
            }
        } 
        catch (SQLException ex1) 
        {
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex1);
        }
        return -1;
    }

    private static int getCityID(Statement statement, int CountryID, String cityTXT, String tempUser) 
    {
        try
        {
            String query = 
                    "SELECT cityID "
                    + "FROM city "
                    + "WHERE city = '" + cityTXT + "' "
                    + "AND countryId = '" + CountryID + "' ";
           ResultSet results = statement.executeQuery(query);
           while(results.next())
           {
               return results.getInt("cityID");
           }
        }
        catch(SQLException ex)
        {
            System.out.println("SQLExcpection: " + ex.getMessage());

        }
        try 
        {

            String query0 =
                    "INSERT INTO city "
                    + "SET city = '" + cityTXT + "' "
                    + ", countryId = '" + CountryID + "' "
                    + ", createDate=NOW() " 
                    + ", createdBy='" + tempUser
                    + "', lastUpdate=NOW() "
                    + ", lastUpdateBy='"+tempUser+"'";
            String query1 =
                    "SELECT MAX(cityID) AS cityID "
                    + "FROM city";

            statement.executeUpdate(query0);
            ResultSet results = statement.executeQuery(query1);
            if(results.next() == false)
            {
            
            }
            else
            {
            return results.getInt(1);
            }
        } 
        catch (SQLException ex1) 
        {
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex1);
        }
        return -1;
    }

    private static int getAddressID(Statement statement, int CityID, String addressTXT, String postalTXT, String tempUser, String phoneTXT) 
    {
        try
        {
            String query = 
                    "SELECT addressId "
                    + "FROM address "
                    + "WHERE cityId = '" + CityID + "' "
                    + "AND address = '" + addressTXT + "' "
                    + "AND postalCode = '" + postalTXT + "' "
                    + "AND phone = '" + phoneTXT  +"' ";
           ResultSet results = statement.executeQuery(query);
           while(results.next())
           {
               return results.getInt("addressId");
           }
        }
        catch(SQLException ex)
        {
            
                System.out.println("SQLExcpection: " + ex.getMessage());
                
        }
        try 
        {
            String query0 =
                    "INSERT INTO address "
                    + "SET cityid =  '" + CityID + "' "
                    + ", address = '" + addressTXT + "' "
                    + ", address2 = ''"
                    + ", postalCode = '" + postalTXT + "' "
                    + ", createDate=NOW() " 
                    + ", createdBy='" + tempUser
                    + "', lastUpdate=NOW() "
                    + ", lastUpdateBy='"+tempUser+"'"
                    + ", phone = '" + phoneTXT +"'";
            String query1 =
                    "SELECT MAX(addressId) AS addressId "
                    + "FROM address";

            statement.executeUpdate(query0);
            ResultSet results = statement.executeQuery(query1);
            if(results.next() == false)
            {
            
            }
            else
            {
            return results.getInt(1);
            }
        } 
        catch (SQLException ex1) 
        {
            Logger.getLogger(CustomerDB.class.getName()).log(Level.SEVERE, null, ex1);
        }
        return -1;
    }

    public static void deleteCustomer(int customerId) 
    {
        try 
        {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "DELETE FROM customer WHERE customerId=" + customerId;
            statement.executeUpdate(query);
 
        } 
        catch(SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    public static boolean modifyCustomer(String nameTXT, String addressTXT, String cityTXT, String postalTXT, String phoneTXT, String countryTXT, int customerID) 
    {
        try 
        {
            String tempUser = Log.fingCurrentUser();
            Statement statement = DBConnection.getConnection().createStatement();
            int CountryID = getCountryID(statement, countryTXT, tempUser);
            int CityID = getCityID(statement, CountryID,cityTXT, tempUser);
            int AddressID = getAddressID(statement, CityID,addressTXT,postalTXT, tempUser , phoneTXT);
            String query = 
                    "UPDATE customer "
                    + "SET addressid = '" + AddressID  + "' "
                    + ", customerName = '" + nameTXT + "' "
                    + ", active = 1"
                    + ", lastUpdate=NOW() "
                    + ", lastUpdateBy='"+tempUser+"' "
                    + "WHERE customerid = " + customerID;
            statement.executeUpdate(query);
        } 
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;    
    }
}
