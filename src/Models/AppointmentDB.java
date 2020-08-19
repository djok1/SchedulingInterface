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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 *
 * @author Djok
 */
public class AppointmentDB 
{
    
    
    
    
    public static Appointment Alert15Min() 
    {
        Appointment appointment;
        LocalDateTime now = LocalDateTime.now();
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime zdt = now.atZone(zid);
        LocalDateTime ldt = zdt.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime ldt2 = ldt.plusMinutes(15);
        String user = UserDB.getCurrentUser().getUsername();
        try 
        {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE start BETWEEN '" + ldt + "' AND '" + ldt2 + "' AND " + "contact='" + user + "'";
            ResultSet results = statement.executeQuery(query);
            if(results.next()) 
            {
                appointment = new Appointment(results.getInt("appointmentId"), results.getInt("customerId"), results.getString("start"), results.getString("end"), results.getString("title"), results.getString("description"), results.getString("location"), results.getString("contact"));
                return appointment;
            }
        } 
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;
    }
}
