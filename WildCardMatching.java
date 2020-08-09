// 44.
class Solution {
    public boolean isMatch(String s, String p) {
        //edge
        if(s.equals(p) || p.equals("*"))
        {
            return true; //both source and pattern are same
        }
        if(s.length() == 0 || p.length() == 0)
        {
            return false; //one of the strings is empty
        }
        
        return wildcardMatchTwoPointers(s, p);
    }
    
    //time - O(length of source * length of pattern)
    //space - O(length of source * length of pattern)
    private boolean wildcardMatchDP(String s, String p) {
        int sLen = s.length();
        int pLen = p.length();
        boolean[][] result = new boolean[sLen + 1][pLen + 1]; //source along rows and pattern along cols
        
        //base
        result[0][0] = true; //empty pattern matches empty source
        for(int i = 1; i <= sLen; i++)
        {
            result[i][0] = false; //any source cant match an empty pattern
        }
        for(int i = 1; i <= pLen; i++)
        {
            if(p.charAt(i - 1) == '*') //exploring the 0 case alone i.e not including * in pattern and matching other parts
            {
                result[0][i] = result[0][i - 1]; 
            }
            //all other cells in 0th row are false -> source is empty
        }
        
        for(int i = 1; i <= sLen; i++)
        {
            for(int j = 1; j <= pLen; j++)
            {
                if(s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '?')
                {
                    //current char matches -> so match s[0 to i - 1] and p[o to j - 1]
                    result[i][j] = result[i - 1][j - 1];
                }
                else if(p.charAt(j - 1) == '*')
                {
                    boolean zeroCase = result[i][j - 1]; //ignore current * and match s[o to i] and p[o to j - 1]
                    boolean oneCase = result[i - 1][j]; //consider that current * matches matches current char at s and match s[0 to i - 1] and p[0 t o j]
                    result[i][j] = zeroCase || oneCase;
                }
                else //no need to set explicitly
                {
                    //chars not equal
                    result[i][j] = false;
                }
            }
        }
        
        return result[sLen][pLen];
    }
    
    //time - O(length of source + length of pattern)
    //space - O(length of source + length of pattern)
    private boolean wildcardMatchTwoPointers(String s, String p) {
        int sp = 0; //sp and pp iterates overs s and p
        int pp = 0;
        int s$ = -1; //s$ and p$ are used to pin the current location of sp and pp in case of * in p
        int p$ = -1;
        
        while(sp < s.length()) //as long as source string is not explored
        {
            if(pp < p.length() && (s.charAt(sp) == p.charAt(pp) || p.charAt(pp) == '?'))
            {
                //pp within bounds and current char matches in both strings so advance 
                sp++;
                pp++;
            }
            else if(pp < p.length() && p.charAt(pp) == '*')
            {
                //pin the locations of sp and pp using s$ and p$
                s$ = sp;
                p$ = pp;
                //advance pp - zero case where * doesnt match to any char in s
                pp++;
            }
            else if(p$ == -1)
            {
                //actual char mismatch
                return false;
            }
            else
            {
                //exploring zero case dint work - so return to pinned locations
                sp = s$;
                pp = p$;
                //explore one case where the current * matches current char in s
                sp++;
            }
        }
        
        //there could be a case whre s is exhausted and pp is at a point where all remaining chars in p are *
        //in such case return true - zero case
        while(pp < p.length())
        {
            if(p.charAt(pp) != '*')
            {
                return false;
            }
            pp++;
        }
        
        return true;
    }
}
