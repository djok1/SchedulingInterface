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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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
    }
    
    @FXML
    public void handleAddBTN()
    {
        
    }
    @FXML
    public void handleCustomerClick(MouseEvent event) {
        selectedCustomer = customerTBL.getSelectionModel().getSelectedItem();
        int id = selectedCustomer.getCustomerId();
//        monthCustomerLabel.setText(selectedCustomer.getCustomerName());
//        weekCustomerLabel.setText(selectedCustomer.getCustomerName());
//        monthAptTable.setItems(AppointmentDB.getMonthlyAppointments(id));
//        weekAptTable.setItems(AppointmentDB.getWeeklyAppoinments(id));
    }
    
}
