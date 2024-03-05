import java.util.Arrays;

public class TSP {
    public static final int n = 4;

    public static void main(String[] args) {
        Long StartTime = System.currentTimeMillis();
        int[][] dist = new int[][]{
                {0, 10, 15, 18},
                {10, 0, 4, 5},
                {15, 4, 0, 3},
                {18, 5, 3, 0}
        };

        int[][] memo = new int[n][1 << n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }

        int finalCost = TSP.fun(0, 1, memo, dist);

        System.out.println("The cost of the most efficient tour = " + finalCost);
        Long StopTime = System.currentTimeMillis();
        Long Difference = StopTime - StartTime;
        System.out.println("The time took: " + Difference + " Milliseconds");
    }

    public static int fun(int node, int visited, int[][] memo, int[][] dist) {
        if (Integer.bitCount(visited) == n) {
            return dist[node][0];
        }

        if (memo[node][visited] != -1) {
            return memo[node][visited];
        }

        int minDist = Integer.MAX_VALUE;
        for (int nextNode = 0; nextNode < n; nextNode++) {
            if (visited == 0 || (visited & (1 << nextNode)) == 0) {
                int temp = dist[node][nextNode] + fun(nextNode, visited | (1 << nextNode), memo, dist);
                if (temp < minDist) {
                    minDist = temp;
                }
            }
        }

        memo[node][visited] = minDist;
        return minDist;
    }
}