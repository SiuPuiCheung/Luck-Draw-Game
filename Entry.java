/*
 * Student name: Siu Pui Cheung
 * Student ID: 1230798
 * LMS username: SIUPUIC
 */

import java.io.Serializable;
import java.util.Scanner;

public class Entry implements Serializable {
    private int entryId;
    private String billId;
    private String memberId;
    private boolean auto;
    private int[] entryNumbers;
    private int currentPrize;


    public Entry(){}

    /***
     * entry for RandomPick
     * @param billId billId of the entry
     * @param memberId  memberId of the entry
     * @param entryId   entryId of the entry
     */
    public Entry(String billId, String memberId, int entryId){
        this.billId = billId;
        this.memberId = memberId;
        this.entryId = entryId;
    }

    /***
     * entry for LuckyNumber manual
     * @param keyboard for input
     * @param entryId entryId of the entry
     */
    public Entry(Scanner keyboard, int entryId){
        this.entryId = entryId;
        NumbersEntry numbersEntry = new NumbersEntry();
        entryNumbers = numbersEntry.manualEntry(keyboard);
    }

    /***
     *
     * @param entryId entryId of the entry
     * @param seed for testing mode auto number
     * @param auto pass the auto to local for other use
     * @param mode testing or normal mode
     */
    public Entry(int entryId, int seed, boolean auto, boolean mode){
        this.entryId = entryId;
        this.auto = auto;
        NumbersEntry numbersEntry = new NumbersEntry();
        //entryObject = numbersEntry;
        entryNumbers = numbersEntry.autoEntry(mode, seed);
    }

    /***
     * print the entry number
     */
    public void printEntries(){
        System.out.print("Numbers:");
        for (int i = 0; i < entryNumbers.length; i++){
            System.out.printf("%3d",entryNumbers[i]);
        }
        if (auto){
            System.out.println(" [Auto]");
        }
        else {
            System.out.println();
        }
    }

    public String getBillId() {
        return billId;
    }

    public String getMemberId() {
        return memberId;
    }

    public int getEntryId() {
        return entryId;
    }

    public int[] getEntryNumber() {
        return entryNumbers;
    }

    public int getPrize() {
        return currentPrize;
    }

    public void setPrize(int currentPrize) {
        this.currentPrize = currentPrize;
    }
}
