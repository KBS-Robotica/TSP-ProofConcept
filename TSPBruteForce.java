import java.util.ArrayList;
import java.util.Arrays;

public class TSPBruteForce {

    static int[][] graph;
    static int numCities;
    static int[] bestPath;
    static int bestDistance = Integer.MAX_VALUE;

    public static void main(String[] args) {

        graph = new int[][]{
                {0, 344, 450, 190},
                {344, 0, 5437, 2335},
                {450, 5437, 0, 543},
                {190, 2335, 543, 0}
        };
        numCities = graph.length;

        long startTime = System.currentTimeMillis();


        int[] currentPath = new int[numCities];
        for (int i = 0; i < numCities; i++) {
            currentPath[i] = i;
        }



        permute(currentPath, 1);

        long endTime = System.currentTimeMillis();

        System.out.println("Best Path: " + Arrays.toString(bestPath));
        System.out.println("Best Distance: " + bestDistance);

        long elapsedTime = endTime - startTime;
        System.out.println("Time taken: " + elapsedTime + " milliseconds");
    }


    static int calculateDistance(int[] path) {
        int distance = 0;
        for (int i = 0; i < numCities - 1; i++) {
            distance += graph[path[i]][path[i + 1]];
        }
        distance += graph[path[numCities - 1]][path[0]]; // Complete the cycle
        return distance;
    }

    static void swap(int[] path, int i, int j) {
        int temp = path[i];
        path[i] = path[j];
        path[j] = temp;
    }

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