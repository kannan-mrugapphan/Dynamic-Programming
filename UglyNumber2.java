// 264.
class Solution {
    public int nthUglyNumber(int n) {
        //brute force - have a counter at 0, run a loop from 1, whenever an ugly number is found, increment counter and return when counter hits n
        //edge
        if(n == 1)
        {
            return 1; // 1 is the 1st ugly number
        }
        
        return dp(n);
    }
    
    //time - O(n logn)
    //space - O(n)
    private int heaps(int n) {
        long current = 0; //current tracks the current ugly number
        int count = 0; // count tracks the number of ugly numbers found so far
        
        HashSet<Long> uglyNumbers = new HashSet<>(); //maintains the ugly numbers found so far
        PriorityQueue<Long> support = new PriorityQueue<>(); //min heap to get the next smallest number for which factors has to be found
        uglyNumbers.add(1L); //add 1 to both set and pq
        support.offer(1L);
        
        //as long as n ugly numbers are not found
        while(count != n)
        {
            current = support.poll(); //get the next ugly number from pq and icrease count
            count++;
            //for each number 2,3,5 find multiple of current & add it to set and pq if they arent already present in set
            long[] primeFactors = {2, 3, 5};
            for(long factor : primeFactors)
            {
                long next = factor * current;
                if(!uglyNumbers.contains(next))
                {
                    uglyNumbers.add(next);
                    support.offer(next);
                }
            }
        }
        
        return (int)current;
    } 
    
    //time - O(n)
    //space - O(n)
    private int dp(int n) {
        int[] result = new int[n]; //result tracks ugly numbers from 1 to n
        result[0] = 1; //1st ugly number is 1
        int p2 = 0;
        int p3 = 0;
        int p5 = 0;
        int n2 = 0;
        int n3 = 0;
        int n5 = 0;
        //for each index from 1 to n - 1
        //find n2 = 2 * result[p2], n3 = 3 * result[p3], n5 = 5 * result[p5]
        //result[i] = min(n2,n3,n5)
        //if result[i] = n2 increase p2 by 1, if result[i] = n3 increase p3 by 1 and if result[i] = n5 increase p5 by 1
        for(int i = 1; i < result.length; i++)
        {
            n2 = 2 * result[p2];
            n3 = 3 * result[p3];
            n5 = 5 * result[p5];
            result[i] = Math.min(n2, Math.min(n3, n5));
            if(result[i] == n2)
            {
                p2++;
            }
            if(result[i] == n3)
            {
                p3++;
            }
            if(result[i] == n5)
            {
                p5++;
            }
        }
        
        //return last cell value
        return result[n - 1];
    }
}
