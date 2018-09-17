/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexicalanalysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

/**
 *
 * @author aftab
 */
 public class MyFileWritter
{
    static File file;
    static FileWriter fileWriter;
    static BufferedWriter myWriter;
   
     public MyFileWritter(String path)
    {
        String fullPath = path;
        file = new File(fullPath);
  
         try
        { 
            fileWriter = new FileWriter(file);
        }
         catch (IOException ex)
        {
             JOptionPane.showMessageDialog(lexicalanalysis.LexicalAnalysis.frame, ex, "ERROR", ERROR_MESSAGE);
        }
        
        myWriter = new BufferedWriter(fileWriter); // buffered Writter aik sath puri line likh sakta hai
    }
    
     public void write(String toWrite) throws IOException
    {
         myWriter.write(toWrite);
         myWriter.newLine();
    }
     
     public void shutIt() throws IOException
    {
         myWriter.close();
         fileWriter.close();
    }
}
