import java.util.Arrays;
import java.util.*;

public class FKnapSack {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
         System.out.println("Enter the Number of items : ");
         int n = sc.nextInt();
         int val[] = new int[n];
         int weight[] = new int[n];
        System.out.println("Enter the Value of the respective Weights....");
        for(int i = 0;i<val.length;i++){
             val[i] = sc.nextInt();
         }

        System.out.println("Enter the Weights....");
        for(int i = 0;i<weight.length;i++){
              weight[i] = sc.nextInt();
         }
         

        int W = 50;
        double ratio[][] = new double[val.length][2];

        for (int i = 0; i < val.length; i++) {
            ratio[i][0] = i;
            ratio[i][1] = val[i] / (double) weight[i];
        }

        System.out.println("----------------------------------------------------------------------------");
        System.out.println("The Values entered are.....\n");
        System.out.println("-------------------------");
        System.out.println("|Weight\t|Value\t|Ratio\t|");
        System.out.println("-------------------------");
        for(int  i =0;i<weight.length;i++){
            System.out.println("|"+weight[i]+"\t|"+val[i]+"\t|"+ratio[i][1]+"\t|");
        }
        System.out.println("-------------------------");
        System.out.println();
        System.out.println();
        // Ascending Order Sorting
        Arrays.sort(ratio, Comparator.comparingDouble(o -> o[1]));
        int capacity = W;
        double finalVal = 0; // Use double to store fractional value
        System.out.println("----------------------------------------------------------------------------");
        System.out.println("The Calculation's are.....\n");
        System.out.println("---------------------------------------------------------");
        System.out.println("|Weight\t|Value\t|Ratio\t|Capacity\t|finalvalue\t|");
        System.out.println("---------------------------------------------------------");
        for (int i = ratio.length - 1; i >= 0; i--) {
            int index = (int) ratio[i][0];
            System.out.println("|"+weight[index]+"\t|"+val[index]+"\t|"+ratio[index][1]+"\t|"+capacity+"\t\t|"+finalVal+"\t\t|");
            if (capacity >= weight[index]) {
                finalVal += val[index]; // Use the original 'val' array
                capacity -= weight[index];
            } else {
                // for fractional item        
                finalVal += (ratio[i][1] * capacity);
                capacity = 0;
                System.out.println("|"+weight[index]+"\t|"+val[index]+"\t|"+ratio[index][1]+"\t|"+capacity+"\t\t|"+finalVal+"\t\t|");
                break;
            }
        }
        System.out.println("---------------------------------------------------------");
        System.out.println("Final Value : " + finalVal); // Output should be 240
        System.out.println("----------------------------------------------------------------------------");
    }
}
