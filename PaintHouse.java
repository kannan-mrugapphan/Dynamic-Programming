//256.
class Solution {
    public int minCost(int[][] costs) {
        // int result = Integer.MAX_VALUE;
        // for(int color = 0; color < costs[0].length; color++)
        // {
        //     result = Math.min(result, findCost(costs, 0, color, new Integer[costs.length][costs[0].length]));
        // }

        // return result;

        return findCost(costs);
    }
    
    //time - O(k^n) n is number of houses and k is number of colors
    //space - O(n) call stack size
    //time with memoization - O(kn) n is number of houses and k is number of colors
    //space with memoization - O(kn) for cache + O(n) call stack size
  
    //findCosts(row, color) - determines the min cost to paint all houses starting from row house with color
    private int findCost(int[][] costs, int row, int color, Integer[][] cache)
    {
        //base
        //last row - paint with i/p color
        if(row == costs.length - 1)
        {
            return costs[row][color];
        }
        
        //check cache
        if(cache[row][color] != null)
        {
            return cache[row][color];
        }

        int futureHouseCost = Integer.MAX_VALUE; //cost to paint the remaining houses given current house is painted with given color

        for(int nextColor = 0; nextColor < costs[0].length; nextColor++)
        {
            //next house color can't be same as current house color
            if(color != nextColor)
            {
                futureHouseCost = Math.min(futureHouseCost, findCost(costs, row + 1, nextColor, cache));
            }
        }

        int result =  costs[row][color] + futureHouseCost;
        cache[row][color] = result;
        return result;
    }
    
    //time - O(kn) n is number of houses and k is number of colors
    //space - O(nk) result matrix size
    private int findCost(int[][] costs)
    {
        int[][] result = new int[costs.length][costs[0].length];
        
        //last row in result matrix is same as costs matrix - base case in recursion
        for(int color = 0; color < costs[0].length; color++)
        {
            result[costs.length - 1][color] = costs[costs.length - 1][color];
        }
        
        //for houses from n-2 to 0
        for(int row = costs.length - 2; row >= 0; row--)
        {
            //try painitng with each color
            for(int color = 0; color < costs[0].length; color++)
            {
                int futureHouseCost = Integer.MAX_VALUE; //cost to paint the remaining houses given current house is painted with given color
                for(int nextColor = 0; nextColor < costs[0].length; nextColor++)
                {
                    //next house color can't be same as current house color
                    if(color != nextColor)
                    {
                        futureHouseCost = Math.min(futureHouseCost, result[row + 1][nextColor]);
                    }
                }

                result[row][color] = costs[row][color] + futureHouseCost;
            }
        }
        
        //out of all possible colors for 0th house pick answer with min cost
        int answer = Integer.MAX_VALUE;
        for(int color = 0; color < costs[0].length; color++)
        {
            answer = Math.min(answer, result[0][color]);
        }

        return answer;
    }
}
