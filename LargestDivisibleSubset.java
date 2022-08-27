// 368.
// time - O(n^2)
// space - O(n)
//approach - similar to LIS, sort nums[] and do LIS adding extra condition of divisiblity
//consider the following subset {6, 3, 9}, if we decide to add 27 to this subset we have to check if 27 is divisible by all 6,3,9
//if sorted the subset will be of form {3, 6, 9} and it is sufficient to check 27 with last element alone

class Solution {
    public List<Integer> largestDivisibleSubset(int[] nums) {
        //edge
        if(nums == null || nums.length == 0)
        {
            return new ArrayList<>();
        }
        
        //sorting is done to make divisors appear in order - so its enough to check if current is divisible by last element of subset and by property of transitivity current is divisible by all elements in subset
        Arrays.sort(nums); 
        
        int[] result = new int[nums.length];
        Arrays.fill(result, 1); //every element is a subset in which all elemenets are paiwise divisible
        
        int[] path = new int[nums.length]; //tracks path
        Arrays.fill(path, -1); 
        
        int max = 0; //size of return list
        int maxIndex = 0; //pos in result[] where max occurs
        
         
        for(int i = 1; i < nums.length; i++)
        {
            for(int j = 0; j < i; j++)
            {
                //i is divisble by j if i % j = 0 and i > j
                //thus i can be added to subset which has j, by property of transitivity i is divisible all other elements in taht subset
                if(nums[i] > nums[j] && nums[i] % nums[j] == 0)
                {
                    if(result[i] < 1 + result[j])
                    {
                        //update result, max, path, maxIndex
                        result[i] = 1 + result[j];
                        path[i] = j;
                        if(max < result[i])
                        {
                            max = result[i];
                            maxIndex = i;
                        }
                    }
                }
            }
        }
        
        //fill the return list
        List<Integer> ans = new ArrayList<>();
        while (maxIndex != -1)
        {
            ans.add(nums[maxIndex]);
            maxIndex = path[maxIndex];
        }
        
        return ans;
    }
}
