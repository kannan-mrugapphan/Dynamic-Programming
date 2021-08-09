// 132.
// time - O(n^2) for building the boolean matrix and O(n^2) for finding min cuts
// space - O(n^2)
class Solution {
    public int minCut(String s) {
        //edge
        if(s == null || s.length() == 0)
        {
            return 0; //no cuts needed
        }
        
        //tracks if every substring in s is a palindrome
        boolean[][] isSubstringPalindrome = new boolean[s.length()][s.length()]; 
        
        //for all i starting from last index
        //for each i consider all j starting from i and going till end of string
        for(int i = s.length() - 1; i >= 0; i--)
        {
            for(int j = i; j < s.length(); j++)
            {
                //consider the substring s(i, j)
                //if the char at i and j are same and the size of substring less than 4 or if s(i + 1, j - 1)
                //is a plaindrome then s(i, j) is a palindrome
                if(s.charAt(i) == s.charAt(j) && (j - i <= 2 || isSubstringPalindrome[i + 1][j - 1]))
                {
                    isSubstringPalindrome[i][j] = true;
                }
            }
        }
        
        int[] cutsNeeded = new int[s.length()]; //ith index tracks min cuts needed to break a string of length i
        
        for(int i = 1; i < s.length(); i++)
        {
            int maxCuts = i; //max cuts needed for a string of size i is i
            for(int j = 0; j <= i; j++)
            {
                if(isSubstringPalindrome[j][i])
                {
                    //if s(j, i) is a palindrome
                    int currentCutsNeeded = (j == 0) ? 0 : 1 + cutsNeeded[j - 1];
                    maxCuts = Math.min(maxCuts, currentCutsNeeded); //minmizing max cuts needed
                }
            }
            cutsNeeded[i] = maxCuts;
        }
        
        return cutsNeeded[cutsNeeded.length - 1];
    }
}
