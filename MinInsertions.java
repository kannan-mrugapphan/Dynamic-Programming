// 1312.
class Solution {
    public int minInsertions(String s) {
        //edge
        if(s == null || s.length() <= 1)
        {
            return 0; //no insertions needed
        }
        //find longest palindrome sub sequence and insert remaining chars to make whole string a palindrome
        return s.length() - longestPalidromicSubsequence(s); 
    }
    
    //time - O(n^2)
    //space - O(n^2)
    private int longestPalidromicSubsequence(String s) {
        int[][] result = new int[s.length()][s.length()];
        for(int i = 0; i < s.length(); i++)
        {
            for(int j = i; j >= 0; j--)
            {
                //sub sequences of length 1
                if(i == j)
                {
                    result[i][j] = 1;
                }
                else if(s.charAt(i) == s.charAt(j)) //current char matches
                {
                    result[i][j] = 2 + result[i - 1][j + 1]; //2 accounting for current match and pick from diagonal
                }
                else if(s.charAt(i) != s.charAt(j)) //current doesn't match
                {
                    result[i][j] = Math.max(result[i - 1][j], result[i][j + 1]); //max of prev row next col and same col prev row
                }
            }
        }
        return result[s.length() - 1][0]; //last row 0th col
    }
}
