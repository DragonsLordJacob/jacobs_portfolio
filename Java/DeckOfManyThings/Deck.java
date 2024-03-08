package Java.DeckOfManyThings;

import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Collections;

public class Deck {
   public static void main (final String args[]) throws FileNotFoundException {
      ArrayList<CardInfo> cardRoster = new ArrayList<CardInfo>();
      Scanner promptResponse = new Scanner(System.in, "US-ASCII");
      try {
         constructCardRoster(cardRoster);
         ChooseDeckSize(cardRoster, promptResponse);
         boolean keepGoing = true;
      
         if (cardRoster.size() < 1) {
            System.out.println("There are no cards left in the Deck of Many Things");
            return;
         } else {
            while (keepGoing) {
               // Ignores a 'N' response and keeps going as if you said yes
               DrawCards(cardRoster, promptResponse);
               WantToContinue(keepGoing, promptResponse);
            }
         }
      } finally {
         promptResponse.close();
      }
      
      
   }
   // works
   public static void constructCardRoster(ArrayList<CardInfo> cardRoster) throws FileNotFoundException {
      File folder = new File("Java\\DeckOfManyThings\\CardsInformation");
      File[] listOfCards = folder.listFiles();
      for (File card : listOfCards) {
         if (card.isFile()) {
            Scanner readCard = new Scanner(card, "US-ASCII");
            try {
               String cardName = readCard.nextLine();
               String cardAction = readCard.nextLine();
               String cardDescription = readCard.nextLine();
               CardInfo cardInformation = new CardInfo(cardName, cardAction, cardDescription);
               cardRoster.add(cardInformation);
            } finally {
               readCard.close();
            }
            
         }
      }
   }
   // Works
   public static void ChooseDeckSize(ArrayList<CardInfo> cardRoster, Scanner promptResponse) {
      Collections.shuffle(cardRoster);
      System.out.println("What size Deck would you like to use: 13 or 22");
      boolean correctResponse = false;
      while (!correctResponse) {
         int response = promptResponse.nextInt();
         if (response == 13) {
            correctResponse = true;
            while (cardRoster.size() > 13) {
               cardRoster.remove(cardRoster.size() -1);
            }
         } else if (response == 22) {
            correctResponse = true;
            // Size Stays the Same
         } else {
            System.out.println("Wrong deck Size: Must be 13 or 22. Try again.");
         }
      }           
   }
   // Works
   public static void DrawCards(ArrayList<CardInfo> cardRoster, Scanner promptResponse) {
      System.out.println("How many cards would you like to draw? Avaliable Cards: " + cardRoster.size());
      boolean correctResponse = false;
      while (!correctResponse) {
         int response = promptResponse.nextInt();
         if (response > cardRoster.size()) {
            System.out.println("The number you wanted to draw was larger than the deck. Pick a number that is less than " + cardRoster.size());
         } else {
            correctResponse = true;
            for (int i = 0; i < response; i++) {
               // Up Next: How to run the code in [CardName.java] when the card is drawn.
               // Custom write the things that should happen for each card
                  // Examples: Removing the card, Exiting the program, rolling 'dice', choosing between two things
               System.out.println(cardRoster.get(i).actionToTake);
            }
         }
      }
   }
   // Broken
   public static void WantToContinue(boolean keepGoing, Scanner promptResponse) {
      System.out.println("Do you wish to continue? Y/N");
      String response = promptResponse.nextLine();
      boolean correctResponse = true;
      while (!correctResponse && keepGoing) {
         if (response.equalsIgnoreCase("Y")) {
            correctResponse = true;
            keepGoing = true;
         } else if (response.equalsIgnoreCase("N")) {
            correctResponse = false;
            keepGoing = false;
            return;
         } else {
            System.out.println("A correct response is 'Y' or 'N'. Try again.");
         }
      }
   }
}
// Works
class CardInfo {
   String cardName;
   String actionToTake;
   String informationToDisplay;
   CardInfo (String cardName, String actionToTake, String informationToDisplay) {
      this.cardName = cardName;
      this.actionToTake = actionToTake;
      this.informationToDisplay = informationToDisplay;
   }
}
