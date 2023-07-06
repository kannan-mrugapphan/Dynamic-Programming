// 1048.
class Solution {
    public int longestStrChain(String[] words) {
        Arrays.sort(words, (String x, String y) -> x.length() - y.length());
        return findLongestStringChain(words);
    }

    //consider current string chain {a, ba, bdca, bca}. It is same as {a, ba, bca, bdca} with length 4
    //if current string chain sorted by length of strings, then checking if it can be expanded by a string is easier
    //for eg if the string chain is {a, ba, bca} adding bdca is easier as we need to check only with last entry
    //to work on sorted string chains, sort input array
    //time - O(n^2 * avg length of each word)
    //space - O(n)
    private int findLongestStringChain(String[] words)
    {
        //result[i] is length of longest string chain where words[i] is the last word
        int[] result = new int[words.length];
        Arrays.fill(result, 1); //each word is a string chain by itself of length 1

        int length = 1; //return val

        for(int index = 1; index < words.length; index++)
        {
            for(int prev = 0; prev < index; prev++)
            {
                //if words[index] can expand words[prev]
                if(canExpand(words[prev], words[index]))
                {
                    int currentChainLength = 1 + result[prev];

                    //if larger length found
                    if(result[index] < currentChainLength)
                    {
                        result[index] = currentChainLength;
                    }
                }
            }

            length = Math.max(length, result[index]); //update result
        }

        return length;
    }

    private boolean canExpand(String prev, String current)
    {
        //current's length should exactly be 1 longer than prev
        if(prev.length() + 1 != current.length())
        {
            return false;
        }

        int i = 0; //iterates over current
        int j = 0; //iterates over previous

        while(i < current.length())
        {
            //char match, increment both
            //1st part of if check accounts for ab, abc
            if(j < prev.length() && current.charAt(i) == prev.charAt(j))
            {
                i++;
                j++;
            }
            //could be the extra char, increment current pointer
            else
            {
                i++;
            }
        }

        if(i == current.length() && j == prev.length())
        {
            //both pointers out of bound at same time
            return true;
        }

        return false;
    }
}
