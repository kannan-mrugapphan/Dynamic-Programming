//198.
//time - O(n)
//space = O(n)
class Solution {
    public int rob(int[] nums) {
        //edge
        if(nums == null || nums.length == 0)
        {
            return 0;
        }
        if(nums.length == 1)
        {
            return nums[0]; //only 1 house, rob that
        }
        if(nums.length == 2)
        {
            return Math.max(nums[0], nums[1]); //only 2 houses, rob the house with max cash
        }
        int[] result = new int[nums.length];
        result[0] = nums[0];
        result[1] = Math.max(nums[0], nums[1]);
        for(int i = 2; i < nums.length; i++)
        {
            int rob = nums[i] + result[i - 2]; //if robbed, then cash = current + cash in the 2nd last house
            int dontRob = result[i - 1];// if not robbed, cash = cah at last house
            result[i] = Math.max(rob, dontRob); //choose max
        }
        return result[nums.length - 1];
    }
}
