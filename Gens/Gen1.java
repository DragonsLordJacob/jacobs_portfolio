package Gens;

import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.IOException;

public class Gen1 {
   public static void main (final String args[]) throws IOException {
      // To compare profiles you need to put them in a text file and run this program with the text file
      if (args.length < 1) {
         System.out.println("Program requires a .txt file to be passed in through the command line");
         return;
      }
      if (args.length > 1) {
         System.out.println("There should only be one .txt file being passed in......");
         return;
      }
      String profilesToCompare = args[0];
      final ArrayList<String> listOfProfiles = new ArrayList<String>();
      final ArrayList<String> masterList = new ArrayList<String>();
      final ArrayList<ProfileInfo> listOfProfilesGames = new ArrayList<ProfileInfo>();
      Path path = Paths.get(profilesToCompare);
      Scanner txtFileToScan = new Scanner(path, "US-ASCII");
      try {
         while (txtFileToScan.hasNextLine()) {
            String profile = txtFileToScan.nextLine();
            listOfProfiles.add(profile);
         }
         constructGameLists(listOfProfilesGames, listOfProfiles);
         findMutualGames(listOfProfilesGames, masterList);
         printMasterList(listOfProfiles, masterList);
      }
      finally {
         txtFileToScan.close();
      }
   }
   public static void constructGameLists(ArrayList<ProfileInfo> listOfProfilesGames, ArrayList<String> listOfProfiles) throws IOException {
      for (int i = 0; i < listOfProfiles.size(); i++) {
         String profileID = listOfProfiles.get(i);
         Path steamPath = Paths.get(profileID);
         Scanner readSteamGames = new Scanner(steamPath, "US-ACSII");
         try {
            while (readSteamGames.hasNextLine()) {
               // Code from here: Need to find the list of games on a steam profile
            }
         }
         finally {
            readSteamGames.close();
         }
      }
   }
   public static void findMutualGames(ArrayList<ProfileInfo> listOfProfilesGames, ArrayList<String> masterList) {
      masterList = listOfProfilesGames.get(0).profileGames;
      for (int i = 1; i < listOfProfilesGames.size() - 1; i++) {
         for (String game : listOfProfilesGames.get(i).profileGames) {
            if (masterList.contains(game)) {
               // Nothing :)
            } else {
               for (int j = 0; i < masterList.size(); j++) {
                  if (masterList.get(j).equals(game)) {
                     masterList.remove(j);
                  }
               }
            }
         }
      }
   }
   public static void printMasterList(ArrayList<String> listOfProfiles, ArrayList<String> masterList) {
      // Header
      for (String profileID : listOfProfiles) {
         System.out.println(profileID);
      }
      // Space Between Header and MasterList 
      for (int i = 0; i < 3; i++) {
         System.out.println();
      }
      System.out.println("Mutual Games: ");
      for (String mutualGame : masterList) {
         System.out.println(mutualGame);
      }
   }
}
class ProfileInfo {
   String profileID;
   ArrayList<String> profileGames;
   ProfileInfo(String profileID, ArrayList<String> profileGames) {
      this.profileID = profileID;
      this.profileGames = profileGames;
   }
}
