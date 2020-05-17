// 518.
// time - O(length of coins array * amount)
// space - O(length of coins array * amount)
class Solution {
    public int change(int amount, int[] coins) {
        int m = coins.length + 1;
        int n = amount + 1;
        int[][] result = new int[m][n];
        //base
        for(int i = 0; i < m; i++)
        {
            result[i][0] = 1; //for amount 0, 3 of ways to make 0 is 1 irrespective of coins[] - always dont choose the coin
        }
        for(int i = 1; i < n; i++)
        {
            result[0][i] = 0; //no coins - so # of ways = 0 for any amount
        }
        for(int i = 1; i < m; i++)
        {
            for(int j = 1; j < n; j++)
            {
                int currentCoin = coins[i - 1];
                if(currentCoin > j)
                {
                    //cant choose the coin
                    result[i][j] = result[i - 1][j];
                }
                else
                {
                    //possiblites = choose the coin + dont choose the coin
                    result[i][j] = result[i - 1][j] + result[i][j - currentCoin];
                }
            }
        }
        return result[m - 1][n - 1];
    }
}
