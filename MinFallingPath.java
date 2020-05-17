// 931.
// time - O(m * n)
//space - O(m * n)
class Solution {
    public int minFallingPathSum(int[][] A) {
        //edge
        if(A == null || A.length == 0)
        {
            return 0;
        }
        int[][] result = new int[A.length][A[0].length];
        //if the matrix has only one row, the copy that row to result
        for(int i = 0; i < A[0].length; i++)
        {
            result[0][i] = A[0][i];
        }
        for(int i = 1; i < A.length; i++)
        {
            for(int j = 0; j < A[0].length; j++)
            {
                if(j == 0)
                {
                    //1st column
                    //for this cell i,j, fall could be from i-1,j(top) or i-1,j+1(right)
                    //add the value in i/p at this cell with min of 2 options
                    int top = result[i - 1][j] + A[i][j];
                    int right = result[i - 1][j + 1] + A[i][j];
                    result[i][j] = Math.min(top, right);
                }
                else if(j == A[0].length - 1)
                {
                    //last column
                    //for this cell i,j, fall could be from i-1,j(top) or i-1,j-1(left)
                    //add the value in i/p at this cell with min of 2 options
                    int top = result[i - 1][j] + A[i][j];
                    int left = result[i - 1][j - 1] + A[i][j];
                    result[i][j] = Math.min(top, left);
                }
                else
                {
                    //middle columns
                    //last column
                    //for this cell i,j, fall could be from i-1,j(top) or i-1,j-1(left) or i-1,j+1(right)
                    //add the value in i/p at this cell with min of 3 options
                    int top = result[i - 1][j] + A[i][j];
                    int left = result[i - 1][j - 1] + A[i][j];
                    int right = result[i - 1][j + 1] + A[i][j];
                    result[i][j] = Math.min(top, Math.min(left, right));
                }
            }
        }
        int answer = Integer.MAX_VALUE;
        //return in sum in last row
        for(int i = 0; i < A[0].length; i++)
        {
            answer = Math.min(answer, result[A.length - 1][i]);
        }
        return answer;
    }
}
