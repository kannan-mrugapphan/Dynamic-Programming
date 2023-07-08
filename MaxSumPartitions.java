// 1043.
class Solution {
    public int maxSumAfterPartitioning(int[] arr, int k) {
        //return findBestPartition(arr, 0, k, new Integer[arr.length]);
        return findBestPartition(arr, k);
    }

    //findBestPartition(i) partitions nums[i, nums.length - 1] to get max sum
    //space - O(n) - each partition is of size 1 - max call stack size
    //time with memoization - O(n^2) - n states and n time for each state
    //space with memoization - O(n) for cache + call stack of O(n)
    private int findBestPartition(int[] nums, int start, int k, Integer[] cache)
    {
        //base
        if(start == nums.length)
        {
            return 0; //no more partitions possible
        }

        //check cache
        if(cache[start] != null)
        {
            return cache[start];
        }

        int maxSum = Integer.MIN_VALUE; //return value - return max sum
        int largestInCurrentSubArray = Integer.MIN_VALUE; //tracks largest element in current subarray
        //if k = 3, possible partitions are 
        //start alone
        //start, start + 1
        //start, start + 1, start + 2
        for(int index = start; index < start + k; index++)
        {
            if(index >= nums.length)
            {
                continue; //invalid partition
            }

            //update if larger element found
            largestInCurrentSubArray = Math.max(largestInCurrentSubArray, nums[index]); 
            int length = index - start + 1; //length of current subarray
            //current sum is length * largest element as largest element is replaced in all indices of current subarray plus recursively find sum of remaining array
            int sum = (largestInCurrentSubArray * length) + findBestPartition(nums, index + 1, k, cache);

            maxSum = Math.max(maxSum, sum); //update max sum
        }

        cache[start] = maxSum; //update cache
        return maxSum;
    }

    //time - O(n^2)
    //space - O(n)
    private int findBestPartition(int[] nums, int k)
    {
        //result[i] is max sum possible if nums[i, nums.length - 1] is partitioned in best way
        int[] result = new int[nums.length + 1];
        Arrays.fill(result, Integer.MIN_VALUE); //maximize result
        result[nums.length] = 0; //base case - 0 value if array is empty

        for(int start = nums.length - 1; start >= 0; start--)
        {
            int largestInCurrentSubArray = Integer.MIN_VALUE; //tracks largest element in current subarray
            for(int index = start; index < start + k; index++)
            {
                if(index >= nums.length)
                {
                    continue; //invalid partition
                }

                //update if larger element found
                largestInCurrentSubArray = Math.max(largestInCurrentSubArray, nums[index]); 
                int length = index - start + 1; //length of current subarray
                //current sum is length * largest element as largest element is replaced in all indices of current subarray plus recursively find sum of remaining array
                int sum = (largestInCurrentSubArray * length) + result[index + 1];

                result[start] = Math.max(result[start], sum); //update max sum
            }
        }

        return result[0];
    }
}
