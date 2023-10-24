import java.util.ArrayList;
import java.util.List;

public class TSPBranchAndBound {
    static int n; // Number of cities
    static int[][] cost; // Cost matrix
    static boolean[] visited; // To keep track of visited cities
    static List<Integer> tour; // Stores the optimal tour
    static int minCost = Integer.MAX_VALUE; // Stores the minimum cost

    public static void tsp(int currentNode, int depth, int currentCost, List<Integer> currentTour) {
        if (depth == n) {
            // If all cities are visited, update the tour if it's better
            if (currentCost + cost[currentNode][0] < minCost) {
                currentTour.add(0); // Return to the starting city
                tour = new ArrayList<>(currentTour);
                minCost = currentCost + cost[currentNode][0];
                currentTour.remove(currentTour.size() - 1); // Backtrack
            }
            return;
        }

        visited[currentNode] = true;
        for (int nextCity = 0; nextCity < n; nextCity++) {
            if (!visited[nextCity] && currentCost + cost[currentNode][nextCity] < minCost) {
                currentTour.add(nextCity);
                tsp(nextCity, depth + 1, currentCost + cost[currentNode][nextCity], currentTour);
                currentTour.remove(currentTour.size() - 1); // Backtrack
            }
        }
        visited[currentNode] = false;
    }

    public static void main(String[] args) {
        n = 4; // Number of cities
        cost = new int[][]{
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };
        visited = new boolean[n];
        tour = new ArrayList<>();
        tour.add(0); // Starting city

        tsp(0, 1, 0, tour);

        System.out.println("Optimal Tour: " + tour);
        System.out.println("Optimal Cost: " + minCost);
    }
}
