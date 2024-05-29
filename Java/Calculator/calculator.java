package Java.Calculator;

/*
 * Author: Jacob Hall-Burns
 * Email: jacob.hallburns02@gmail.com
 * Date Started: 20240528 21:58
 * Last Update Submitted: 20240528 22:29
 * Time Spent: 31 minutes
 * 
 * Project Title: Calculator Project
 * Project Goal: The goal of this calculator project is to brush up on my basic Java skills, get familiar with making GUI's, and work on making a useful tool.
 * Overarching: Have fun and make a memory safe, light weight calculator for all sorts of uses
 * Step 1: Emplementing four funtion  calculator usage.
 * Step 2: Setting up a good GUI for entering basic equations
 * Step 3: Set up basic exponential, etc functions
 * Step 4: Add graphing capabilities
 * Step 5: Allow the user to create their own variables to assign vaules to for easy use
 * Step 6: Start creating specific formats for common hard equations in math, science and engineering classes
 * 
 * Project Details: Currently in Step 1.
 */
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;

public class calculator {
   // Look up how to use Set again
   public Set<String> operators = new Set<String> ("+", "-", "/", "*"); 
   class OutCome {
      String input;
      String output;
      OutCome (String input, String output) {

      }
      public void setInput(String input) {
         this.input = input;
      }
      public void setOutput(String output) {
         this.output = output;
      }
      public String getInput() {
         return input;
      }
      public String getOutput() {
         return output;
      }
   }
   public static void main (String[] args) throws FileNotFoundException {
      final String fileName = args[0];
      final File file = new File(fileName);
      if (file.isFile()) {
         Scanner readFile = new Scanner(file, "US-ASCII");
         while (readFile.hasNextLine()) {
            String line = readFile.nextLine();
            // Set this as the input for a new OutCome

         }
      } else {
         return;
      }
   }
}
