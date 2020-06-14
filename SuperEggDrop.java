class Solution {
    public int superEggDrop(int K, int N) {
        return superEggDropOptimalDP(K, N);
    }
    
    //time - O(kn^2)
    //space - (kn)
    //tle
    private int superEggDropDP(int K, int N) {
        int[][] result = new int[K][N + 1]; //eggs along rows and floors along cols
        
        //base
        for(int floor = 0; floor <= N; floor++)
        {
            result[0][floor] = floor; //if # of eggs = 1, attemps needed is same as number of floors -> 0th row is for k = 1
        }
        //result[i][0] = 0 for all i from 1 to k - 1 -> number of attempts for 0 floors is 0 -> no need to explictly set to 0 
        
        for(int egg = 1; egg < K; egg++)
        {
            for(int floor = 1; floor <= N; floor++)
            {
                //try to frop an egg from each floor from 1 to current floor and find # of attemps
                //selectmin of all possiblites
                result[egg][floor] = Integer.MAX_VALUE; //initilaiiy set to inf
                for(int current = 1; current <= floor; current++)
                {
                    int breakCase = result[egg - 1][current - 1]; //egg breaks so reduce egg by 1 and explore all floors below current
                    int notBreakCase = result[egg][floor - current]; //egg doent break so  explore all floors above current
                    int attempts = Math.max(breakCase, notBreakCase) + 1; //# of attempts needed is 1 + max of both cases
                    result[egg][floor] = Math.min(result[egg][floor], attempts); //min of all possiblities
                }
            }
        }
        
        return result[K - 1][N];
    }
    
    //time - O(nk)
    //space - O(nk)
    private int superEggDropOptimalDP(int K, int N) {
        int[][] result = new int[N + 1][K + 1]; //attempts along rows and eggs along cols
        //base
        //result[0][i] = 0 for all i from 0 to N since max floors covered with 0 attempts is 0 -> no need to explictly set to 0 
        //result[i][0] = 0 for all i from 1 to K since max floors covered with 0 eggs is 0 -> no need to explictly set to 0 
        
        int attempts = 0; //start from 0 th row
        while(result[attempts][K] < N)
        {
            //as long as N floors aren't covered
            attempts++; //increase attempts by 1
            for(int egg = 1; egg <= K; egg++)
            {
                //max floors covered is 1 + egg break case = egg doent break case
                //egg break case = attempts -1 (as 1 attempt is used) and egg - 1
                //egg doesnt break case = attempts - 1 and egg
                result[attempts][egg] = 1 + result[attempts - 1][egg - 1] + result[attempts - 1][egg];
            }
        }
        
        return attempts;
    }
}
