/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.time.format.DateTimeFormatter;
import Models.AppointmentDB;
import Models.Customer;
import Models.UserDB;
import java.io.IOException;
import static java.lang.String.format;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import static java.time.temporal.TemporalQueries.zoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author Djok
 */
public class AppoitmentAddController implements Initializable 
{
    Customer selectedCustomer;
    @FXML
    private TextField customerNameTXT;
    
    @FXML 
    private ComboBox contactCMB;
    
    @FXML
    private ComboBox locationCMB;
    
    @FXML
    private DatePicker datePicker;
    
    @FXML
    private TextField startTXT;
    @FXML
    private TextField endTXT;
    
    @FXML  
    private TextField typeTXT;
    
    private final ObservableList<String> locations = FXCollections.observableArrayList("Utah", "New York", "Toronto","Oslo");
    private ObservableList<String> users = FXCollections.observableArrayList();
    //private final ObservableList<String> locations = (ObservableList<String>) ZoneId.getAvailableZoneIds();
    //MST
    private String open = "09:00";
    private String close = "17:00";
    private ZoneId timeZoneID;
    private ZonedDateTime  apoitmentStart;
    private ZonedDateTime  apointmentEnd; 
    private ZonedDateTime  opening;
    private ZonedDateTime  closeing;
    private DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
    /**
     * Initializes the controller class.
     */
            
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        users = UserDB.getUsers();
        contactCMB.setItems(users);
        locationCMB.setItems(locations);
        
    }    
    
    
    @FXML
    private void handleSaveBTN(ActionEvent event)
    {
        if(verifyDate())
        {
            try 
            {
                String tempLocation = locations.get(locationCMB.getSelectionModel().getSelectedIndex());
                String tempUser = users.get(contactCMB.getSelectionModel().getSelectedIndex());

                AppointmentDB.saveAppointment(selectedCustomer.getCustomerId(), typeTXT.getText(), tempUser,tempLocation , format.format(apoitmentStart),format.format(apointmentEnd));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/AppointmentsMain.fxml"));
                Parent AppoitmentsMainParent = loader.load();
                Scene AppoitmentsMainScene = new Scene (AppoitmentsMainParent);
                Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                window.setScene(AppoitmentsMainScene);
                window.show();
            } catch (IOException ex) 
            {
 
            }
        }
    }

    private boolean verifyDate()
    {
        timeZoneID = ZoneId.of("America/Denver");
        LocalDate appoitmentDate = datePicker.getValue();

        

        if(appoitmentDate.isBefore(LocalDate.now()))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Date");
            alert.setContentText("Please enter date in the future");
            alert.showAndWait();
            return false;
        }
        try
        {
            apoitmentStart =  ZonedDateTime.of(appoitmentDate,LocalTime.parse(startTXT.getText()),ZoneId.systemDefault());
            apointmentEnd = ZonedDateTime.of(appoitmentDate,LocalTime.parse(endTXT.getText()),ZoneId.systemDefault());
            opening = ZonedDateTime.of(appoitmentDate,LocalTime.parse(open),timeZoneID);
            closeing = ZonedDateTime.of(appoitmentDate,LocalTime.parse(close),timeZoneID);
        }
        catch (DateTimeParseException e)
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Time");
            alert.setContentText("Please enter time HH:MM useing 24 H format");
            alert.showAndWait();
            return false;
        }
        if(apoitmentStart.isBefore(opening) || apointmentEnd.isAfter(closeing))
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Time");
            alert.setContentText("Please enter time between 09:00 MST and 17:00 MST");
            alert.showAndWait();
            return false;
        }
        String tempUser = users.get(contactCMB.getSelectionModel().getSelectedIndex());
        apoitmentStart = apoitmentStart.withZoneSameInstant(ZoneId.of("UTC"));
        apointmentEnd = apointmentEnd.withZoneSameInstant(ZoneId.of("UTC"));
       if(!AppointmentDB.overlappingAppointment(tempUser,format.format(apoitmentStart),format.format(apointmentEnd)))    
       {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Time");
            alert.setContentText("Apoitments overlap");
            alert.showAndWait();
            return false;
       }
        return true;
    }


    @FXML
    private void handleCancelBTN(ActionEvent event)throws IOExpception, IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/AppointmentsMain.fxml"));
        Parent AppoitmentsMainParent = loader.load();
        Scene AppoitmentsMainScene = new Scene (AppoitmentsMainParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(AppoitmentsMainScene);
        window.show();
    }
    public void CustomerReciver(Customer SelectedCustoemr)
    {
        selectedCustomer = SelectedCustoemr;
        customerNameTXT.setText(selectedCustomer.getName());
    }
}
