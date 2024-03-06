/*
  Author: Jacob Hall-Burns
  Email: jacob.hallburns02@gmail.com
  Description of this file: Manages three products with with a custom SinglyLinkedLists containing sellers and the price/ quantity information.
                            The information is modified by parsing command phrases from the command line and assigning them to values that are then used in
                            the five (5) main methods. AddSeller, RemoveSeller, IncreaseInventory, CustomerPurchase, and DisplaySellerList.
                            Each time the different methods are executed it prints a line reflecting on what action was completed and what was modified in 
                            accordance with the HW1 documentation.  
 */
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;

public class InventoryManager {
   // Contains the executed code
   public static void main (final String [] args) throws IOException {

      // Constructs the SinglyLinkedLists for the three products
      final SinglyLinkedList appleIPhone = new SinglyLinkedList();
      final SinglyLinkedList earBuds = new SinglyLinkedList();
      final SinglyLinkedList keyboard = new SinglyLinkedList();

      // Constructs the ArrayList 'lists' to store the three lists and adds them in an order
      final ArrayList<SinglyLinkedList> lists = new ArrayList<SinglyLinkedList>();
      lists.add(appleIPhone);
      lists.add(earBuds);
      lists.add(keyboard);

      // Checks to make sure a file name is in the command line
      if (args.length < 1) {
         // Prints an error and exits the program if there is no file name
         System.out.println("Correct command line prompt: 'java HW1 [YourInputFile]' ");
         System.exit(0);
      }

      // Takes in the file name from the command line and creates a path
      final String inputFile = args[0];
      final Path path = Paths.get (inputFile);
      // Opens a Scanner that reads the file
      final Scanner in = new Scanner(path, "US-ASCII");

      // A try / finally loop to close the scanner when done
      try{
         // If the input file has a next line it will read it and process that information
         while (in.hasNextLine()) {
            // Captures the data in the file and splits it by spaces
            final String line = in.nextLine();
            final String[] tokens = line.split(" ");

            // Initilizes the actionName and productName for use
            final String actionName = tokens[0];
            final String productName = tokens[1];
         
            // Assigns all five (5) action inputs a number
            final int actionIndecator = desiredAction(actionName);
            // If the action name does not match, print this error message
            if (actionIndecator == 0) {
               System.out.printf("The action %s is an invalid action phrase.", actionName);
            } else { // If it matches procced
               // Assigns the three product names a number that corresponds to its spot in the ArrayList
               final int productIndicator = desiredProcuct(productName);
               // If the product name is not a valid name, print this error message
               if (productIndicator == 3) {
                  System.out.printf("This product %s is an invalid product name.", productIndicator);
               } else { // If it matches, procced
                  // Starts the action chain
                  // This method is a switch statement pointing to different methods
                  accessActionList(actionIndecator, productIndicator, tokens, lists);
               }
            }
         }
         // Closes the Scanner when all lines have been scanned.
      } finally {
         in.close();
      }
   }
   // Assigns each action phrase a number 1-5
   // Returns the current action phrases number
   public static int desiredAction(String actionName) {
      // Initilizes and provides the "bad" case
      int actionIndecator = 0;
      // If else statements assigning a number used in main
      if (actionName.equalsIgnoreCase("AddSeller")) {
         actionIndecator = 1;
      } else if (actionName.equalsIgnoreCase("RemoveSeller")) {
         actionIndecator = 2;
      } else if (actionName.equalsIgnoreCase("IncreaseInventory")) {
         actionIndecator = 3;
      } else if (actionName.equalsIgnoreCase("CustomerPurchase")) {
         actionIndecator = 4;
      } else if (actionName.equalsIgnoreCase("DisplaySellerList")) {
         actionIndecator = 5;
      }
      return actionIndecator;
   }
   // Assigns each product phrase a number 0-2
   // Returns the current product phrases number
   public static int desiredProcuct(String productName) {
      // Initilizes and provides the "bad" case
      int productIndicator = 3;
      // If else statements assigning a number used in main
      if (productName.equalsIgnoreCase("appleIPhone")) {
         productIndicator = 0;
      } else if (productName.equalsIgnoreCase("earBuds")) {
         productIndicator = 1;
      } else if (productName.equalsIgnoreCase("keyboard")) {
         productIndicator = 2;
      }
      return productIndicator;
   }



