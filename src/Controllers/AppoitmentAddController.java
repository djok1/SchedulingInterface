/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Djok
 */
public class AppoitmentAddController implements Initializable 
{
    Customer selectedCustomer;
    @FXML
    private TextField cutmerNameTXT;
    
    @FXML 
    private TextField contactTXT;
    
    @FXML
    private TextField locationTXT;
    
    @FXML
    private DatePicker datePicker;
    
    @FXML
    private TextField timeTXT;
    
    @FXML  
    private TextField typeTXT;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    
    
    
    @FXML
    private void handleSaveBTN(ActionEvent event)
    {
        
    }
    @FXML
    private void handleCancelBTN(ActionEvent event)
    {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void CustomerReciver(Customer SelectedCustoemr)
    {
        selectedCustomer = SelectedCustoemr;
    }
}
