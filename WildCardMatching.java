// 44.
class Solution {
    public boolean isMatch(String s, String p) {
        //return isValid(p, s, p.length(), s.length(), new Boolean[p.length() + 1][s.length() + 1]);
        return isValid(p, s);
    }

    //isValid(i, j) tracks if pattern[0, i] matches word[0, j]
    //time - O(2^(m+n))
    //space - O(m+n)
    //time with memoization - O(mn)
    //space with memoization - O(mn) for cache and O(m+n) for call stack
    private boolean isValid(String pattern, String word, int i, int j, Boolean[][] cache)
    {
        //base 1
        if(j == 0)
        {
            //all chars in word are matched
            while(i > 0)
            {
                if(pattern.charAt(i - 1) == '*')
                {
                    i--; //current char in pattern is * so skip it and not match with any char in word
                }
                else
                {
                    return false; //valid y=unmatched char is present in pattern that isn't matched with word
                }
            }

            return true; //all chars in word are matched
        }
        //base 2
        if(i == 0)
        {
            //pattern exhausted but word has more chars
            return false;
        }

        //check cache
        if(cache[i][j] != null)
        {
            return cache[i][j];
        }

        //if current character matches or current char in pattern is ? which matches any char in word
        if(pattern.charAt(i - 1) == word.charAt(j - 1) || pattern.charAt(i - 1) == '?')
        {
            boolean result = isValid(pattern, word, i - 1, j - 1, cache);
            cache[i][j] = result; //update cache
            return result;
        }

        //else if current current char in pattern is * which matches 0 or more chars in word
        else if(pattern.charAt(i - 1) == '*')
        {
            boolean skip = isValid(pattern, word, i - 1, j, cache); //skip current * to account for * not matching any char
            boolean pick = isValid(pattern, word, i, j - 1, cache); //current * matches j in word and i remains at current * to account for it matching multiple chars in word

            boolean result = skip || pick; //return true if one case returns true
            cache[i][j] = result; //update cache
            return result;
        }

        cache[i][j] = false; //update cache
        return false; //else case char at pattern is not ? or * and not macthes with char in word
    }

    //time - O(mn)
    //space - O(mn) 
    private boolean isValid(String pattern, String word)
    {
        //result[i][j] tracks if pattern[0, i] matches word[0, j]
        boolean[][] result = new boolean[pattern.length() + 1][word.length() + 1];

        result[0][0] = true; //emptry pattern matches empty word - base case
        for(int i = 1; i <= word.length(); i++)
        {
            result[0][i] = false; //empty pattern doesn't match non-empty word - base case
        }

        for(int i = 1; i <= pattern.length(); i++)
        {
            if(pattern.charAt(i - 1) == '*')
            {
                result[i][0] = result[i - 1][0]; //empty word and * in pattern - skip case - base case
            }
            else
            {
                result[i][0] = false; //non empty pattern empty word - base case
            }
        }

        for(int i = 1; i <= pattern.length(); i++)
        {
            for(int j = 1; j <= word.length(); j++)
            {
                //if current character matches or current char in pattern is ? which matches any char in word
                if(pattern.charAt(i - 1) == word.charAt(j - 1) || pattern.charAt(i - 1) == '?')
                {
                    result[i][j] = result[i - 1][j - 1];
                }

                //else if current current char in pattern is * which matches 0 or more chars in word
                else if(pattern.charAt(i - 1) == '*')
                {
                    boolean skip = result[i - 1][j]; //skip current * to account for * not matching any char
                    boolean pick = result[i][j - 1]; //current * matches j in word and i remains at current * to account for it matching multiple chars in word

                    result[i][j] = skip || pick; //return true if one case returns true
                }

                //else case char at pattern is not ? or * and not macthes with char in word
                else
                {
                    result[i][j] = false;
                }
            }
        }

        return result[pattern.length()][word.length()];
    }
}
