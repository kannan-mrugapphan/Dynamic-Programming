// 132.
class Solution {
    public int minCut(String s) {
        //eg: s = 'abc'
        //only possible partition = 'a', 'b', 'c'
        //formed by 1 + f(bc) -> 1 + f(c) -> 1 + f('') 
        //            3       <-   2      <-   1      <- 0
        //even after string exhausted, it considers the last partition palindromeSubstring + f('') as a partition
        //last valid partition is not needed
        //return findMinPartitions(s, 0, new Integer[s.length()]) - 1;
        return findMinPartitions(s) - 1;
    }

    //input string can be broken into n-1 strings where each string is one character which is a palindrome
    //max partitions needed is n-1 -> minimize the number of partitions

    //findMinPartitions(i) returns min partitions to break s[i, length - 1]
    //space - O(n) max call stack size where each character has its own partition to form n-1 partitions
    //time with memoization - O(n ^ 2) -> n states and for loop for each state
    //space with memoization - O(n) for cache and O(n) for call stack
    private int findMinPartitions(String s, int start, Integer[] cache)
    {
        //base
        if(start == s.length())
        {
            return 0; //string exhausted, no more partitions possible
        }

        //chec cache
        if(cache[start] != null)
        {
            return cache[start];
        }

        int minPartitions = Integer.MAX_VALUE; //return value
        for(int index = start; index < s.length(); index++)
        {
            String currentSubstring = s.substring(start, index + 1); //substing last argument is exclusive
            //break s[start, index] into 1 partition
            if(isPalindrome(currentSubstring))
            {
                int partitions = 1 + findMinPartitions(s, index + 1, cache); //recursively break remaining string
                minPartitions = Math.min(minPartitions, partitions); //update result
            }
        }
        
        cache[start] = minPartitions; //update cache
        return minPartitions;
    }

    //time - O(n^2)
    //space - O(n)
    private int findMinPartitions(String s)
    {
        //result[i] tracks min partitions to break s[i, end of string]
        int[] result = new int[s.length() + 1];
        Arrays.fill(result, Integer.MAX_VALUE); //minimize result
        result[s.length()] = 0; //empty string - base case

        for(int start = s.length() - 1; start >= 0; start--)
        {
            for(int index = start; index < s.length(); index++)
            {
                String currentSubstring = s.substring(start, index + 1); //substing last argument is exclusive
                //break s[start, index] into 1 partition
                if(isPalindrome(currentSubstring))
                {
                    int partitions = 1 + result[index + 1]; //recursively break remaining string
                    result[start] = Math.min(result[start], partitions); //update result
                }
            }
        }

        return result[0];
    }

    private boolean isPalindrome(String s)
    {
        int start = 0;
        int end = s.length() - 1;

        //as long as there are valid chars
        while(start <= end)
        {
            if(s.charAt(start) != s.charAt(end))
            {
                return false;
            }
            start++;
            end--;
        }

        return true;
    }
}
