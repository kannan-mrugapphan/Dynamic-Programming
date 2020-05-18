// 983.
// time - O(max(max day in days[], length of days[])) //max time among populating hashset and result - days can have max of 366 days - so can be considered constant
// space - O(max(max day in days[], length of days[])) //for result[] - days can have max of 366 days - so can be considered constant
class Solution {
    public int mincostTickets(int[] days, int[] costs) {
        //hashset for a faster look up to check whether we travel on a particular day
        int max = Integer.MIN_VALUE; //denotes the largest day in a year [1, 365] on which travel occurs
        HashSet<Integer> travelDays = new HashSet<>(); 
        for(int day : days)
        {
            travelDays.add(day);
            max = Math.max(max, day);
        }
        int[] result = new int[max + 1];
        result[0] = 0; //cost to travel on 0th day is 0
        for(int i = 1; i < result.length; i++)
        {
            if(travelDays.contains(i)) // travel occurs on day i
            {
                //if the prev day or the 7th prev day or the 30th prev day is out of bounds, 
                //buy a new ticket at day i which has min cost - so the corresponding index to look from result[] is 0 
                int daily = Math.max(0, i - 1); 
                int weekly = Math.max(0, i - 7);
                int monthly = Math.max(0, i - 30);
                
                int dailyPass = costs[0] + result[daily]; //try extending from prev day
                int weeklyPass = costs[1] + result[weekly]; //try extending from 7th day
                int monthlyPass = costs[2] + result[monthly]; //try extending from 30th day
                result[i] = Math.min(dailyPass, Math.min(weeklyPass, monthlyPass)); //result - min among 3 choices
            }
            else //not travelling on i - sso cost remains same as previous day
            {
                result[i] = result[i - 1];
            }
        }
        return result[max]; //return cost till the max day in days[]
    }
}
