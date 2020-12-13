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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    
        public static ObservableList<Appointment> getMonthlyAppointments (int id) 
        {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Appointment appointment;
        LocalDate begin = LocalDate.now();
        LocalDate end = LocalDate.now().plusMonths(1);
        try 
        {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE customerId = '" + id + "' AND " + 
                "start >= '" + begin + "' AND start <= '" + end + "'"; 
            ResultSet results = statement.executeQuery(query);
            while(results.next()) 
            {
                appointment = new Appointment(results.getInt("appointmentId"), results.getInt("customerId"), results.getString("start"),
                    results.getString("end"), results.getString("title"), results.getString("description"),
                    results.getString("location"), results.getString("contact"));
                appointments.add(appointment);
            }
            statement.close();
            return appointments;
        } 
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
    public static ObservableList<Appointment> getWeeklyAppoinments(int id) 
    {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        Appointment appointment;
        LocalDate begin = LocalDate.now();
        LocalDate end = LocalDate.now().plusWeeks(1);
        try 
        {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT * FROM appointment WHERE customerId = '" + id + "' AND " + 
                "start >= '" + begin + "' AND start <= '" + end + "'";
            ResultSet results = statement.executeQuery(query);
            while(results.next()) 
            {
                appointment = new Appointment(results.getInt("appointmentId"), results.getInt("customerId"), results.getString("start"),
                    results.getString("end"), results.getString("title"), results.getString("description"),
                    results.getString("location"), results.getString("contact"));
                appointments.add(appointment);
            }
            statement.close();
            return appointments;
        } 
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
    
    public static boolean deleteAppointment(int id) 
    {
        try 
        {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "DELETE FROM appointment WHERE appointmentId = " + id;
            int update = statement.executeUpdate(query);
            if(update == 1) 
            {
                return true;
            }
        } 
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }
    
    public static boolean overlappingAppointment(String contact, String start, String end) 
    {
        try 
        {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = 
                    "SELECT appointmentId, start, end, contact "
                    + "FROM appointment WHERE (start BETWEEN '" + start + "'AND'" + end + "' "
                    + "OR end BETWEEN '" + start + "'AND'" + end + "')" 
                    + "AND contact = '" + contact + "'";
            ResultSet results = statement.executeQuery(query);
            int rowcount = 0;
            while(results.next())
            {
                rowcount++;
            }
            if(rowcount != 0) 
            {
                statement.close();
                return false;
            } 
            else 
            {
                statement.close();
                return true;
            }
        } 
        catch (SQLException e) 
        {
            System.out.println("SQLExcpection: " + e.getMessage());
            return true;
        }
    }

    
    public static boolean saveAppointment(int id, String type, String contact, String location, String start, String end) {
        try 
        {
            String tempUser = Log.fingCurrentUser();
            Statement statement = DBConnection.getConnection().createStatement();
            String query = 
                "INSERT INTO appointment SET customerId='" + id +"', userId='" + findUserID(tempUser) + "', title='" + "NA" + "', description='" +
                type + "', contact='" + contact + "', type='" + "test"+ "', location='" + location + "', start='" + start + "', end='" + 
                end + "', url='', createDate=NOW(), createdBy='" + tempUser +  "', lastUpdate=NOW(), lastUpdateBy='"+tempUser+"'";
            int update = statement.executeUpdate(query);
            if(update == 1) {
                return true;
            }
        } 
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }
    
    private static String findUserID(String user) throws SQLException
    {    
        String id = "";
        Statement statement = DBConnection.getConnection().createStatement();
        String query = "SELECT userId FROM user WHERE userName ='" + user + "'";
        ResultSet results = statement.executeQuery(query);
        
        while(results.next()) 
            {
                id = String.valueOf(results.getInt("userId"));
            }
        return id;
    }
}
