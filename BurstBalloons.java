// 312

//approach
//[b1, b2, b3, b4, b5, b6] -> if we decide to burst b4 first
//value = (b3*b4*b5) + value from (b1-b3) + value from (b5-b6) -> this inference is wrong because value(b1-b3) and value(b5-b6) cannot be solved independently due to dependence of b3 on b5 and b5 on b3

//if we decide to burst the balloons from last, this relation between sub problems will be broken
//[b4] (last balloon) -> [bx, b4] (2nd last stage, bx depends only on b4) -> [bx, by, b4] (3rd last stage, by depends on bx and b4) -> cross relation is broken
//so burst balloons from end

//f(i, j) -> tracks the [i, j] sub array as the remaining ballons
//for eaxh x in [i, j], burst it at the end to get (a[i - 1] * x * a[j + 1]) + value (i, x - 1) + value(x + 1, j) 
class Solution {
    public int maxCoins(int[] nums) {
        //return findMaxValue(nums, 0, nums.length - 1, new Integer[nums.length][nums.length]);
        return burstBallons(nums);
    }
    
    //time - O(n^3) -> n^2 states and n loop for each state
    //space - O(n^2)
    private int findMaxValue(int[] nums, int start, int end, Integer[][] cache) {
        //base
        if(start > end)
        {
            //array exhauseted
            return 0;
        }
        //check cache
        if(cache[start][end] != null)
        {
            return cache[start][end];
        }
        //start->end tracks current sub array 
        //logic
        int result = Integer.MIN_VALUE; //return value
        for(int last = start; last <= end; last++)
        {
            //try bursting each balloon in the end
            int leftBalloon = 1; //denotes the balloon to left of last when last is burst at the end
            int rightBalloon = 1; //denotes the balloon to right of last when last is burst at the end
            
            if(start != 0)
            {
                leftBalloon = nums[start - 1]; //valid left is present
            }
            if(end != nums.length - 1)
            {
                rightBalloon = nums[end + 1]; //valid right is present
            }
            
            int currentBurstValue = leftBalloon * nums[last] * rightBalloon;
            currentBurstValue += findMaxValue(nums, start, last - 1, cache); //recurse on remaing portion
            currentBurstValue += findMaxValue(nums, last + 1, end, cache);
            
            result = Math.max(result, currentBurstValue);
        }
        
        cache[start][end] = result;
        return result;
    }
    
    //time - O(n^3)
    //space - O(n^2)
    private int burstBallons(int[] nums) {
        int[][] result = new int[nums.length][nums.length]; //tracks all n^2 states
        
        for(int snippetSize = 1; snippetSize <= nums.length; snippetSize++)
        {
            //start by bursting the last balloon and increment in steps of 1
            for(int start = 0; start <= nums.length - snippetSize; start++)
            {
                //for all possible start indices with given snippet size
                int end = start + snippetSize - 1; //find the end index of current snippet
                
                for(int last = start; last <= end; last++)
                {
                    //for each balloon in current snippet, try bursting it at the end
                    int left = 1;  //values to left and right of last balloon
                    int right = 1;
                    
                    if(start != 0)
                    {
                        left = nums[start - 1]; //valid left and right present
                    }
                    if(end != nums.length - 1)
                    {
                        right = nums[end + 1];
                    }
                    
                    int currentBurstValue = (left * nums[last] * right);
                    int prevSubProblem = (start == last) ? 0 : result[start][last - 1]; //f(i, last - 1) in recursive call
                    int nextSubProblem = (end == last) ? 0 : result[last + 1][end]; //f(last + 1, j) in recursive call
                    
                    currentBurstValue += prevSubProblem + nextSubProblem;
                    result[start][end] = Math.max(result[start][end], currentBurstValue); //update result
                }
            }
        }
        
        return result[0][nums.length - 1];
    }
}
