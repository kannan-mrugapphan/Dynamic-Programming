// 931.
class Solution {
    public int minFallingPathSum(int[][] matrix) {
        // int result = Integer.MAX_VALUE;

        // for(int col = 0; col < matrix[0].length; col++)
        // {
        //     result = Math.min(result, findPath(matrix, 0, col, new Integer[matrix.length][matrix[0].length]));
        // }

        // return result;

        return findPath(matrix);
    }

    //finds min falling path sum starting from cell (row, col)
    //time - O(3^mn) - for each cell in matrix, 3 choices are possible to get next cell in path - function called n times to get total time as O(n3^mn)
    //space - O(mn) - max call stack size
    //time with memoization - O(m * n)
    //space with memoization - O(m * n)
    private int findPath(int[][] matrix, int row, int col, Integer[][] cache)
    {   
        //base 
        //fell out of bounds and this path is invalid
        if(col < 0 || col >= matrix[0].length)
        {
            return (int)1e7;
        }

        //base 2 
        //last row reached, path can't be expanded
        if(row == matrix.length - 1)
        {
            return matrix[row][col];
        }

        //check cache
        if(cache[row][col] != null)
        {
            return cache[row][col];
        }

        int downPath = findPath(matrix, row + 1, col, cache); //next row same col
        int leftPath = findPath(matrix, row + 1, col - 1, cache); //next row prev col
        int rightPath = findPath(matrix, row + 1, col + 1, cache); //next row next col

        int result = matrix[row][col] + Math.min(downPath, Math.min(leftPath, rightPath)); //pick min among the 3 options

        cache[row][col] = result; //update cache
        return result;
    }
    
    //time - O(m * n)
    //space - O(m * n)
    private int findPath(int[][] matrix)
    {
        int[][] result = new int[matrix.length][matrix[0].length];

        //last row is same as matrix's last row - base case in recursion
        for(int col = 0; col < matrix[0].length; col++)
        {
            result[matrix.length - 1][col] = matrix[matrix.length - 1][col];
        }

        for(int row = matrix.length - 2; row >= 0; row--)
        {
            for(int col = 0; col < matrix[0].length; col++)
            {
                int leftPath = (col == 0) ? Integer.MAX_VALUE : result[row + 1][col - 1]; //next row prev col if within bounds
                int rightPath = (col == matrix[0].length - 1) ? Integer.MAX_VALUE : result[row + 1][col + 1]; //next row next col if within bounds
                int downPath = result[row + 1][col]; //next row same col

                result[row][col] = matrix[row][col] + Math.min(downPath, Math.min(leftPath, rightPath)); //pick min path
            }
        }

        //out of all paths starting at each col in 0th row, pick path with min falling path sum
        int answer = Integer.MAX_VALUE;
        for(int col = 0; col < matrix[0].length; col++)
        {
            answer = Math.min(answer, result[0][col]);
        }

        return answer;
    }
}
