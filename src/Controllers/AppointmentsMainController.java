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
        
    }
    @FXML
    public void handleCustomerClick(MouseEvent event) {
        selectedCustomer = customerTBL.getSelectionModel().getSelectedItem();
        int id = selectedCustomer.getCustomerId();
        monthCustomerLabel.setText(selectedCustomer.getName());
        weekCustomerLabel.setText(selectedCustomer.getName());
        monthAptTable.setItems(AppointmentDB.getMonthlyAppointments(id));
        weekAptTable.setItems(AppointmentDB.getWeeklyAppoinments(id));
    }
    
}
