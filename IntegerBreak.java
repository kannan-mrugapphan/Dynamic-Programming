// 343.
class Solution {
    
    int sourceN;
    
    public int integerBreak(int n) {
        
        sourceN = n;
        //return maxPossibleProduct(n, new int[n + 1]);
        int[] dp = new int[n + 1];
        dp[1] = 1; //base case
        
        for(int i = 2; i <= n; i++)
        {
            if(i != n)
            {
                dp[i] = i; //use i directly if it is a child with no splits
            }
            //for all possible left halfes
            for(int leftHalf = 1; leftHalf <= i/2; leftHalf++)
            {
                int rightHalf = i - leftHalf;
                //find right half, current product and update max
                int currentProduct = dp[leftHalf] * dp[rightHalf];
                dp[i] = Math.max(dp[i], currentProduct);
            }
        }
        
        return dp[n];
    }
    
    //run time with no cache = O((n/2)^n) -> number of branches in each level
    // with cache runtime - O(n^2) -> n states and n time for for loop
    private int maxPossibleProduct(int n, int[] dp) {
        //base
        if(n == 1)
        {
            dp[1] = 1;
            return 1; //no possible splits
        }
        //logic
        int maxProduct = 0;
        if(n != sourceN)
        {
            //if child recursive calls are made, then there is an option to use the whole number without breaks
            maxProduct = n; 
        }
        
        for(int leftHalf = 1; leftHalf <= n / 2; leftHalf++)
        {
            //if n = 4, possible breaks are 1,3 and 2,2 (3, 1 is a duplicate and is not processed due to n/2 check)
            int rightHalf = n - leftHalf;
            //update result
            maxProduct = Math.max(maxProduct, maxPossibleProduct(leftHalf, dp) * maxPossibleProduct(rightHalf, dp));
        }
        
        dp[n] = maxProduct;
        return maxProduct;
    }
}
