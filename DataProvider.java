/*
 * Student name: Siu Pui Cheung
 * Student ID: 1230798
 * LMS username: SIUPUIC
 */

import java.io.*;
import java.util.ArrayList;

public class DataProvider extends SimpleCompetitions implements Serializable{
    private final ArrayList<String[]> memberList = new ArrayList<>();
    private final ArrayList<String[]> billList = new ArrayList<>();
    private ArrayList<Boolean> billUsed = new ArrayList<>();
    private final int idLength = 6;

    /**
     *
     * @param memberFile A path to the member file (e.g., members.csv)
     * @param billFile A path to the bill file (e.g., bills.csv)
     * @throws DataAccessException If a file cannot be opened/read
     * @throws DataFormatException If the format of the the content is incorrect
     */
     public DataProvider(String memberFile, String billFile)
                        throws DataAccessException, DataFormatException {
         try {//read the member file
             BufferedReader member = new BufferedReader(new FileReader(memberFile));
             String lineMember;
             //check the validity
             while ((lineMember = member.readLine() )!= null){
                 String[] members = lineMember.split(",");
                 if (members.length != 3 || !members[0].matches("[0-9]+") ||
                         members[0].length() != idLength){
                     throw new Exception();
                 }
                 for (int i = 0; i < members.length;i++){
                     if (members[i].isEmpty()){
                         throw new Exception();
                     }
                 }
                 memberList.add(members); //when the member is valid, add it to memberList

             }
             //read the bill file
             BufferedReader bill = new BufferedReader(new FileReader(billFile));
             String lineBill;
             //check the validity
             while ((lineBill = bill.readLine()) != null){
                 String[] bills = lineBill.split(",");
                 if (bills.length != 4 || !bills[0].matches("[0-9]+") || bills[0].length() != idLength ||
                         (!bills[1].isEmpty() && (!bills[1].matches("[0-9]+") ||bills[1].length() != idLength )) ||
                         !bills[2].matches("[0-9]+.[0-9]+")||
                         !bills[3].equalsIgnoreCase("true") &&
                                 !bills[3].equalsIgnoreCase("false")){
                     throw new Exception();
                 }
                 for (int i = 0; i < bills.length; i++){
                     if (i != 1 && bills[i].isEmpty()){
                         throw new Exception();
                     }
                 }
                 billList.add(bills);//when the bill is valid, add it to billList
                 billUsed.add(Boolean.parseBoolean(bills[3])); //keep the usedBill checking independently so that it can be changed

             }
             member.close();
         }catch (FileNotFoundException e){
             throw new DataAccessException("File cannot be opened/read",e);
         } catch (IOException e) {
             e.printStackTrace();
         }catch (Exception e){
             throw new DataFormatException("The format of the the content is incorrect.", e);

         }
     }

    /***
     * to write a new bill csv file when it is called
     */
     public void writingCSV(){
         try {
             BufferedWriter write = new BufferedWriter(new FileWriter("bills.csv"));
             for (int i = 0; i < billList.size(); i++){
                 write.write(billList.get(i)[0] + "," + billList.get(i)[1] + "," + billList.get(i)[2] + "," +
                         billUsed.get(i));
                 write.newLine();
             }
             write.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    public ArrayList<String[]> getMemberList() {
        return memberList;
    }

    public ArrayList<String[]> getBillList() {
        return billList;
    }

    public ArrayList<Boolean> getBillUsed() {
        return billUsed;
    }

    public void setBillUsed(int billIndex) {
         billUsed.set(billIndex, true);
    }
}
