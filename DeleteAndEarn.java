// 740.
class Solution {
    public int deleteAndEarn(int[] nums) {
        //if a particular element x is picked, then all occurences of x-1 and x+1 can be picked
        //if just 1 occurence of x is picked, then all occurrence of x-1 and x+1 will be deleted
        //the remaining occurences of x can picked with no more deletions
        //thus it is pick/skip all occurences of x
        
        int largestElement = nums[0]; //tracks largest element in nums[]
        for(int num : nums)
        {
        largestElement = Math.max(largestElement, num); 
        }

        int[] support = new int[largestElement + 1]; //support[i] is i * number of times i is present in nums[]
        for(int num : nums)
        {
            support[num] += num;
        }

        //if support[i] is picked, support[i-1] and suppport[i+1] can't be picked
        //considering this constraint, find the max value
        return findMaxValue(support); 
    }

    //house robber function
    //time - O(n)
    //space - O(n) for cache and O(n) for call stack
    private int findMaxValue(int[] support, int index, Integer[] cache)
    {
        //base 1
        if(index == 0)
        {
            return support[0]; //only 1 element remaining, pick it
        }
        //base 2
        if(index == 1)
        {
            return Math.max(support[0], support[1]); //2 elements remaining, pick largest
        }

        //check cache
        if(cache[index] != null)
        {
            return cache[index];
        }

        int skip = findMaxValue(support, index - 1, cache); //skip current
        int pick = support[index] + findMaxValue(support, index - 2, cache); //pick current and skip prev

        cache[index] = Math.max(skip, pick); //update cache
        return Math.max(skip, pick);
    }

    //time - O(n)
    //space - O(n)
    private int findMaxValue(int[] support)
    {
        if(support.length == 1)
        {
            return support[0]; //only 1 element remaining, pick it
        }
        if(support.length == 2)
        {
            return Math.max(support[0], support[1]); //2 elements remaining, pick largest
        }

        int[] result = new int[support.length];
        result[0] = support[0];
        result[1] = Math.max(support[0], support[1]); //base cases

        for(int index = 2; index < support.length; index++)
        {
            int skip = result[index - 1]; //skip current
            int pick = support[index] + result[index - 2]; //pick current and skip prev

            result[index] = Math.max(skip, pick); //update cache
        }

        return result[support.length - 1];
    }
}
