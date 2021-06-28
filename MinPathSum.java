// 64.
class Solution {
    
    int minSum = Integer.MAX_VALUE; //global result
    public int minPathSum(int[][] grid) {
        // pathFinder(grid, 0, 0, 0);
        // return minSum;
        return pathFinderDp(grid);
    }
    
    //time - O(2^mn)
    //space - O(mn)
    private void pathFinder(int[][] grid, int row, int col, int sum) {
        //base
        //out of bounds
        if(row >= grid.length || col >= grid[0].length)
        {
            return;
        }
        //bottom right corner reached
        if(row == grid.length - 1 && col == grid[0].length - 1)
        {
            sum += grid[grid.length - 1][grid[0].length - 1]; //add val of bottom right corner cell to path sum
            minSum = Math.min(minSum, sum); //update min path sum
            return;
        }
        //logic
        int currentSum = sum + grid[row][col]; //add current cell val to path sum
        pathFinder(grid, row, col + 1, currentSum); //go right
        pathFinder(grid, row + 1, col, currentSum); //go down
    }
    
    //time - O(mn)
    //space - O(mn)
    private int pathFinderDp(int[][] grid) {
        int[][] dp = new int[grid.length][grid[0].length]; //support dp matrix
        dp[0][0] = grid[0][0]; //if the i/p grid has only one cell, then the min path sum is val in that cell
        
        //all cells in 0th row can be reached only from the adjeacent left cell in same row
        //so sum of the path till that point is running sum
        for(int i = 1; i < grid.length; i++)
        {
            dp[i][0] = grid[i][0] + dp[i - 1][0];
        }
        
        //all cells in 0th col can be reached only from the adjeacent top cell in same col
        //so sum of the path till that point is running sum
        for(int i = 1; i < grid[0].length; i++)
        {
            dp[0][i] = grid[0][i] + dp[0][i - 1];
        }
        
        //all other cells can be reached from top or left
        //so min path sum is min of top and left plus current cell value
        for(int i = 1; i < grid.length; i++)
        {
            for(int j = 1; j < grid[0].length; j++)
            {
                dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
            }
        }
        
        return dp[grid.length - 1][grid[0].length - 1]; //return bottom right corner
    }
    
}
