/*
 * Student name: Siu Pui Cheung
 * Student ID: 1230798
 * LMS username: SIUPUIC
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RandomPickCompetition extends Competition {
    private final int FIRST_PRIZE = 50000;
    private final int SECOND_PRIZE = 5000;
    private final int THIRD_PRIZE = 1000;
    private final int[] prizes = {FIRST_PRIZE, SECOND_PRIZE, THIRD_PRIZE};
	private ArrayList<Entry> entries = new ArrayList<>();
    private final int MAX_WINNING_ENTRIES = 3;
    private int winningNumber;
    private int prize;
    private String active = "yes";  //active trigger of the competition, yes means active and no means inactive


    /***
     * submethod of addEntries for RandomPick, create RandomPick entries
     * @param keyboard for input
     */
    public void addEntries (Scanner keyboard) {
            double billAmount = inputBill(keyboard); //call inputBill method to let user input billId and check if it is valid
            int entryAmount = (int) billAmount / 50;

            if (entryAmount == 0) { //tell user that no entry is available
                System.out.println("This bill is not eligible for an entry. The total amount is smaller than $50.0");
            } else {//if user can play one or more entries, do the following actions
                System.out.println("This bill ($" + billAmount + ") is eligible for " + entryAmount + " entries.");
                System.out.println("The following entries have been automatically generated:");
            }
            //create entries automatically according to the bill amounts
            Entry entry;
            for (int i = 0; i < entryAmount; i++) {
                setEntryId(getEntryId() + 1);
                addNumberOfEntries();
                entry = new Entry(getBillId().get(getBillId().size()-1),
                        getMemberId().get(getMemberId().size()-1), getEntryId());
                entries.add(entry);
                System.out.printf("Entry ID: %-5d \n", getNumberOfEntries());
            }
            getCompetitionEntries().add(entries);
            moreEntry(keyboard); //ask user if more entries input are wanted
        }

    /***
     * to draw winner entries
     */
    public void drawWinners() {
        if (getNumberOfEntries() == 0){
            System.out.println("The current competition has no entries yet!");
        } else {
            active = "no";  //turn active to no
            System.out.println("Competition ID: " + getId() + ", Competition Name: " + getName() +
                    ", Type: RandomPickCompetition");
            System.out.println("Winning entries:");
            // select the winners randomly
            Random randomGenerator = null;
        if (getIsTestingMode()) {
            randomGenerator = new Random(getId());
        } else {
            randomGenerator = new Random();
        }

        int winningEntryCount = 0;
        while (winningEntryCount < MAX_WINNING_ENTRIES && winningEntryCount < entries.size()) {
            int winningEntryIndex = randomGenerator.nextInt(entries.size());
            Entry winningEntry = entries.get(winningEntryIndex);
            /*
             * Ensure that once an entry has been selected,
             * it will not be selected again.
             */
            if (winningEntry.getPrize() == 0) {
                int currentPrize = prizes[winningEntryCount];
                winningEntry.setPrize(currentPrize);
                winningEntryCount++;
            }
        }

        chooseWinner(); //choose the winners and print them
        }
        }

    /***
     * set rules and choose winners
     */
    public void chooseWinner(){
            //first rule: choose the entries that user has inputted with the largest prize
            ArrayList<Entry> winningEntries = new ArrayList<>();
            for (int i = 0; i < getBillId().size(); i++) {
                int winningEntryId = 0;
                int maxPrize = 0; //set maxPrize
                for (int j = 0; j < entries.size(); j++){
                    if (entries.get(j).getBillId() == (getBillId().get(i)) && entries.get(j).getPrize() > maxPrize){
                        winningEntryId = entries.get(j).getEntryId();
                        maxPrize = entries.get(j).getPrize();

                    }
                }
                if (maxPrize != 0){
                    winningEntries.add(entries.get(winningEntryId-1));
                }
            }
            //second rule: find the entry with the largest prize in a memberId
            ArrayList<Entry> uniqueWinningEntries = new ArrayList<>();
            for (int i = 0; i < winningEntries.size(); i++){
                Entry uniqueWinning = null;
                int sameMemberCount = 0;
                for (int j = 0; j < winningEntries.size(); j++){
                    if (winningEntries.get(i).getMemberId().equals(winningEntries.get(j).getMemberId())){
                        sameMemberCount++;
                        if (winningEntries.get(i).getPrize() > winningEntries.get(j).getPrize()){
                            uniqueWinning = winningEntries.get(i);
                        }
                    }
                }
                if (uniqueWinning != null){
                    uniqueWinningEntries.add(uniqueWinning);
                }
                if (uniqueWinning == null && sameMemberCount == 1){
                    uniqueWinningEntries.add(winningEntries.get(i));
                }
            }

            //print the winner entries
            for (int i = 0; i < uniqueWinningEntries.size(); i++){
                Member member = new Member();
                String memberID = uniqueWinningEntries.get(i).getMemberId();
                String memberName = member.findMemberName(memberID, getMemberList());
                int entryID = uniqueWinningEntries.get(i).getEntryId();
                int prize =  uniqueWinningEntries.get(i).getPrize();
                this.winningNumber++;
                this.prize += prize;
                System.out.printf("Member ID: %s, Member Name: %s, Entry ID: %d, Prize: %-5d\n",
                        memberID, memberName, entryID, prize);
            }
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
