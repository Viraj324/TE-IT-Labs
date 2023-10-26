#include <stdio.h>

int main() {
    int numberOfProcesses, numberOfResources;
    printf("Enter the number of processes: ");
    scanf("%d", &numberOfProcesses);
    printf("Enter the number of resources: ");
    scanf("%d", &numberOfResources);

    int allocation[numberOfProcesses][numberOfResources];
    int max[numberOfProcesses][numberOfResources];
    int avail[numberOfResources];

    printf("Enter the Allocation Matrix:\n");
    for (int i = 0; i < numberOfProcesses; i++) {
        for (int j = 0; j < numberOfResources; j++) {
            printf("Enter allocation for P%d and resource R%d: ", i + 1, j + 1);
            scanf("%d", &allocation[i][j]);
        }
    }

    printf("Enter the Maximum Matrix:\n");
    for (int i = 0; i < numberOfProcesses; i++) {
        for (int j = 0; j < numberOfResources; j++) {
            printf("Enter Maximum for P%d and resource R%d: ", i + 1, j + 1);
            scanf("%d", &max[i][j]);
        }
    }

    printf("Enter available resources for each of the resource types:\n");
    for (int i = 0; i < numberOfResources; i++) {
        printf("Resource R%d: ", i + 1);
        scanf("%d", &avail[i]);
    }

    int f[numberOfProcesses], ans[numberOfProcesses], ind = 0;
    for (int k = 0; k < numberOfProcesses; k++) {
        f[k] = 0;
    }

    int need[numberOfProcesses][numberOfResources];
    for (int i = 0; i < numberOfProcesses; i++) {
        for (int j = 0; j < numberOfResources; j++) {
            need[i][j] = max[i][j] - allocation[i][j];
        }
    }

    for (int k = 0; k < numberOfProcesses; k++) {
        for (int i = 0; i < numberOfProcesses; i++) {
            if (f[i] == 0) {
                int flag = 0;
                for (int j = 0; j < numberOfResources; j++) {
                    if (need[i][j] > avail[j]) {
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    ans[ind++] = i;
                    for (int y = 0; y < numberOfResources; y++) {
                        avail[y] += allocation[i][y];
                    }
                    f[i] = 1;
                }
            }
        }
    }

    int flag = 1;
    for (int i = 0; i < numberOfProcesses; i++) {
        if (f[i] == 0) {
            flag = 0;
            printf("The following system is not safe");
            break;
        }
    }
    
    if (flag == 1) {
        printf("Following is the SAFE Sequence\n");
        for (int i = 0; i < numberOfProcesses - 1; i++) {
            printf(" P%d ->", ans[i]);
        }
        printf(" P%d\n", ans[numberOfProcesses - 1]);
    }

    return 0;
}
