// 354.

class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        //edge
        if(envelopes == null || envelopes.length == 0 || envelopes[0].length == 0)
        {
            return 0;
        }
        
        //envelope[i] is of form [width, height]
        //sort the envelopes in non decreasing order of either width or height - then find length of lis on the other parameter
        //sorting on one parameter and lis on other ensures that envelopes can be placed inside each other
        //after sorting lis works because, we know that the other parameter is already in increasing order
        
        //for binary search soln, arrange in decreasing order of heights in case of equal widths - this satisfies that lower height envelope is also considered while building result[]
        Arrays.sort(envelopes, (int[] a, int[] b) ->  
                    {
                        if(a[0] == b[0])
                        {
                            return b[1] - a[1];
                        }
                        return a[0] - b[0];
                     });
        
        //Arrays.sort(envelopes, (int[] a, int[] b) -> a[0] - b[0]); //custom sort works only for lis dp - check & remove equal widths in dp code
        //return russianDollsDP(envelopes);
        
        int[] heights = new int[envelopes.length];
        for(int i = 0; i < envelopes.length; i++)
        {
            heights[i] = envelopes[i][1];
        }
        
        return russianDollsBinarySearch(heights);
    }
    
    //time - O(n^2)
    //space - O(n)
    //lis with dp
    private int russianDollsDP(int[][] envelopes) {
        int[] result = new int[envelopes.length];
        Arrays.fill(result, 1); //every element is an increasing sub sequence of length 1
        int max = 1;
        
        for(int i = 1; i < envelopes.length; i++)
        {
            for(int j = 0; j < i; j++)
            {
                //current envelope is larger than jth envelope in both height and width
                if(envelopes[i][1] > envelopes[j][1] && envelopes[i][0] > envelopes[j][0])
                {
                    result[i] = Math.max(result[i], 1 + result[j]);
                    max = Math.max(max, result[i]);
                }
            }
        }
        
        return max;
    }
    
    //time - O(n logn)
    //space - O(n)
    //lis using binary serach
    private int russianDollsBinarySearch(int[] heights) {
        int[] result = new int[heights.length];
        result[0] = heights[0];
        int index = 1;
        
        for(int i = 1; i < heights.length; i++)
        {
            if(heights[i] > result[index - 1])
            {
                result[index++] = heights[i];
            }
            else
            {
                int replacementIndex = findClosest(result, 0, index - 1, heights[i]);
                result[replacementIndex] = heights[i];
            }
        }
        
        return index;
    }
    
    //find target or number closest (next highest) to target
    //time - O(log n)
    private int findClosest(int[] nums, int low, int high, int target) {
        while(low <= high)
        {
            int mid = low + (high - low) / 2;
            if(nums[mid] == target)
            {
                return mid;
            }
            else if(nums[mid] > target)
            {
                high = mid - 1;
            }
            else
            {
                low = mid + 1;
            }
        }
        return low;
    }
}
