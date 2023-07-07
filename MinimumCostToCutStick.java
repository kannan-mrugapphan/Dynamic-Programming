//1547.
class Solution {
    public int minCost(int n, int[] cuts) {
        Arrays.sort(cuts); //to make left and right subproblems independent
        int[] modifiedCuts = new int[cuts.length + 2];
        modifiedCuts[0] = 0; //add 0 and n to first and last of cuts to get length of stick
        modifiedCuts[modifiedCuts.length - 1] = n;
        for(int i = 1; i <= cuts.length; i++)
        {
            modifiedCuts[i] = cuts[i - 1];
        }

        //return findBestCut(modifiedCuts, 1, cuts.length, new Integer[modifiedCuts.length][modifiedCuts.length]);
        return findBestCut(modifiedCuts);
    }

    //consider n = 7 and cuts = [1, 3, 4, 5]
    //if the stick is cut at posititon 3, 2 sticks of length 3 and 4 are obtainded. The cost so far is 7 as the stick length is initially 7 
    //The cuts array for the first stick is [1] with n = 3
    //cuts array for second stick is [4,5] with n = 4
    //the 2 subproblems can be solved independently if cuts[i] in first half is smaller than cuts[i] in second half
    //so the cuts[] is sorted

    //the length of the stick for the smaller subproblems are unavailable.
    //so 0 is added to start of cuts[] and n is added to last of cuts[] and the range is just from [1, n-2] in cuts are the border elments at 0 and n-1 are just used to calculate stick lengths
    //for any range [i,j] in cuts[], the length of stick is cuts[j + 1] - cuts[i - 1]

    //time with memoization - O(n^3) where n is length of cuts
    //space with memoization - O(n^2) for cache and O(n) for call stack where n is length of cuts
    private int findBestCut(int[] cuts, int start, int end, Integer[][] cache)
    {
        //base
        if(start > end)
        {
            //stick is invalid
            return 0;
        }

        //check cache
        if(cache[start][end] != null)
        {
            return cache[start][end];
        }

        int length = cuts[end + 1] - cuts[start - 1]; //length of current stick is difference between outer elements
        int cost = Integer.MAX_VALUE; //return value

        //stick can be cut at any index of cuts[] within [start, end]
        for(int index = start; index <= end; index++)
        {
            int currentCost = length; //current cut
            int leftStick = findBestCut(cuts, start, index - 1, cache);
            int rightStick = findBestCut(cuts, index + 1, end, cache);

            currentCost += leftStick + rightStick; //add cost to cut smaller sticks
            cost = Math.min(cost, currentCost); //minimize result
        }

        cache[start][end] = cost; //update cache
        return cost;
    }

    //time - O(n^3)
    //space - O(n^2)
    private int findBestCut(int[] cuts)
    {
        //result[i][j] tracks if stick is cut only on positions with [i,j] of cuts[]
        int[][] result = new int[cuts.length][cuts.length];

        //recursive function called with so start in reverse to solve smaller subproblems
        for(int start = cuts.length - 2; start >= 1; start--)
        {
            for(int end = start; end <= cuts.length - 2; end++)
            {
                //current range = [start, end]
                result[start][end] = Integer.MAX_VALUE; //minimize cut
                for(int index = start; index <= end; index++)
                {
                    int currentLengthOfStick = cuts[end + 1] - cuts[start - 1];
                    //1st part of ternary check is base case where range is invalid
                    int leftStickCost = (start > index - 1) ? 0 : result[start][index - 1];
                    int rightStickCost = (index + 1 > end) ? 0 : result[index + 1][end];
                    int cost = currentLengthOfStick + leftStickCost + rightStickCost;

                    result[start][end] = Math.min(result[start][end], cost);
                }
            }
        }

        return result[1][cuts.length - 2];
    }
}
