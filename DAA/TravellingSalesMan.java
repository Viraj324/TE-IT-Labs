import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

class Node {
    ArrayList<Pair<Integer, Integer>> path;
    int[][] reducedMatrix;
    int cost;
    int vertex;
    int level;

    Node() {
        path = new ArrayList<>();
        reducedMatrix = new int[N][N];
    }
}

class Pair<F, S> {
    F first;
    S second;

    Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
}

public class TravelingSalesman {
    static final int N = 5;
    static final int INF = Integer.MAX_VALUE;

    static Node newNode(int parentMatrix[][], ArrayList<Pair<Integer, Integer>> path,
                        int level, int i, int j) {
        Node node = new Node();
        node.path = new ArrayList<>(path);
        if (level != 0) {
            node.path.add(new Pair<>(i, j));
        }
        for (int x = 0; x < N; x++) {
            System.arraycopy(parentMatrix[x], 0, node.reducedMatrix[x], 0, N);
        }
        for (int k = 0; level != 0 && k < N; k++) {
            node.reducedMatrix[i][k] = INF;
            node.reducedMatrix[k][j] = INF;
        }
        node.reducedMatrix[j][0] = INF;
        node.level = level;
        node.vertex = j;
        return node;
    }

    static int rowReduction(int reducedMatrix[][], int row[]) {
        for (int i = 0; i < N; i++) {
            row[i] = INF;
            for (int j = 0; j < N; j++) {
                if (reducedMatrix[i][j] < row[i]) {
                    row[i] = reducedMatrix[i][j];
                }
            }
        }
        int cost = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (reducedMatrix[i][j] != INF && row[i] != INF) {
                    reducedMatrix[i][j] -= row[i];
                }
                if (reducedMatrix[i][j] < INF) {
                    cost += reducedMatrix[i][j];
                }
            }
        }
        return cost;
    }

    static int columnReduction(int reducedMatrix[][], int col[]) {
        for (int i = 0; i < N; i++) {
            col[i] = INF;
            for (int j = 0; j < N; j++) {
                if (reducedMatrix[i][j] < col[i]) {
                    col[i] = reducedMatrix[i][j];
                }
            }
        }
        int cost = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (reducedMatrix[i][j] != INF && col[j] != INF) {
                    reducedMatrix[i][j] -= col[j];
                }
                if (reducedMatrix[i][j] < INF) {
                    cost += reducedMatrix[i][j];
                }
            }
        }
        return cost;
    }

    static int calculateCost(int reducedMatrix[][]) {
        int row[] = new int[N];
        int col[] = new int[N];
        int cost = 0;
        cost += rowReduction(reducedMatrix, row);
        cost += columnReduction(reducedMatrix, col);
        return cost;
    }

    static void printPath(ArrayList<Pair<Integer, Integer>> list) {
        for (Pair<Integer, Integer> pair : list) {
            System.out.println((pair.first + 1) + " -> " + (pair.second + 1));
        }
    }

    static class Comp implements Comparator<Node> {
        public int compare(Node lhs, Node rhs) {
            return lhs.cost - rhs.cost;
        }
    }

    static int solve(int costMatrix[][]) {
        PriorityQueue<Node> pq = new PriorityQueue<>(new Comp());
        ArrayList<Pair<Integer, Integer>> v = new ArrayList<>();
        Node root = newNode(costMatrix, v, 0, -1, 0);
        root.cost = calculateCost(root.reducedMatrix);
        pq.add(root);

        while (!pq.isEmpty()) {
            Node min = pq.poll();
            int i = min.vertex;
            if (min.level == N - 1) {
                min.path.add(new Pair<>(i, 0));
                printPath(min.path);
                return min.cost;
            }
            for (int j = 0; j < N; j++) {
                if (min.reducedMatrix[i][j] != INF) {
                    Node child = newNode(min.reducedMatrix, min.path, min.level + 1, i, j);
                    child.cost = min.cost + min.reducedMatrix[i][j] + calculateCost(child.reducedMatrix);
                    pq.add(child);
                }
            }
        }
        return -1; // In case no solution is found
    }

    public static void main(String[] args) {
        int costMatrix[][] = {
            { INF, 10, 8, 9, 7 },
            { 10, INF, 10, 5, 6 },
            { 8, 10, INF, 8, 9 },
            { 9, 5, 8, INF, 6 },
            { 7, 6, 9, 6, INF }
        };

        System.out.println("Total cost is " + solve(costMatrix));
    }
}
