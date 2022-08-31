// 264.
//brute force - for each number, find prime factors, check if number other 2,3,5 is a prime factor, if so don't count it
//approach -> multiples of 2,3,5 could potentially be ugly numbers. The multiples of other numbers can be skipped
//but we can't continously pick multiples of just 2,3,5 => ex: 2*7 = 14 (not ugly due to 7), 2*35 = 2*5*7 (not ugly due to 7)
//so pick multiples of 2,3,5 just from the well defined set starting with just 1
class Solution {
    public int nthUglyNumber(int n) {
        //return (int)uglyNumberPicker(n);
        return uglyNumberFinder(n);
    }
    
    //time - O(nlogn)
    //space - O(n)
    private long uglyNumberPicker(int n) {
        HashSet<Long> uglyNumbers = new HashSet<>(); //set to mainintain unique number
        PriorityQueue<Long> support = new PriorityQueue<Long>(); //min heap to pick min number from well defined set
        
        uglyNumbers.add(1l);
        support.offer(1l); //1 is 1st ugly number
        
        int count = 0; //ugly numbers seen so far
        
        while(count < n)
        {
            //as long as n ugly numbers aren't seen
            //pick min from well defined set
            long currentUglyNumber = support.poll();
            count++;
            
            if(count == n)
            {
                return currentUglyNumber; //result
            }
            
            //find 2,3,5 multiples of current
            long multiple2 = 2 * currentUglyNumber;
            long multiple3 = 3 * currentUglyNumber;
            long multiple5 = 5 * currentUglyNumber;
            
            //if these numbers weren't seen earlier, add to set and heap
            if(!uglyNumbers.contains(multiple2))
            {
                uglyNumbers.add(multiple2);
                support.offer(multiple2);
            }
            if(!uglyNumbers.contains(multiple3))
            {
                uglyNumbers.add(multiple3);
                support.offer(multiple3);
            }
            if(!uglyNumbers.contains(multiple5))
            {
                uglyNumbers.add(multiple5);
                support.offer(multiple5);
            }
        }
        
        return -1; //code never reachees here for valid n
    }
    
    //time - O(n)
    //space - O(n)
    private int uglyNumberFinder(int n) {
        int[] result = new int[n]; //tracks all n ugly numbers
        
        result[0] = 1; //well defined set starts with just 1
        
        //3 pointers are needed to track 2,3,5 multiples
        //initially all are at 0th index because 2,3,5 multiplies of 1 are yet to be picked
        int p2 = 0; 
        int p3 = 0;
        int p5 = 0;
        
        for(int index = 1; index < n; index++)
        {
            //find 2,3,5 multiples of p2,p3,p5 elements
            int multiple2 = 2 * result[p2];
            int multiple3 = 3 * result[p3];
            int multiple5 = 5 * result[p5];
            
            //pick min and place it in index
            result[index] = Math.min(multiple2,
                                    Math.min(multiple3, multiple5));
            
            if(result[index] == multiple2)
            {
                //2 multiple of p2 is picked 
                p2++;
            }
            if(result[index] == multiple3)
            {
                //3 multiple of p3 is picked 
                p3++;
            }
            if(result[index] == multiple5)
            {
                //5 multiple of p5 is picked 
                p5++;
            }
        }
        
        return result[n - 1];
    }
}
