/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Djok
 */
public class CustomerAddController implements Initializable 
{
    @FXML
    private TextField nameTXT;  
    @FXML
    private TextField addressTXT;  
    @FXML
    private TextField cityTXT;
    @FXML
    private TextField counttryTXT;
    @FXML
    private TextField postalTXT;
    @FXML
    private TextField phoneTXT;

    /**
     * Initializes the controller class.
     */
    
    String error;
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    
    @FXML
    private void handleSaveBTN(ActionEvent event)
    {
        error = "";
        Validate();
        if("".equals(error))
        {
            
        }
        else
        {
            
        }
    }
    @FXML
    private void handleCancelBTN(ActionEvent event)
    {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    private void Validate()
    {
        //upon reading looks like 5 didgits is the shortest personl use phone number in the solomon islands
        if(phoneTXT.getText().replace("-","").matches("[0-9]+") && phoneTXT.getText().length() >= 5)
        {
            if(!nameTXT.getText().isEmpty() && !addressTXT.getText().isEmpty() && !cityTXT.getText().isEmpty() && !counttryTXT.getText().isEmpty() && !postalTXT.getText().isEmpty())
            {
                CustomerDB.saveCustomer(nameTXT, addressTXT, cityTXT, counttryTXT, postalTXT);
            }
            else
            {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("All boxes must be filled out");
                alert.showAndWait();
            }
        }
        else
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Please enter valid phone number");
            alert.showAndWait();
        }
    }
    
    
//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setTitle(titleBar);
//        alert.setHeaderText(headerMessage);
//        alert.setContentText("it worked");
//        alert.showAndWait();
}
