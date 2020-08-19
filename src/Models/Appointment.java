/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Djok
 */
public class Appointment 
{
    private final int ID;
    private final int custID;
    private final String Start;
    private final String End;
    private final String Title;
    private final String Description;
    private final String Location;
    private final String Contact;

    public Appointment(int id, int custid, String start, String end, String title, String description, String location, String contact) 
    {
        ID = id;
        custID = custid;
        Start = start;
        End = end;
        Title = title;
        Description = description;
        Location = location;
        Contact = contact;
    }
    public int getCustomerID()
    {
        return custID;
    }
    public String getDescription()
    {
        return Description; 
    }
    
    
    
    
    
    public String get15Time() 
    {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"); 
 	LocalDateTime ldt = LocalDateTime.parse(this.Start, df);
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime utcDate = zdt.withZoneSameInstant(zid); 
        DateTimeFormatter tFormatter = DateTimeFormatter.ofPattern("kk:mm"); 
	LocalTime localTime = LocalTime.parse(utcDate.toString().substring(11,16), tFormatter);
        return localTime.toString();
    }
}
