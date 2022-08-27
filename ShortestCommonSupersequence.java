// 1092. 
// approcah -> valid super sequence = str1 + str2 (longest possible)
// all chars in lcs between str1 and str2 can be present once in result
// shortest possible = str1.length() + str2.length - lcs(str1, str2)

// time - O(nm*(n+m))
// space - O(nm)
class Solution {
    public String shortestCommonSupersequence(String str1, String str2) {
        int[][] lcs = new int[str1.length() + 1][str2.length() + 1];
        
        //all cells in 0th row and 0th col can be left 0 as lcs(str1, '') = 0 and lcs('', str2) - 0
        
        for(int i = 1; i <= str1.length(); i++)
        {
            for(int j = 1; j <= str2.length(); j++)
            {
                if(str1.charAt(i - 1) == str2.charAt(j - 1)) //0 indexing
                {
                    lcs[i][j] = 1 + lcs[i - 1][j - 1]; //lcs(ABCS, ADCY) = 1 + lcs(BCS, DCY)
                }
                else
                {
                    lcs[i][j] = Math.max(lcs[i - 1][j], lcs[i][j - 1]); //lcs(match, catch) = max(lcs(atch, catch), lcs(match, atch))
                }
            }
        }
        
        String result = ""; //reverse of shortest common supersequence
        int i = lcs.length - 1; //pointer of str1
        int j = lcs[0].length - 1; //pointer of str2
        
        //as long as rows and cols are available in matrix
        while(i >= 1 && j >= 1)
        {
            if(str1.charAt(i - 1) == str2.charAt(j - 1))
            {
                //current char part of lcs
                result += str1.charAt(i - 1); //choose only one occurence
                i--;
                j--;
            }
            else if(lcs[i][j] == lcs[i - 1][j])
            {
                result += str1.charAt(i - 1); //str1 char didn't contribute to lcs so choose it
                i--; //j is choosen
            }
            else if(lcs[i][j] == lcs[i][j - 1])
            {
                result += str2.charAt(j - 1); //str2 char didn't contribute to lcs so choose it
                j--;
            }
        }
        
        while(i > 0)
        {
            result += str1.charAt(i - 1); //consume remaining chars in str1
            i--;
        }
        
        while(j > 0)
        {
            result += str2.charAt(j - 1); //consume remaining chars in str2
            j--;
        }
        
        String scs = "";
        for(int k = result.length() - 1; k >= 0; k--)
        {
            scs += result.charAt(k);
        }
        
        return scs; //return reverse of result
    }
}

//support problems
// longest palindrome subsequence = lcs(i/p string, reverse of i/p string)
// min delete operations to make 2 strings equal(583) = length of str1 + length of str2 + 2*lcs(str1, str2) -> idea is to retain only chars in lcs ->
// so delete all chars in both strings excepts ones in lcs -> eat, sea -> lcs = ea -> delete s in 2nd str and t in 1st str -> ans 2
