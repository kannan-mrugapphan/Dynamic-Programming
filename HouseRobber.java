// 198.
class Solution {
    public int rob(int[] nums) {
        //edge
        if(nums == null || nums.length == 0)
        {
            return 0;
        }
        if(nums.length == 1)
        {
            return nums[0];
        }
        if(nums.length == 2)
        {
            return Math.max(nums[0], nums[1]);
        }

        //return findProfit(nums, nums.length - 1, new Integer[nums.length]);
        //return findProfitTabulation(nums);
        return findProfit(nums);
    }

    //findProfit(i) tracks the max money possible from houses in the range 0 to i
    //time - O(2^n) for each house there are 2 options (steal or skip)
    //space - O(n) max call stack size
    //time with memoization - O(n)
    //space with memoization - O(n) for cache and O(n) for call stack
    private int findProfit(int[] nums, int index, Integer[] cache)
    {
        //base 1
        if(index == 0)
        {
            return nums[0]; //rob house 0 if only house 0 is present
        }
        //base 2
        if(index == 1)
        {
            return Math.max(nums[0], nums[1]); //rob the house with largest cash, if there are only 2 houses
        }

        //check cache
        if(cache[index] != null)
        {
            return cache[index];
        }

        int steal = nums[index] + findProfit(nums, index - 2, cache); //rob current house and goto index - 2 house
        int skip = findProfit(nums, index - 1, cache); //skip current and goto prev house

        int result = Math.max(steal, skip); //return max between 2 options
        cache[index] = result; //update cache
        return result;
    }

    //time - O(n)
    //space - O(n)
    private int findProfitTabulation(int[] nums)
    {
        int[] result = new int[nums.length]; //result[i] tracks the max money possible from 0th index to ith index
        result[0] = nums[0]; //base case 1 in recursion
        result[1] = Math.max(nums[0], nums[1]); //base case 2 in recursion

        for(int i = 2; i < nums.length; i++)
        {
            int steal = nums[i] + result[i - 2]; //rob current and goto i-2 house
            int skip = result[i - 1]; //skip current and goto i-1 house
            result[i] = Math.max(steal, skip); //largest among 2 options
        }

        return result[nums.length - 1];
    }

    //time - O(n)
    //space - constant
    private int findProfit(int[] nums)
    {
        //just have 2 variables to track the profit till i-2 and i-1 house
        int secondPrevious = nums[0]; //result[0]
        int previous = Math.max(nums[0], nums[1]); //result[1]

        for(int i = 2; i < nums.length; i++)
        {
            int steal = nums[i] + secondPrevious; //rob current and goto i-2 house
            int skip = previous; //skip current and goto i-1 house
            int current = Math.max(steal, skip); //largest among 2 options

            //update prev and secondPrev for next iteration
            secondPrevious = previous;
            previous = current;
        }

        return previous;
    }
}
