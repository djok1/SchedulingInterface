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
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

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
    
    private Customer selectedCustomer;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
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
    public void handleAddBTN()
    {
        if(customerTBL.getSelectionModel().getSelectedItem() != null) 
        {
            selectedCustomer = customerTBL.getSelectionModel().getSelectedItem();
        } 
        else 
        {
            return;
        }
        /*Dialog<ButtonType> dialog = new Dialog();
        dialog.initOwner(AppointmentsMain.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("AppointmentAdd.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            System.out.println("AppointmentAdd Error: " + e.getMessage());
        }
        ButtonType save = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(save);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        AppointmentAddController controller = fxmlLoader.getController();
        controller.populateCustomerName(selectedCustomer.getCustomerName());
        dialog.showAndWait().ifPresent((response -> {
            if(response == save) {
                if(controller.handleAddAppointment(selectedCustomer.getCustomerId())) {
                    monthAptTable.setItems(AppointmentDB.getMonthlyAppointments(selectedCustomer.getCustomerId()));
                    weekAptTable.setItems(AppointmentDB.getWeeklyAppoinments(selectedCustomer.getCustomerId()));
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Add Appointment Error");
                    alert.setContentText(controller.displayErrors());
                    alert.showAndWait().ifPresent((response2 -> {
                        if(response2 == ButtonType.OK) {
                            handleAddButton();
                        }
                    }));
                }
            }
        }));*/
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
    
}
