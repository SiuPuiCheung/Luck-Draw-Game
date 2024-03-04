/*
 * Student name: Siu Pui Cheung
 * Student ID: 1230798
 * LMS username: SIUPUIC
 */

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class SimpleCompetitions implements Serializable{

    /***
     * To add new competition object either LuckyNumber or RandomPick
     * @param keyboard pass the input keyboard to the object
     * @param id competition id
     * @param type l for LuckyNumber and r RandomPick
     * @param data pass the data of bills and members in Dataprovider
     * @param mode t for test mode and n for normal mode
     * @return return the object of the competition, newLuckyNumber is LuckyNumber and newRandomPick is RandomPick
     */
    public Competition addNewCompetition(Scanner keyboard, int id, String type, DataProvider data, boolean mode) {
        if (type.equals("l")){
            Competition newLuckyNumber = new LuckyNumbersCompetition();
            newLuckyNumber.setCompetition(keyboard, id, type, data, mode);
            return newLuckyNumber;
        }else {
            Competition newRandomPick = new RandomPickCompetition();
            newRandomPick.setCompetition(keyboard, id, type, data, mode);
            return newRandomPick;
        }
    }


    /***
     * Summary report for all competitions
     * @param completedCompetition the number of completed competitions
     * @param active whether the competition is active, 0 is inactive and 1 is active
     */
    public void report(int completedCompetition, int active) {
        System.out.println("----SUMMARY REPORT----");
        System.out.println("+Number of completed competitions: " + completedCompetition);
        System.out.print("+Number of active competitions: " + active + "\n\n");
    }

    /**
    * Main program that uses the main SimpleCompetitions class
    * @param args main program arguments
    */
    public static void main(String[] args) throws IOException {

    	//Create an object of the SimpleCompetitions class
        SimpleCompetitions sc = new SimpleCompetitions();
        Scanner keyboard = new Scanner(System.in);
        boolean selectMode = true; //whether it is test mode, initialize to true
        System.out.println("----WELCOME TO SIMPLE COMPETITIONS APP----");
        //the whole loop for the program
        mainLoop:
        while (true) {
            boolean loadRecord = false; //check if the user load the competition file
            ObjectInputStream inputRecord = null;
            //ask user whether he/she wants to load competition files to the program
            while (true) {
                try {
                    System.out.println("Load competitions from file? (Y/N)?");
                    String load = keyboard.nextLine().toLowerCase();

                    if (!load.matches("y") && !load.matches("n")) {
                        System.out.println("Unsupported option. Please try again!");
                    } else if (load.matches("y")) {
                        System.out.println("File name:");
                        String loadFile = keyboard.nextLine();
                        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(loadFile));
                        loadRecord = true; //pass true to loadRecord showing that competition files are loaded
                        inputRecord = inputStream; //pass the inputStream out of the loop
                        break;
                        //ask if the user wants the program being run in Testing or Normal
                    } else {
                        while (true) {
                            System.out.println("Which mode would you like to run? (Type T for Testing, and N for Normal mode):");
                            String mode = keyboard.nextLine().toLowerCase();
                            if (!mode.matches("t") && !mode.matches("n")) {
                                System.out.println("Invalid mode! Please choose again.");
                            } else {
                                if (mode.equals("n")){
                                    selectMode = false;
                                }
                                break;
                            }
                        }
                        break;
                    }
                    //exception catching
                } catch (FileNotFoundException e) {
                    System.out.println("Cannot find file.");
                    break mainLoop;
                } catch ( IOException e){
                    System.out.println("Cannot access file.");
                    break mainLoop;
                }
            }
                //input member and bill files
            try {
                System.out.println("Member file: ");
                String memberFile = keyboard.nextLine();
                System.out.println("Bill file: ");
                String billFile = keyboard.nextLine();

                DataProvider data = new DataProvider(memberFile, billFile);//create a dataprovider object to handle data
                int active = 0;                 //active 0 = no active competition, 1 = 1 active competition;
                int completedCompetition = 0;   //how many competitions are completed
                ArrayList<Competition> competitions = new ArrayList<>();    //collect the competition objects
                //when the user load competition files, load these 3 features
                if (loadRecord){
                    competitions = (ArrayList<Competition>) inputRecord.readObject(); //arraylist of old competitions
                     completedCompetition = (int) inputRecord.readObject(); //record of old completed competitions
                     active = (int) inputRecord.readObject(); //whether a competition is active
                    inputRecord.close();
                }
                //main menu
                while(true){
                    try {
                    System.out.println("Please select an option. Type 5 to exit.");
                    System.out.println("1. Create a new competition");
                    System.out.println("2. Add new entries");
                    System.out.println("3. Draw winners");
                    System.out.println("4. Get a summary report");
                    System.out.println("5. Exit");

                    int menuOption = keyboard.nextInt();
                    keyboard.nextLine();

                        switch (menuOption){
                            case 1:
                                if (active != 0){
                                    System.out.println("There is an active competition. " +
                                            "SimpleCompetitions does not support concurrent competitions!");
                                } else {
                                    //create a competition and turn active mode to be active

                                    while (true){
                                        try {
                                            System.out.println("Type of competition (L: LuckyNumbers, R: RandomPick)?:");
                                            String selectCompetition = keyboard.nextLine().toLowerCase();
                                            if (!selectCompetition.matches("l") && !selectCompetition.matches("r")){
                                                throw new InputMismatchException();
                                            }
                                            //add competition
                                            competitions.add(sc.addNewCompetition(keyboard, competitions.size()+1,
                                                    selectCompetition, data, selectMode));
                                            active = 1;
                                            break;
                                        } catch (InputMismatchException e){
                                            System.out.println("Invalid competition type! Please choose again.");

                                        }
                                }}
                                break;

                            case 2:
                                if (competitions.size() == 0 || active == 0){
                                    System.out.println("There is no active competition. Please create one!");
                                } else{

                                    //create an entry of the competition
                                    Competition competitionEntry = competitions.get(competitions.size()-1);
                                    competitionEntry.addEntries(keyboard);
                                }
                                break;

                            case 3:
                                if (competitions.size() == 0 || active == 0){
                                    System.out.println("There is no active competition. Please create one!");
                                } else {
                                        //extract the active competition that contains entries, then do the lucky draw
                                        competitions.get(competitions.size()-1).drawWinners();
                                        if (competitions.get(competitions.size()-1).getNumberOfEntries() > 0) {
                                            active = 0;
                                            completedCompetition++;
                                    }
                                }
                                break;

                            case 4:
                                if (competitions.size() == 0){
                                    System.out.println("No competition has been created yet!");
                                } else {
                                    //report for all competitions
                                    sc.report(completedCompetition,active);
                                    for (int i = 0; i < competitions.size(); i++){
                                        competitions.get(i).report();
                                        if (i < competitions.size()-1){
                                            System.out.println();
                                        }
                                    }
                                }
                                break;

                            case 5:
                                while (true){
                                    System.out.println("Save competitions to file? (Y/N)?");
                                    char save = keyboard.next().toLowerCase().charAt(0);
                                    keyboard.nextLine();

                                    if (save != 'y' && save != 'n') {
                                        System.out.println("Unsupported option. Please try again!");
                                    }else if (save == 'y'){
                                        System.out.println("File name:");
                                        //save competitions details and update bills.csv
                                        try {
                                            String fileName = keyboard.nextLine();

                                            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
                                            outputStream.writeObject(competitions);
                                            outputStream.writeObject(completedCompetition);
                                            outputStream.writeObject(active);

                                            outputStream.close();
                                            if (competitions.size() > 0){ //when there is new competitions created, update csv
                                                competitions.get(competitions.size()-1).exportCSV();
                                            }
                                            System.out.println("Competitions have been saved to file.");
                                            System.out.println("The bill file has also been automatically updated.");

                                            System.out.println("Goodbye!");
                                            break mainLoop;
                                        } catch (IOException e){
                                            System.out.println("Problem with the file output.");
                                        }
                                    } else {
                                        System.out.println("Goodbye!");
                                        break mainLoop;
                                    }
                                }


                            default:
                                System.out.println("Unsupported option. Please try again!");
                        }

                }catch (InputMismatchException e){
                    System.out.println("Unsupported option. Please try again!");
                    keyboard.nextLine();
                    }
                }

            } catch (DataAccessException | DataFormatException | ClassNotFoundException | NullPointerException e) {
                System.out.println(e);
                break mainLoop;
            }
        }
    }
}
