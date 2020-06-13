// 72.
//time - O(m * n)
//space - O(m * n)
class Solution {
    public int minDistance(String word1, String word2) {
        int w1 = word1.length();
        int w2 = word2.length();
        int[][] result = new int[w2 + 1][w1 + 1];
        
        //base
        //minimum opns to convert '' to '' is 0
        result[0][0] = 0;
        //if dest str is '', then delete all chars from source str to match dest
        for(int i = 1; i <= w1; i++)
        {
            result[0][i] = i;
        }
        //if source str is '', then add chars to source str to match dest
        for(int i = 1; i <= w2; i++)
        {
            result[i][0] = i;
        }
        
        //logic
        for(int i = 1; i <= w2; i++)
        {
            for(int j = 1; j <= w1; j++)
            {
                //trying to convert 'h' to 'r'
                //three options - add r to h -> 'hr' to 'r' -> same as 'h' to '' (1 opn) -> 2
                //update h to r - 'r' to 'r' -> same as '' to '' (0 opns) -> 1
                //delete h -> '' to 'r' (1 opn) -> 2
                //select min of 3 options
                
                //if chars at j in 1st str and i in 2ns str are same - pick val from diagonal
                
                char wordOne = word1.charAt(j - 1);
                char wordTwo = word2.charAt(i - 1);
                
                if(wordOne == wordTwo)
                {
                    result[i][j] = result[i - 1][j - 1];
                }
                else
                {
                    result[i][j] = 1 + Math.min(result[i - 1][j - 1], Math.min(result[i - 1][j], result[i][j - 1]));
                }
            }
        }
        
        return result[w2][w1];
    }
}
