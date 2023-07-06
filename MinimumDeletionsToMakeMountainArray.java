// 1671.
class Solution {
    public int minimumMountainRemovals(int[] nums) {
        int maxMountainLength = findLargestMountainSubsequence(nums);
        //delete all elements except ones in largest mountain subsequence
        return nums.length - maxMountainLength;
    }

    //returns the length of mountain subsequence (increasing first then decreasing) possible in nums
    //time - O(n^2)
    //space - O(n)
    private int findLargestMountainSubsequence(int[] nums)
    {
        //ith index tracks length of lis where nums[i] is last element - computed from left of nums
        int[] lisFromLeft = new int[nums.length]; 
        Arrays.fill(lisFromLeft, 1); //each element is an increasing subsequence of length 1 by itself

        //ith index tracks length of lis where nums[i] is last element - computed from right of nums
        int[] lisFromRight = new int[nums.length]; 
        Arrays.fill(lisFromRight, 1); //each element is an increasing subsequence of length 1 by itself

        int maxMountainLength = 1; //return value

        //compute from left
        for(int index = 1; index < nums.length; index++)
        {
            for(int prev = 0; prev < index; prev++)
            {
                //if nums[index] can be appended to subsequence ending with nums[prev]
                if(nums[index] > nums[prev])
                {
                    int currentLisLength = 1 + lisFromLeft[prev];
                    lisFromLeft[index] = Math.max(lisFromLeft[index], currentLisLength); //update if larger lis is found
                }
            }
        }

        //compute from right
        for(int index = nums.length - 2; index >= 0; index--)
        {
            for(int prev = nums.length - 1; prev > index; prev--)
            {
                //if nums[index] can be appended to subsequence ending with nums[prev]
                if(nums[index] > nums[prev])
                {
                    int currentLisLength = 1 + lisFromRight[prev];
                    lisFromRight[index] = Math.max(lisFromRight[index], currentLisLength); //update if larger lis is found
                }
            }
        }

        //if lisFromLeft[i] = x, then x is length of lis with nums[i] as last element from left
        //if lisFromRight[i] = y, then y is length of lis with nums[i] as last element from right
        //if nums[i] is peak, then length of mountain subsequence = lisFromLeft[i] + lisFromRight[i] - 1
        //-1 is done as nums[i] is used twice in both lis arrays
        for(int i = 0; i < nums.length; i++)
        {
            int mountainLength = lisFromLeft[i] + lisFromRight[i] - 1;
            //as per problem description, peak can't be at 0 or last index
            if(i == 0 || i == nums.length - 1)
            {
                continue;
            }

            //as per problem description, strictly increasing and string decreasing subsequences aren't mountains
            if(lisFromLeft[i] == 1 || lisFromRight[i] == 1)
            {
                //there are no elements to left smaller than i
                //there are no elements to right smaller than i
                continue;
            }

            maxMountainLength = Math.max(maxMountainLength, mountainLength); //update max
        }

        return maxMountainLength;
    }
}
