// 1463.
class Solution {
    public int cherryPickup(int[][] grid) {
        return collectCherries(grid, 0, 0, grid[0].length - 1, new Integer[grid.length][grid[0].length][grid[0].length]);
    }
    
    //in each move both the robots will be in the same row (potentiallt different or same columns)
    //bot1 position = row, col1 bot2 position = row, col2
    //time with out cache = O(9^mn) -> for each cell 9 child cells are possible
    //space with out cache = O(mn) -> call stack size, touch all cells
    //time with cache = O(nm^2)
    //space with cache = O(nm^2) for cache plus call stack
    private int collectCherries(int[][] grid, int row, int col1, int col2, Integer[][][] cache) {
        //base
        if(row == grid.length || col1 < 0 || col1 >= grid[0].length || col2 < 0 || col2 >= grid[0].length)
        {
            //one of the bots is out of bounds
            return 0;
        }
        //check cache
        if(cache[row][col1][col2] != null)
        {
            return cache[row][col1][col2];
        }
        
        //logic
        int maxCherriesCollected = Integer.MIN_VALUE; //return value
        
        //each robot can move to col + 1, col, col - 1 in next row
        //9 possible cominations are possible
        for(int i = -1; i <= 1; i++)
        {
            for(int j = -1; j <= 1; j++)
            {
                int nextRow = row + 1; //both bots move to next row every time
                int nextCol1 = col1 + i; //next col for bot depends on i, j
                int nextCol2 = col2 + j; 
                
                int cherriesCollected = 0; //collect cherries with current config
                if(col1 == col2)
                {
                    cherriesCollected += grid[row][col1]; //collect cherries once if both bots are in same cell
                }
                else
                {
                    cherriesCollected = grid[row][col1] + grid[row][col2]; //collect cherries from both cells
                }
                
                cherriesCollected += collectCherries(grid, nextRow, nextCol1, nextCol2, cache); //update with recursive result
                maxCherriesCollected = Math.max(maxCherriesCollected, cherriesCollected);
            }
        }
        
        cache[row][col1][col2] = maxCherriesCollected; //update cache
        return maxCherriesCollected;
    }
}
