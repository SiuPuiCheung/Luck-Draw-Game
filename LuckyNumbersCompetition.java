/*
 * Student name: Siu Pui Cheung
 * Student ID: 1230798
 * LMS username: SIUPUIC
 */

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LuckyNumbersCompetition extends Competition {

    private ArrayList<ArrayList<Entry>> entries = new ArrayList<>();
    private int prize;  //accumulated prize of the competition
    private String active = "yes";  //active trigger of the competition, yes means active and no means inactive
    private int winningNumber;  //the number of winning entries


    /***
     * submethod of addEntries, used for adding entries
     * @param keyboard for input
     */
    public void addEntries(Scanner keyboard){
        double billAmount = inputBill(keyboard); //input the bill

        int entryAmount = (int) billAmount / 50;
        if (entryAmount == 0) { //tell user that no entry is available
            System.out.println("This bill is not eligible for an entry. The total amount is smaller than $50.0");
        } else {//if user can play one or more entries, do the following actions
            System.out.println("This bill ($" + billAmount + ") is eligible for " + entryAmount +
                    " entries. How many manual entries did the customer fill up?: ");}
        //check whether use input a valid number for manual entry
        while (true){
            try {
                    int manualEntry = keyboard.nextInt();
                    keyboard.nextLine();

                    if (manualEntry > entryAmount){
                        throw new Exception();
                    }
                    createEntry(keyboard, manualEntry, entryAmount);
                    moreEntry(keyboard);
                break;
            }catch (InputMismatchException e){
                System.out.printf("The number must be in the range from 0 to %d. Please try again.\n", entryAmount);
                keyboard.nextLine();
            } catch (Exception e) {
                System.out.printf("The number must be in the range from 0 to %d. Please try again.\n", entryAmount);
            }
        }
    }

    /***
     * to create manual and auto entries
     * @param keyboard for input
     * @param manualEntry the number of manual entry
     * @param entryAmount amount of total entries
     */
    public void createEntry(Scanner keyboard,int manualEntry, int entryAmount){
        ArrayList<Entry> entries = new ArrayList<>();   //save the entries under this round
        //for loop used for creating manual/auto entries
        for (int i = 0; i < entryAmount; i++) {
            setEntryId(getEntryId() + 1);
            addNumberOfEntries();
            Entry entry;

            if (manualEntry > 0) {//when user selects one or more manual entries
                entry = new Entry(keyboard, getEntryId());
                entries.add(entry);
                manualEntry--;  //create one manual entry, decrease one manual entry request
            } else {    //when no more manual entry needs to be created, creates auto entry
                int seed = getNumberOfEntries() - 1;     //use the number of entries as the seed
                entry = new Entry(getEntryId(), seed, true, getIsTestingMode());
                entries.add(entry);
            }
        }
        this.entries.add(entries);
        //print the created entries this round
        System.out.println("The following entries have been added:");
        for (int i = 0 ; i < entries.size(); i++) {
            System.out.print("Entry ID: ");
            System.out.printf("%-7d", entries.get(i).getEntryId());
            entries.get(i).printEntries();
        }
    }


    /***
     * prize calculater
     * @param number
     * @return how many number of the entry matches the luckydraw
     */
    public int prizePoint(int number) {
        switch (number) {
            case 1:
                return 0;
            case 2:
                prize += 50;
                return 50;
            case 3:
                prize += 100;
                return 100;
            case 4:
                prize += 500;
                return 500;
            case 5:
                prize += 1000;
                return 1000;
            case 6:
                prize += 5000;
                return 5000;
            default:
                prize += 50000;
                return 50000;
        }
    }

    /***
     * create a lucky draw and show the winners
     */
    public void drawWinners() {
        //tell user lucky draw can not be made because there is no entries in this competition yet
        if (getNumberOfEntries() == 0){
            System.out.println("The current competition has no entries yet!");
        } else {
            active = "no";  //turn active to no
            System.out.println("Competition ID: " + getId() + ", Competition Name: " + getName() +
                    ", Type: LuckyNumbersCompetition");
            Entry luckyEntry = new Entry(getEntryId(), getId(), true, getIsTestingMode());
            System.out.print("Lucky ");
            luckyEntry.printEntries();     //print the lucky entry number
            System.out.println("Winning entries:");
            //find the entry winners
            for (int i = 0; i < entries.size(); i++) {
                Entry maxMatchEntry = null; //record the entries most matching the lucky draw
                int maxMatch = 0;       //count the number of matching number of the entries

                //do the comparison
                for (int j = 0; j < entries.get(i).size(); j++) {
                    int match = compare(entries.get(i).get(j).getEntryNumber(), luckyEntry.getEntryNumber());
                    if (match > maxMatch && match > 1) {//if the entry matches more number than the current max one, replace it
                        maxMatch = match;
                        maxMatchEntry = entries.get(i).get(j);
                    }
                }
                //show the most matching entries only if they have more than one matching number
                if (maxMatch > 1) {
                    Member memberName = new Member();//using member method to find the winning member details
                    winningNumber++;
                    System.out.printf("Member ID: %s, Member Name: %s, Prize: %-5d\n",
                            getMemberId().get(i),memberName.findMemberName(getMemberId().get(i),
                                    getMemberList()), prizePoint(maxMatch));

                    System.out.printf("--> Entry ID: %d, ", maxMatchEntry.getEntryId());
                    maxMatchEntry.printEntries();
                }
            }
        }
    }

    /***
     * method for the comparison between the selected entry and lucky entry
     * @param entry the entry number
     * @param luckyEntry the lucky entry number
     * @return return the number of matching number
     */
    public int compare(int[] entry, int[] luckyEntry){
        int entryLength = 7;
        int matchCount = 0;
        for (int i = 0; i < entryLength; i++){

            for (int j = 0; j < entry.length; j++) {
                if ( entry[j] == luckyEntry[i]) {
                    matchCount++;   //if entry[j] equals to luckEntry[i], count 1 more in matchCount
                }
            }
        }
        return matchCount;  //return the number of the matching number
    }

    @Override
    protected String getActive() {
        return active;
    }

    @Override
    protected int getWinningNumber() {
        return winningNumber;
    }

    @Override
    protected int getPrize() {
        return prize;
    }
}

