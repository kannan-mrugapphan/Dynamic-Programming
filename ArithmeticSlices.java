// 413.
class Solution {
    public int numberOfArithmeticSlices(int[] A) {
        //edge
        if(A == null || A.length == 0)
        {
            return 0;
        }
        
        return optimalDP(A);
    }
    
    //time - O(n^2)
    //space - O(1)
    private int nestedOperations(int[] A) {
        int count = 0;
        for(int i = 0; i < A.length - 2; i++) //no need to consider last 2 elements as an AP has atleast 3 elements
        {
            int initialDiff = A[i + 1] - A[i];  //x2 - x1 is calculated initially
            for(int j = i + 1; j < A.length - 1; j++)
            {
                //check if x3 - x2 = x2 - x1 -> if so x1,x2,x3 is an AP and increase count by 1
                //else break out of inner for() so the outer for starts from next i
                if(initialDiff == A[j + 1] - A[j])
                {
                    count++;
                }
                else
                {
                    break;
                }
            }
        }
        return count;
    }
    
    //time - O(n)
    //space - O(n)
    private int memoization(int[] A) {
        int[] result = new int[A.length];
        result[A.length - 1] = 0; //the last 2 elements cant form an AP so set to zero
        result[A.length - 1] = 0;
        //at the end, add all elements of result[]
        int count = 0;
        for(int i = result.length - 3; i >= 0; i--)
        {
            if(A[i + 1] - A[i] == A[i + 2] - A[i + 1])
            {
                result[i] = 1 + result[i + 1]; //A[i],A[i+1],A[i+2] is an AP, so add 1 to result[i+1] to result[i]
                count += result[i]; 
            }
            else
            {
                result[i] = 0;
            }
        }
        return count;
    }
    
    //time - O(n)
    //space - O(1)
    private int optimalDP(int[] A) {
        //in tabulation sol, only the next cell val is neede, right tracks it
        int count = 0;
        int right = 0; //result[A.length - 2] = 0 so right start from 2
        for(int i = A.length - 3; i >= 0; i--)
        {
            if(A[i + 1] - A[i] == A[i + 2] - A[i + 1])
            {
                right = 1 + right; //result[i] = 1 + right and result[i] is right for next iteration
                count += right; //add right to count at each step
            }
            else
            {
                right = 0; 
            }
        }
        return count;
    }
}
