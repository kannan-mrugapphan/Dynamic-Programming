// 368.
class Solution {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        Arrays.sort(nums);
        return findLds(nums);
    }

    //consider subset {4,2,1,8}. It is a divisible subset of length 4. It is same as {2,1,4,8} and {1,2,4,8}
    //if current subset is sorted, it will be easy to check if it possible to add an element to this subset
    //for eg. {1,2,4,8} it is easy to check if it is possible to add 16
    //since 16 divides 8, it will divide all previous elements because of tranisitive nature
    //to look at sorted subsets everytime, the whole array has to be sorted
    //time - O(n^2)
    //space - O(n)
    private List<Integer> findLds(int[] nums)
    {
        //lds[i] is length of largest divisible subset with nums[i] as last element
        int[] lds = new int[nums.length]; 
        Arrays.fill(lds, 1); //each element is a divisble subset by itself of length 1

        //previous[i] is the previous index of largest divisible subset where nums[i] is last element
        int[] previous = new int[nums.length]; 
        for(int i = 0; i < nums.length; i++)
        {
            previous[i] = i; //each element is a divisble subset by itself of length 1 so previous is itself
        }

        int ldsLength = 1; // return ans
        int ldsLastIndex = 0; //last index at which lds ends

        for(int index = 1; index < nums.length; index++)
        {
            for(int prev = 0; prev < index; prev++)
            {
                //for each element, try to see if it can be appended with an exisiting element
                if(nums[index] % nums[prev] == 0)
                {
                    //index divides prev and can be appended to subset ending with prev
                    int currentDivisibleSubsetLength = 1 + lds[prev];
                    //if larger subset found
                    if(lds[index] < currentDivisibleSubsetLength)
                    {
                        lds[index] = currentDivisibleSubsetLength;
                        previous[index] = prev;
                    }
                }
            }

            //if lds ending at index is largest update result
            if(ldsLength < lds[index])
            {
                ldsLength = lds[index];
                ldsLastIndex = index;
            }
        }

        List<Integer> result = new LinkedList<>(); //return list tracking lds

        //as long as prev is not itself
        while(ldsLastIndex != previous[ldsLastIndex])
        {
            result.add(0, nums[ldsLastIndex]); //add it to result at first and goto prev
            ldsLastIndex = previous[ldsLastIndex];
        }

        //add 1st element to result
        result.add(0, nums[ldsLastIndex]);
        return result;
    }

}
