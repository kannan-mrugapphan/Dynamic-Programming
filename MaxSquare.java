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
