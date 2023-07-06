// 221.
class Solution {
    public int maximalSquare(char[][] matrix) {
        // int maxSideLength = 0;
        // for(int i = 0; i < matrix.length; i++)
        // {
        //     for(int j = 0; j < matrix[0].length; j++)
        //     {
        //         int sideLength = findMaxSideLength(matrix, i, j); //(i, j) is top left
        //         maxSideLength = Math.max(maxSideLength, sideLength);
        //     }
        // }

        // return maxSideLength * maxSideLength; //return area
        return findMaxSideLength(matrix);
    }

    //returns max side length of square with top left corner is (row,col)
    //time - O(mn) for each cell to get total O(m^2n^2)
    //space - O(1)
    private int findMaxSideLength(char[][] matrix, int row, int col)
    {
        if(matrix[row][col] == '0')
        {
            return 0; //top left cell is 0
        }

        boolean flag = true; //checks if current is a valid square
        int sideLength = 1; //current cell has 1 so it is a square of side 1

        //as long as current is valid, expand
        while(flag)
        {
            int diagonalRow = row + sideLength; //check the diagonal right cell of current
            int diagonalCol = col + sideLength;

            //if next layer is outofbounds
            if(diagonalRow >= matrix.length || diagonalCol >= matrix[0].length)
            {
                return sideLength; //prev layer was valid
            }

            //all cols from col to diagonalCol in diagonalRow should be 1
            for(int c = col; c <= diagonalCol; c++)
            {
                if(matrix[diagonalRow][c] != '1')
                {
                    flag = false; //next layer is invalid
                    break;
                }
            }

            if(flag)
            {
                //all rows from to row to diagonalRow in diagonalCol should be 1
                for(int r = row; r <= diagonalRow; r++)
                {
                    if(matrix[r][diagonalCol] != '1')
                    {
                        flag = false; //next layer is invalid
                        break;
                    }
                }
            }

            if(flag)
            {
                //next layer is valid at this point
                sideLength++; //goto next layer
            }
            else
            {
                return sideLength; //current layer is invalid so return prev sideLegth
            }
        }

        return sideLength; //never reaches here
    }

    //time - O(mn)
    //space - O(mn)
    private int findMaxSideLength(char[][] matrix)
    {
        //result[i][j] is the side length of largest square with (i,j) as bottom right cell
        int[][] result = new int[matrix.length][matrix[0].length];
        int maxSideLength = 0; //return value

        //for all cells in 0th row or 0th col, the cell can't be expanded to get a larger square
        for(int col = 0; col < matrix[0].length; col++)
        {
            if(matrix[0][col] == '1')
            {
                //1 square of side length 1 is found which can't be expanded
                result[0][col] = 1;
                maxSideLength = 1; //1 square of side 1 is found
            }
        }

        for(int row = 0; row < matrix.length; row++)
        {
            if(matrix[row][0] == '1')
            {
                //1 square of side length 1 is found which can't be expanded
                result[row][0] = 1;
                maxSideLength = 1; //1 square of side 1 is found
            }
        }

        for(int i = 1; i < matrix.length; i++)
        {
            for(int j = 1; j < matrix[0].length; j++)
            {
                //if current cell is 1
                if(matrix[i][j] == '1')
                {
                    //it can be expanded based on max sideLengths of squares ending at top, left and diagonal cells
                    result[i][j] = 1 + Math.min(result[i - 1][j - 1], Math.min(result[i - 1][j], result[i][j - 1])); //1 for current cell, and expansion is based on 3 cells
                    maxSideLength = Math.max(maxSideLength, result[i][j]); //update side
                }
            }
        }

        return maxSideLength * maxSideLength; //area
    }
}

// 1277.
class Solution {
    public int countSquares(int[][] matrix) {
        
        // int totalSquares = 0; //return val
        // for(int i = 0; i < matrix.length; i++)
        // {
        //     for(int j = 0; j < matrix[0].length; j++)
        //     {
        //         //find length of largest square with (i, j) as top left cell
        //         int sideLength = findSideLength(matrix, i, j);

        //         //if side length is 2, 4 squares are possible of length 1 and 1 square is possible is of length 2
        //         //count just 1 square of length 2 and 1 square of length 1 (current cell alone) in the result
        //         //the other 3 squares of length 1 will be counted during their calls
        //         totalSquares +=  sideLength;
        //     }
        // }

        // return totalSquares;
        return count(matrix);
    }

    //returns side length of largest square with (row,col) as top left cell
    //O(mn) time with constan space
    //total time is O(m^2n^2) as method is called for each cell
    //space - constant
    private int findSideLength(int[][] matrix, int row, int col)
    {
        if(matrix[row][col] == 0)
        {
            return 0; //top left cell is 0 and can't be expanded
        }

        int sideLength = 1; //currently side length is 1 account for just the current cell (row, col)
        boolean flag = true; //flag is true signifying that current square is valid with all 1s

        //as long as current square is valid
        while(flag)
        {
            //nextRow,nextCol tracks the right diagonal of current cell
            int nextRow = row + sideLength;
            int nextCol = col + sideLength;

            //if right diagonal is out of bounds, current square can't be expanded
            if(nextRow >= matrix.length || nextCol >= matrix[0].length)
            {
                return sideLength; //return side length of current square
            }

            //for the square to expanded, all cells in next layer should be 1
            for(int r = row; r <= nextRow; r++)
            {
                if(matrix[r][nextCol] != 1)
                {
                    flag = false; //next layer is invalid
                    break; 
                }
            }

            if(flag)
            {
                for(int c = col; c <= nextCol; c++)
                {
                    if(matrix[nextRow][c] != 1)
                    {
                        flag = false; //next layer is invalid
                        break;
                    }
                }
            }

            //if next layer is valid
            if(flag)
            {
                sideLength++; //goto next layer
            }
            else
            {
                return sideLength; //return side length of prev layer
            }
        }

        return sideLength; //code never reaches here
    }

    //time - O(mn)
    //space - O(mn)
    private int count(int[][] matrix)
    {
        //result[i][j] tracks the length of largest square with (i,j) as bottom right cell
        int[][] result = new int[matrix.length][matrix[0].length];
        int totalSquares = 0; //return val

        //all cells in 0th row and 0th col are just 1 layer squares anc can't be expanded
        for(int i = 0; i < matrix.length; i++)
        {
            if(matrix[i][0] == 1)
            {
                result[i][0] = 1; //square by itself
                totalSquares += 1;
            }
        }
        for(int i = 1; i < matrix[0].length; i++)
        {
            if(matrix[0][i] == 1)
            {
                result[0][i] = 1; //square by itself
                totalSquares += 1;
            }
        }

        for(int i = 1; i < matrix.length; i++)
        {
            for(int j = 1; j < matrix[0].length; j++)
            {
                if(matrix[i][j] == 1)
                {
                    //it can be expanded
                    result[i][j] = 1 + Math.min(result[i - 1][j - 1],
                                               Math.min(result[i - 1][j], result[i][j - 1]));
                    
                    //if side length is 2, 4 squares are possible of length 1 and 1 square is possible is of length 2
                    //count just 1 square of length 2 and 1 square of length 1 (current cell alone) in the result
                    //the other 3 squares of length 1 will be counted during their calls
                    totalSquares += result[i][j];
                }
            }
        }

        return totalSquares;
    }
}