   // Switch Statement that will run the method for the coresponding action item
   public static void accessActionList(int actionIndecator, int productIndicator, String[] tokens, ArrayList<SinglyLinkedList> lists) {
      switch (actionIndecator) {
         case 1: 
            // Adds a new  node
            AddSeller(productIndicator, tokens, lists);
            return;
         case 2:
            // Removes a node
            RemoveSeller(productIndicator, tokens, lists);
            return;
         case 3:
            // Increases the Inventory of a node (updates node.quantity)
            IncreaseInventory(productIndicator, tokens, lists);
            return;
         case 4:
            // Checks to see if there is enough inventory to sell
            // If so, it removes that inventory (updates node.quantity)
            // If inventory falls to zero, it removes the node
            CustomerPurchase(productIndicator, tokens, lists);
            return;
         case 5:
            // Displays all of the nodes (sellers) in a SinglyLinkedList
            DisplaySellerList(productIndicator, tokens, lists);
            return;
      }
   }
   // Creates a new node for a company and inserts it in the correctly sorted spot
   public static void AddSeller(int productIndicator, String[] tokens, ArrayList<SinglyLinkedList> lists) {
      // Initilize the information from tokens[] to use in the printf and SinglyLinkedList method call
      final String company = tokens[2];
      final double productPrice = Double.parseDouble(tokens[3]);
      final double shippingPrice = Double.parseDouble(tokens[4]);
      final double totalPrice = productPrice + shippingPrice;
      final int quantity = Integer.parseInt(tokens[5]);
      final String productName = tokens[1];
      // Prints the action line
      System.out.printf("AddSeller %s %s %.2f %.2f %d", productName, company, productPrice, shippingPrice, quantity);
      // If the quantiy is less than or equal to zero, prints this error at the end of the line
      if (quantity <= 0) {
         System.out.println(" NonPositiveQuantityError");
      } else { // Otherwise, the line is ended and the new node is created.
         System.out.println();
         // Calls the SinglyLinkedList corrsponding to the (productIndicator) from the ArrayList lists
         // And executes the SinglyLinkedList method addNode with the information above
         lists.get(productIndicator).addNode(productName, company, productPrice, shippingPrice, totalPrice, quantity);
      }
   }
   // Removes the node from a SinglyLinkedList
   public static void RemoveSeller(int productIndicator, String[] tokens, ArrayList<SinglyLinkedList> lists) {
      // Initilize the information from tokens[] to use in the printf and SinglyLinkedList method call
      final String company = tokens[2];
      final String productName = tokens[1];
      // Prints the action line
      System.out.printf("RemoveSeller %s %s ", productName, company);
      // Calls the SinglyLinkedList corrsponding to the (productIndicator) from the ArrayList lists
      // And executes the SinglyLinkedList method removeNode with the information above
      lists.get(productIndicator).removeNode(company);
   }
   // Increases the inventory of a specific node (based on company name) from the coresponding list
   public static void IncreaseInventory(int productIndicator, String[] tokens, ArrayList<SinglyLinkedList> lists) {
      // Initilize the information from tokens[] to use in the printf and SinglyLinkedList method call
      final String company = tokens[2];
      final int quantity = Integer.parseInt(tokens[3]);
      final String productName = tokens[1];
      // Calls the SinglyLinkedList corrsponding to the (productIndicator) from the ArrayList lists
      // And executes the SinglyLinkedList method IncreaseInventory with the information above
      lists.get(productIndicator).IncreaseInventory(productName, company, quantity);
   }
   // Removes inventory from a specific node (based on company name) from the coresponding list 
   public static void CustomerPurchase(int productIndicator, String[] tokens, ArrayList<SinglyLinkedList> lists) {
      // Initilize the information from tokens[] to use in the printf and SinglyLinkedList method call
      final String company = tokens[2];
      final int quantity = Integer.parseInt(tokens[3]);
      final String productName = tokens[1];
      // Calls the SinglyLinkedList corrsponding to the (productIndicator) from the ArrayList lists
      // And executes the SinglyLinkedList method CustomerPurchase with the information above
      lists.get(productIndicator).CustomerPurchase(productName, company, quantity);
   }
   // Displays all the sellers for a specific list
   public static void DisplaySellerList(int productIndicator, String[] tokens, ArrayList<SinglyLinkedList> lists) {
      // Initilize the information from tokens[] to use in the printf and SinglyLinkedList method call
      final String productName = tokens[1];
      // Prints the action line
      System.out.printf("DisplaySellerList %s%n", productName);
      // Prints the display header
      System.out.printf("    seller  productPrice  shippingCost  totalCost%n");
      // Calls the SinglyLinkedList corrsponding to the (productIndicator) from the ArrayList lists
      // And executes the SinglyLinkedList method displayNodes with the information above
      lists.get(productIndicator).displayNodes();
   }
}
