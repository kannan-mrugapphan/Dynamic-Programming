//63.
class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        //edge
        if(obstacleGrid[obstacleGrid.length - 1][obstacleGrid[0].length - 1] == 1)
        {
            return 0; //if there is an obstacle in bottom right cell, total ways to reach it is 0
        }
        
        //return findPaths(obstacleGrid, 0, 0, new Integer[obstacleGrid.length][obstacleGrid[0].length]);
        return findWays(obstacleGrid);
    }

    //time - O(2^mn) - 2 choices (down and right) for each cell in grid
    //space - O(m+n) - max call stack size is length of longest path

    //time with memoization - O(mn) 
    //space with memoization - O(mn) 
    private int findPaths(int[][] grid, int row, int col, Integer[][] cache)
    {
        //base 1
        if(row == grid.length - 1 && col == grid[0].length - 1 && grid[row][col] != 1)
        {
            return 1; //bottom right cell reached and no obstacle is found, 1 way found
        }
        //base 2
        if(row == grid.length || col == grid[0].length || grid[row][col] == 1)
        {
            return 0; //fell out of bounds or stuck at an obstacle
        }
        //check cache
        if(cache[row][col] != null)
        {
            return cache[row][col];
        }

        int downPath = findPaths(grid, row + 1, col, cache); //next row, same col
        int rightPath = findPaths(grid, row, col + 1, cache); //same row, next col

        int result = downPath + rightPath; //sum of 2 possible options gives the result
        cache[row][col] = result; //update cache
        return result;
    }

    //time - O(mn)
    //space - O(mn)
    private int findWays(int[][] grid)
    {
        //result[i][j] tracks the total ways to reach bottom right cell from i,j
        int[][] result = new int[grid.length][grid[0].length]; 
        result[grid.length - 1][grid[0].length - 1] = 1; //base case in recursion

        for(int row = grid.length - 1; row >= 0; row--)
        {
            for(int col = grid[0].length - 1; col >= 0; col--)
            {
                if(row == grid.length - 1 && col == grid[0].length - 1)
                {
                    continue; //already set
                }

                //if current cell has obstacle then total ways to reach bottom right cell from current is 0
                if(grid[row][col] != 1)
                {
                    //from current we can go down or right (if within bounds)
                    int downAnswer = (row == grid.length - 1) ? 0 : result[row + 1][col];
                    int rightAnswer = (col == grid[0].length - 1) ? 0 : result[row][col + 1];

                    result[row][col] = downAnswer + rightAnswer; //sum of 2 options
                }
            }
        }

        return result[0][0];
    }
}
