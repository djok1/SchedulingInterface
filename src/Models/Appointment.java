/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalQueries;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Djok
 */
public class Appointment 
{
    private final SimpleIntegerProperty ID = new SimpleIntegerProperty();
    private final SimpleIntegerProperty custID = new SimpleIntegerProperty();
    private final SimpleStringProperty Start = new SimpleStringProperty();
    private final SimpleStringProperty End = new SimpleStringProperty();
    private final SimpleStringProperty Title = new SimpleStringProperty();
    private final SimpleStringProperty Description = new SimpleStringProperty();
    private final SimpleStringProperty Location = new SimpleStringProperty();
    private final SimpleStringProperty Contact = new SimpleStringProperty();
    private ZoneId timeZone;

    public Appointment(int id, int custid, String start, String end, String title, String description, String location, String contact) 
    {
        
        try
        {
            timeZone = ZoneId.of(location);
            setID(id);
            setCustID(custid);
            setStart(start);
            setEnd(end);
            setTitle(title);
            setDescription(description);
            setLocation(location);
            setContact(contact);
        }
        catch(Exception e)
        {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText("Invalid location");
        alert.showAndWait();
        }
        
    }
    //getters 
    public int getID() {
        return ID.get();
    }
    
    public int getCustID() {
        return custID.get();
    }
    
    public String getEnd() {
        return End.get();
    }
    
    public String getStart() {
        return Start.get();
    }
    
    public String getTitle() {
        return Title.get();
    }
    
    public String getDescription() {
        return Description.get();
    }
    
    public String getLocation() {
        return Location.get();
    }
    
    public String getContact() {
        return Contact.get();
    }
    
    public StringProperty getTitleProperty() {
        return this.Title;
    }
    
    public StringProperty getDescriptionProperty() {
        return this.Description;
    }
    
    public StringProperty getLocationProperty() {
        return this.Location;
    }
    
    public StringProperty getContactProperty() {
        return this.Contact;
    }

    
    public String get15Time() 
    {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"); 
 	LocalDateTime loacalDT = LocalDateTime.parse((CharSequence) this.Start, dateFormat);
        ZonedDateTime zoneDT = loacalDT.atZone(ZoneId.of("UTC"));
        ZoneId zoneID = ZoneId.systemDefault();
        ZonedDateTime utcDate = zoneDT.withZoneSameInstant(zoneID); 
        DateTimeFormatter tFormatter = DateTimeFormatter.ofPattern("kk:mm"); 
	LocalTime localTime = LocalTime.parse(utcDate.toString().substring(11,16), tFormatter);
        return localTime.toString();
    }
    //setters
    public void setID(int aptId) 
    {
        this.ID.set(aptId);
    }
    
    public void setCustID(int aptCustId) 
    {
        this.custID.set(aptCustId);
    }
    
    public void setEnd(String aptEnd) 
    {
        this.End.set(aptEnd);
    }
    
    public void setStart(String aptTimeStart) 
    {
        this.Start.set(aptTimeStart);
    } 
    
    public void setTitle(String aptTitle) 
    {
        this.Title.set(aptTitle);
    }
    
    public void setDescription(String aptDescription) 
    {
        this.Description.set(aptDescription);
    }
    
    public void setLocation(String aptLocation) 
    {
        this.Location.set(aptLocation);
    }
    
    public void setContact(String aptContact) 
    {
        this.Contact.set(aptContact);
    }
    
    public StringProperty getStartProperty() 
    {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"); 
 	LocalDateTime localDT = LocalDateTime.parse(this.Start.getValue(), dateFormat);
        ZonedDateTime zoneDT = localDT.atZone(ZoneId.of("UTC"));
        ZoneId zoneID = ZoneId.systemDefault();
        ZonedDateTime utcDate = zoneDT.withZoneSameInstant(zoneID); 
        StringProperty date = new SimpleStringProperty(utcDate.toLocalDateTime().toString());
        return date;
    }
    
    public StringProperty getEndProperty() 
    {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"); 
 	LocalDateTime localDT = LocalDateTime.parse(this.End.getValue(), dateFormat);
        ZonedDateTime zoneDT = localDT.atZone(ZoneId.of("UTC"));
        ZoneId zoneID = ZoneId.systemDefault();
        ZonedDateTime utcDate = zoneDT.withZoneSameInstant(zoneID); 
        StringProperty date = new SimpleStringProperty(utcDate.toLocalDateTime().toString());
        return date;
    }
    
    public LocalDate getDateOnly() 
    {
        Timestamp timeStamp = Timestamp.valueOf(this.Start.get());
        ZonedDateTime zoneDT;
        LocalDate localDate;
        zoneDT = timeStamp.toLocalDateTime().atZone(timeZone);
        localDate = zoneDT.toLocalDate();
        return localDate;
    }
    

//    public String getTimeOnly() 
//    {
//        Timestamp timeStamp = Timestamp.valueOf(this.Start.get());
//        ZonedDateTime zoneDT;
//        ZoneId zoneID;
//        LocalTime lt;
//        if(this.Location.get().equals("New York")) {
//            zoneID = ZoneId.of("America/New_York");
//            zoneDT = timeStamp.toLocalDateTime().atZone(zoneID);
//            lt = zoneDT.toLocalTime().minusHours(4);
//        } else if(this.Location.get().equals("Phoenix")) {
//            zoneID = ZoneId.of("America/Phoenix");
//            zoneDT = timeStamp.toLocalDateTime().atZone(zoneID);
//            lt = zoneDT.toLocalTime().minusHours(7);
//        } else {
//            zoneID = ZoneId.of("Europe/London");
//            zoneDT = timeStamp.toLocalDateTime().atZone(zoneID);
//            lt = zoneDT.toLocalTime().plusHours(1);
//        }
//        int rawH = Integer.parseInt(lt.toString().split(":")[0]);
//        if(rawH > 12) {
//            rawH -= 12;
//        }
//        String ampm;
//        if(rawH < 9 || rawH == 12) {
//            ampm = "PM";
//        } else {
//            ampm = "AM";
//        }
//        String time = rawH + ":00 " + ampm;
//        return time;
//    }
}
