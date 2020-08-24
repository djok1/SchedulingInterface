/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Models.UserDB;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Djok
 */
public class LoginFormController implements Initializable 
{
    
    
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
    private String errorText;
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        Locale locale = Locale.getDefault(); 
        rb = ResourceBundle.getBundle("Lang/LoginForm", locale);
        usernameLBL.setText(rb.getString("username"));
        passwordLBL.setText(rb.getString("password"));
        loginBTN.setText(rb.getString("login"));
        mainLBL.setText(rb.getString("message"));
        languageLBL.setText(rb.getString("language"));
        titleLBL.setText(rb.getString("title"));
        errorText = rb.getString("errortext");
    }    
    @FXML
    public void handleLogin(ActionEvent event) throws IOException 
    {
        String username = usernameTXT.getText();
        String password = passwordTXT.getText();
        boolean validUser = UserDB.login(username, password);
        if(validUser)
        {
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/Views/Main.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            mainLBL.setText(errorText);
        }
    }
}
