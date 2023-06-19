//55.
class Solution {
    public boolean canJump(int[] nums) {
        //edge
        if(nums == null || nums.length <= 1)
        {
            return true;
        }
        
        //return isPossible(nums, 0, new Boolean[nums.length]);
        //return isPossible(nums);
        return isPossibleGreedy(nums);
    }

    //isPossible(i) tracks if it is possible to reach last index from ith index
    //time - O(k*k^n) k is max of all elements in nums[], for each index, k jumps (k calls are possible)
    //space - O(n) max call stack size
    //time with memoization - O(nk)
    //space with memoization - O(n) for cache and O(n) for call stack
    private boolean isPossible(int[] nums, int index, Boolean[] cache)
    {
        //base 1
        if(index == nums.length - 1)
        {
            return true; //last index reached
        }
        //base 2
        if(index >= nums.length)
        {
            return false; //out of bounds
        }

        //check cache
        if(cache[index] != null)
        {
            return cache[index];
        }

        int maxJumpLength = nums[index]; //max jump length possible from given index
        for(int jumpLength = 1; jumpLength <= maxJumpLength; jumpLength++)
        {
            //simulate all possible jump lengths
            //if any possible jump length returns true
            if(isPossible(nums, index + jumpLength, cache))
            {
                cache[index] = true; //update cache
                return true;
            }
        }

        cache[index] = false; //update cache
        return false; 
    }

    //time - O(nk)
    //space - O(n) for result array
    private boolean isPossible(int[] nums)
    {
        boolean[] result = new boolean[nums.length]; //result[i] tracks if last index can be reached from ith index
        result[nums.length - 1] = true; //base case 1

        for(int index = nums.length - 2; index >= 0; index--)
        {
            int maxJumpLength = nums[index]; //max jump length possible from given index

            for(int jumpLength = 1; jumpLength <= maxJumpLength; jumpLength++)
            {
                //simulate all possible jump lengths
                //if any possible jump length returns true, it is possible to jump to last index from current index
                if(index + jumpLength < nums.length && result[index + jumpLength])
                {
                    result[index] = true;
                }
            }
        }

        return result[0];
    }

    //time - O(n)
    //space - constant
    private boolean isPossibleGreedy(int[] nums)
    {
        int dest = nums.length - 1; //last index 

        for(int index = nums.length - 2; index >= 0; index--)
        {
            int maxJumpLength = nums[index]; //max jump length possible from given index
            if(index + maxJumpLength >= dest)
            {
                //current dest can be reached from index by 1 jump
                dest = index; //update dest to current index

                if(index == 0)
                {
                    return true; //if 0th index is reached
                }
            }
        }

        return false;
    }
}

// 45.
class Solution {
    public int jump(int[] nums) {
        //edge
        if(nums == null || nums.length <= 1)
        {
            return 0;
        }

        //return findBestPath(nums, 0, new Integer[nums.length]);
        //return findBestPath(nums);
        return findBestPathGreedy(nums);
    }

    //findBestPath(i) tracks the min jumps needed to reach last index from i
    //time - O(k*k^n) k is max of all elements in nums[], for each index, k jumps (k calls are possible)
    //space - O(n) max call stack size
    //time with memoization - O(nk)
    //space with memoization - O(n) for cache and O(n) for call stack
    private int findBestPath(int[] nums, int index, Integer[] cache)
    {
        //base
        if(index == nums.length - 1)
        {
            return 0; //last index reached, no more jumps needed 
        }

        //check cache
        if(cache[index] != null)
        {
            return cache[index];
        }

        int maxJumpLength = nums[index]; //max jump length possible from given index
        int minJumpsNeeded = (int) 1e7; //min jumps needed to reach last index from current index - initially set to infinity

        for(int jumpLength = 1; jumpLength <= maxJumpLength; jumpLength++)
        {
            //simulate all possible jump lengths
            //if a jump length stays within bounds
            if(index + jumpLength < nums.length)
            {   
                //jump to index + jumpLength and recurse    
                int jumpsNeeded = 1 + findBestPath(nums, index + jumpLength, cache);
                minJumpsNeeded = Math.min(minJumpsNeeded, jumpsNeeded); //update result;
            }
        }

        cache[index] = minJumpsNeeded; //update cache
        return minJumpsNeeded;
    }

    //time - O(nk)
    //space - O(n) for result array
    private int findBestPath(int[] nums)
    {
        int[] result = new int[nums.length]; //result[i] = min jumps needed to reach last index from i
        result[nums.length - 1] = 0; //base case

        for(int index = nums.length - 2; index >= 0; index--)
        {
            int maxJumpLength = nums[index]; //max jump length possible from given index
            int minJumpsNeeded = (int) 1e7; //min jumps needed to reach last index from current index - initially set to infinity

            for(int jumpLength = 1; jumpLength <= maxJumpLength; jumpLength++)
            {
                //simulate all possible jump lengths
                //if future jump index is within bounds
                if(index + jumpLength < nums.length)
                {
                   //jump to index + jumpLength and recurse    
                    int jumpsNeeded = 1 + result[index + jumpLength];
                    minJumpsNeeded = Math.min(minJumpsNeeded, jumpsNeeded); //update result;
                }
            }

            result[index] = minJumpsNeeded;
        }

        return result[0];
    }

    //time - O(n)
    //space - constant
    private int findBestPathGreedy(int[] nums)
    {   
        //start and end tracks the bounds of current level
        //all indices within current level are reachable from 0th index
        //initially the 0th index is only reachable [start index]
        //1st level is just 0th index, start = end = 0
        int start = 0; 
        int end = 0;

        int jumpsNeeded = 0; //return value

        //as long as last index is not within current level
        while(end < nums.length - 1)
        {
            int farthestReachableIndex = Integer.MIN_VALUE; //tracks the farthest index reachable from any index in current level by 1 jump
            for(int index = start; index <= end; index++)
            {
                //for each index in current level
                int maxJumpLength = nums[index]; //max jump length possible from given index
                int largestIndex = index + maxJumpLength; //farthest index reachable from current index

                farthestReachableIndex = Math.max(farthestReachableIndex, largestIndex); //update farthestReachableIndex
            }

            jumpsNeeded++; //jump to next level
            start = end + 1; //update pointers to track next level
            end = farthestReachableIndex; 
        }

        return jumpsNeeded;
    }
}
