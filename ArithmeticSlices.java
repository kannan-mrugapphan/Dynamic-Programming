// 413.
class Solution {
    public int numberOfArithmeticSlices(int[] nums) {
        //edge
        if(nums == null || nums.length < 3)
        {
            return 0;
        }
        
        return countArithmeticSlicesSPaceOptimized(nums);
    }

    //time - O(n^2)
    //space - constant
    private int countArithmeticSlices(int[] nums)
    {
        int count = 0; //return value
        //starting from each index i in nums[] count the number of APs that start at ith index
        //AP has atleast 3 elements, so valid start positions are [0, nums.length - 3]
        for(int start = 0; start <= nums.length - 3; start++)
        {
            //check if start index has a valid AP
            if(nums[start + 2] - nums[start + 1] == nums[start + 1] - nums[start])
            {
                //common diff of 1st elements are same, so start index has a valid AP
                int commonDiff = nums[start + 1] - nums[start];
                count++; //to account for (start, start + 1, start + 2)
                //try to expand current AP as long as possible
                //from start + 3 till end 
                for(int end = start + 3; end < nums.length; end++)
                {
                    //AP at start still holds
                    if(nums[end] - nums[end - 1] == commonDiff)
                    {
                        count++; //acount for [start, start + 1, start + 2, ... end]
                        //the child AP starting at start + 1 will be counted in next iteration of start
                    }
                    //AP starting at start doesn't hold anymore, continue to next start
                    else
                    {
                        break;
                    }
                }
            }
        }

        return count;
    }

    //time - O(n)
    //space - O(n)
    private int countArithmeticSlicesUsingCaching(int[] nums)
    {
        //in the previous approach, APs starting at each start are counted
        //eg: [1,2,3,4,5]
        //[1,2,3], [1,2,3,4], [1,2,3,4,5] are counted in first iteration
        //[2,3,4], [2,3,4,5] are counted in 2nd iteration
        //[3,4,5] are counted in last iteration
        //when AP starting at 2 is calculated, [2,3,4] is valid, so this AP is expanded as much as possible
        //since [2,3,4] is valid AP, count of APs starting at 2 is 1 + count of APs starting at 3

        //result[i] is count of APs starting at ith index
        int[] result = new int[nums.length];
        //valid AP needs atleast 3 elements so valid start positions are [0, nums.length - 3]
        result[nums.length - 1] = 0;
        result[nums.length - 2] = 0;

        int count = 0; //return val

        for(int start = nums.length - 3; start >= 0; start--)
        {
            //check if start index has a valid AP
            if(nums[start + 2] - nums[start + 1] == nums[start + 1] - nums[start])
            {
                result[start] = 1 + result[start + 1]; //count updated
            }
            else
            {
                //start is invalid
                result[start] = 0;
            }

            count += result[start]; //add APs starting from start to result
        }

        return count;
    }

    //time - O(n)
    //space - constant
    private int countArithmeticSlicesSPaceOptimized(int[] nums)
    {
        int count = 0; //return val
        int countOfAPsStartingAtNextIndex = 0; //tracks count of APs starting at i+1 for each index i

        for(int start = nums.length - 3; start >= 0; start--)
        {
            int current = 0; //tracks count of APs starting at start
            //check if start index has a valid AP
            if(nums[start + 2] - nums[start + 1] == nums[start + 1] - nums[start])
            {
                current = 1 + countOfAPsStartingAtNextIndex; //count updated
            }
            else
            {
                current = 0; //start is invalid start position
            }
            
            count += current; //add APs starting from start to result
            countOfAPsStartingAtNextIndex = current; //for next iteration, current becomes next
        }

        return count;
    }
}
