// 322.
// time - O(length of coins array * amount)
// space - O(length of coins array * amount)
class Solution {
    public int coinChange(int[] coins, int amount) {
        //edge 
        if(coins == null || coins.length == 0)
        {
            return -1;
        }
        int m = coins.length + 1;
        int n = amount + 1;
        int[][] result = new int[m][n];
        //base
        for(int i = 0; i < n; i++)
        {
            //if no coins are given, # of coins needed is infinity for any amount
            result[0][i] = Integer.MAX_VALUE - 100; //-100 to prevent underflow
        }
        for(int i = 1; i < m; i++)
        {
            result[i][0] = 0; //for amount 0, # of coins is 0
        }
        for(int i = 1; i < m; i++)
        {
            for(int j = 1; j < n; j++)
            {
                int currentCoin = coins[i - 1];
                if(currentCoin > j) 
                {
                    //cant choose the coin - as the current coin is above the current amount
                    result[i][j] = result[i - 1][j];
                }
                else
                {
                    //choose or dont choose the coin and store the min
                    int dontChoose = result[i - 1][j];
                    int choose = 1 + result[i][j - currentCoin];
                    result[i][j] = Math.min(choose, dontChoose);
                }
            }
        }
        return (result[m - 1][n - 1] == Integer.MAX_VALUE - 100) ? -1 : result[m - 1][n - 1];
    }
}
