// 322.
class Solution {
    public int coinChange(int[] coins, int amount) {
        //int result = findMinimumCoins(coins, coins.length - 1, amount, new Integer[coins.length][amount + 1]);

        int result = findMinimumCoins(coins, amount);
        return (result == (int) 1e7) ? -1 : result;
    }

    //findMinimumCoins(i, a) tracks the minimum coins needed to make amount a with coins[0, i]
    //time -  O(m2^n) - for each coin 2 options skip or pick
    //space - O(m+n) - max call stack size
    //time with memoization - O(mn)
    //space with memoization - O(mn) for cache and O(m+n) for call stack
    private int findMinimumCoins(int[] coins, int index, int remainingAmount, Integer[][] cache)
    {
        //base 1
        if(remainingAmount == 0)
        {
            return 0; //if amount to be made becomes 0, no more coins needed
        }

        //base 2
        if(index < 0 || remainingAmount < 0)
        {
            //no denominations are there but amount remaining is > 0 so infinite coins or remainingAmount becomes < 0
            return (int) 1e7; 
        }

        //check cache
        if(cache[index][remainingAmount] != null)
        {
            return cache[index][remainingAmount];
        }

        //skip the current denomination and goto the previous with remainingAmount staying the same
        int skip = findMinimumCoins(coins, index - 1, remainingAmount, cache);
        int pick = (int) 1e7;

        //pick case becomes possible only when remainingAmount >= current denomination
        if(coins[index] <= remainingAmount)
        {
            //choose current, remainingAmount becomes initialAmount - currentValue and index stays same due to infinite supply of coins
            pick = 1 + findMinimumCoins(coins, index, remainingAmount - coins[index], cache);
        }

        int result = Math.min(pick, skip); //min among 2 options
        cache[index][remainingAmount] = result; //update cache
        return result;
    }

    //time - O(mn)
    //space - O(mn) 
    private int findMinimumCoins(int[] coins, int amount)
    {
        int[][] result = new int[coins.length + 1][amount + 1]; //result[i][j] tracks min coins needed to make j with coins[0, i]

        for(int money = 1; money <= amount; money++)
        {
            result[0][money] = (int) 1e7; //no coins and any amount needs infinite coins - base case 2
        }

        for(int coin = 0; coin <= coins.length; coin++)
        {
            result[coin][0] = 0; //if remaining amount is 0, coins needed is 0 for any coins[] - base case 1
        }

        for(int coin = 1; coin <= coins.length; coin++)
        {
            for(int money = 1; money <= amount; money++)
            {
                //skip the current denomination and goto the previous with remainingAmount staying the same
                int skip = result[coin - 1][money];
                int pick = (int) 1e7;

                //pick case becomes possible only when remainingAmount >= current denomination
                if(coins[coin - 1] <= money)
                {
                    //choose current, remainingAmount becomes initialAmount - currentValue and index stays same due to infinite supply of coins
                    pick = 1 + result[coin][money - coins[coin - 1]];
                }

                result[coin][money] = Math.min(pick, skip); //min among 2 options
            }
        }

        return result[coins.length][amount];
    }
}
