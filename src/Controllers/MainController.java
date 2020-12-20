/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Appointment;
import Models.AppointmentDB;
import Models.Customer;
import Models.CustomerDB;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Djok
 */
public class MainController implements Initializable 
{

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        Appointment appointment = AppointmentDB.Alert15Min();
        if(appointment != null) 
        {
            Customer customer = CustomerDB.getCustomer(appointment.getCustID());
            String text = String.format("You have a %s appointment with %s at %s", appointment.getDescription(), customer.getName(), appointment.get15Time());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointment Reminder");
            alert.setHeaderText("Appointment Within 15 Minutes");
            alert.setContentText(text);
            alert.showAndWait();
        } 
    }
   
    @FXML
    public void handleAppointmentBTN() 
    {
        try 
        {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/Views/AppointmentsMain.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } 
        catch (IOException e) 
        {
            System.out.println("Appointment Main Error: " + e.getMessage());
        }
    }
    
    @FXML
    public void handleCustomerBTN() 
    {
        try 
        {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/Views/CustomerMain.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } 
        catch (IOException e) 
        {
            System.out.println("Customer Main Error: " + e.getMessage());
        }
    }
    @FXML
    public void handleReportsBTN() 
    {
        try 
        {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/Views/ReportMain.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } 
        catch (IOException e) 
        {
            System.out.println("Report Main Error: " + e.getMessage());
        }
    }
    
    @FXML
    public void handleLogsBTN() 
    {
        File file = new File("log.txt");
        if(file.exists()) 
        {
            if(Desktop.isDesktopSupported()) 
            {
                try 
                {
                    Desktop.getDesktop().open(file);
                } 
                catch (IOException e) 
                {
                    System.out.println("Error Opening Log File: " + e.getMessage());
                }
            }
        }
    }    
    
}
