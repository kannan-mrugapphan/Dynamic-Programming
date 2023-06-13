//63.
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        //edge
        if(obstacleGrid[obstacleGrid.length - 1][obstacleGrid[0].length - 1] == 1)
        {
            return 0; //if there is an obstacle in bottom right cell, total ways to reach it is 0
        }
        
        //return findWays(obstacleGrid.length - 1, obstacleGrid[0].length - 1, obstacleGrid, new Integer[obstacleGrid.length][obstacleGrid[0].length]);
        return findWays(obstacleGrid);
    }

    //findWays(row, col) tracks number of ways to reach row, col from 0, 0
    //time - O(2^mn) 2 choices (left or up) for each cell in matrix
    //space - O(m+n) length of longest path is max stack size
    //time with memoization - O(mn)
    //space with memoization - O(mn) for cache and O(m+n) for call stack
    private int findWays(int row, int col, int[][] grid, Integer[][] cache)
    {
        //base1
        if(grid[row][col] == 1)
        {
            return 0; //can't reach row, col due to obstacle
        }

        //base2
        if(row == 0 && col == 0)
        {
            return 1; //top left is reached 
        }

        //check cache
        if(cache[row][col] != null)
        {
            return cache[row][col];
        }

        //reach row, col from top i.e row - 1, col. If row is 0, we can't reach row, col from up
        int up = (row == 0) ? 0 : findWays(row - 1, col, grid, cache); 
        //reach row, col from left i.e row, col - 1. If col is 0, we can't reach row, col from left
        int left = (col == 0) ? 0 : findWays(row, col - 1, grid, cache); 

        int result = up + left; //return sum of 2 options
        cache[row][col] = result; //update cache
        return result;
    }

    //time - O(mn)
    //space - O(mn)
    private int findWays(int[][] grid)
    {
        int[][] result = new int[grid.length][grid[0].length]; //result[i][j] tracks number of ways to reach (i, j) from (o, 0)
        result[0][0] = (grid[0][0] == 1) ? 0 : 1; //base case in recursion

        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid[0].length; j++)
            {
                if(i == 0 && j == 0)
                {
                    continue; //already set in base case
                }
                if(grid[i][j] == 1)
                {
                    continue; //(i, j) is unreachable
                }

                int up = (i == 0) ? 0 : result[i - 1][j]; //prev row same col
                int left = (j == 0) ? 0 : result[i][j - 1]; //same row prev col

                result[i][j] = up + left;
            }
        }

        return result[grid.length - 1][grid[0].length - 1];
    }
}
