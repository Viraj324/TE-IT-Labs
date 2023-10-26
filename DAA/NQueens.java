public class Nqueens {
    private static int N;

    public static void main(String[] args) {
        N = 4; // Change N to the desired board size
        solveNQueens();
    }

    private static void solveNQueens() {
        int[][] board = new int[N][N];
        solveNQueensUtil(board, 0);
    }

    private static boolean solveNQueensUtil(int[][] board, int col) {
        if (col >= N) {
            // All queens are placed, print the final solution
            printSolution(board);
            return true;
        }

        boolean result = false;

        for (int i = 0; i < N; i++) {
            if (isSafe(board, i, col)) {
                board[i][col] = 1;

                // Print the current board with queens placed
                System.out.println("Step " + col + ":");
                printSolution(board);

                // Recur for the next column
                result = solveNQueensUtil(board, col + 1) || result;

                // If placing the queen in (i, col) doesn't lead to a solution,
                // then remove the queen from (i, col)
                board[i][col] = 0;

                // Print the board after removing the queen
                System.out.println("Backtrack at Step " + col + ":");
                printSolution(board);
            }
        }

        return result;
    }

    private static boolean isSafe(int[][] board, int row, int col) {
        int i, j;

        // Check the left side of this row
        for (i = 0; i < col; i++) {
            if (board[row][i] == 1) {
                return false;
            }
        }

        // Check upper diagonal on the left side
        for (i = row, j = col; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        // Check lower diagonal on the left side
        for (i = row, j = col; i < N && j >= 0; i++, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

    private static void printSolution(int[][] board) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
