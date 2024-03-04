/*
 * Student name: Siu Pui Cheung
 * Student ID: 1230798
 * LMS username: SIUPUIC
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class NumbersEntry extends Entry  {
    private int[] numbers;
    private final int entryLength = 7;

    /***
     * ask user to enter manual LuckyNumber and check whether the numbers are valid
     * @param keyboard input
     * @return return the valid number
     */
    public int[] manualEntry(Scanner keyboard){
        while (true) {
            try {
                System.out.println("Please enter 7 different numbers (from the range 1 to 35) separated by whitespace.");
                String[] number = keyboard.nextLine().split(" "); //to be able to split the numbers by space, set input type as String
                int[] entryNumber = new int[number.length];
                //if the input is not 0-9 tell user it is invalid
                for (int i = 0; i < number.length; i++){
                    if (!number[i].matches("[0-9]+")){
                        throw new InputMismatchException("Invalid input! Numbers are expected. Please try again!");
                    }//if the input is <1 or > 35, tell user it is invalid
                    entryNumber[i] = Integer.parseInt(number[i]);
                    if (Integer.parseInt(number[i]) < 1 || Integer.parseInt(number[i]) > 35){
                        throw new InputMismatchException("Invalid input! All numbers must be in the range from 1 to 35!");
                    }
                }
                //if the input length is not 7, tell user it is invalid
                if (number.length < entryLength) {
                    throw new Exception("Invalid input! Fewer than 7 numbers are provided. Please try again!");
                } else if (number.length > entryLength) {
                    throw new Exception("Invalid input! More than 7 numbers are provided. Please try again!");
                } else {
                    boolean sameNumberCheck = false;  //save whether the entry contains the same number

                    for (int i = 0; i < entryNumber.length - 1; i++) {
                        if (sort(entryNumber)[i] == sort(entryNumber)[i + 1]) {
                            sameNumberCheck = true;
                            break;
                        }
                    }
                    //tell user to input entry again if the entry contains the same numbers
                    if (sameNumberCheck) {
                        System.out.println("Invalid input! All numbers must be different!");
                    } else {
                        numbers = sort(entryNumber);
                        break;
                    }
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return numbers;
    }

    /***
     * create auto LuckyNumber including drawWinner number
     * @param mode  check if mode is testing
     * @param seed  use seed in autoNumberEntry when it is testing mode
     * @return
     */
    public int[] autoEntry(boolean mode, int seed){
        AutoNumbersEntry autoNumber = new AutoNumbersEntry();
        //select which autonumber creator should use
        if (mode) {
            numbers = sort(autoNumber.createNumbers(seed));
        } else {
            numbers = sort(autoNumber.createNumbers());
        }
        return numbers;
    }


    /***
     * do sorting for the luck number
     * @param array input the luck number array
     * @return return the sorted number
     */
    public int[] sort(int[] array){
        int temp;
        int index;

        for (int i = 0; i < array.length; i++){
            temp = array[i];
            index = i;

            for (int j = i+1; j < array.length; j++){
                if (temp > array[j]){
                    temp = array[j];
                    index = j;
                }
            }

            array[index] = array[i];
            array[i] = temp;
        }
        return array;
    }
}
