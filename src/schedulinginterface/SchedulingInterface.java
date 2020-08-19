package schedulinginterface;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Utils.DBConnection;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Djok
 */
public class SchedulingInterface extends Application {
    
    @Override
    public void start(Stage stage) throws Exception 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/LoginForm.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /** 
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        DBConnection.startConnection();
        launch(args);
        DBConnection.closeConnection();
    }
    
}
