import java.util.ArrayList;
import java.util.Arrays;

public class TSPBruteForce {

    static int[][] graph;
    static int numCities;
    static int[] bestPath;
    static int bestDistance = Integer.MAX_VALUE;

    public static void main(String[] args) {
        // Example graph (distance matrix)
        graph = new int[][]{
                {0, 4, 15, 20},
                {10, 0, 35, 25},
                {15, 35, 0, 30},
                {20, 25, 30, 0}
        };
        numCities = graph.length;

        // Initialize array to store current path
        int[] currentPath = new int[numCities];
        for (int i = 0; i < numCities; i++) {
            currentPath[i] = i;
        }

        // Start with the first city (0) as the starting point
        permute(currentPath, 1);

        // Output the best path and distance
        System.out.println("Best Path: " + Arrays.toString(bestPath));
        System.out.println("Best Distance: " + bestDistance);
    }

    // Helper method to calculate total distance for a given path
    static int calculateDistance(int[] path) {
        int distance = 0;
        for (int i = 0; i < numCities - 1; i++) {
            distance += graph[path[i]][path[i + 1]];
        }
        distance += graph[path[numCities - 1]][path[0]]; // Complete the cycle
        return distance;
    }

    // Helper method to swap elements in an array
    static void swap(int[] path, int i, int j) {
        int temp = path[i];
        path[i] = path[j];
        path[j] = temp;
    }

    // Recursive method to generate all permutations of cities
    static void permute(int[] path, int startIndex) {
        if (startIndex == numCities) {
            int distance = calculateDistance(path);
            if (distance < bestDistance) {
                bestDistance = distance;
                bestPath = Arrays.copyOf(path, numCities);
            }
        } else {
            for (int i = startIndex; i < numCities; i++) {
                swap(path, startIndex, i);
                permute(path, startIndex + 1);
                swap(path, startIndex, i); // Backtrack
            }
        }
    }
}