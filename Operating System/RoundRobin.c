#include <stdio.h>

int main() {
    int n, i, qt, count = 0, temp, sq = 0, bt[10], tat[10], rem_bt[10], wt[10]; // Added 'wt' array for waiting time
    float awt = 0, atat = 0;
    
    printf("Enter number of Processes: ");
    scanf("%d", &n);
    
    printf("Enter the Burst time of processes: ");
    for (i = 0; i < n; i++) {
        scanf("%d", &bt[i]);
        rem_bt[i] = bt[i];
    }
    
    printf("Enter The Quantum Time: ");
    scanf("%d", &qt);
    
    while (1) {
        for (i = 0, count = 0; i < n; i++) {
            temp = qt;
            
            if (rem_bt[i] == 0) {
                count++;
                continue;
            }
            
            if (rem_bt[i] > qt) {
                rem_bt[i] = rem_bt[i] - qt;
            } else {
                temp = rem_bt[i];
                rem_bt[i] = 0;
            }
            
            sq = sq + temp;
            tat[i] = sq;
        }
        
        if (n == count) {
            break;
        }
    }
    
    printf("\nProcess\tBurst Time\tTurnaround Time\tWaiting Time\n");
    for (i = 0; i < n; i++) {
        wt[i] = tat[i] - bt[i];
        awt = awt + wt[i];
        atat = atat + tat[i];
        printf("%d\t%d\t\t%d\t\t%d\n", i + 1, bt[i], tat[i], wt[i]);
    }
    
    awt = awt / n;
    atat = atat / n;
    printf("Average Waiting Time = %f\n", awt);
    printf("Average Turnaround Time = %f\n", atat);
    
    return 0;
}

