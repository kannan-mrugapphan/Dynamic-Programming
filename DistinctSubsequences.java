// 115.
// time = space = O(mn)
class Solution {
    public int numDistinct(String s, String t) {
        //edge
        if(s.length() < t.length())
        {
            return 0;
        }
        
        //return countWays(s, t, 0, 0);
        int[][] result = new int[s.length() + 1][t.length() + 1];
        
        for(int i = 0; i <= s.length(); i++)
        {
            //t string is empty
            result[i][0] = 1; //1 way to get empty string from any s
        }
        for(int i = 1; i <= t.length(); i++)
        {
            //s is empty
            result[0][i] = 0; //no possible ways if s is empty
        }
        
        for(int i = 1; i <= s.length(); i++)
        {
            for(int j = 1; j <= t.length(); j++)
            {
                if(s.charAt(i - 1) == t.charAt(j - 1))
                {
                    int choose = result[i - 1][j - 1]; //ith char in s can be chossen or skipped
                    int skip = result[i- 1][j];
                    result[i][j] = choose + skip;
                }
                else
                {
                    result[i][j] = result[i - 1][j]; //only skip ith char in s is possible
                }
            }
        }
        
        return result[s.length()][t.length()];
    }
    
    //time - O(2^n) where n is length of s -> without cache
    private int countWays(String s, String t, int i, int j) {
        //base
        if(i == s.length())
        {
            //all chars in s are consumed
            if(j == t.length())
            {
                //t is fully covered
                return 1; //1 possible way found
            }
            return 0; //chars in t are still present but all chars in i are consumed
        }
        if(t.length() == j)
        {
            //all chars in t consumed
            return 1;
        }
        //logic
        if(s.charAt(i) == t.charAt(j)) 
        {
            int chooseI = countWays(s, t, i + 1, j + 1); //cw(babgbag, bag) = cw(abgbag, ag) -> choose current b in s
            int skipI = countWays(s, t, i + 1, j); //cw(babgbag, bag) = cw(abgbag, bag) -> skip current b in s
            return chooseI + skipI;
        } 
        else
        {
            //current char in i can't be choosen
            return countWays(s, t, i + 1, j);
        }
    }
}
