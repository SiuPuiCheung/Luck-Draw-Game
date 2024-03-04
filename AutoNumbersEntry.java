/*
 * Student name: Siu Pui Cheung
 * Student ID: 1230798
 * LMS username: SIUPUIC
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class AutoNumbersEntry extends NumbersEntry {
    private final int NUMBER_COUNT = 7;
    private final int MAX_NUMBER = 35;

    /***
     * create number automatically for testing mode
     * @param seed provide the seed to calculate values
     * @return return the auto made number
     */
    public int[] createNumbers (int seed) {
        ArrayList<Integer> validList = new ArrayList<>();
	int[] tempNumbers = new int[NUMBER_COUNT];
        for (int i = 1; i <= MAX_NUMBER; i++) {
    	    validList.add(i);
        }
        Collections.shuffle(validList, new Random(seed));
        for (int i = 0; i < NUMBER_COUNT; i++) {
    	    tempNumbers[i] = validList.get(i);
        }
        Arrays.sort(tempNumbers);
        return tempNumbers;
    }

    /***
     * create number automatically for normal mode
     * @return return the auto made number
     */
    public int[] createNumbers () {
        ArrayList<Integer> validList = new ArrayList<>();
        int[] tempNumbers = new int[NUMBER_COUNT];
        for (int i = 1; i <= MAX_NUMBER; i++) {
            validList.add(i);
        }
        Collections.shuffle(validList, new Random());
        for (int i = 0; i < NUMBER_COUNT; i++) {
            tempNumbers[i] = validList.get(i);
        }
        Arrays.sort(tempNumbers);
        return tempNumbers;
    }
}
