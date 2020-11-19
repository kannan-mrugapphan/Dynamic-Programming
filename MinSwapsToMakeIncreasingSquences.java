// 801.
// time - O(n)
// space - O(n)
class Solution {
    public int minSwap(int[] A, int[] B) {
        
        //swaps[i] denotes the cost to make both A and B strictly increasing till ith index with a swap at ith index
        //noSwaps[i] denotes the cost to make both A and B strictly increasing till ith index without a swap at ith index
        int[] swaps = new int[A.length];
        int[] noSwaps = new int[A.length];
        
        swaps[0] = 1; //the cost to make a one element array stictly increasing with a swap at 0th element
        noSwaps[0] = 0; //the cost to make a one element array stictly increasing without a swap at 0th element
        
        for(int i = 1; i < A.length; i++)
        {
            swaps[i] = Integer.MAX_VALUE;
            noSwaps[i] = Integer.MAX_VALUE;
            if(A[i] > A[i - 1] && B[i] > B[i - 1]) //i & i - 1 are in order in both A & B i.e no swaps needed at both i & i - 1
            {
                noSwaps[i] = noSwaps[i - 1]; //dont have to swap at i so dont swap at i - 1 too
                swaps[i] = swaps[i - 1] + 1; //definitely have to swap at i so swap at i - 1 also to maintain the order (+1 for this swap)
            }
            if(A[i] > B[i - 1] && B[i] > A[i - 1]) //only one is needed either at i or i - 1
            {
                noSwaps[i] = Math.min(swaps[i - 1], noSwaps[i]); //no swap at i so swap at i - 1
                swaps[i] = Math.min(noSwaps[i - 1] + 1, swaps[i]); //swap at i so no swap at i - 1 (+1 for this swap)
            }
        }
        
        return Math.min(noSwaps[A.length - 1], swaps[A.length - 1]);
    }
}
