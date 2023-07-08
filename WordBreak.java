// 139.
class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        HashSet<String> dict = new HashSet<>(wordDict);
        //return isPossible(s, 0, dict, new Boolean[s.length()]);
        return isPossible(s, dict);
    }

    //isPossible(i) tracks if it is possible to break s[i, end of string] into substrings such that each substring is present in the dict
    //space without memoization - O(n) worst case string broken into chars of length 1
    //time with memoization - O(n^2) - n states, n time for each state
    //space with memoization - O(n) for cache and O(n) for call stack
    private boolean isPossible(String s, int start, HashSet<String> dict, Boolean[] cache)
    {
        //base
        if(start == s.length())
        {
            return true; //empty string - whole string exhausted
        }

        //check cache
        if(cache[start] != null)
        {
            return cache[start];
        }

        for(int index = start; index < s.length(); index++)
        {
            String current = s.substring(start, index + 1); //2nd argument is exclusive so +1
            //if current is present and recurively find if the remaining string is presnt in dict
            if(dict.contains(current) && isPossible(s, index + 1, dict, cache))
            {
                cache[start] = true; 
                return true;
            }
        }

        cache[start] = false;
        return false; //all possible cominations tried
    }

    //time - O(n^2)
    //space - O(n)
    private boolean isPossible(String s, HashSet<String> dict)
    {
        //result[i] tracks if s[i, end of string] is valid
        boolean[] result = new boolean[s.length() + 1];
        result[s.length()] = true; //base case in recursion

        for(int start = s.length() - 1; start >= 0; start--)
        {
            for(int index = start; index < s.length(); index++)
            {
                String current = s.substring(start, index + 1); //2nd argument is exclusive so +1
                //if current is present and recurively find if the remaining string is presnt in dict
                if(dict.contains(current) && result[index + 1])
                {
                    result[start] = true; 
                }
            }
        }

        return result[0];
    }
}
