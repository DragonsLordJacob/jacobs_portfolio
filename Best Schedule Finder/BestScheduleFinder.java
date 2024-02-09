/*
  Author: Jacob Hall-Burns
  Email: jhallburns2021@my.fit.edu
  Course: Algorithms and Data Structures, CSE2010
  Section: 2
  Description of this file: This program takes in a file from the command line and parses that input into a course name and a list
                            of course times. Then, it recusivly makes each and every possible schedule involving the courses and 
                            times given, checking it against the best schedule. If the current schedule is better than the best
                            scheudle, the best schedule will be updated to the current schedule. Then the program will continue to
                            run until there are no more schedules to test against, and the best schedule possible will be printed out.
 */
import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.lang.OutOfMemoryError;

public class BestScheduleFinder {
    // Builds the schedule that will be used to compare against and ultimately print out at the end
    static Schedule bestSchedule = new Schedule();

    /*
     * This method takes the input from the Scanner and parces it into tokens using .split().
     * Then it adds the course names and course times to a new Course class node
     * Finally, it adds each node to the ArrayList of the Course class
     */
    public static void readInput(final Scanner in, final ArrayList<Course> courses) {
        while (in.hasNextLine()) {
            // Each line from the file is turned into a singluar String
            final String lineOfInput = in.nextLine();
            // Then it is parsed by spaces using .split(" ")
            final String[] tokens = lineOfInput.split(" ");
            // This makes sure there is at least the course name and one class time for each file
            if (tokens.length < 2) {
                throw new IllegalArgumentException("Invalid input format in line: " + lineOfInput);
            }
            // This adds the course name to the node Course newCourse
            final String courseName = tokens[0];
            final Course newCourse = new Course(courseName);
            // This itterates through the rest of the tokens and adds all the times for each
            // course to the node Course newCourse
            for (int i = 1; i < tokens.length; i++) {
                // This assigns each individual time to a variable and then 
                // adds it to newCourse using the addTimes method
                final String courseTime = tokens[i];
                newCourse.addTimes(courseTime);
            }
            // Adds the newCourse to an ArrayList of Courses
            courses.add(newCourse);
        }
    }

    /*
     * This method takes in the ArrayList of courses and prints the best course Schedule possible
     * As well as building the conflicting courses output
     */
    public static void printAnswer(final ArrayList<Course> courses) {
        // Prints the header for the courses that fit into one schedule
        System.out.println("---Course schedule---");
        // Iterates through all of the course names stored in the best schedule
        for (int i = 0; i < bestSchedule.courseNames.size(); i++) {
            // Prints out all the course names and the specific course time selected for the best schedule
            System.out.printf("%s %s %n", bestSchedule.courseNames.get(i), bestSchedule.timeSlots.get(i));
        }

        // Creates an array list for all of the conflicting output data
        ArrayList<String> conflictOutput = new ArrayList<String>();
        // For each statement that looks through all interations
        for (final Course course : courses) {
            // If the best Schedule does not contain the course name, add its infomation to the conflicting output
            if (!bestSchedule.courseNames.contains(course.courseName)) {
                // builds the course name into courseInfo
                StringBuilder courseInfo = new StringBuilder(course.courseName);
                // Adds the course time to courseInfo
                courseInfo.append(" ").append(String.join(" ", course.times));
                // Adds course info to the conflict output using . toString
                conflictOutput.add(courseInfo.toString());
            }
        }
        // Checks to see if the conflictOutput is empty
        final boolean noConflict = conflictOutput.isEmpty();
        // if it is not empty, we need to print the conflicting information
        if (!noConflict) {
            // Prints the header for the conflicting information
            System.out.println("---Courses with a time conflict---");
            // For each mapping each conflicting output to a string that is then printed
            for (String conflict : conflictOutput) {
                System.out.println(conflict);
            }
        }
    }

