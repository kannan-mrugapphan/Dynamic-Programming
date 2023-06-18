// 494.
class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        //return findWays(nums, nums.length - 1, target, 0);
        //edge
        if(nums == null || nums.length == 0)
        {
            return 0;
        }
        
        //edge
        if(nums.length == 1)
        {
            //only 1 element matching target return 1 way else 0 way
            if(nums[0] == target || nums[0] == -target)
            {
                return 1;
            }
            return 0;
        }

        //total sum
        int sum = 0;
        for(int num : nums)
        {
            sum += num;
        }

        int simplifiedTarget = (sum - target) / 2;
        //if total sum is less than target or simplifiedTarget is odd then no ways possible
        if((sum - target) % 2 != 0 || sum < target)
        {
            return 0;
        }

        //return findWays(nums, nums.length - 1, simplifiedTarget, new Integer[nums.length][simplifiedTarget + 1]);
        return findWays(nums, simplifiedTarget);
    }

    //findWays(i) - tracks total ways to assign sign for elements in nums[0, i]
    //time - O(2^n) - 2 choices(+/-) for each n
    //space - O(n) - max call stack size
    //tabulation solution not possible because of -ve keys
    //memoization needs hashmap to handle -ve keys
    private int findWays(int[] nums, int index, int sum, int target)
    {
        //base
        if(index < 0)
        {
            //signs are assigned to all elements
            if(sum == target)
            {
                return 1; //1 valid way of sign assignment is found
            }
            return 0; //invalid sign assignment
        }

        //add + to current element
        int plusCase = findWays(nums, index - 1, sum + nums[index], target);
        //add - to current element
        int minusCase = findWays(nums, index - 1, sum - nums[index], target);

        return plusCase + minusCase; //sum of 2 choices
    }

    //let s be the sum of all elements in nums[]
    //nums[] has to broken into 2 subsets where the elements in 1st will have +sign and ones in 2nd will have -sign
    //let s1 be sum of elements in 1st subset and s2 be 2nd subset's sum
    //s1-s2 = target
    //(s - s2) - s2 = target => s2 = (s - target) / 2 (s2 is found to handle -ve targets)
    //find number of ways to make a subset where sum of elements is (s - target) / 2
    //time - O(2^n) [with memoization - O(n * target)]
    //space - O(n) [with memoization - O(n * target) + O(n)]
    private int findWays(int[] nums, int index, int remainingAmount, Integer[][] cache)
    {
        //regular base cases
        // if(remainingAmount == 0)
        // {
        //     return 1;
        // }

        // if(index < 0 || remianingAMount < 0)
        // {
        //     return 0;
        // }
        
        //base 
        //modified base case to handle 0s.
        //eg: [0, 0, 0, 1] with target as 1 should return 4
        if(index == 0)
        {
            //only 1st element remaining
            //1st element is 0 and remainingAmount is 0
            if(nums[0] == 0 && remainingAmount == 0)
            {
                return 2; //2ways possible by including and skipping current 0
            }
            if(remainingAmount == 0 || nums[0] == remainingAmount)
            {
                return 1; //include current element if nums[0] == remainingAmount else remainingAmount is 0 so skip current
            }
            return 0; //skip current
        }

        //check cache
        if(cache[index][remainingAmount] != null)
        {
            return cache[index][remainingAmount];
        }

        //skip current element
        int skip = findWays(nums, index - 1, remainingAmount, cache);
        int pick = 0;
        //pick is possible only when current element is smaller than remaining amount
        if(nums[index] <= remainingAmount)
        {
            pick = findWays(nums, index - 1, remainingAmount - nums[index], cache);
        }

        cache[index][remainingAmount] = skip + pick; //update cache
        return skip + pick;
    }

    //time - O(n * target)
    //space - O(n * target)
    private int findWays(int[] nums, int target)
    {
        int[][] result = new int[nums.length][target + 1]; //result[i][j] tracks total ways to make j with nums[0, i]

        //base case in recursion
        for(int col = 0; col <= target; col++)
        {
            //remainingAmount is 0 and 0th index is 0
            if(col == 0 && nums[0] == 0)
            {
                result[0][0] = 2; //2 ways possible skip and include current 0
            }

            else if(nums[0] == col || col == 0)
            {
                result[0][col] = 1; //1 way possible pick current element
            }

            else
            {
                result[0][col] = 0;
            }
        }

        for(int row = 1; row < nums.length; row++)
        {
            for(int remainingAmount = 0; remainingAmount <= target; remainingAmount++)
            {
                //skip current element
                int skip = result[row - 1][remainingAmount]; 
                int pick = 0;
                //pick is possible only when current element is smaller than remaining amount
                if(nums[row] <= remainingAmount)
                {
                    pick = result[row - 1][remainingAmount - nums[row]];
                }

                result[row][remainingAmount] = skip + pick;
            }
        }

        return result[nums.length - 1][target];
    }
}
