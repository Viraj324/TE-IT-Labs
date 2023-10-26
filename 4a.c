#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#define BUFFER_SIZE 5

int buffer[BUFFER_SIZE];
int count = 0; // Number of items in the buffer

pthread_mutex_t mutex;
pthread_cond_t full, empty;

void *producer(void *arg) {
    int item;
    for (int i = 0; i < 10; i++) {
        item = rand() % 100; // Produce an item (for simplicity, a random integer)
        
        pthread_mutex_lock(&mutex);
        while (count == BUFFER_SIZE) {
            // Buffer is full, wait for consumer to consume
            pthread_cond_wait(&empty, &mutex);
        }

        buffer[count] = item;
        count++;
        printf("Produced: %d\n", item);

        // Signal that the buffer is not empty anymore
        pthread_cond_signal(&full);
        pthread_mutex_unlock(&mutex);
    }
    pthread_exit(NULL);
}

void *consumer(void *arg) {
    int item;
    for (int i = 0; i < 10; i++) {
        pthread_mutex_lock(&mutex);
        while (count == 0) {
            // Buffer is empty, wait for producer to produce
            pthread_cond_wait(&full, &mutex);
        }

        item = buffer[count - 1];
        count--;
        printf("Consumed: %d\n", item);

        // Signal that the buffer is not full anymore
        pthread_cond_signal(&empty);
        pthread_mutex_unlock(&mutex);
    }
    pthread_exit(NULL);
}

int main() {
    pthread_t producer_thread, consumer_thread;

    pthread_mutex_init(&mutex, NULL);
    pthread_cond_init(&full, NULL);
    pthread_cond_init(&empty, NULL);

    pthread_create(&producer_thread, NULL, producer, NULL);
    pthread_create(&consumer_thread, NULL, consumer, NULL);

    pthread_join(producer_thread, NULL);
    pthread_join(consumer_thread, NULL);

    pthread_mutex_destroy(&mutex);
    pthread_cond_destroy(&full);
    pthread_cond_destroy(&empty);

    return 0;
}
