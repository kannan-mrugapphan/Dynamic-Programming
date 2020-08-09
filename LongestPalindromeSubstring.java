// 5.

class Solution {
    public String longestPalindrome(String s) {
        //brute force - find all possible substrings and check for the longest palindrome - O(n^3)
        //edge
        if(s == null || s.length() == 0)
        {
            return s;
        }
        return longestPalindromeCenters(s);
    }
    
    //time - O(n^2)
    //space - O(n^2)
    private String longestPalindromeDP(String s) {
        boolean[][] result = new boolean[s.length()][s.length()];
        int start = 0; //start and end tracks the sub string which is longest palindrome so far
        int end = 0;
        for(int i = 0; i < s.length(); i++)
        {
            //current substring = s[j, i]
            for(int j = 0; j <= i; j++)
            {
                //eg - aba with j = 0 and i = 2 => s[j] = s[i] and i - j <= 2 so s[j, i] = true
                //eg - babab with j = 0 and i = 4 => s[j] = s[i] & result[i-1][j+1] is true i.e s[j+1,i-1] is a palindrome so s[j,i] is true
                //if s[j] = s[i] then s[j, i] = s[j + 1, i - 1] 
                if(s.charAt(j) == s.charAt(i) && (i - j <= 2 || result[i - 1][j + 1]))
                {
                    result[i][j] = true; 
                    //update start and end when a longer palindrome is found
                    if(i - j + 1 > end - start + 1)
                    {
                        start = j;
                        end = i;
                    }
                }
            }
        }
        return s.substring(start, end + 1);
    }
    
    //time - O(n^2)
    //space - constant
    private String longestPalindromeCenters(String s) {
        String result = "";
        
        //possible centers
        for(int i = 0; i < s.length(); i++)
        {
            String current = extendAroundCenter(s, i, i); //odd length strings
            if(current.length() > result.length())
            {
                result = current;
            }
            current = extendAroundCenter(s, i, i + 1); //even length strings
            if(current.length() > result.length())
            {
                result = current;
            }
        }
        
        return result;
    }
    
    //time - O(n)
    //space - constant
    private String extendAroundCenter(String s, int start, int end) {
        //start by comparing chars at start and end
        //if equal decrement start, increment end and repeat again till both pointers are within bounds of s
        while(start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end))
        {
            start--;
            end++;
        }
        //after processing start and end are 1 letter away - eg - babab with initial start and end = 2 will cause while() to end with start = -1 and end = 5 - so bring both pointers within range and return that substring
        start++;
        end--;
        String palindrome = s.substring(start, end + 1);
        return palindrome;
    }
}
