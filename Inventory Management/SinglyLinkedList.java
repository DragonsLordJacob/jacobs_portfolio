/*
  Author: Jacob Hall-Burns
  Email: jhallburns2021@my.fit.edu
  Course: Algorithms and Data Structures, CSE2010
  Section: 2
  Description of this file: Custom SinglyLinkedList for HW1. When a node is added, it stores all the information for the node and then storts it first
                            by accending price and then by lexiographic order. In addition to this it has functions for: increasing the quantity of the item,
                            approving sales of items and adjusting the quantity after the fact, removing a product seller, and
                            displaying the current sellers for the corresponding product. 
 */
public class SinglyLinkedList {
   class Node {
      // Initilizes all the variables
      String productName;
      String company;
      double productPrice;
      double shippingPrice;
      double totalPrice;
      int quantity;

      Node next;
      // Method for the storing the Nodes information
      public Node(String productName, String company, double productPrice, double shippingPrice, double totalPrice, int quantity) {
         this.productName = productName;
         this.company = company;
         this.productPrice = productPrice;
         this.shippingPrice = shippingPrice;
         this.totalPrice = totalPrice;
         this.quantity = quantity;
      
         this.next = null;
      }
      // Prints out the specific node's information when called
      public void displayNode() {
         System.out.printf("%10s%14.2f%14.2f%11.2f%n", company, productPrice, shippingPrice, totalPrice);
      }
   }
   // Initilizes head
   public Node head = null;
   // Method for adding a node to a SinglyLinkedList
   // (We were instructed that offical test cases would not include making a node that already exists)
   public void addNode(String productName, String company, double productPrice, double shippingPrice, double totalPrice, int quantity) {
      Node newNode = new Node(productName, company, productPrice, shippingPrice, totalPrice, quantity);

      Node pointer = head;
      Node previous = null;
      // Finds the correct point to add the new node by totalPrice accendingly, and if tied alphabetically
      // If the list is empty, it skips this step and creates the head
      while (pointer != null && (pointer.totalPrice < newNode.totalPrice ||
            (pointer.totalPrice == newNode.totalPrice && pointer.company.compareTo(company) < 0))) {
         previous = pointer;
         pointer = pointer.next;
      }
      // If there is no head node, the newNode is stored as head
      if (previous == null) {
         newNode.next = head;
         head = newNode;
      } else { // The newNode's pointer is set the the point found in the while loop
         newNode.next = pointer;
         previous.next = newNode;
      }
   }
   // Method for removing a node from the SinglyLinkedList
   public void removeNode (String company) {
      Node pointer = head;
      Node previous = null;
      // Starts at index 0 of the link list and looks through it until it finds a matching node.company name
      // If the pointer = null (list is empty or there is no matching node.company), the while loop ends
      while (pointer != null && !pointer.company.equalsIgnoreCase(company)) {
         previous = pointer;
         pointer = pointer.next;
      }
      // Checking that the pointer is not null
      if (pointer != null) {
         // If the removedNode is the head, the next node becomes the head
         // Garbage collection takes care of the lost node
         if (pointer == head) {
            head = head.next; 
         } else { // Else, the node previous moves its pointer from 'pointer' to the node after pointer
                  // Garbage collection takes care of the lost node
            previous.next = pointer.next; 
         }
         System.out.println();
      } else { // If the pointer is null, this error is printer
         System.out.println("NonExistingSellerError");
      }
   }
   // Method for displaying all of the nodes within a given list
   public void displayNodes() {
      Node pointer = head;
      // While pointer is not null call the Node method for printing
      // a specific node and then change the pointer to the next node
      while(pointer != null) {
         pointer.displayNode();
         pointer = pointer.next;
      }
   }
   // Method for Increading the 'Inventory' (quantity) of a specific node
   public void IncreaseInventory(String productName, String company, int quantity) {
      Node pointer = head;
      // While the company name does not match, move to pointer forwards
      while (pointer != null && !pointer.company.equalsIgnoreCase(company)) {
         pointer = pointer.next;
      }
      // Adds the quantity added to the perviously held quantity
      pointer.quantity += quantity;
      // Prints out the action line
      System.out.printf("IncreaseInventory %s %s %d %d", productName, company, quantity, pointer.quantity);
      
   }
   // Method for the purchasing action
   public void CustomerPurchase(String productName, String company, int quantity) {
      Node pointer = head;
      Node previous = null;
      // While pointer is not empty and not equal to the variable company
      while (pointer != null && !pointer.company.equalsIgnoreCase(company)) {
         previous = pointer;
         pointer = pointer.next;
      }
      // Prints out the fact that a purchase request has been put in
      System.out.printf("CustomerPurchase %s %s %d", productName, company, quantity);
      // If there is not enough (quantity) for the purchase throw an error
      if (quantity > pointer.quantity) {
         // Prints out the error message at the end of the line
         System.out.println(" NotEnoughInventoryError");
      } else { // If there is enough inventory, proceed
               // Subtract the amount bought from the (quantity)
         pointer.quantity -= quantity;
         // Prints out the new (quantity) at the end of the line
         System.out.printf(" %d%n", pointer.quantity);
         // If the (quantity) equals zero, remove the node
         if (pointer.quantity == 0) {
            // Prints out a line saying that a depleted item is removed.
            System.out.printf("DepletedInventoryRemoveSeller %s %s%n", productName, company);
            // If the removed node is the head, set the new head
            // Garbage collection takes care of the removed node
            if (pointer == head) {
               head = head.next; 
            } else { // Else, set the previous nodes pointer to the 'pointer''s pointer and remove the node
                     // Garbage collection will take care of the removed node
               previous.next = pointer.next; 
            }
         }
      }
      
   }
}
