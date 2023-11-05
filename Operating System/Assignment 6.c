#include <stdio.h>
#include <stdlib.h>

void fifoAlgorithm(int n, int m, int *p) {
    int a[n][m];
    int mp[m];
    for (int i = 0; i < m; i++) {
        mp[i] = 0;
    }
    int hit = 0;
    int totalAccesses = 0; // Total number of page accesses

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            a[i][j] = -1;
        }
    }

    for (int i = 0; i < m; i++) {
        int hasrun = 0;
        totalAccesses++; // Increment total accesses for each page request

        for (int j = 0; j < n; j++) {
            if (a[j][i] == p[i]) {
                hit++;
                mp[p[i]]++;
                hasrun = 1;
                break;
            }

            if (a[j][i] == -1) {
                for (int k = i; k < m; k++) {
                    a[j][k] = p[i];
                }
                mp[p[i]]++;
                hasrun = 1;
                break;
            }
        }

        if (!hasrun) {
            for (int j = 0; j < n; j++) {
                if (a[j][i] == p[i - n]) {
                    mp[a[j][i - n]]--;
                    for (int k = i; k < m; k++) {
                        a[j][k] = p[i];
                    }
                    mp[p[i]]++;
                    break;
                }
            }
        }
    }

    // Calculate hit ratio and miss ratio
    float hitRatio = (float)hit / totalAccesses;
    float missRatio = 1.0 - hitRatio;

    printf("Process ");
    for (int i = 0; i < m; i++) {
        printf("%d ", p[i]);
    }
    printf("\n");
    for (int i = 0; i < n; i++) {
        printf("Frame %d ", i);
        for (int j = 0; j < m; j++) {
            if (a[i][j] == -1)
                printf("E ");
            else
                printf("%d ", a[i][j]);
        }
        printf("\n");
    }

    printf("Hit %d\nPage Fault %d\n", hit, m - hit);
    printf("Hit Ratio: %.2f\nMiss Ratio: %.2f\n", hitRatio, missRatio);
}

void lruAlgorithm(int n, int m, int *p) {
    int a[n][m];
    int mp[m];
    for (int i = 0; i < m; i++) {
        mp[i] = 0;
    }
    int hit = 0;
    int totalAccesses = 0; // Total number of page accesses

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            a[i][j] = -1;
        }
    }

    for (int i = 0; i < m; i++) {
        int c[m][2];
        for (int j = 0; j < m; j++) {
            c[j][0] = 0;
            c[j][1] = 0;
        }
        totalAccesses++; // Increment total accesses for each page request

        int hasrun = 0;

        for (int j = 0; j < n; j++) {
            if (a[j][i] == p[i]) {
                hit++;
                mp[p[i]]++;
                hasrun = 1;
                break;
            }

            if (a[j][i] == -1) {
                for (int k = i; k < m; k++) {
                    a[j][k] = p[i];
                }
                mp[p[i]]++;
                hasrun = 1;
                break;
            }
        }

        if (!hasrun) {
            for (int j = 0; j < n; j++) {
                if (a[j][i] == c[m - 1][1]) {
                    mp[c[m - 1][1]]--;
                    for (int k = i; k < m; k++) {
                        a[j][k] = p[i];
                    }
                    mp[p[i]]++;
                    break;
                }
            }
        }

        for (int j = 0; j < m; j++) {
            if (c[j][1] != p[i]) {
                mp[c[j][1]]++;
            }
        }
    }

    // Calculate hit ratio and miss ratio
    float hitRatio = (float)hit / totalAccesses;
    float missRatio = 1.0 - hitRatio;

    printf("Process ");
    for (int i = 0; i < m; i++) {
        printf("%d ", p[i]);
    }
    printf("\n");
    for (int i = 0; i < n; i++) {
        printf("Frame %d ", i);
        for (int j = 0; j < m; j++) {
            if (a[i][j] == -1)
                printf("E ");
            else
                printf("%d ", a[i][j]);
        }
        printf("\n");
    }

    printf("Hit %d\nPage Fault %d\n", hit, m - hit);
    printf("Hit Ratio: %.2f\nMiss Ratio: %.2f\n", hitRatio, missRatio);
}

