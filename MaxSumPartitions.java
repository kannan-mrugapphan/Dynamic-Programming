// 1043.
class Solution {
    public int maxSumAfterPartitioning(int[] arr, int k) {
        //return findOptimalPartition(arr, k, 0, new Integer[arr.length]);
        return findMaxSum(arr, k);
    }
    
    //time - O(n^2) -> n possible states and for llop runs for n time for each state
    //space - O(2n) cache + call stack size
    private int findOptimalPartition(int[] nums, int k, int index, Integer[] cache) {
        //base
        if(index == nums.length)
        {
            return 0;
        }
        //check cache
        if(cache[index] != null)
        {
            return cache[index];
        }
        
        //logic
        int result = Integer.MIN_VALUE; //maximize result for nums starting from index
        //if k = 3, (index), (index, index + 1), (index, index + 1, index + 2) are 3 possible partitions
        //for each partition, find max element
        int maxInCurrentPartition = Integer.MIN_VALUE;
        for(int i = index; i < Math.min(nums.length, index + k); i++)
        {
            //partition = (index - i)
            maxInCurrentPartition = Math.max(maxInCurrentPartition, nums[i]); //update max element
            int currentPartitionSize = i - index + 1; //find size of current partiton
            //recurse
            int currentPartiton = (maxInCurrentPartition * currentPartitionSize) + findOptimalPartition(nums, k, i + 1, cache);
            result = Math.max(result, currentPartiton);
        }
        
        cache[index] = result;
        return result;
    }
    
    // time - O(n)
    // space - O(n)
    private int findMaxSum(int[] nums, int k) {
        int[] result = new int[nums.length];
        
        for(int i = 0; i < nums.length; i++)
        {
            //if k = 3, (i), (i, i - 1), (i, i- 1, i- 2) are 3 partitons
            int maxElement = Integer.MIN_VALUE;
            for(int j = i; j > i - k; j--)
            {
                if(j < 0)
                {
                    //invalid partiton
                    continue;
                }
                
                //current partiton (j - i)
                maxElement = Math.max(maxElement, nums[j]);
                int currentPartSize = (i - j + 1); //end - start + 1
                //prev exisits
                if(j - 1 >= 0)
                {
                    result[i] = Math.max(result[i],
                                    (maxElement * currentPartSize) + result[j - 1]);
                }
                //prev doesn't esisits
                else
                {
                    result[i] = Math.max(result[i], maxElement * currentPartSize);
                }
            }
        }
        
        return result[nums.length - 1];
    }
}
