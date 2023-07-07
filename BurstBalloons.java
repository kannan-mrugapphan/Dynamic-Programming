// 312

//approach
//[b1, b2, b3, b4, b5, b6] -> if we decide to burst b4 first
//value = (b3*b4*b5) + value from (b1-b3) + value from (b5-b6) -> this inference is wrong because value(b1-b3) and value(b5-b6) cannot be solved independently due to dependence of b3 on b5 and b5 on b3

//if we decide to burst the balloons from last, this relation between sub problems will be broken
//[b4] (last balloon) -> [bx, b4] (2nd last stage, bx depends only on b4) -> [bx, by, b4] (3rd last stage, by depends on bx and b4) -> cross relation is broken
//so burst balloons from end
class Solution {
    public int maxCoins(int[] nums) {
        //return findMaxValue(nums, 0, nums.length - 1, new Integer[nums.length][nums.length]);
        return findMaxValue(nums);
    }

    //consider [6,1,3,4,5] and let 3 be burst first to get value of 1*3*4
    //left subproblem is [6,1] and right subproblem is [4,5]
    //if 1 from left subproblem is burst, it depends on 4 from right subproblem
    //similarly if 4 from right subproblem is burst, it depends on 1 from left subproblem

    //consider the balloon 3 is burst at the end to get value 1*3*1
    //left subproblem now is [6,1] and is not dependent on right subproblem [4,5]
    //because 3 (to be burst in next level) seperates the subproblem
    //if a prticular balloon x in the range [i,j] is burst at last, the total value is nums[i - 1] * nums[x] * nums[j + 1] + value from left subproblem (i, x - 1) + value from right subproblem (x + 1, j)

    //returns max value by bursting balloons in nums[start, end]
    //space - O(n) max call stack as all balloons must be burst
    //time with memoization - O(n^3)
    //space with memoization - O(n^2) for cache and O(n) for call stack
    private int findMaxValue(int[] nums, int start, int end, Integer[][] cache)
    {
        //base
        if(start > end)
        {
            return 0; //invalid range - no balloons
        }

        //check cache
        if(cache[start][end] != null)
        {
            return cache[start][end];
        }

        int maxValue = Integer.MIN_VALUE; //return value
        //any balloon in the range (start, end) can be burst at the end
        for(int index = start; index <= end; index++)
        {
            //if index is burst at the end, all balloons except index in (start, end) will be burst before
            //balloon to left of index will be at start - 1 and balloon to right of index will be at end + 1
            //get values considering out of bounds check
            int valueFromLeft = (start == 0) ? 1 : nums[start - 1];
            int valueFromRight = (end == nums.length - 1) ? 1 : nums[end + 1];
            int currentBurst = valueFromLeft * nums[index] * valueFromRight;

            int leftProblemResult = findMaxValue(nums, start, index - 1, cache);
            int rightProblemResult = findMaxValue(nums, index + 1, end, cache);

            //current burst is added with results of 2 subproblems
            currentBurst += leftProblemResult + rightProblemResult; 

            maxValue = Math.max(maxValue, currentBurst); //update result
        }

        cache[start][end] = maxValue; //update cache
        return maxValue;
    } 

    //time - O(n^3)
    //space - O(n^2)
    private int findMaxValue(int[] nums)
    {
        //result[i][j] tracks maximum value possible by bursting balloons in range nums[i, j]
        int[][] result = new int[nums.length][nums.length];

        //recursive function call was f(0, n - 1) with start as 0 and end as n - 1
        //iterate in reverse order to solve smaller subproblems first
        for(int start = nums.length - 1; start >= 0; start--)
        {
            //end always starts from last index and shrinks till start
            for(int end = start; end < nums.length; end++)
            {
                result[start][end] = Integer.MIN_VALUE;
                //any balloon in this range can be burst last
                for(int index = start; index <= end; index++)
                {
                    //if index is burst at the end, all balloons except index in (start, end) will be burst before
                    //balloon to left of index will be at start - 1 and balloon to right of index will be at end + 1
                    //get values considering out of bounds check
                    int valueFromLeft = (start == 0) ? 1 : nums[start - 1];
                    int valueFromRight = (end == nums.length - 1) ? 1 : nums[end + 1];
                    int currentBurst = valueFromLeft * nums[index] * valueFromRight;

                    int leftProblemResult = (index - 1 < start) ? 0 : result[start][index - 1];
                    int rightProblemResult = (index + 1 > end) ? 0 : result[index + 1][end];

                    //current burst is added with results of 2 subproblems
                    currentBurst += leftProblemResult + rightProblemResult; 

                    result[start][end] = Math.max(result[start][end], currentBurst);
                }
            }
        }

        return result[0][nums.length - 1];
    }
}
