// 474.
//brute force - find power set, iterate through each element in power set and pick the longest one with given condition
class Solution {
    public int findMaxForm(String[] strs, int m, int n) {
        return findLargestSubset(strs, 0, 0, 0, m, n, new Integer[strs.length][m + 1][n + 1]);
    }
    
    //time = O(length of strs[] * m * n)
    //space = O(length of strs[] * m * n) + call stack size = O(length of strs[])
    private int findLargestSubset(String[] strs, int index, int currentZeros, int currentOnes, int maxZeros, int maxOnes, Integer[][][] cache) {
        //base
        if(index == strs.length)
        {
            return 0; //all words are consumed
        }
        //check cache
        if(cache[index][currentZeros][currentOnes] != null)
        {
            return cache[index][currentZeros][currentOnes];
        }
        
        //logic
        int largestSet = Integer.MIN_VALUE; //return value
        //for the current word strs[index], there are 2 choices, include it and skip it
        int zeros = countZeros(strs[index]); //get 0 and 1 count in current word
        int ones = strs[index].length() - zeros;
        
        if(currentZeros + zeros <= maxZeros && currentOnes + ones <= maxOnes)
        {
            //choosing current word is feasible
            largestSet = Math.max(largestSet, 1 + findLargestSubset(strs, index + 1, currentZeros + zeros, currentOnes + ones, maxZeros, maxOnes, cache));
        }
        
        largestSet = Math.max(largestSet, findLargestSubset(strs, index + 1, currentZeros, currentOnes, maxZeros, maxOnes, cache));//skip case
        
        cache[index][currentZeros][currentOnes] = largestSet; //update cache
        return largestSet; 
    }
    
    //time - O(length of word)
    private int countZeros(String word) {
        int result = 0;
        for(int i = 0; i < word.length(); i++)
        {
            if(word.charAt(i) == '0')
            {
                result++;
            }
        }
        return result;
    }
}
