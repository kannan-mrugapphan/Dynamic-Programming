//1143.
class Solution {
    public int longestCommonSubsequence(String text1, String text2) {
        //return lcs(text1, text2, text1.length() - 1, text2.length() - 1, new Integer[text1.length()][text2.length()]);
        return lcs(text1, text2);
    }

    //lcs(i, j) tracks lcs between text1[0, i] and text2[0, j]
    //time - O(2^(min(m,n))) worst case char mismatch in each index, 2 choices for each index
    //space - O(min(m,n)) max call stack size
    //time with memo - O(mn)
    //space with memo - O(mn) for cache and O(min(m,n)) for call stack
    private int lcs(String text1, String text2, int i, int j, Integer[][] cache)
    {
        //base
        if(i < 0 || j < 0)
        {
            return 0; //1 string is exhausted, no more string matching possible
        }

        //check cache
        if(cache[i][j] != null)
        {
            return cache[i][j];
        }

        //current chars in both strs are same
        if(text1.charAt(i) == text2.charAt(j))
        {
            int result = 1 + lcs(text1, text2, i - 1, j - 1, cache); //pick current char as part of result and recurse on remaining substrings
            cache[i][j] = result; //update cache
            return result;
        }

        //try ignoring ith char from text1 in the 1st case
        //try ignoring jth char from text2 in 2nd case
        //choose max among 2 cases
        int result =  Math.max(lcs(text1, text2, i, j - 1, cache), lcs(text1, text2, i - 1, j, cache));
        cache[i][j] = result; //update cache
        return result;
    }

    //time - O(mn)
    //space - O(mn) for result[][]
    private int lcs(String text1, String text2)
    {
        int[][] result = new int[text1.length() + 1][text2.length() + 1]; //result[i][j] tracks lcs between text1[0, i] and text2[0, j]

        //base case already default to 0 upon init

        for(int i = 1; i <= text1.length(); i++)
        {
            for(int j = 1; j <= text2.length(); j++)
            {
                char text1Char = text1.charAt(i - 1);
                char text2Char = text2.charAt(j - 1);

                if(text1Char == text2Char)
                {
                    result[i][j] = 1 + result[i - 1][j - 1]; //pick (i, j) and recurse on remaining
                }

                else
                {
                    result[i][j] = Math.max(result[i - 1][j], result[i][j - 1]); //max among 2 cases obtained by skipping i or j
                }
            }
        }

        System.out.println(findLcs(result, text1, text2));
        return result[text1.length()][text2.length()];
    }

    //time - O(mn)
    //space - constant
    private String findLcs(int[][] lcs, String text1, String text2)
    {
        StringBuilder result = new StringBuilder();
        int i = text1.length(); //iterates over text1
        int j = text2.length(); //iterates over text2

        //as long as there are chars in either text1 or text2
        while(i > 0 && j > 0)
        {
            char text1Char = text1.charAt(i - 1);
            char text2Char = text2.charAt(j - 1);
            if(text1Char == text2Char)
            {
                result.append(text1Char); //char matches, pick char once in result
                i--; //both i and j are accounted
                j--; 
            }
            else
            {
                //char mismatch
                if(lcs[i][j] == lcs[i - 1][j])
                {
                    //came by ignoring ith char from 1st string text1
                    i--; //skip ith char
                }
                else
                {
                    //came by ignoring jth char from 2nd string text2
                    j--; //skip jth char
                }
            }
        }

        return result.reverse().toString();
    }
}

//Longest Palindromic Subsequence - 516.
//Same as LCS(input string, reverse of input string)

//Minimum Insertions to make string Palidrome - 1312.
//Retain the longest palindromic subsequence and insert the remaining mismatching chars at correct positions to form the palindrome
//Length of input string - Length of longest palindromic subsequence

//Deletion operation for 2 Strings - 583.
//Given 2 strings, allowed to delete characters in both strings, return minimum deletions to make strings equal
//Retain the longest common subsequence in both strings, delete everything in 1st and 2nd string except the chars in lcs
//Length of 1st string - length of lcs -> chars to be deleted in 1st string
//Length of 2nd string - length of lcs -> chars to be deleted in 2nd string
//sum of 2 is the result

//Shortest Common Supersequence - 1092.
//Result string = all chars in 1st string except lcs + all chars in 2nd string except lcs + chars in lcs
//len(m) + len(n) - 2len(lcs)

// 718.
class Solution {
    public int findLength(int[] nums1, int[] nums2) {
        return longestCommonSubArray(nums1, nums2);
    }

    //time - O(mn)
    //space - O(mn)
    private int longestCommonSubArray(int[] nums1, int[] nums2)
    {
        //result[i][j] is the longest common subarray between nums1 and nums2 such that resultant subarray ends at i for nums1 and j for nums2
        int[][] result = new int[nums1.length + 1][nums2.length + 1];
        int lcs = 0; //return val

        for(int i = 0; i <= nums2.length; i++)
        {
            result[0][i] = 0; //lcs between nums2 and nums1 is 0 if nums1 is empty
        }

        for(int i = 0; i <= nums1.length; i++)
        {
            result[i][0] = 0; //lcs between nums1 and nums2 is 0 if nums2 is empty
        }

        for(int i = 1; i <= nums1.length; i++)
        {
            for(int j = 1; j <= nums2.length; j++)
            {
                //ith element in nums1 is matching jth element in nums2
                //count 1 for i,j match and expand to left i.e i-1,j-1
                if(nums1[i - 1] == nums2[j - 1])
                {
                    result[i][j] = 1 + result[i - 1][j - 1];
                }
                //i and j mismatches
                //can't be expanded to left
                else
                {
                    result[i][j] = 0;
                }

                lcs = Math.max(lcs, result[i][j]); //update result
            }
        }

        return lcs;
    }
}
