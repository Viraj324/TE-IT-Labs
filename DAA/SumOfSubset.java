import java.util.ArrayList;
import java.util.List;

public class SubsetSum {

    static boolean flag = false;

    public static void main(String[] args) {
        // Test case 1
        int[] set1 = {1, 2, 1};
        int sum1 = 3;
        List<Integer> subset1 = new ArrayList<>();
        System.out.println("Output 1:");
        printSubsetSum(0, set1.length, set1, sum1, subset1);
        System.out.println();
        flag = false;

        // Test case 2
        int[] set2 = {3, 34, 4, 12, 5, 2};
        int sum2 = 30;
        List<Integer> subset2 = new ArrayList<>();
        System.out.println("Output 2:");
        printSubsetSum(0, set2.length, set2, sum2, subset2);
        if (!flag) {
            System.out.println("There is no such subset");
        }
    }

    static void printSubsetSum(int i, int n, int[] set, int targetSum, List<Integer> subset) {
        if (targetSum == 0) {
            flag = true;
            System.out.print("[ ");
            for (int num : subset) {
                System.out.print(num + " ");
            }
            System.out.print("]");
            return;
        }

        if (i == n) {
            return;
        }

        printSubsetSum(i + 1, n, set, targetSum, subset);

        if (set[i] <= targetSum) {
            subset.add(set[i]);
            printSubsetSum(i + 1, n, set, targetSum - set[i], subset);
            subset.remove(subset.size() - 1);
        }
    }
}
