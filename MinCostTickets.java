// 983.
class Solution {
    public int mincostTickets(int[] days, int[] costs) {
        //return findMinCost(days, days.length - 1, costs, new Integer[days.length]);
        return findMinCost(days, costs);
    }

    //assumption - days will be sorted
    //f(i) tracks min cost to travel on days[0, i]
    //time - O(3^n), space - O(n)
    //time with memoization - O(n), space with memoization - O(n) for cache and O(n) for call stack
    private int findMinCost(int[] days, int index, int[] costs, Integer[] cache)
    {
        //base
        if(index < 0)
        {
            return 0; //all days covered
        }

        //check cache
        if(cache[index] != null)
        {
            return cache[index];
        }

        int oneDayPassCost = costs[0];
        //if one day pass is purchased, earliest day travel is possible is the same day
        oneDayPassCost += findMinCost(days, index - 1, costs, cache); //recurse on the remaining days

        int sevenDayPassCost = costs[1];
        //if 7 day pass is purchased on day, then travel is possible on day, day-1, day-2, ..., day-6
        int minimumDayCovered = days[index] - 6;
        int nextIndex = index;
        while(nextIndex >= 0 && days[nextIndex] >= minimumDayCovered)
        {
            nextIndex--;
        }
        sevenDayPassCost += findMinCost(days, nextIndex, costs, cache); //the next day gets updated

        int thirtyDayPassCost = costs[2];
        //if 30 day pass is purchased on day, then travel is possible on day, day-1, day-2, ..., day-29
        minimumDayCovered = days[index] - 29;
        nextIndex = index;
        while(nextIndex >= 0 && days[nextIndex] >= minimumDayCovered)
        {
            nextIndex--;
        }
        thirtyDayPassCost += findMinCost(days, nextIndex, costs, cache); //the next day gets updated


        int result = Math.min(oneDayPassCost, Math.min(sevenDayPassCost, thirtyDayPassCost));
        cache[index] = result; //update cache
        return result;
    }

    //time - O(n)
    //space - O(n)
    private int findMinCost(int[] days, int[] costs)
    {
        //result[i] tracks min cost needed to travel on days[0, i]
        int[] result = new int[days.length + 1];
        result[0] = 0; //days is empty, no cost needed, base case

        for(int index = 1; index <= days.length; index++)
        {
            int oneDayPassCost = costs[0];
            //if one day pass is purchased, earliest day travel is possible is the same day
            oneDayPassCost += result[index - 1]; //recurse on the remaining days

            int sevenDayPassCost = costs[1];
            //if 7 day pass is purchased on day, then travel is possible on day, day-1, day-2, ..., day-6
            int minimumDayCovered = days[index - 1] - 6;
            int nextIndex = index;
            while(nextIndex > 0 && days[nextIndex - 1] >= minimumDayCovered)
            {
                nextIndex--;
            }
            sevenDayPassCost += result[nextIndex]; //the next day gets updated

            int thirtyDayPassCost = costs[2];
            //if 30 day pass is purchased on day, then travel is possible on day, day-1, day-2, ..., day-29
            minimumDayCovered = days[index - 1] - 29;
            nextIndex = index;
            while(nextIndex > 0 && days[nextIndex - 1] >= minimumDayCovered)
            {
                nextIndex--;
            }
            thirtyDayPassCost += result[nextIndex]; //the next day gets updated

            result[index] = Math.min(oneDayPassCost, Math.min(sevenDayPassCost, thirtyDayPassCost));
        }

        return result[days.length];
    }
}
