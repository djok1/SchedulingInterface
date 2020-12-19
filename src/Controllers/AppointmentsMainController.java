/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Customer;
import Models.CustomerDB;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import Models.Appointment;
import Models.AppointmentDB;
import java.io.IOException;
import Controllers.AppoitmentAddController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Tab;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Djok
 */
public class AppointmentsMainController implements Initializable 
{
    @FXML
    private TableView<Customer> customerTBL;
    
    @FXML 
    private TableColumn<Customer, Integer> customerID;
    
    @FXML
    private TableColumn<Customer, String> customerName;
    
    @FXML
    private Label monthCustomerLabel;
    
    @FXML
    private TableView<Appointment> monthAptTable;
    
    @FXML
    private TableColumn<Appointment, String> monthDescription;
    
    @FXML
    private TableColumn<Appointment, String> monthContact;
    
    @FXML
    private TableColumn<Appointment, String> monthLocation;
    
    @FXML
    private TableColumn<Appointment, String> monthStart;
    
    @FXML
    private TableColumn<Appointment, String> monthEnd;
    
    @FXML
    private Label weekCustomerLabel;
    
    @FXML
    private TableView<Appointment> weekAptTable;
    
    @FXML
    private TableColumn<Appointment, String> weekDescription;
    
    @FXML
    private TableColumn<Appointment, String> weekContact;
    
    @FXML
    private TableColumn<Appointment, String> weekLocation;
    
    @FXML
    private TableColumn<Appointment, String> weekStart;
    
    @FXML
    private TableColumn<Appointment, String> weekEnd;
    @FXML 
    private Tab monthly;
    
    private Customer selectedCustomer;
    private Appointment selectedAppointment;
    private boolean isMonthly;
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        customerTBL.setItems(CustomerDB.getAllCustomers());
        monthDescription.setCellValueFactory(cellData -> 
        {
            return cellData.getValue().getDescriptionProperty();
        });
        monthContact.setCellValueFactory(cellData -> 
        {
            return cellData.getValue().getContactProperty();
        });
        monthLocation.setCellValueFactory(cellData ->
        {
            return cellData.getValue().getLocationProperty();
        });
        monthStart.setCellValueFactory(cellData ->
        {
            return cellData.getValue().getStartProperty();
        });
        monthEnd.setCellValueFactory(cellData -> 
        {
            return cellData.getValue().getEndProperty();
        });
        weekDescription.setCellValueFactory(cellData -> 
        {
            return cellData.getValue().getDescriptionProperty();
        });
        weekContact.setCellValueFactory(cellData -> 
        {
            return cellData.getValue().getContactProperty();
        });
        weekLocation.setCellValueFactory(cellData -> 
        {
            return cellData.getValue().getLocationProperty();
        });
        weekStart.setCellValueFactory(cellData -> 
        {
            return cellData.getValue().getStartProperty();
        });
        weekEnd.setCellValueFactory(cellData -> 
        {
            return cellData.getValue().getEndProperty();
        });
    }
    

    @FXML
    public void handleCustomerClick(MouseEvent event) 
    {
        selectedCustomer = customerTBL.getSelectionModel().getSelectedItem();
        int id = selectedCustomer.getCustomerId();
        monthCustomerLabel.setText(selectedCustomer.getName());
        weekCustomerLabel.setText(selectedCustomer.getName());
        monthAptTable.setItems(AppointmentDB.getMonthlyAppointments(id));
        weekAptTable.setItems(AppointmentDB.getWeeklyAppoinments(id));
    }
    
    @FXML
    public void handleModifyButton(ActionEvent event) 
    {
        if(monthly.isSelected()) 
        {
            if(monthAptTable.getSelectionModel().getSelectedItem() != null) 
            {
                selectedAppointment = monthAptTable.getSelectionModel().getSelectedItem();
            } else 
            {
                return;
            }
        } 
        else
        {
            if(weekAptTable.getSelectionModel().getSelectedItem() != null) 
            {
                selectedAppointment = weekAptTable.getSelectionModel().getSelectedItem();
            } 
            else 
            {
                return;
            }
        }
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ApointmentModify.fxml"));
            Parent ApointmentModifyParent = loader.load();
            Scene AppointmenModifyScene = new Scene (ApointmentModifyParent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            ApointmentModifyController AppointmenModify = loader.getController();
            AppointmenModify.Reciver(selectedCustomer,selectedAppointment);


            window.setScene(AppointmenModifyScene);
            window.show();
        }
        catch (IOException ex) 
        {
            
        }

    }
        
    @FXML
    public void handleDeleteButton() 
    {
        if(monthly.isSelected()) 
        {
            isMonthly = true;
            if(monthAptTable.getSelectionModel().getSelectedItem() != null) 
            {
                selectedAppointment = monthAptTable.getSelectionModel().getSelectedItem();
            } else 
            {
                return;
            }
        }
        else 
        {
            isMonthly = false;
            if(weekAptTable.getSelectionModel().getSelectedItem() != null) 
            {
                selectedAppointment = weekAptTable.getSelectionModel().getSelectedItem();
            } 
            else 
            {
                return;
            }
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Appointment Record");
        alert.setContentText("Delete Appointment?");
        alert.showAndWait().ifPresent((response -> 
        {
            if(response == ButtonType.OK) 
            {
                AppointmentDB.deleteAppointment(selectedAppointment.getID());
                if(isMonthly) 
                {
                   monthAptTable.setItems(AppointmentDB.getMonthlyAppointments(selectedCustomer.getCustomerId())); 
                } 
                else 
                {
                    weekAptTable.setItems(AppointmentDB.getWeeklyAppoinments(selectedCustomer.getCustomerId()));
                }
            }
        }));
    }
    @FXML 
    public void handleCloseBTN(MouseEvent event)
    {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void handleAddBTN(ActionEvent event) throws IOExpception, IOException
    {
       if(customerTBL.getSelectionModel().getSelectedItem() != null) 
        {
            selectedCustomer = customerTBL.getSelectionModel().getSelectedItem();
        } 
        else 
        {
            return;
        }


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/AppoitmentAdd.fxml"));
            Parent AppoitmentAddParent = loader.load();
            Scene AppointmentAddScene = new Scene (AppoitmentAddParent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            AppoitmentAddController AppointmentAdd = loader.getController();
            AppointmentAdd.CustomerReciver(selectedCustomer);


            window.setScene(AppointmentAddScene);
            window.show();
        

    }

}
