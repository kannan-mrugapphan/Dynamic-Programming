// 72.
class Solution {
    public int minDistance(String word1, String word2) {
        // return findEditDistance(word1, word2, word1.length(), word2.length(), new Integer[word1.length() + 1][word2.length() + 1]);

        return findEditDistance(word1, word2);
    }

    //f(i, j) tracks min operations needed to transform s1(0, i) to s2(0, j) 
    //time - O(3^(m+n)) for each char in both strs 3 options possible
    //space - O(m+n) worst case m+n operations are needed
    //time with memoization - O(mn)
    //space with memoization - O(mn) for cache and O(m+n) for call stack
    private int findEditDistance(String s1, String s2, int i, int j, Integer[][] cache)
    {
        //base 1
        if(i == 0)
        {
            //all chars in s1 are used, insert the remaining 1st j chars from s2 at start of s1
            return j;
        }
        //base 2
        if(j == 0)
        {
            //all chars in s2 are used, delete 1st i chars start of s1
            return i;
        }

        //check cache
        if(cache[i][j] != null)
        {
            return cache[i][j];
        }

        //if current chars are same no operation is needed
        if(s1.charAt(i - 1) == s2.charAt(j - 1))
        {
            int result = findEditDistance(s1, s2, i - 1, j - 1, cache); //steps needed is same as f(i - 1, j- 1)
            cache[i][j] = result; //update cache
            return result;
        }
        else
        {
            //insert jth char of s2 at i and match s1[0, i] with s2[0, j - 1]
            int insertion = 1 + findEditDistance(s1, s2, i, j - 1, cache); 
            //delete ith char in s1 and match s1[0, i - 1] with s2[0, j]
            int deletion = 1 + findEditDistance(s1, s2, i - 1, j, cache);
            //replace ith char in s1 with jth char in s2 and match s1[0, i - 1] with s2[0, j - 1]
            int replace = 1 + findEditDistance(s1, s2, i - 1, j - 1, cache);

            int result =  Math.min(insertion, Math.min(deletion, replace)); //return min among 3 cases
            cache[i][j] = result; //update cache
            return result;
        }
    }

    //time - O(mn)
    //space - O(mn)
    private int findEditDistance(String s1, String s2)
    {
        //result[i][j] tracks min steps needed to convert s1[0, i] to s2[0, j]
        int[][] result = new int[s1.length() + 1][s2.length() + 1]; 

        for(int j = 0; j <= s2.length(); j++)
        {
            result[0][j] = j; //base 1 - s1 fully used
        }
        for(int i = 0; i <= s1.length(); i++)
        {
            result[i][0] = i; //base 2 - s2 fully used
        }

        for(int i = 1; i <= s1.length(); i++)
        {
            for(int j = 1; j <= s2.length(); j++)
            {
                if(s1.charAt(i - 1) == s2.charAt(j - 1))
                {
                    //char matches, no operation needed
                    result[i][j] = result[i - 1][j - 1];
                }
                else
                {
                    //insert jth char of s2 at i and match s1[0, i] with s2[0, j - 1]
                    int insertion = 1 + result[i][j - 1]; 
                    //delete ith char in s1 and match s1[0, i - 1] with s2[0, j]
                    int deletion = 1 + result[i - 1][j];
                    //replace ith char in s1 with jth char in s2 and match s1[0, i - 1] with s2[0, j - 1]
                    int replace = 1 + result[i - 1][j - 1];

                    result[i][j] =  Math.min(insertion, Math.min(deletion, replace)); //return min among 3 cases 
                }
            }
        }

        return result[s1.length()][s2.length()];
    }
}
