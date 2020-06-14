// 120.
//time - O(n * 2) (not a tight bound)
//space - O(1) - polluting i/p
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        //edge
        if(triangle == null || triangle.size() == 0)
        {
            return 0;
        }
        
        //start from second last row
        for(int row = triangle.size() - 2; row >= 0; row--)
        {
            for(int current = 0; current < triangle.get(row).size(); current++)
            {
                //possible places of landing at i,j is i + 1, j and i + 1, j + 1 -> select min and add to val at i,j
                int bottom = triangle.get(row).get(current) + triangle.get(row + 1).get(current);
                int right = triangle.get(row).get(current) + triangle.get(row + 1).get(current + 1);
                triangle.get(row).set(current, Math.min(bottom, right));
            }
        }
        
        return triangle.get(0).get(0); //1st row 1st col is return val
    }
}
