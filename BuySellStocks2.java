// 122.
class Solution {
    public int maxProfit(int[] prices) {
        // Integer[][] cache = new Integer[prices.length][2];
        // return findMaxProfit(prices, 0, 1, cache); //start from 0th day with a potential buy allowed
        
        //return findMaxProfitTabulation(prices);
        return optimalSolution(prices);
    }
    
    //time - O(2^n) -> each day, irrespective of bool flag, there are 2 choices
    //with cache time = nuber of states = n * 2 (n for prices list and 2 possible values for flag)
    // space - O(n) -> call stack size + cache size = 2n
    private int findMaxProfit(int[] prices, int index, int allowedToBuy, Integer[][] cache) {
        //base
        if(index == prices.length)
        {
            //no more transactions possible
            return 0;
        }
        //check cache
        if(cache[index][allowedToBuy] != null)
        {
            return cache[index][allowedToBuy];
        }
        //logic
        //if allowed to buy
        if(allowedToBuy == 1)
        {
            //2 choices are possible, buy stock on current day, else skip current day
            //since stock is bought, we aren't allowed to buy in next recursive call and -prices[i] is lost while buying
            int buyStock = -prices[index] + findMaxProfit(prices, index + 1, 0, cache);
            //since no transaction happened, buy is possible in next recuresive call
            int skip = findMaxProfit(prices, index + 1, 1, cache); 
            
            cache[index][allowedToBuy] = Math.max(buyStock, skip); //cache result
        }
        else
        {
            //2 choices are possible, sell stock on current day, else skip current day
            //since stock is sold, we arenaret allowed to buy in next recursive call and prices[i] is gained while selling
            int sellStock = prices[index] + findMaxProfit(prices, index + 1, 1, cache);
            //since no transaction happened, buy is not possible in next recuresive call as stock is not yet sold
            int skip = findMaxProfit(prices, index + 1, 0, cache);
            
            cache[index][allowedToBuy] = Math.max(sellStock, skip);//cache result
        }
        
        return cache[index][allowedToBuy];
    }
    
    //time - O(n)
    // space - O(2n)
    private int findMaxProfitTabulation(int[] prices) {
        int[][] result = new int[prices.length + 1][2]; //possible states = n for each day and 2 for flag
        
        //current day profit depends on next day, so iterate in reverse order
        for(int day = prices.length - 1; day >= 0; day--)
        {
            int currentDayPrice = prices[day]; 
            
            //buy on current day
            int buyOnCurrentDay = -currentDayPrice + result[day + 1][0]; //price next day for sell - current day price
            int skipCurrentDay = result[day + 1][1]; //price till prev day for buy
            
            result[day][1] = Math.max(buyOnCurrentDay, skipCurrentDay); //max b/w 2 options
            
            //sell on current day
            int sellOnCurrentDay = currentDayPrice + result[day + 1][1]; //price next day for buy + current day price
            skipCurrentDay = result[day + 1][0]; //price next day for buy
            
            result[day][0] = Math.max(sellOnCurrentDay, skipCurrentDay); //max b/w 2 options
        }
        
        return result[0][1]; //return first day buy
    }
    
    //time - O(n)
    //space - constant
    private int optimalSolution(int[] prices) {
        int result = 0;
        
        //for each pair, if +ve profit is possible, make a transaction
        for(int i = 1; i < prices.length; i++)
        {
            int currentDayPrice = prices[i];
            int prevDayPrice = prices[i - 1];
            
            if(currentDayPrice - prevDayPrice > 0)
            {
                result += currentDayPrice - prevDayPrice;
            }
        }
        
        return result;
    }
}

//with transaction fee
// 714.
class Solution {
    public int maxProfit(int[] prices, int fee) {
        //return findMaxProfit(prices, 0, 1, fee, new Integer[prices.length][2]);
        return findMaxProfitTabulation(prices, fee);
    }
    
    //time - O(2n) -> 2n possible states
    //space - O(2n + n) -> 2n for cache and n for call stack
    private int findMaxProfit(int[] prices, int index, int allowedToBuy, int fee, Integer[][] cache) {
        //base 
        if(index == prices.length)
        {
            //no more trans possible
            return 0;
        }
        //check cache
        if(cache[index][allowedToBuy] != null)
        {
            return cache[index][allowedToBuy];
        }
        //logic
        if(allowedToBuy == 1)
        {
            //2 options -> buy or skip on current day
            //loose current day price to market because of buy and next day flag is set to sell
            int buyOnCurrentDay = -prices[index] + findMaxProfit(prices, index + 1, 0, fee, cache);
            //flag remains at buy necause no buy happened on current day
            int skipCurrentDay = findMaxProfit(prices, index + 1, 1, fee, cache);
            
            cache[index][allowedToBuy] = Math.max(buyOnCurrentDay, skipCurrentDay);
        }
        else
        {
            //2 options -> sell or skip on current day
            //gain current day price from market because of sell and pay transaction fee and next day flag is set to buy
            int sellOnCurrentDay = prices[index] - fee + findMaxProfit(prices, index + 1, 1, fee, cache);
            //flag remains at buy because no sell happened on current day
            int skipCurrentDay = findMaxProfit(prices, index + 1, 0, fee, cache);
            
            cache[index][allowedToBuy] = Math.max(sellOnCurrentDay, skipCurrentDay);
        }
        
        return cache[index][allowedToBuy];
    }
    
    //time - O(n)
    //space - O(2n)
    private int findMaxProfitTabulation(int[] prices, int fee)
    {
        int[][] result = new int[prices.length + 1][2]; //possible states = 2n
        
        //profit(i) depends on profit(i + 1)
        for(int day = prices.length - 1; day >= 0; day--)
        {
            //buy
            int buyOnCurrentDay = -prices[day] + result[day + 1][0]; //next day sell minus current day price
            int skipCurrentDay = result[day + 1][1]; //next day buy
            
            result[day][1] = Math.max(buyOnCurrentDay, skipCurrentDay);
            
            //sell
            int sellOnCurrentDay = prices[day] - fee + result[day + 1][1]; //next day buy plus current day price minus trans fee
            skipCurrentDay = result[day + 1][0]; //next day sell
            
            result[day][0] = Math.max(sellOnCurrentDay, skipCurrentDay);
        }
        
        return result[0][1]; //return 0th day buy
    }
}
