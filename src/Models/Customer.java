/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Djok
 */
public class Customer 
{
    private int ID;
    private String Name;
    private String Address;
    private String City;
    private String Zip;
    private String Phone;

    public Customer(){};
    public Customer(int id, String name, String address, String city, String zip, String phone) 
    {
        ID = id;
        Name = name;
        Address = address;
        City = city;
        Zip = zip;
        Phone = phone;
    }

    Customer() {}
    
    
    
    
    public void setName(String name)
    {
        Name = name;
    }
    
    
    public String getName()
    {
        return Name;
    }

    public int getCustomerId() 
    {
        return ID;
    }
}
