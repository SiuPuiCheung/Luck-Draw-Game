/*
 * Student name: Siu Pui Cheung
 * Student ID: 1230798
 * LMS username: SIUPUIC
 */

import java.util.ArrayList;

public class Bill{
    private int validBillIndex;


    /**
     * to check whether is valid, can be used in different competitions
     * @param number the bill number user input
     * @param list the bill list from DataProvider
     * @param billUsed check whether the bill has been used for competition
     * @return
     */
    public boolean billCheck(String number, ArrayList<String[]> list, DataProvider billUsed){
        int validBillLength = 6;
        try {//if the bill number is not only in 0-9, show error
            if (number.length() != validBillLength || !number.matches("[0-9]+")){
                throw new Exception("Invalid bill id! It must be a 6-digit number. Please try again.");
            }
            //search the bill list to find if the bill is valid
            for (int i = 0; i < list.size(); i++){
                if (number.equals(list.get(i)[0]) ){
                    if (list.get(i)[1].isEmpty()){
                        throw new Exception("This bill has no member id. Please try again.");

                    }
                    if (billUsed.getBillUsed().get(i)){
                        throw new Exception("This bill has already been used for a competition. Please try again.");
                    }

                    if (!billUsed.getBillUsed().get(i)){
                        validBillIndex = i;
                        billUsed.setBillUsed(i);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        System.out.println("This bill does not exist. Please try again.");
        return false;
    }

    /**
     * to return the index of the bill in the bill list
     * @return return the index in the bill list
     */
    public int getValidBillIndex() {
        return validBillIndex;
    }
}
