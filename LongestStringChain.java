// 1048.
//time - O(n* n * max length word in words list)
//space - O(n)
class Solution {
    public int longestStrChain(String[] words) {
        //edge
        if(words == null || words.length == 0)
        {
            return 0;
        }
        
        //sorting is needed to consider the scenario where the smallest word is start of result chain
        Arrays.sort(words, (String a, String b) -> a.length() - b.length());
        
        int[] longestStringChain = new int[words.length]; //ith index tracks longest string chain with words[i] as last word
        longestStringChain[0] = 1; //1st word alone is a valid string chain
        
        int result = 1; //return value
        
        for(int i = 1; i < words.length; i++)
        {
            longestStringChain[i] = 1; //each word in itself is a valid chain
            for(int j = 0; j < i; j++)
            {
                if(isValidExtension(words[j], words[i]) && longestStringChain[i] < 1 + longestStringChain[j])
                {
                    longestStringChain[i] = 1 + longestStringChain[j];
                }
                result = Math.max(result, longestStringChain[i]);
            }
        }
        
        return result;
    }
    
    //time - O(max(length of child and parent word))
    private boolean isValidExtension(String parent, String child)
    {
        //parent is shorter word and child is extended word
        if(parent.length() + 1 != child.length())
        {
            return false;
        }
        
        int parentPointer = 0;
        int childPointer = 0; //pointers for both strings
        
        while(childPointer < child.length())
        {
            //test case b, ba
            if(parentPointer == parent.length())
            {
                //all chars in parent are covered
                if(childPointer + 1 == child.length())
                {
                    //if only one char in child is remaining return true
                    return true;
                }
                return false;
            }
            
            //as long as there are more letters in parent
            if(parent.charAt(parentPointer) == child.charAt(childPointer))
            {
                parentPointer++;
                childPointer++;
            }
            
            //skip current letter in child and proceed
            else
            {
                childPointer++;
            }
        }
        
        //test case - a, ca
        if(parentPointer == parent.length())
        {
            //all chars in parent are covered
            return true;
        }
        
        return false;
    }
}
