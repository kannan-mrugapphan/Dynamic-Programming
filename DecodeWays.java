// 91.
class Solution {
    public int numDecodings(String s) {
        //edge
        if(s == null || s.length() == 0 || s.charAt(0) == '0')
        {
            return 0;
        }
        char[] mapping = {'-', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'}; 
        // StringBuilder path = new StringBuilder();
        // List<String> result = new ArrayList<>();
        // findWays(s, result, path, mapping);
        // System.out.println(result);
        // return result.size();
        return findWaysDP(s);
    }
    
    //time - O(n^n)
    //space - O(n) - for call stack
    private void findWays(String s, List<String> result, StringBuilder path, char[] mappings) {
        //base
        if(s.length() == 0)
        {
            result.add(path.toString());
            return;
        }
        if(s.charAt(0) == '0') //to ignore leading zeros
        {
            return;
        }
        //logic
        for(int i = 1; i <= s.length(); i++)
        {
            String current = s.substring(0, i);
            String remaining = s.substring(i);
            long currentVal = Long.parseLong(current);
            if(currentVal >= 1 && currentVal <= 26)
            {
                path.append(mappings[(int)currentVal]);
                findWays(remaining, result, path, mappings);
                path.deleteCharAt(path.length() - 1);
            }
        }
        return;
    }
    
    //time - O(n)
    //space - O(n)
    private int findWaysDP(String s) {
        int[] result = new int[s.length() + 1];
        result[0] = 1; //no ways to decode an empty str
        result[1] = (s.charAt(0) == 0) ? 0 : 1; //1 way to decode a string of length 1 if its above 0
        for(int i = 2; i <= s.length(); i++)
        {
            //current digit is at i - 1st index
            int oneDigit = Integer.parseInt(s.substring(i - 1, i)); //current digit goes alone
            if(oneDigit >= 1)
            {
                result[i] += result[i - 1];
            }
            int twoDigit = Integer.parseInt(s.substring(i - 2, i)); //current digit goes with prev digit
            if(twoDigit >= 10 && twoDigit <= 26)
            {
                result[i] += result[i - 2];
            }   
        }
        return result[s.length()];
    }
}
