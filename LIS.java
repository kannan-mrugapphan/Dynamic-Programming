// 300.
// brute force - all possible subsequences - power set of given list - chack for lis - time - n!
class Solution {
    public int lengthOfLIS(int[] nums) {
        //edge
        if(nums == null || nums.length == 0)
        {
            return 0;
        }
        return lisBinarySerach(nums);
    }
    
    //time - O(n^2)
    //space - O(n)
    private int lisDP(int[] nums) {
        int max = 1;
        int[] result = new int[nums.length];
        Arrays.fill(result, 1); //every element is an increasing sub sequence of length 1
        
        for(int i = 1; i < nums.length; i++)
        {
            for(int j = 0; j < i; j++)
            {
                //for every i from 1 to end, check for any j < i (0 < j < i) such that nums[j] < nums[i], such i,j is an increasing sub sequence
                if(nums[i] > nums[j])
                {
                    //(j, i) is an increasing sub sequence
                    //thus length of increasing sub sequence from 0 to i is 1 + length of increasing sub sequence from 0 to j
                    result[i] = Math.max(result[i], 1 + result[j]);
                    max = Math.max(max, result[i]);
                }
            }
        }
        
        return max;
    }
    
    //time - O(n logn)
    //space - O(n)
    private int lisBinarySerach(int[] nums) {
        int[] result = new int[nums.length];
        result[0] = nums[0]; //initially the first element alone is an increasing sub sequence, store it in 1st cell of result[]
        int index = 1; //index tracks the next vacant index in result[], the lis so far is the effective length of result[] = index
        
        for(int i = 1; i < nums.length; i++)
        {
            if(nums[i] > result[index - 1])
            {
                //i can be included at the end of current lis whose length is tracked by index
                result[index] = nums[i];
                index++;
            }
            else
            {
                //the prob of find a longer increasing sub sequence with current nums[i] is higher
                //so add this at appropriate pos in result[]
                int replacementIndex = findClosest(result, 0, index - 1, nums[i]);
                result[replacementIndex] = nums[i];
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

//number of lis
// 673.
// time - O(n^2)
// space - O(n)

class Solution {
    public int findNumberOfLIS(int[] nums) {
        //edge
        if(nums == null || nums.length == 0)
        {
            return 0;
        }
        
        int[] lis = new int[nums.length]; //lis[i] = length of lis with nums[i] as last element 
        int[] countLis = new int[nums.length]; //countLis[i] = number of lis with length = lis[i] and nums[i] as last element
        
        lis[0] = 1; //if only 0th element is present, list[0] = 1
        countLis[0] = 1; //number of lis of length 1 with nums[0] as last element nums[0] = 1
        
        int lisLength = 1; //return val
        
        for(int i = 1; i < nums.length; i++)
        {
            lis[i] = 1; //initial values considering nums[i] as seperate subsequence on its own
            countLis[i] = 1; 
            for(int j = 0; j < i; j++)
            {
                if(nums[i] > nums[j])
                {
                    //nums[i] can attach to lis ending with nums[j]
                    if(1 + lis[j] > lis[i])
                    {
                        lis[i] = 1 + lis[j]; //update lis
                        countLis[i] = countLis[j]; //number of lis ending with nums[i] is same as that ending with nums[j]
                    }
                    else if(1 + lis[j] == lis[i])
                    {
                        //lis[i] stays same, but number of lis with last element as nums[i] becomes,
                        //current value + number of lis with nums[j] as last element
                        countLis[i] += countLis[j]; 
                    }
                    lisLength = Math.max(lisLength, lis[i]);
                }
            }
        }
        
        int result = 0; //return value
        //find all countLis[i] with lis[i] = lisLength
        for(int i = 0 ; i < nums.length; i++)
        {
            if(lis[i] == lisLength)
            {
                result += countLis[i];
            }
        }
        
        return result;
    }
}
