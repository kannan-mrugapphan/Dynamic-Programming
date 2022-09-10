// 96.
class Solution {
    public int numTrees(int n) {
        return countBSTDP(n);
    }
    
    private int countBST(int n) {
        //base
        if(n == 0)
        {
            return 1; //null node is a bst
        }
        //logic
        //any number from 1 to n can be the root of a bst
        int count = 0; //return value
        for(int root = 1; root <= n; root++)
        {
            //if root is i then nodes from 1 to i-1 will be in left subtree and nodes from i+1 to n will be in right subtree
            int leftSubtreeCount = countBST(root - 1);
            int rightSubtreeCount = countBST(n - root);
            
            int treesPossibleWithCurrentRoot = leftSubtreeCount * rightSubtreeCount;
            count += treesPossibleWithCurrentRoot;
        }
        return count;
    }
    
    //time - O(n^2)
    //space - O(n)
    private int countBSTDP(int n) {
        int[] result = new int[n + 1];
        result[0] = 1; //if no nodes are presnt, 1 bst is possible
        
        for(int i = 1; i <= n; i++)
        {
            //each node from 1 to i can be a root
            for(int root = 1; root <= i; root++)
            {
                int leftSubtreeCount = result[root - 1];
                int rightSubtreeCount = result[i - root];
                
                result[i] += leftSubtreeCount * rightSubtreeCount;
            }
        }
        
        return result[n];
    }
}
