import java.util.*;

public class TSE {
    // There are three nodes in the example graph (graph is 1-based)
    static int n = 3;
    // Give appropriate maximum to avoid overflow
    static int MAX = 1000000;

    // dist[i][j] represents shortest distance to go from i
    // to j. This matrix can be calculated for any given
    // graph using all-pair shortest path algorithms.
    static int[][] dist = {
            { 0, 7, 12, 9 },    // Dummy row for 1-based indexing
            { 7, 0, 3, 8 },
            { 12, 3, 0, 2 },
            { 9, 8, 2, 0 },
    };

    // Memoization for top-down recursion
    static int[][] memo = new int[n + 1][1 << (n + 1)];
    // Array to store the path
    static int[][] path = new int[n + 1][1 << (n + 1)];

    static int fun(int i, int mask) {
        // Base case
        // If only ith bit and 1st bit is set in our mask,
        // it implies we have visited all other nodes already
        if (mask == ((1 << i) | 3)) {
            path[i][mask] = 1; // Start node
            return dist[1][i];
        }

        // Memoization
        if (memo[i][mask] != 0)
            return memo[i][mask];

        int res = MAX; // Result of this sub-problem
        int next = -1; // Next node in the path

        // We have to travel all nodes j in mask and end the
        // path at ith node. So for every node j in mask,
        // recursively calculate the cost of traveling all
        // nodes in mask except i, and then travel back from
        // node j to node i taking the shortest path. Take
        // the minimum of all possible j nodes.
        for (int j = 1; j <= n; j++) {
            if ((mask & (1 << j)) != 0 && j != i && j != 1) {
                int temp = fun(j, mask & (~(1 << i))) + dist[j][i];
                if (temp < res) {
                    res = temp;
                    next = j;
                }
            }
        }
        memo[i][mask] = res;
        path[i][mask] = next;
        return res;
    }


    // Function to retrieve the path
    static String retrievePath(int start, int mask) {
        StringBuilder sb = new StringBuilder();
        int current = start;
        while (true) {
            sb.append(current).append(" -> ");
            int next = path[current][mask];
            if (next == -1 || next == 1) break; // End of path
            mask &= ~(1 << current); // Update mask
            current = next;
        }
        sb.append("1"); // End at node 1
        return sb.toString();
    }


    // Driver program to test above logic
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        int ans = MAX;
        int endNode = -1;

        for (int i = 1; i <= n; i++) {
            // Try to go from node 1 visiting all nodes in
            // between to i, then return from i taking the
            // shortest route to 1.
            int temp = fun(i, (1 << (n + 1)) - 1) + dist[i][1];
            if (temp < ans) {
                ans = temp;
                endNode = i;
            }
        }

        System.out.println("The cost of the most efficient tour = " + ans);
        System.out.println("Path: " + retrievePath(endNode, (1 << (n + 1)) - 1));

        long stopTime = System.currentTimeMillis();
        long difference = stopTime - startTime;
        System.out.println("The task took: " + difference + " milliseconds");
    }
}
