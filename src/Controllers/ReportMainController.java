/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Utils.DBConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Djok
 */
public class ReportMainController implements Initializable {

    @FXML
    private TextArea CustTotalTXT;
    @FXML
    private TextArea consultantTXT;
    @FXML
    private TextArea aptTypeTXT;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        custTotalApoitments();
        consultant();
        apoitmentType();
    }    

    @FXML
    private void handleBackBTN(ActionEvent event) 
    {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();  
    }
    
    public void apoitmentType() 
    {
        try 
        {
            StringBuilder apoitmentTypeText;
            try (Statement statement = DBConnection.getConnection().createStatement()) {
                String query = 
                        "SELECT description, MONTHNAME(start) as 'Month', COUNT(*) as 'Total' "
                        + "FROM appointment "
                        + "GROUP BY description, MONTH(start), Month";
                ResultSet results = statement.executeQuery(query);
                apoitmentTypeText = new StringBuilder();
                apoitmentTypeText.append(String.format("%1$-55s %2$-55s %3$s \n", "Month", "Appointment Type", "Total"));
                apoitmentTypeText.append(String.join("", Collections.nCopies(110, "-")));
                apoitmentTypeText.append("\n");
                while(results.next()) {
                    apoitmentTypeText.append(String.format("%1$-55s %2$-60s %3$d \n",
                            results.getString("Month"), results.getString("description"), results.getInt("Total")));
                }
            }
            aptTypeTXT.setText(apoitmentTypeText.toString());
        } 
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
        }
    }    
    
    public void consultant() {
        try 
        {
            StringBuilder consultantText;
            try (Statement statement = DBConnection.getConnection().createStatement()) 
            {
                String query = "SELECT appointment.contact, appointment.description, customer.customerName, start, end "
                        + "FROM appointment JOIN customer ON customer.customerId = appointment.customerId "
                        + "GROUP BY appointment.contact, MONTH(start), start, end, customer.customerName, appointment.description";
                ResultSet results = statement.executeQuery(query);
                consultantText = new StringBuilder();
                consultantText.append(String.format("%1$-25s %2$-25s %3$-25s %4$-25s %5$s \n",
                        "Consultant", "Appointment", "Customer", "Start", "End"));
                consultantText.append(String.join("", Collections.nCopies(110, "-")));
                consultantText.append("\n");
                while(results.next()) 
                {
                    consultantText.append(String.format("%1$-25s %2$-25s %3$-25s %4$-25s %5$s \n",
                            results.getString("contact"), results.getString("description"), results.getString("customerName"),
                            results.getString("start"), results.getString("end")));
                }
            }
            consultantTXT.setText(consultantText.toString());
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }
    
    public void custTotalApoitments() 
    {
        try 
        {
            Statement statement = DBConnection.getConnection().createStatement();
            String query = "SELECT customer.customerName, COUNT(*) as 'Total' "
                    + "FROM customer JOIN appointment "
                    + "ON customer.customerId = appointment.customerId GROUP BY customerName";
            ResultSet results = statement.executeQuery(query);
            StringBuilder custTotalApoitmentsText = new StringBuilder();
            custTotalApoitmentsText.append(String.format("%1$-65s %2$-65s \n", "Customer", "Total Appointments"));
            custTotalApoitmentsText.append(String.join("", Collections.nCopies(110, "-")));
            custTotalApoitmentsText.append("\n");
            while(results.next()) 
            {
                custTotalApoitmentsText.append(String.format("%1$s %2$65d \n", 
                    results.getString("customerName"), results.getInt("Total")));
            }
            statement.close();
            CustTotalTXT.setText(custTotalApoitmentsText.toString());
        } 
        catch (SQLException e) 
        {
            System.out.println("SQLExcpetion: " + e.getMessage());
        }
    }
    

    
}