void optimalAlgorithm(int n, int m, int *p) {
    int a[n][m];
    int mp[m];
    for (int i = 0; i < m; i++) {
        mp[i] = 0;
    }
    int hit = 0;
    int totalAccesses = 0; // Total number of page accesses

    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            a[i][j] = -1;
        }
    }

    for (int i = 0; i < m; i++) {
        int op[m];
        int c[m][2];
        for (int j = 0; j < m; j++) {
            c[j][0] = 0;
            c[j][1] = 0;
        }
        totalAccesses++; // Increment total accesses for each page request

        for (int j = i + 1; j < m; j++) {
            for (int k = 0; k < n; k++) {
                if (a[k][i] == p[j]) {
                    op[j] = p[j];
                }
            }
        }

        for (int j = 0; j < m; j++) {
            op[j] = -1;
        }

        int dontCall = 1;

        for (int j = 0; j < m; j++) {
            if (op[j] == -1) {
                dontCall = 0;
            }
        }

        int hasrun = 0;

        for (int j = 0; j < n; j++) {
            if (a[j][i] == p[i]) {
                mp[p[i]]++;
                hasrun = 1;
                break;
            }

            if (a[j][i] == -1) {
                for (int k = i; k < m; k++) {
                    a[j][k] = p[i];
                }
                mp[p[i]]++;
                hasrun = 1;
                break;
            }
        }

        if (!hasrun) {
            for (int j = 0; j < n; j++) {
                if (dontCall == 1) {
                    if (a[j][i] == c[m - 1][1]) {
                        mp[c[m - 1][1]]--;
                        for (int k = i; k < m; k++) {
                            a[j][k] = p[i];
                        }
                        mp[p[i]]++;
                        break;
                    }
                } else if (dontCall == 0) {
                    if (a[j][i] == op[m - 1]) {
                        mp[op[m - 1]]--;
                        for (int k = i; k < m; k++) {
                            a[j][k] = p[i];
                        }
                        mp[p[i]]++;
                        break;
                    }
                }
            }
        }

        for (int j = 0; j < m; j++) {
            if (c[j][1] != p[i]) {
                mp[c[j][1]]++;
            }
        }
    }

    int hitv[m];

    for (int i = 1; i < m; i++) {
        hitv[i] = 0;

        for (int j = 0; j < n; j++) {
            if (p[i] == a[j][i - 1]) {
                hit++;
                hitv[i] = 1;
                break;
            }
        }
    }

    // Calculate hit ratio and miss ratio
    float hitRatio = (float)hit / totalAccesses;
    float missRatio = 1.0 - hitRatio;

    printf("Process ");
    for (int i = 0; i < m; i++) {
        printf("%d ", p[i]);
    }
    printf("\n");
    for (int i = 0; i < n; i++) {
        printf("Frame %d ", i);
        for (int j = 0; j < m; j++) {
            if (a[i][j] == -1)
                printf("E ");
            else
                printf("%d ", a[i][j]);
        }
        printf("\n");
    }

    printf("HIT     ");
    for (int i = 0; i < m; i++) {
        if (hitv[i] == 0)
            printf("  ");
        else
            printf("%d ", hitv[i]);
    }
    printf("\n");
    printf("Hit %d\nPage Fault %d\n", hit, m - hit);
    printf("Hit Ratio: %.2f\nMiss Ratio: %.2f\n", hitRatio, missRatio);
}

int main() {
    int n, m;
    printf("Enter number of frames: ");
    scanf("%d", &n);
    printf("Enter number of processes: ");
    scanf("%d", &m);
    int p[m];

    printf("Enter processes:\n");
    for (int i = 0; i < m; i++) {
        scanf("%d", &p[i]);
    }

    int choice;

    while (1) {
        printf("Select page replacement algorithm:\n");
        printf("1. FIFO\n2. LRU\n3. Optimal\n4. Exit\n");
        scanf("%d", &choice);

        if (choice == 4) {
            break; // Exit the program if the user chooses option 4
        }

        switch (choice) {
            case 1:
                printf("Running FIFO Algorithm\n");
                fifoAlgorithm(n, m, p);
                break;
            case 2:
                printf("Running LRU Algorithm\n");
                lruAlgorithm(n, m, p);
                break;
            case 3:
                printf("Running Optimal Algorithm\n");
                optimalAlgorithm(n, m, p);
                break;
            default:
                printf("Invalid choice\n");
        }
    }

    return 0;
}
