// 10.
// time - O(sLen * pLen)
// space - O(sLen * pLen)
class Solution {
    public boolean isMatch(String s, String p) {
        //edge
        if(p == null)
        {
            return s == null; //if pattern is empty return true only if main str is also empty
        }
        
        int pLen = p.length();
        int sLen = s.length();
        boolean[][] result = new boolean[sLen + 1][pLen + 1]; //main str along rows and pattern along cols
        
        //base
        result[0][0] = true; //empty apttern matches empty str
        for(int i = 1; i <= pLen; i++)
        {
            //main str is ''
            //if current char at p is *, then explore 0 case i.e not choose prev char in p - so check for val 2 steps back
            if(p.charAt(i - 1) == '*') 
            {
                result[0][i] = result[0][i - 2]; 
            }
            else //for all other chars, it wont match with empty str, so false, no need to set explicitly to false
            {
                result[0][i] = false;
            }
        }
        for(int i = 1; i <= sLen; i++)
        {
            result[i][0] = false; //pattern is '' - so it wont match any str, no need to explictly set to false
        }
        
        for(int i = 1; i <= sLen; i++)
        {
            for(int j = 1; j <= pLen; j++)
            {
                char pattern = p.charAt(j - 1);
                char match = s.charAt(i - 1);
                if(pattern == match || pattern == '.')
                {
                    //since current char in both matches check whether pattern[0,...,j - 1] = main[0,...,i - 1]  - pick from diagonal
                    result[i][j] = result[i - 1][j - 1];
                }
                else if(pattern == '*')
                {
                    boolean zeroCase = result[i][j - 2]; //not choose the char before *
                    if(match == p.charAt(j - 2) || p.charAt(j - 2) == '.')
                    {
                        //explore one case only if prev chars in p and s match
                        boolean oneCase = result[i - 1][j]; //current * matches current char in s, check if same p matches prev chars in s
                        zeroCase = zeroCase || oneCase; //answer is logical OR of both cases
                    }
                    result[i][j] = zeroCase;
                }
            }
        }
        
        return result[sLen][pLen];
    }
}
