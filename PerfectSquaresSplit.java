// a number x is a perfect square if
// r = Math.sqrt(x) and r * r == x

//279.
// time - O(n * root(n)) for dp
// space - O(n)

class Solution {
    public int numSquares(int n) {
        return breakNumberDp(n);
    }
    
    private int breakNumberRecursive(int n) {
        //base
        //if n <= 3, then splits needed is n because it can only be written as sum of 1^2
        if(n <= 3)
        {
            return n;
        }
        //logic
        //the max number of splits need for a number n is n because n can be represented as 1^2 + 1^2 + ... n times
        int splits = n; 
        //we try to break the number n at 1(1^2), 4(2^2), 9(3^2), ... as long as the break point is less than or equalto n
        for(int breakPoint = 1; breakPoint * breakPoint <= n; breakPoint++)
        {
            //for each valid break point recurse with n - (breakPoint * breakPoint)
            //update splits
            splits = Math.min(splits, 1 + breakNumberRecursive(n - (breakPoint * breakPoint)));
        }
        return splits;
    }
    
    private int breakNumberDp(int n) {
        //edge
        if(n <= 3)
        {
            return n;
        }
        
        int[] support = new int[n + 1];
        //filling the 1st 3 indices with n itself (base case)
        //sufficient to fill only 0th and 1st index
        support[0] = 0;
        support[1] = 1;
        support[2] = 2;
        support[3] = 3;
        
        for(int i = 4; i <= n; i++)
        {
            support[i] = i; //at max i needs i splits of 1^2 each
            //for each break point
            for(int breakPoint = 1; breakPoint * breakPoint <= i; breakPoint++)
            {
                support[i] = Math.min(support[i], 1 + support[i - breakPoint * breakPoint]);
            }
        }
        
        return support[n];
    }
}
