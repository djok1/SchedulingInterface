/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Djok
 */
public class Log 
{
    
    private static final String FILENAME = "log.txt";
    
    
    public static void log (String username, boolean success) 
    {
        try (FileWriter fw = new FileWriter(FILENAME, true); BufferedWriter bw = new BufferedWriter(fw); PrintWriter pw = new PrintWriter(bw)) 
        {
            pw.println(ZonedDateTime.now() + " " + username + (success ? " Success" : " Failure"));
        } 
        catch (IOException e) 
        {
            System.out.println("Log Error: " + e.getMessage());
        }
    }
    public static String fingCurrentUser()  
    {
        ObservableList<String> lines = FXCollections.observableArrayList();
        String user = "";
        try (FileReader fr = new FileReader(FILENAME);BufferedReader br = new BufferedReader(fr);) 
        {
            String line = br.readLine();
            while(line != null)
            {
                lines.add(line);
                line = br.readLine();
            }
            int size  = lines.size() - 1;
            String lastLine = lines.get(size);
            //2020-12-12T17:11:39.760-07:00[America/Denver] test Success
            int index1 = lastLine.lastIndexOf(']') +2;
            int index2 = lastLine.lastIndexOf("Success")-1;
            user = lastLine.substring(index1, index2);

        } 
        catch (IOException e) 
        {
            System.out.println("Log Error: " + e.getMessage());
        }
        return user;
    }
}
