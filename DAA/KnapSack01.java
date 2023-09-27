import java.util.*;

public class KnapSack01 {
    private static int knapSack(int weight, int[] weight2, int[] val, int len) {
        System.out.println("----------------------------------------------------------------------------------------------------------");
        int K[][] = new int[len+1][weight+1];
        for (int i = 0; i <= len; i++) {
            for (int j = 0; j <= weight; j++) {
                if (i == 0 || j == 0) {
                    K[i][j] = 0;
                    System.out.print(K[i][j] + "\t"); // Use tabs for spacing
                } else if (weight2[i - 1] <= j) {
                    K[i][j] = Math.max(val[i - 1] + K[i - 1][j - weight2[i - 1]], K[i - 1][j]);
                    System.out.print(K[i][j] + "\t");
                } else {
                    K[i][j] = K[i - 1][j];
                    System.out.print(K[i][j] + "\t");
                }
            }
            System.out.println();
        }
        

        boolean[] selectedItems = new boolean[len];
        int i = len, j = weight;
        while (i > 0 && j > 0) {
            if (K[i][j] != K[i - 1][j]) {
                selectedItems[i - 1] = true;
                j -= weight2[i - 1];
            }
            i--;
        }
    
        // Print selected items
        System.out.println("\nSelected Items:");
        System.out.println("----------------");
        System.out.println("| Weight | Value |");
        System.out.println("----------------");
        for (i = 0; i < len; i++) {
            if (selectedItems[i]) {
                System.out.println("| " + weight2[i] + "\t  | " + val[i] + "\t |");
            }
        }
        System.out.println("----------------");
        System.out.println();
        System.out.println();
        System.out.println("The maximum Profit Generated : "+K[len][weight]);
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.println();
        return K[len][weight];
        
    }

    private static int knapSackGreedy(int weight, int[] weight2, int[] val, int len) {
        System.out.println("----------------------------------------------------------------------------------------------------------");
        // Greedy approach implementation
        double[] valuePerWeight = new double[len];
        for (int i = 0; i < len; i++) {
            valuePerWeight[i] = (double) val[i] / weight2[i];
        }

        double maxValue = 0;
        int[] selectedItems = new int[len];
        for (int i = 0; i < len; i++) {
            selectedItems[i] = 0;
        }

        while (weight > 0) {
            int maxIndex = -1;
            double maxRatio = -1;
            for (int i = 0; i < len; i++) {
                if (selectedItems[i] == 0 && valuePerWeight[i] > maxRatio) {
                    maxRatio = valuePerWeight[i];
                    maxIndex = i;
                }
            }

            if (maxIndex == -1) {
                break;
            }

            selectedItems[maxIndex] = 1;
            weight -= weight2[maxIndex];
            maxValue += val[maxIndex];
        }

        // Print selected items
        System.out.println("\nSelected Items (Greedy Approach):");
        System.out.println("----------------");
        System.out.println("| Weight | Value |");
        System.out.println("----------------");
        for (int i = 0; i < len; i++) {
            if (selectedItems[i] == 1) {
                System.out.println("| " + weight2[i] + "\t  | " + val[i] + "\t |");
            }
        }
        System.out.println("----------------");
        System.out.println("The maximum Profit Generated (Greedy): " + maxValue);
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.println();
        return (int) maxValue;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the Number of items : ");
        int n = sc.nextInt();
        int val[] = new int[n];
        int weight[] = new int[n];
        System.out.println("Enter the Total Weight of the bag : ");
        int Weight = sc.nextInt();

        System.out.println("Enter the Value of the respective Weights....");
        for(int i = 0;i<val.length;i++){
             val[i] = sc.nextInt();
         }

        System.out.println("Enter the Weights....");
        for(int i = 0;i<weight.length;i++){
              weight[i] = sc.nextInt();
        }

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("The Values entered are.....\n");
        System.out.println();
        System.out.println();
        System.out.println("The Total Weight of the bag : "+Weight);
        System.out.println("-----------------");
        System.out.println("|Weight\t|Value\t|");
        System.out.println("-----------------");
        for(int  i =0;i<weight.length;i++){
            System.out.println("|"+weight[i]+"\t|"+val[i]+"\t|");
        }
        System.out.println("-----------------");
        System.out.println();
        System.out.println();
        int len = val.length;
        int choice;
        // System.out.println("Enter 1 for Dynamic Programming approach or 2 for Greedy approach:");
        // choice = sc.nextInt();

        // switch (choice) {
        //     case 1:
        //         knapSack(Weight, weight, val, len);
        //         break;
        //     case 2:
        //         knapSackGreedy(Weight, weight, val, len);
        //         break;
        //     default:
        //         System.out.println("Invalid choice");
        // }
        int p1 = knapSack(Weight, weight, val, len);
        int p2 = knapSackGreedy(Weight, weight, val, len);
        if(p1 > p2){
            System.out.println("The Dp arroach is more Optimal Than the greedy Approach with profit : "+p1);
        }
        else{
            System.err.println("The Greedy arroach is more Optimal Than the dp Approach with profit :"+p2);
        }

    }


}
