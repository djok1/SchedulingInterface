/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Djok
 */
public class LoginFormController implements Initializable {
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @FXML
    private Label titleLBL;
    @FXML
    private Label usernameLBL;    
    @FXML
    private TextField usernameTXT;    
    @FXML
    private Label passwordLBL;    
    @FXML
    private PasswordField passwordTXT;    
    @FXML
    private Button loginBTN;
    @FXML
    private Label mainLBL;
    @FXML
    private Label languageLBL;
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    
    @FXML
    public void handleLogin(ActionEvent event) throws IOException 
    {
        
    }
}
