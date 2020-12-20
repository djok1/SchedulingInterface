/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Customer;
import Models.CustomerDB;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Djok
 */
public class CustomerMainController implements Initializable 
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
        customerName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        customerTBL.setItems(CustomerDB.getAllCustomers());
    }
    @FXML
    public void handleAddBTN(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/CustomerAdd.fxml"));
        Parent CustomerAddParent = loader.load();
        Scene CustomerAddScene = new Scene (CustomerAddParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(CustomerAddScene);
        window.show();
    }
    @FXML
    public void handleModifyBTN(ActionEvent event) throws IOException
    {
        if(customerTBL.getSelectionModel().getSelectedItem() != null) 
        {
        selectedCustomer = customerTBL.getSelectionModel().getSelectedItem();
        } 
        else 
        {
            return;
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/CustomerModify.fxml"));
        Parent CustomerModifyParent = loader.load();
        Scene AppointmenModifyScene = new Scene (CustomerModifyParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        CustomerModifyController CustomerModify = loader.getController();
        CustomerModify.Reciver(selectedCustomer);


        window.setScene(AppointmenModifyScene);
        window.show();
    }
    @FXML
    public void handleDeleteBTN()
    {
        if(customerTBL.getSelectionModel().getSelectedItem() != null) 
        {
        selectedCustomer = customerTBL.getSelectionModel().getSelectedItem();
        } 
        else 
        {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Customer Record");
        alert.setContentText("Delete Customer: " + selectedCustomer.getName() + " ?");
        alert.showAndWait().ifPresent((response -> 
        {
            if(response == ButtonType.OK) 
            {
                CustomerDB.deleteCustomer(selectedCustomer.getCustomerId());
                customerTBL.setItems(CustomerDB.getAllCustomers());
            }
        }));
    }
    @FXML
    public void handleCloseBTN(ActionEvent event) 
    {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }    
    
}
