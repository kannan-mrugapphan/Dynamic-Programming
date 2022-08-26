// 764. 
// time - O(nm)
// space - O(nm) -> ignoring grid creation
class Solution {
    public int orderOfLargestPlusSign(int n, int[][] mines) {
        //build grid
        int[][] grid = new int[n][n];
        for(int i =0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                grid[i][j] = 1; //initially all 1s
            }
        }
        
        for(int[] mine : mines)
        {
            grid[mine[0]][mine[1]] = 0; //swap to 0 if cell is a mine
        }
        
        //support grids
        int[][] left = new int[n][n];
        int[][] right = new int[n][n];
        int[][] bottom = new int[n][n];
        int[][] top = new int[n][n];
        
        //scan each row in grid from left to right
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                if(grid[i][j] == 1)
                {
                    //count umber of sucessive 1s from left to right 
                    int leftToRight = 1;
                    if(j != 0)
                    {
                        leftToRight += left[i][j - 1];
                    }
                    left[i][j] = leftToRight;
                    
                    //count umber of sucessive 1s from top to bottom
                    int topToBottom = 1;
                    if(i != 0)
                    {
                        topToBottom += top[i - 1][j];
                    }
                    top[i][j] = topToBottom;
                }
            }
        }
        
        for(int i = n - 1; i >= 0; i--)
        {
            for(int j = n - 1; j >= 0; j--)
            {
                if(grid[i][j] == 1)
                {
                    //count umber of sucessive 1s from right to left
                    int rightToLeft = 1;
                    if(j != n - 1)
                    {
                        rightToLeft += right[i][j + 1];
                    }
                    right[i][j] = rightToLeft;
                    
                    //count umber of sucessive 1s from top to bottom
                    int bottomToTop = 1;
                    if(i != n - 1)
                    {
                        bottomToTop += bottom[i + 1][j];
                    }
                    bottom[i][j] = bottomToTop;
                    
                }
            }
        }
             
        int maxOrder = Integer.MIN_VALUE; //return val
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                int currentCellOrder = Math.min(left[i][j],
                                               Math.min(right[i][j],
                                                       Math.min(top[i][j], bottom[i][j])));
                maxOrder = Math.max(maxOrder, currentCellOrder);
            }
        }
        
        return maxOrder;
    }
}

// 764. 
// time - O(nm(n+m))
// space - O(1) -> ignoring grid creation
class Solution {
    public int orderOfLargestPlusSign(int n, int[][] mines) {
        //build grid
        int[][] grid = new int[n][n];
        for(int i =0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                grid[i][j] = 1; //initially all 1s
            }
        }
        
        for(int[] mine : mines)
        {
            grid[mine[0]][mine[1]] = 0; //swap to 0 if cell is a mine
        }
        
        int maxOrder = 0; //result
        
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                if(grid[i][j] == 1)
                {
                    //i-j is a valid center
                    int currentOrder = 1;
                    
                    //track the boundaries of plus sign
                    int top = i - 1;
                    int bottom = i + 1;
                    int left = j - 1;
                    int right = j + 1;
                    
                    //as long as boundries are within bounds and have 1, keep expanding
                    while(top >= 0 && bottom < n && left >= 0 && right < n && grid[i][left] == 1 && grid[i][right] == 1
                         && grid[top][j] == 1 && grid[bottom][j] == 1)
                    {
                        currentOrder += 1;
                        top--;
                        left--;
                        bottom++;
                        right++;
                    }
                    
                    maxOrder = Math.max(maxOrder, currentOrder); //update result;
                }
            }
        }
        
        return maxOrder;
    }
}
