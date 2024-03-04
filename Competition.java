/*
 * Student name: Siu Pui Cheung
 * Student ID: 1230798
 * LMS username: SIUPUIC
 */


import java.util.ArrayList;
import java.util.Scanner;

public abstract class Competition extends SimpleCompetitions  {
    private String name; //competition name
    private int id; //competition identifier
    private int numberOfEntries;    //the number of entries in the competition
    private boolean mode = true; //whether it is test mode
    private ArrayList<String[]> memberList;
    private ArrayList<String[]> billList;
    private DataProvider data; // data obejct
    private ArrayList<String> billId = new ArrayList<>(); //list of bill that user use
    private int entryId =0;
    private ArrayList<String> memberId = new ArrayList<>(); //list of member ID in the competitions
    private ArrayList<ArrayList<Entry>> competitionEntries = new ArrayList<>();

    /***
     * add entry to LuckyNumber or Randompick
     * @param keyboard for input
     */
    public abstract void addEntries(Scanner keyboard);

    /***
     * to draw winner
     */
    public abstract void drawWinners();

    /***
     * LuckyNumber or Randompick report, showing each of their competitions
     */
    public void report() {
        System.out.println("Competition ID: " + id + ", name: "+ name + ", active: " + getActive());
        System.out.println("Number of entries: " + numberOfEntries);
        //if the competition is no longer active, print the number of winning entries and the prizes
        if (getActive().equals("no")){
            System.out.println("Number of winning entries: " + getWinningNumber());
            System.out.println("Total awarded prizes: " + getPrize());
        }
    }

    /***
     * to update bills.csv when the method is called
     */
    public void exportCSV(){
        data.writingCSV(); //update the file in data obejct
    }

    /***
     * set the competition variable for future use, and print out the details
     * @param keyboard for input
     * @param id    competition id
     * @param type  l for LuckyNumber and r for RandomPick
     * @param data  data object for accessing data
     * @param mode  testing mode or normal mode
     */
    public void setCompetition(Scanner keyboard, int id, String type,  DataProvider data, boolean mode){
        this.id = id;
        memberList = data.getMemberList();
        billList = data.getBillList();
        this.data = data;
        this.mode = mode;

        System.out.println("Competition name: ");
        String name = keyboard.nextLine();
        this.name = name;

        System.out.println("A new competition has been created!");
        if (type.equals("l")){
            System.out.println("Competition ID: " + id + ", Competition Name: " + name + ", Type: LuckyNumbersCompetition");
        } else {
            System.out.println("Competition ID: " + id + ", Competition Name: " + name + ", Type: RandomPickCompetition");
        }
    }

    /***
     * for user to input the bill number, and check if the bill is valid using bill method
     * @param keyboard for input
     * @return return the bill amount for addEntries use
     */
    public double inputBill(Scanner keyboard){
        Bill bill = new Bill();
        double billAmount;
        while (true){   //input bill Id and check whether it is valid
            System.out.println("Bill ID: ");
            String billId = keyboard.nextLine();
            this.billId.add(billId);
            //using billCheck to check if the bill is valid
            if (bill.billCheck(billId, billList, data)){
                billAmount = Double.parseDouble(billList.get(bill.getValidBillIndex())[2]);
                memberId.add(billList.get(bill.getValidBillIndex())[1]);
                break;
            }
        }
        return billAmount;
    }

    /***
     * ask user whether another entry is wanted
     * @param keyboard for input
     */
    public void moreEntry(Scanner keyboard){
        //ask user whether more entries are wanted
        addLoop:
        while (true) {
            System.out.println("Add more entries (Y/N)?");
            String moreEntries = keyboard.nextLine();
            switch (moreEntries.toLowerCase()) {
                case "y":
                    addEntries(keyboard);
                    break addLoop;
                case "n":
                    break addLoop;
                default:
                    System.out.println("Unsupported option. Please try again!");
            }
        }
    }
    //3 abstract method for report use
    protected abstract String getActive();
    protected abstract int getWinningNumber();
    protected abstract int getPrize();


    public boolean getIsTestingMode(){
    return mode;
    }

    public int getId() {
        return id;
    }

    public void addNumberOfEntries() {
        this.numberOfEntries++;
    }

    public int getNumberOfEntries() {
        return numberOfEntries;
    }


    public String getName() {
        return name;
    }

    public ArrayList<String> getBillId() {
        return billId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public int getEntryId() {
        return entryId;
    }

    public ArrayList<String> getMemberId() {
        return memberId;
    }

    public ArrayList<String[]> getMemberList() {
        return memberList;
    }

    public ArrayList<ArrayList<Entry>> getCompetitionEntries() {
        return competitionEntries;
    }
}
