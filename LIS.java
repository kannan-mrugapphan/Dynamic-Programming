class Solution {
    public int lengthOfLIS(int[] nums) {
        //return findLIS(nums, 0, -1);
        //return findLIS(nums);
        return findLISUsingBinarySearch(nums);
    }

    //f(i, prev) tracks LIS possible from nums[i, nums.length-1] with the last picked element is prev
    //time - O(2^n)
    //space - O(n)
    private int findLIS(int[] nums, int index, int previousIndex)
    {
        //base
        if(index == nums.length)
        {
            return 0; //no more elements can be picked
        }

        //skip current element by not including it in the current LIS
        int skip = findLIS(nums, index + 1, previousIndex);
        int pick = Integer.MIN_VALUE;
        //pick is possible only if current is larger than previous
        if(previousIndex == -1 || nums[index] > nums[previousIndex])
        {
            //current is picked and previous now becomes current
            pick = 1 + findLIS(nums, index + 1, index);
        }

        return Math.max(skip, pick);
    }

    //time - O(n^2)
    //space - O(n)
    private int findLIS(int[] nums)
    {
        //result[i] tracks the length of LIS with nums[i] as last element
        int[] result = new int[nums.length];
        //prev[i] tracks the prev index present in LIS where nums[i] is last element
        int[] prev = new int[nums.length];

        int lis = 1; //return val
        int lastIndex = 0; //used to find LIS

        Arrays.fill(result, 1); //each element is an increasing subsequence by itself of length 1
        for(int i = 0; i < prev.length; i++)
        {
            prev[i] = i; //initially each element is an increasing sub sequence by itself so prev is istelf
        } 

        for(int index = 1; index < nums.length; index++)
        {
            for(int prevIndex = 0; prevIndex < index; prevIndex++)
            {
                //check if nums[index] can be appended to nums[prevIndex]
                if(nums[index] > nums[prevIndex])
                {
                    //an increasing subsequence is found where last element is index and 2nd last element is prev
                    //length of this is 1 + length of LIS where last element is prevIndex
                    int currentLISLength = 1 + result[prevIndex];
                    
                    //update result if larger LIS is found
                    if(result[index] < currentLISLength)
                    {
                        result[index] = currentLISLength; 
                        prev[index] = prevIndex; //index is now reached from prevIndex
                    }
                }
            }

            //update return val
            if(lis < result[index])
            {
                lis = result[index];
                lastIndex = index;
            }
        }

        System.out.println(printLIS(nums, prev, lastIndex));

        return lis;
    }

    private String printLIS(int[] nums, int[] prev, int lastIndex)
    {
        StringBuilder result = new StringBuilder();

        //as long as prev element of current element is not itself
        while(prev[lastIndex] != lastIndex)
        {
            result.append(Integer.toString(nums[lastIndex])); //add current to result
            result.append(",");
            lastIndex = prev[lastIndex]; //point to prev of current
        }

        result.append(Integer.toString(nums[lastIndex])); //add first element
        return result.reverse().toString();
    }

    //time - O(nlogn)
    //space - O(n)
    private int findLISUsingBinarySearch(int[] nums)
    {
        int[] result = new int[nums.length]; 
        int index = -1; //pointer to track next valid index in result[]

        for(int i = 0; i < nums.length; i++)
        {
            //if nums[i] can be appended to current increasing subsequence
            if(index == -1 || result[index] < nums[i])
            {
                result[index + 1] = nums[i]; //place nums[i] in next valid index
                index++; //index incremented by 1 to track current addition
            }
            //insert nums[i] in correct position in result[]
            else
            {
                int correctIndex = findCorrectIndex(result, index, nums[i]); //if nums[i] is present in result, returns its index else returns the index of next largest element
                result[correctIndex] = nums[i];
            }
        }

        //result[0, index] 
        return index + 1;
    }

    //time - O(log n)
    private int findCorrectIndex(int[] nums, int end, int target)
    {
        int start = 0;
        //as long as valid elements exists
        while(start <= end)
        {
            int mid = start + (end - start) / 2; 
            if(nums[mid] == target)
            {
                return mid; //target found
            }
            else if(nums[mid] < target)
            {
                start = mid + 1; //shift to right half
            }
            else
            {
                end = mid - 1; //shift to left half
            }
        }

        return start; //low tracks next largest element if target is absent
    }
}

// 673.
class Solution {
    public int findNumberOfLIS(int[] nums) {
        return findCountOfLIS(nums);
    }

    //time - O(n^2)
    //space - O(n)
    private int findCountOfLIS(int[] nums)
    {
        int[] result = new int[nums.length]; //result[i] tracks length of lis with nums[i] as last element
        int[] count = new int[nums.length]; //count[i] tracks number of increasing subsequences of length lis[i]

        int lis = 1; //length of lis in nums[]

        for(int index = 0; index < nums.length; index++)
        {
            //each element is an increasing subsequence by itself of length 1
            result[index] = 1;
            count[index] = 1;

            //check if current can be appended with any previous element
            for(int prev = 0; prev < index; prev++)
            {
                //current can be appended
                if(nums[index] > nums[prev])
                {
                    //append current with increasing subsequence ending at prev
                    //length of this increasing subsequence is 1 + length(LIS with nums[prev] as last element)
                    int currentLISLength = 1 + result[prev]; 

                    //check if this larger than result[index]
                    if(result[index] < currentLISLength)
                    {
                        //a larger increasing subsequence with nums[index] as last element is found
                        result[index] = currentLISLength;
                        //the number of subsequences with length as currentLISLength is same as number of subsequences with nums[prev] as last element (because count remains same as just an append happens)
                        count[index] = count[prev];
                    }

                    //if length of current increasing subsequence is same as the length tracked in result
                    //total number of subsequences of this length is count of subsequences with nums[prev] as last element + count[index] (current count)
                    else if(result[index] == currentLISLength)
                    {
                        count[index] += count[prev];
                    }

                    lis = Math.max(lis, result[index]); //update lis
                }
            }
        }

        int totalCount = 0; //number of subsequences with length = lis
        for(int i = 0; i < nums.length; i++)
        {
            //if lis ends at nums[i]
            if(result[i] == lis)
            {
               totalCount += count[i]; 
            }
        }

        return totalCount;
    }
}
