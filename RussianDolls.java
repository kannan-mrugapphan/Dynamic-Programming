// 354.
class Solution {
    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, (int[] x, int[] y) -> x[0] == y[0] ? y[1] - x[1] : x[0] - y[0]); 
        return findLongestStackBinarySearch(envelopes);
    }

    //consider a stack of 3 envelopes of dimensions (a,b) within (c,d) within (e,f)
    //for a new envelope with dimensions (x,y) to fit into this stack, it can either fit inside (a,b) or between (a,b) and (c,d) or between (c,d) and (e,f) or it can cover the existing 3 envelopes 
    //if the enevelopes are sorted in dimensions, then the new enevelope can only cover the existing envelopes
    //so the envelopes are sorted by either height or width
    //   2  4  6  6 -> sorted in increasing order of widths
    //   1  2  1  3 -> find lis in height
    //russian doll length is 3  
    //   2  4  6  6 -> sorted in increasing order of widths
    //   1  2  3  4 -> find lis in height but 4th can't cover 3rd, so check width as well
    //russian doll length is 3  
    //time - O(n^2)
    //space - O(n)
    private int findLongestStack(int[][] envelopes)
    {
        //result[i] tracks length of largest stack with envelopes[i] as the outermost envelope
        int[] result = new int[envelopes.length];
        Arrays.fill(result, 1); //each enevelop is a russian doll by itself of length 1

        int length = 1; //return val

        for(int index = 1; index < envelopes.length; index++)
        {
            for(int prev = 0; prev < index; prev++)
            {
                //see if index can cover the russian doll ending at prev
                if(envelopes[index][1] > envelopes[prev][1] && envelopes[index][0] > envelopes[prev][0])
                {
                    int currentLength = 1 + result[prev]; //add current to prev
                    result[index] = Math.max(result[index], currentLength);
                }
            }

            length = Math.max(length, result[index]);
        }

        return length;
    }

    //time - O(nlogn)
    //space - O(n)
    private int findLongestStackBinarySearch(int[][] envelopes)
    {
        int[] result = new int[envelopes.length]; 
        result[0] = envelopes[0][1]; //initially the 0th envelope is a russian doll by itself
        int index = 1; //tracks the next vacant index

        for(int i = 1; i < envelopes.length; i++)
        {
            //check if i can extend the last element of result[]
            if(envelopes[i][1] > result[index - 1])
            {
                result[index] = envelopes[i][1];
                index++; 
            }
            else
            {
                int correctIndex = findCorrectIndex(result, index - 1, envelopes[i][1]);
                result[correctIndex] = envelopes[i][1];
            }
        }

        return index;
    }

    //returns the index at which target is present in nums[0, high]
    //if target is absent, it returns the index of next highest number
    //time - O(log n)
    private int findCorrectIndex(int[] nums, int high, int target)
    {
        int low = 0;

        //as long as more lements are presnt
        while(low <= high)
        {
            int mid = low + (high - low) / 2; 
            if(nums[mid] == target)
            {
                return mid; //target found
            }
            else if(nums[mid] < target)
            {
                low = mid + 1; //shift to right half
            }
            else
            {
                high = mid - 1; //shift to left half
            }
        }

        return low; //tracks index of next largest number
    }
}
