class Solution{
    static int matrixMultiplication(int N, int arr[])
    {
        //edge
        if(arr == null || arr.length <= 2)
        {
            return 0;
        }
        
        //return findMinimumMultiplications(arr, 1, arr.length - 1, new Integer[arr.length][arr.length]);
    
        return findMinimumMultiplications(arr);
    }
    
    //2 matrices of order (a*b) and (b*c) can be multiplied as cols in 1st matrix is same as rows in 2nd matrix
    //order of result matrix is (a*c)
    //total number of multiplications needed is a*b*c
    
    //f(start, end) returns minimum multiplications needed to multiply matrices in nums[start, end]
    //time with memoization - O(n^3) -> n^2 states and n time for each state
    //space - O(n^2) for cache and O(n) for call stack
    private static int findMinimumMultiplications(int[] nums, int start, int end, Integer[][] cache)
    {
        //base 
        if(start == end)
        {
            //only 1 matrix remains
            return 0; 
        }
        
        //check cache
        if(cache[start][end] != null)
        {
            return cache[start][end];
        }
        
        int minimumMultiplicationsNeeded = Integer.MAX_VALUE; //return val
        
        //let A,B,C,D be the matrices tracked by start and end
        //A(BCD), (AB)(CD) and (ABC)D are possible options
        //possible partitions are from start to end - 1
        for(int index = start; index < end; index++)
        {
            //consider index to break ABCD into (AB)(CD)
            //start = 1, end = 4, index = 2 -> 1 based indexing as nums[] has 5 elements to track orders of 4 matrices
            //left sub problem = (AB) -> start = 1 and end = index = 2
            //right sub problem = (CD) -> start = index + 1 (3) and end = 4
            
            //left subproblem computes to one matrix of order (nums[start - 1] * nums[index])
            //right subproblem computes to one matrix of order (nums[index] * nums[end])
            
            //these 2 subproblem results are again multiplies at current level to form 1 matrix of order (nums[start - 1] * nums[end])
            //multiplications needed for this are nums[start - 1] * nums[index] * nums[end]
            int multiplicationsNeeded = nums[start - 1] * nums[index] * nums[end];
            
            int leftSubProblem = findMinimumMultiplications(nums, start, index, cache);
            int rightSubProblem = findMinimumMultiplications(nums, index + 1, end, cache);
            
            multiplicationsNeeded += leftSubProblem + rightSubProblem; //add multiplications needed for subproblems
            minimumMultiplicationsNeeded = Math.min(minimumMultiplicationsNeeded, multiplicationsNeeded);
        }
        
        cache[start][end] = minimumMultiplicationsNeeded; //update cache
        return minimumMultiplicationsNeeded;
    }
    
    //time - O(n^3)
    //space - O(n^2)
    private static int findMinimumMultiplications(int[] nums)
    {
        //result[i][j] tracks minimum multiplications needed for nums[i, j]
        int[][] result = new int[nums.length][nums.length];
        
        for(int start = nums.length - 1; start >= 1; start--)
        {
            for(int end = start; end < nums.length; end++)
            {
                result[start][end] = Integer.MAX_VALUE; //minimize result
                if(start == end)
                {
                    result[start][end] = 0; //base case - 1 matrix - no multiplication needed
                }
                else
                {
                    for(int index = start; index < end; index++)
                    {
                        int multiplicationsNeeded = nums[start - 1] * nums[index] * nums[end];
            
                        int leftSubProblem = result[start][index];
                        //1st part of ternary check handles invalid range when start > end
                        int rightSubProblem = (index + 1 > end) ? 0 : result[index + 1][end];
                        
                        multiplicationsNeeded += leftSubProblem + rightSubProblem; //add multiplications needed for subproblems
                    
                        result[start][end] = Math.min(result[start][end], multiplicationsNeeded);
                    }
                }
            }
        }
        
        return result[1][nums.length - 1];
    }
}
