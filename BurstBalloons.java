// 312.
// time - O(n^3)
// space - O(n^2)
class Solution {
    public int maxCoins(int[] nums) {
        //edge
        if(nums == null || nums.length == 0)
        {
            return 0; //empty list
        }
        
        int[][] result = new int[nums.length][nums.length];
        
        for(int length = 1; length <= nums.length; length++)
        {
            //consider snippets of length 1,2,3,...,length of nums[]
            for(int start = 0; start <= nums.length - length; start++)
            {
                //possible indices at which snippet starts - start
                int end = start + length - 1; //end of current snippet
                for(int last = start; last <= end; last++)
                {
                    //try to burst one balloon at a time at last 
                    //left and right tracks vals to be taken from nums[] that has to be multiplied when nums[last] is burst
                    //if the 1st balloon is burts its left val is 1
                    //if the last balloon is burst its right val is 1
                    //for other balloons left = nums[start - 1] and right = nums[end + 1]
                    int left = 1; 
                    int right = 1;
                    if(start != 0)
                    {
                        left = nums[start - 1];
                    }
                    if(end != nums.length - 1)
                    {
                        right = nums[end + 1];
                    }
                    //before and after tracks val to be added to finish processing current snippet from start to end after last is burst
                    //for snipptes of length 1, before = after = 0
                    //eg snippet 2,3,4 -> start = 0, end = 2, last = 1
                    //val = result[0][0] + left * 3 * right + result[2][2]
                    //here before dp[start][last - 1] and after = dp[last + 1][end]
                    int before = 0;
                    int after = 0;
                    if(last != start)
                    {
                        before = result[start][last - 1];
                    }
                    if(last != end)
                    {
                        after = result[last + 1][end];
                    }
                    result[start][end] = Math.max(result[start][end], before + (left * nums[last] * right) + after);
                } 
            }
        }
        
        return result[0][nums.length - 1];
    }
}
