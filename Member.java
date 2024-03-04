/*
 * Student name: Siu Pui Cheung
 * Student ID: 1230798
 * LMS username: SIUPUIC
 */


import java.util.ArrayList;

public class Member {


    /***
     * to find the member name by memberId provided by the competitions
     * @param memberId the target memberId
     * @param memberList the memberlist provide from DataProvider
     * @return return the member name
     */
    public String findMemberName(String memberId, ArrayList<String[]> memberList){

        String memberName = null;
        for (int i = 0; i < memberList.size(); i++){
            if (memberId.equals(memberList.get(i)[0])){ //if the memberId match the one in the list, return the name
                memberName = memberList.get(i)[1];
            }
        }
        return memberName;
    }
}
