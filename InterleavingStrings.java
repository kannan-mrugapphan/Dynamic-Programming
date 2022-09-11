// 97.
class Solution {
    public boolean isInterleave(String s1, String s2, String s3) {
        //edge
        if(s1.length() + s2.length() != s3.length())
        {
            return false; //interleaved string must be exactly s1+s2 long
        }
        
        return isInterleaving(s1, s2, s3);
    }
    
    //time - O(2^(m+n)) -> worst case s1 = aa, s2 = aa, s3 = aaaa -> 2 choices in each level
    //space - O(m+n) -> call stack
    private boolean isValid(String s1, String s2, String s3, int i, int j) {
        //i iterates over s1 and j iterates over s2
        //i -> tracks current char in s1 that has to be tracked in s3
        //j -> tracks current char in s2 that has to be tracked in s3
        //i + j at any point tracks current char in s3 that has to be mapped to either s1 or s2
        
        //base
        if(i + j == s3.length())
        {
            //all chars in both strs matched
            return true;
        }
        //1st string exhausted
        if(i == s1.length())
        {
            //match all chars in 2nd string
            while(j < s2.length())
            {
                if(s2.charAt(j) == s3.charAt(i + j))
                {
                    j++;
                }
                else
                {
                    return false;
                }
            }
            return true;
        }
        //2nd string exhausted
        if(j == s2.length())
        {
            //match all chars in 1st string
            while(i < s1.length())
            {
                if(s1.charAt(i) == s3.charAt(i + j))
                {
                    i++;
                }
                else
                {
                    return false;
                }
            }
            return true;
        }
        
        //logic
        if(i < s1.length() && j < s2.length())
        {
            //chars to be mapped in both strs
            if(s1.charAt(i) != s3.charAt(i + j) && s2.charAt(j) != s3.charAt(i + j))
            {
                //both chars in s1 and s2 doesn't match to s3
                return false;
            }
            else if(s1.charAt(i) != s3.charAt(i + j))
            {
                //char at s2 matches s3
                return isValid(s1, s2, s3, i, j + 1);
            }
            else if(s2.charAt(j) != s3.charAt(i + j))
            {
                //char at s1 matches s3
                return isValid(s1, s2, s3, i + 1, j);
            }
            else
            {
                //both chars matches
                return isValid(s1, s2, s3, i + 1, j) || isValid(s1, s2, s3, i, j + 1);
            }
        }
        
        return false; //code never reaches here
    }
    
    //time - O(mn)
    //space - O(mn)
    private boolean isInterleaving(String s1, String s2, String s3) {
        
        //result[i][j] tracks whether s1[0, i] and s2[0, j] form s3[0, i+j]
        boolean[][] result = new boolean[s1.length() + 1][s2.length() + 1];
        result[0][0] = true; //empty s1 and empty s2 is valid for empty s3
        
        for(int i = 1; i <= s1.length(); i++)
        {
            //second string is empty
            //try to match s1[0, i] with s3[0, i+0]
            if(s1.charAt(i - 1) == s3.charAt(i - 1) && result[i - 1][0])
            {
                //eg: s1 = aa and s3 = aa and i = 1 -> char match and s1[0, i - 1] is valid for s3[0, i - 1]
                //eg: s1 = aa and s3 = ca and i = 1 -> char match and s1[0, i - 1] is not valid for s3[0, i - 1]
                result[i][0] = true; 
            }
        }
        
        for(int i = 1; i <= s2.length(); i++)
        {
            //1st string is empty
            //try to match s2[0, i] with s3[0, i+0]
            if(s2.charAt(i - 1) == s3.charAt(i - 1) && result[0][i - 1])
            {
                result[0][i] = true;
            }
        }
        
        for(int i = 1; i <= s1.length(); i++)
        {
            for(int j = 1; j <= s2.length(); j++)
            {
                //s1[0, i] and s2[0, j]
                if(s1.charAt(i - 1) == s3.charAt(i + j - 1) && result[i - 1][j])
                {
                    //ith char in s1 matches i+j th char in s3
                    //try match s1[0, i-1] and s2[0, j] with s3[0, i+j-1]
                    result[i][j] = true; 
                }
                if(s2.charAt(j - 1) == s3.charAt(i + j - 1) && result[i][j - 1])
                {
                    //jth char in s2 matches i+j th char in s3
                    //try match s1[0, i] and s2[0, j - 1] with s3[0, i+j-1]
                    result[i][j] = true; 
                }
            }
        }
        
        return result[s1.length()][s2.length()];
    }
}
