// 64.
class Solution {
    public int minPathSum(int[][] grid) {
        //return findPath(grid, grid.length - 1, grid[0].length - 1, new Integer[grid.length][grid[0].length]);
        return findPath(grid);
    }

    //findPath(row, col) returns the min cost path from (0, 0) to (row, col)
    //time - O(2^mn) 2 choices (up or left) for each cell in grid
    //space - O(m+n) length of longest path is stack size
    //time with memoization - O(mn)
    //space with memoization - O(mn) for cache and O(m+n) for call stack
    private int findPath(int[][] grid, int row, int col, Integer[][] cache)
    {   
        //base
        if(row == 0 && col == 0)
        {
            return grid[0][0]; //top left reached
        }

        //check cache
        if(cache[row][col] != null)
        {
            return cache[row][col];
        }

        int up = (row == 0) ? (int)1e7 : findPath(grid, row - 1, col, cache); //from prev row same col if within bounds
        int left = (col == 0) ? (int)1e7 : findPath(grid, row, col - 1, cache); //from same row prev col if within bounds

        int result = grid[row][col] + Math.min(up, left);
        cache[row][col] = result; //update cache
        return result;
    }

    //time - O(mn)
    //space - O(mn) 
    private int findPath(int[][] grid)
    {
        int[][] result = new int[grid.length][grid[0].length]; //result[i][j] tracks the min cost to (i,j) from (0,0)

        result[0][0] = grid[0][0]; //base case in recursion

        for(int row = 0; row < grid.length; row++)
        {
            for(int col = 0; col < grid[0].length; col++)
            {
                if(row == 0 && col == 0)
                {
                    continue; //already set
                }

                int up = (row == 0) ? (int)1e7 : result[row - 1][col]; //from prev row same col if within bounds
                int left = (col == 0) ? (int)1e7 : result[row][col - 1]; //from same row prev col if within bounds

                result[row][col] = grid[row][col] + Math.min(up, left);
            }
        }

        return result[grid.length - 1][grid[0].length - 1];
    }
}
