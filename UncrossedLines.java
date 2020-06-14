// 1035.
// time - O(m * n)
// space - O(m * n)
class Solution {
    public int maxUncrossedLines(int[] A, int[] B) {
        //approach similar to longest common sub sequence
        int[][] result = new int[A.length + 1][B.length + 1];
        
        //base
        //no need to set explicitly
        for(int i = 0; i <= B.length; i++)
        {
            result[0][i] = 0; //a[] is empty - so for any b[] number of uncrossed lines is 0
        }
        for(int i = 0; i <= A.length; i++)
        {
            result[i][0] = 0; //b[] is empty - so for any a[] number of uncrossed lines is 0
        }
        
        for(int i = 1; i <= A.length; i++)
        {
            for(int j = 1; j <= B.length; j++)
            {
                int numA = A[i - 1]; //current numbers in A[] and B[]
                int numB = B[j - 1];
                if(numA == numB)
                {
                    //draw a line b/w numA and numB (1 line so far) and add to result from A[0,...,i - 1] and B[0,...,j - 1]
                    result[i][j] = 1 + result[i - 1][j - 1];
                }
                else
                {
                    int case1 = result[i][j - 1]; //trying to add only numA
                    int case2 = result[i - 1][j]; //trying to add only numB
                    result[i][j] = Math.max(case1, case2);
                }
            }
        }
        
        return result[A.length][B.length];
    }
}