    // Checks to find the best course composition, and pushes the best schedule to wherever buildschedule is called
    public static void buildSchedule(final ArrayList<Course> courses, final int index, final Schedule currentSchedule) {
        // If the index (starts at 0 and iterates) is equal to the sice of the original ArrayList<Course> courses
        if (index == courses.size()) {
            // It then checks to see if currentSchedule is better than the current best schedule
            if (currentSchedule.isBetterSchedule(bestSchedule)) {
                // If the current Schedule is better, assign it to bestSchedule
                bestSchedule = new Schedule(currentSchedule);
            }
            // Return bestSchedule and end the recusion
            return;
        }
        // Creates the currentCourse Course class node and sets it ti the course in ArrayList<Course> courses at index
        final Course currentCourse = courses.get(index);

        // For each statement setting time to currentCourse's times and checks each for the following
        for (final String time : currentCourse.times) {
            // If the current schedule does not have a conflict with the current time
            if (!currentSchedule.hasConflict(time)) {
                // Create a new scheduele node set to currentSchedule
                final Schedule curSchedule = new Schedule(currentSchedule);
                // Use the addCourse function to add the course name and time
                curSchedule.addCourse(currentCourse.courseName, time);
                // run the recusion again with an iterated index
                buildSchedule(courses, index + 1, curSchedule);
            }
        }
        // Run the recusion again with an iterated index
        buildSchedule(courses, index + 1, currentSchedule);
    }

    
    /* Sets up the scanner, ArrayList<Course> courses, all the error handling, and runs the methods: 
     * readInput(Scanner, ArrayList<Course>), buildSchedule(ArrayList<Course>, index, Schedule node),
     * printAnswer(courses)
     */ 
    public static void main(final String[] args) {
        // Tests for catch errors while executing the code
        try {
            // If there is no input in the command line, ask for a command line argument and end
            if (args.length == 0) {
                throw new IllegalArgumentException("A command line argument is required");
            }
            // Opens the scanner to path for the first command line input
            final Scanner in = new Scanner(Paths.get(args[0]), "US-ASCII");
            // Initilizes the most important ArratList<Course> for the entire program
            final ArrayList<Course> courses = new ArrayList<Course>();
            // Runs the following methods, outlined above
            readInput(in, courses);
            buildSchedule(courses, 0, new Schedule());
            printAnswer(courses);
            // Closes the Scanner
            in.close();
            // Catches all of the standard errors
        } catch (NoSuchFileException e) {
            // Can't find the file name
            System.err.println("The specified input file does not exist: " + e.getMessage());
        } catch (OutOfMemoryError e) {
            // Issue with the program/ input causing a stack overflow
            System.err.println("Out of memory error occurred: " + e.getMessage());
        } catch (IOException e) {
            // There is something wrong with the input formating
            System.err.println("An error occurred while reading the input file: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            // Something unexpected happened
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

   // Creates a class to store the course name and times for each course given in the input
   // Adds functions for adding times to the course node
    public static class Course {
        final String courseName;
        final ArrayList<String> times = new ArrayList<String>();

        // Constructs the coursName variable
        Course(final String courseName) {
            this.courseName = courseName;
        }

        // Method for the addTimes, constructing the method
        void addTimes(final String time) {
            this.times.add(time);
        }
    }

    // Creates a class to store all of the course names and specified class times in the Schedule node
    public static class Schedule {
        final ArrayList<String> courseNames;
        final ArrayList<String> timeSlots;

        // Constructs the ArrayList<String>'s
        public Schedule() {
            this.courseNames = new ArrayList<String>();
            this.timeSlots = new ArrayList<String>();
        }

        // adds the courseNames and timeSlots to Schedule
        public Schedule(final Schedule schedule) {
            this.courseNames = new ArrayList<String>(schedule.courseNames);
            this.timeSlots = new ArrayList<String>(schedule.timeSlots);
        }

        // Method that can be called to add the specified course and course time to the current schedule
        public void addCourse(final String courseName, final String timeSlot) {
            this.courseNames.add(courseName);
            this.timeSlots.add(timeSlot);
        }

        // Boolean to tell if the current Schedule is better than the one it is being compared to
        // ONLY USE THIS ON THE CURRENT BEST SCHEDULE
        public boolean isBetterSchedule(final Schedule comparingSchedule) {
            // If this is true it will execute the code in the if statement this boolean
            // is used in, if not, it will move on
            return this.courseNames.size() > comparingSchedule.courseNames.size();
        }

        // Boolean to tell if there is a conflict with the current schedule times
        // When used is will allow the program to either send the current schedule to the conflictingOutput
        // or the current schedule that is being built
        public boolean hasConflict(final String newTime) {
            // Tells if the current time matches the time in the time slot it is comparing to
            return timeSlots.contains(newTime);
        }
    }
}
