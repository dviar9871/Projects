/*
Author: Daniel Viar
Professor: Dr. Thomas
Date: 03/23/2023
Lab 2 - a multithreaded sorting program focused on speed
*/

#define _GNU_SOURCE

#include <pthread.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>
#include <stdbool.h>
#include <sys/time.h>
#include <errno.h>
#include <sys/time.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>

void quickSort(int low, int high);
bool isSorted();
// Partition struct to hold data points for easy passing
struct partition
{
    int low;
    int high;
    int range;
};

// Setting global variable as parameters to save stack space
unsigned int *dataArray;

struct partition *partitionIndices;

int size;

char alternate = 'S';

long int threshold = 10;

long int seed;

char multithread = 'n';

int pieces = 10;

int maxThreads = 4;

char median = 'n';

char early = 'y';
// Calls quick sort on passed partition
void *thread(void *args)
{
    struct partition passedPartition = *(struct partition *)args; // cast to int pointer then dereference it to avoid warning

    quickSort(passedPartition.low, passedPartition.high);

    pthread_exit(0);
}
// Runs the early thread and calls quick sort on passed partition
void *earlyThread(void *args)
{
    struct partition partition = *(struct partition *)args; // cast to int pointer then dereference it to avoid warning

    double earlyThreadPercent = ((double)(partition.high - partition.low) / (double)size) * 100;
    printf("Early Launching: %d - %d (%1.2f%c)\n", partition.low, partition.high, earlyThreadPercent, 37);

    quickSort(partition.low, partition.high);

    pthread_exit(0);
}

int main(int argc, char *argv[], char *env[])
{
    // Declare time keeping variables
    struct timeval beforeSort, afterSort, beforeFile, afterFile;
    clock_t beforeClockSort, afterClockSort;
    int totalThreads = 0;
    // Command line read in *******************************************************************************************
    // Read through argv to get lines from command
    for (int i = 1; i < argc; i++)
    {
        // Read in for size
        if (strcmp(argv[i], "-n") == 0)
        {
            i++;
            size = atoi(argv[i]);
            if (size < 1 || size > 1000000000) // check for size
            {
                printf("Failed to provide a valid size");
                return -1;
            }
            else if (size == 100000000)
                size--;
        }
        // Read in alternate
        else if (strcmp(argv[i], "-a") == 0)
        {
            i++;
            // check s or i to determine sort
            if (argv[i][0] == 'S' || argv[i][0] == 's' || argv[i][0] == 'i' || argv[i][0] == 'I')
                alternate = argv[i][0];
            else
            {
                printf("\nFailed to provide a proper alternate %c\n", argv[i][0]);
                return -1;
            }
        }
        // threshold read in
        else if (strcmp(argv[i], "-s") == 0)
        {
            i++;
            threshold = atoi(argv[i]);
            /*
            if (threshold < 3) // makes sure threshold is valid
            {
                printf("%s", "Failed to provide a large enough threshold");
                return -1;
            }
            else if (threshold > size)
            {
                printf("%s", "Failed to provide a small enough threshold");
                return -1;
            }
            */
        }
        // Seed value read in
        else if (strcmp(argv[i], "-r") == 0)
        {
            i++;
            seed = atoi(argv[i]) * sizeof(int);

            if (seed > 1000000000 || seed < 0)
            {
                printf("Failed to provide proper seed value");
                return -1;
            }
        }
        // Multithreading read in
        else if (strcmp(argv[i], "-m") == 0)
        {
            i++;
            if (argv[i][0] == 'y' || argv[i][0] == 'Y' || argv[i][0] == 'n' || argv[i][0] == 'N')
            {
                multithread = argv[i][0];
            }
            else
            {
                printf("Failed to provide a proper multithreading answer\n");
                return -1;
            }
        }
        // Read in for pieces
        else if (strcmp(argv[i], "-p") == 0)
        {
            i++;
            pieces = atoi(argv[i]);
        }
        // Read in for max thread
        else if (strcmp(argv[i], "-t") == 0)
        {
            i++;
            maxThreads = atoi(argv[i]);

            if (maxThreads > pieces)
            {
                printf("Failed to provide a valid number of pieces");
                return -1;
            }
        }
        // Read in for median of three
        else if (strcmp(argv[i], "-m3") == 0)
        {
            i++;
            if (argv[i][0] == 'y' || argv[i][0] == 'Y' || argv[i][0] == 'n' || argv[i][0] == 'N')
            {
                median = argv[i][0];
            }
            else
            {
                printf("Failed to provide a proper median answer\n");
                return -1;
            }
        }
        // Read in for early thread
        else if (strcmp(argv[i], "-e") == 0)
        {
            i++;
            if (argv[i][0] == 'y' || argv[i][0] == 'Y' || argv[i][0] == 'n' || argv[i][0] == 'N')
            {
                early = argv[i][0];
            }
            else
            {
                printf("Failed to provide a proper multithreading answer\n");
                return -1;
            }
        }
        // Invalid parameter passed
        else
        {
            printf("Invalid parameter provided");
            return -1;
        }
    }

    // printf("Sorted\n Size: %d \n alternate: %c\n threshold: %ld\n" , size, alternate, threshold);
    //  Determine if there is a seed
    bool noSeed = (seed <= -1) ? 0 : 1;
    // Allocate space for global arrays
    dataArray = (int *)malloc(size * sizeof(int));
    partitionIndices = (struct partition *)malloc(pieces * sizeof(struct partition));

    // Generate a random seed if no seed is provided
    if (noSeed)
    {
        struct timeval seedTime;
        gettimeofday(&seedTime, NULL);
        // use time in usec to seed srand
        srand(seedTime.tv_usec);
        seed = rand() % 1000000000;
    }
    gettimeofday(&beforeFile, NULL); // start tracking file read time
    // File read *********************************************************************************************************
    int readValue;
    FILE *fp;
    // open file (may need to change address in the future)
    fp = fopen("random.dat", "r");
    // Ensure file has been opened properly
    if (fp == NULL)
    {
        printf("Could not open file \n");
        return -1;
        // read file if it opened
    }
    else
    {
        // If there is a seed value start from there

        // Read into array using fread
        fseek(fp, seed * sizeof(int), SEEK_CUR);
        readValue = fread(dataArray, sizeof(int), size, fp);
        // If the end of the file was hit
        if (readValue != size)
        {
            fseek(fp, 0, SEEK_SET); // wrap around and read the rest
            readValue = fread((void *)&dataArray + readValue, sizeof(int), size - readValue, fp);
        }

        // close file
        fclose(fp);
    }

    gettimeofday(&afterFile, NULL);  // Get load time
    gettimeofday(&beforeSort, NULL); // start tracking sorting time
    beforeClockSort = clock();

    if (multithread == 'y' || multithread == 'Y') // If we are multithreading
    {
        // early thread*******************************************************************************************************
        int earlyJ = 0;
        pthread_t threadEarly;
        struct partition partitionEarlyLower, partitionEarlyUpper;

        if (early == 'y' || early == 'Y') // If we are launching early thread
        {
            // Initialize locations and values array for early thread
            int locations[11];
            int values[11];

            // Get locations based on size
            for (int i = 1; i < 11; i++)
                locations[i] = ((double)i * (double).1) * size;

            locations[0] = 0;
            locations[10] = size - 1;
            // Fill values array
            for (int i = 0; i < 11; i++)
            {
                int index = locations[i];
                values[i] = dataArray[index];
            }
            // Get starting min value
            int min = values[0];
            int minIndex;
            // Get first min value
            for (int i = 0; i < 11; i++)
            {
                if (values[i] < min)
                {
                    min = values[i];
                    minIndex = i;
                }
            }
            int secondMin;
            int secondMinIndex;
            // get starting min value
            if (values[0] == min)
                secondMin = values[1];
            else
                secondMin = values[0];
            // Get second min value
            for (int i = 0; i < 11; i++)
            {
                if ((values[i] < secondMin || values[i] == secondMin) && values[i] > min)
                {
                    secondMin = values[i];
                    secondMinIndex = i;
                }
            }

            // Swap spot 0 of array and second min index to get a better partition
            int temp = dataArray[0];
            dataArray[0] = dataArray[secondMinIndex];
            dataArray[secondMinIndex] = temp;

            // Partition segment
            int earlyI = 0;
            earlyJ = size + 1; // Partition starts here: start at the two ends
            int earlyPivot = dataArray[0];
            do //  do partition found in quicksort
            {
                do
                {
                    earlyI++;
                } while (dataArray[earlyI] < earlyPivot);

                do
                {
                    earlyJ--;
                } while (dataArray[earlyJ] > earlyPivot);

                if (earlyI < earlyJ)
                {
                    int temp = dataArray[earlyI];
                    dataArray[earlyI] = dataArray[earlyJ];
                    dataArray[earlyJ] = temp;
                }
                else
                    break;
            } while (true);

            temp = dataArray[0];
            dataArray[0] = dataArray[earlyJ];
            dataArray[earlyJ] = temp;

            // set up struct for the early partition
            partitionEarlyUpper.high = size - 1;
            partitionEarlyUpper.low = earlyJ + 1;
            partitionEarlyUpper.range = partitionEarlyUpper.high - partitionEarlyUpper.low;

            partitionEarlyLower.high = earlyJ - 1;
            partitionEarlyLower.low = 0;
            partitionEarlyLower.range = partitionEarlyLower.high - partitionEarlyLower.low;

            if (partitionEarlyUpper.range > partitionEarlyLower.range) // Start early thread for smaller side
                pthread_create(&threadEarly, NULL, earlyThread, &partitionEarlyLower);
            else
                pthread_create(&threadEarly, NULL, earlyThread, &partitionEarlyUpper);
        }

        // Partition Array ****************************************************************************************************

        // Get space of next empty spot in array
        int emptySpaceIndex = 1;

        // Array to hold all partitions
        // Initialize first partition with full array to start
        if (early == 'y' || early == 'Y')
        {
            if (partitionEarlyUpper.range > partitionEarlyLower.range) // If the early thread is called adjust the starting partition accordingly
            {
                partitionIndices[0].high = partitionEarlyUpper.high;
                partitionIndices[0].low = partitionEarlyUpper.low;
                partitionIndices[0].range = partitionEarlyUpper.range;
            }
            else // use the normal set up
            {
                partitionIndices[0].high = partitionEarlyLower.high;
                partitionIndices[0].low = partitionEarlyLower.low;
                partitionIndices[0].range = partitionEarlyLower.range;
            }
        }
        else
        {
            partitionIndices[0].high = size - 1;
            partitionIndices[0].low = 0;
            partitionIndices[0].range = partitionIndices[0].high - partitionIndices[0].low;
        }
        int largestSegmentIndex = 0; // we know only segment is in spot 0
        int jHighFlag = 0;
        while ((partitionIndices[pieces - 1].range == 0 && emptySpaceIndex <= pieces))
        {

            // Fill struct with largest segment to partition
            struct partition largestSegment = partitionIndices[largestSegmentIndex];

            // Use quick sort partitioning algo to get pivot using values i, j from largest segment
            int partitionI = largestSegment.low;
            int partitionJ = largestSegment.high + 1;
            int partitionPivot = dataArray[largestSegment.low];

            if (median == 'y' || median == 'Y') // Check for median of three
            {

                int k = (largestSegment.low + largestSegment.high) / 2; // get median

                int A = dataArray[largestSegment.low];
                int B = dataArray[k];
                int C = dataArray[largestSegment.high];
                int medianValue;
                if ((A >= B && A <= C) || (A >= C && A <= B))
                    medianValue = largestSegment.low; // i.e., A is between B & C (BAC or CAB)
                else if ((B >= A && B <= C) || (B >= C && B <= A))
                    medianValue = k; // B between A & C (ABC CBA)
                else
                    medianValue = largestSegment.high; // C is all that's left!
                // swap for median of three
                int partitionI = largestSegment.low;
                int partitionJ = largestSegment.high + 1; // Partition starts here: start at the two ends
                int partitionPivot = dataArray[medianValue];
            }

            do // Do partition found in quick sort
            {
                do
                {
                    partitionI++;
                } while (dataArray[partitionI] < partitionPivot);

                do
                {
                    partitionJ--;
                } while (dataArray[partitionJ] > partitionPivot);

                if (partitionI < partitionJ)
                {
                    int temp = dataArray[partitionI];
                    dataArray[partitionI] = dataArray[partitionJ];
                    dataArray[partitionJ] = temp;
                }
                else
                    break;
            } while (true);

            int temp = dataArray[largestSegment.low];
            dataArray[largestSegment.low] = dataArray[partitionJ];
            dataArray[partitionJ] = temp;

            // Take each side of the new partitions and fill struct
            struct partition right;
            struct partition left;

            right.low = partitionJ + 1;
            right.high = largestSegment.high;
            right.range = right.high - right.low;

            left.low = largestSegment.low;
            left.high = partitionJ - 1;
            left.range = left.high - left.low;

            // put partitions back into partitionIndices array in correct spot
            partitionIndices[emptySpaceIndex] = left;
            partitionIndices[largestSegmentIndex] = right;

            // Update the index of the next empty space
            emptySpaceIndex++;
            double leftPercent = 0;
            double rightPercent = 0;
            // Fill variables for print statements
            leftPercent = ((double)left.range / (double)largestSegment.range) * 100;
            rightPercent = (double)right.range / (double)largestSegment.range * 100;

            if (jHighFlag == 0) // ensures that if the right side is 0% it prints that way
            {
                printf("Partitioning: %9d - %9d (%d)...result:  %d - %d (%1.1f/ %1.1f)\n",
                       largestSegment.low, largestSegment.high, largestSegment.range + 1, left.range, right.range,
                       leftPercent, rightPercent);
            }
            else
            { // Print partition
                printf("Partitioning: %9d - %9d (%d)...result:  %9d - %9d (%1.1f/ %1.1f)\n",
                       largestSegment.low, largestSegment.high, largestSegment.range, left.range, right.range,
                       leftPercent, rightPercent);
            }

            // find index of the largest partition in list
            largestSegmentIndex = 0;

            for (int k = 1; k < pieces; k++)
            {
                if (partitionIndices[k].range > partitionIndices[largestSegmentIndex].range)
                {
                    largestSegmentIndex = k;
                }
            }
        }

        for (int i = 0; i < pieces; i++) // partition sort using insertion sort
        {
            for (int j = i; j > 0; j--)
            {
                if (partitionIndices[j - 1].range > partitionIndices[j].range)
                {
                    struct partition temp = partitionIndices[j - 1];
                    partitionIndices[j - 1] = partitionIndices[j];
                    partitionIndices[j] = temp;
                }
            }
        }
        // Print all the partitions
        for (int i = 0; i < pieces; i++)
        {
            float rightMostPrintElement = partitionIndices[i].low / partitionIndices[i].range;

            printf("%d: %9d - %9d (%9d - %1.3f) \n",
                   i, partitionIndices[i].low, partitionIndices[i].high, partitionIndices[i].range, rightMostPrintElement);
        }
        int threadCount = 1;
        int threadIndex = 0;
        pthread_t *threadList;
        threadList = (pthread_t *)malloc(pieces * sizeof(pthread_t));
        // Set all pthread values to 0
        for (int i = 0; i < pieces; i++)
        {
            threadList[i] = 0;
        }

        // Create threads up to partition size
        for (int i = pieces - 1; i >= 0; i--)
        {
            if (threadCount <= maxThreads)
            {
                // Create and join thread
                pthread_t threadId;
                totalThreads++;
                pthread_create(&threadId, NULL, thread, (void *)&partitionIndices[i]);

                printf("Launching thread to sort: %9d - %9d(%9d): (row %d)\n",
                       partitionIndices[i].low, partitionIndices[i].high,
                       partitionIndices[i].range, i);

                // add thread to list to keep track
                threadList[threadIndex] = threadId;
                // Increment counters
                threadCount++;
                threadIndex++;
            }
            else
            {
                i++; // stay at the same index in partition array

                for (int j = 0; j < pieces; j++) // iterate through all threads
                {
                    if (threadList[j] != 0) //  try join seg faults of pid of 0 so we skip them
                    {
                        int threadStatus = pthread_tryjoin_np(threadList[j], NULL);
                        if (threadStatus != EBUSY) // Throws segmantation fault
                        {
                            threadList[j] = 0; //  we are done with thread set it to zero
                            threadCount--;     // lower number of threads running
                        }
                    }
                }
                usleep(100000); // wait so we are not polling constantly
            }
        }

        while (threadCount > 0 || totalThreads != 0) // wait for all threads to finish
        {
            for (int j = 0; j < pieces; j++) // iterate through all threads
            {
                if (threadList[j] != 0) //  try join seg faults of pid of 0 so we skip them
                {

                    int threadStatus = pthread_tryjoin_np(threadList[j], NULL);
                    if (threadStatus != EBUSY) // Check to see if they are busy
                    {
                        threadList[j] = 0;
                        threadCount--; // decrement thread count cause a thread completed
                    }
                    j--; // keep checking thread that is valid to make sure it is done
                }
                else
                { //  If the thread id is 0 we know it is done so we can leave it out of the count
                    threadCount--;
                    totalThreads--;
                }
            }
            usleep(100);
        }
        if (early == 'y' || early == 'Y') // wait for the early thread to finish
        {
            while (pthread_tryjoin_np(threadEarly, NULL) == EBUSY)
            {
                printf("EARLY THREAD IS STILL RUNNING AT END\n");
                usleep(100000);
            }
        }
    }

    else
    {
        // Call quick sort on whole array of we are not threading
        quickSort(0, size - 1);
    }
    // Get end clock times
    gettimeofday(&afterSort, NULL);
    afterClockSort = clock();

    clock_t elapsedClock = afterClockSort - beforeClockSort;
    struct timeval elapsedSort, elapsedFile;
    timersub(&afterFile, &beforeFile, &elapsedFile);
    timersub(&afterSort, &beforeSort, &elapsedSort);

    // Clock arithmatic to get it into correct form for print
    float totalWallSortTime = (elapsedSort.tv_usec / (1.0e6)) + elapsedSort.tv_sec;
    float totalFileTime = (elapsedFile.tv_usec / (1.0e6)) + elapsedFile.tv_sec;
    float totalClockSortTime = (float)elapsedClock / (float)CLOCKS_PER_SEC;
    float totalTime = totalWallSortTime + totalFileTime;
    printf("Load: %1.3f Sort (WALL/CPU): %1.3f / %1.3f Total: %1.3f  ", totalFileTime, totalWallSortTime, totalClockSortTime, totalTime);

    if (isSorted())
        printf("Sorted\n");

    else
        printf("ERROR - Unsorted\n");

    return 0;
}
// Quicksort using median of three and hybrid sorts to allow for time to be saved
void quickSort(int low, int high)
{

    if (low < high) // base case: when down to 1 item, p == r, so nothing to do
    {
        int difference = high + 1 - low;

        // Handle when there aren't enough elements to sort
        if (difference < 2)
            return;
        else if (difference == 2) // Check to see if swap is needed when there are only two elements
        {
            if (dataArray[low] > dataArray[high])
            {
                int temp = dataArray[low];
                dataArray[low] = dataArray[high];
                dataArray[high] = temp;
            }

            return;
        }
        // Check to see if a switch to a different sort is needed
        else if (difference <= threshold && threshold > 3)
        {

            // Shell Sort
            if (alternate == 's' || alternate == 'S')
            {
                // ensure all possibly used variables are cleared
                int i = 0;
                int j = 0;
                int k = 0;
                int temp = 0;
                // Make hibbard sequence value
                k = 1;
                while (k <= high + 1)
                    k *= 2;
                k = (k / 2) - 1;

                // Shell sorting
                do
                {
                    for (i = low; i < (high + 1 - k); i++)
                    {
                        for (j = i; j >= low; j -= k)
                        {
                            if (dataArray[j] <= dataArray[j + k])
                                break;
                            else
                            { // Swap values
                                temp = dataArray[j];
                                dataArray[j] = dataArray[j + k];
                                dataArray[j + k] = temp;
                            }
                        }
                    }
                    k = k >> 1;

                } while (k > 0);

                return;
            }
            // Insertion Sort
            else if (alternate == 'i' || alternate == 'I')
            {

                for (int insertI = low; insertI < high + 1; insertI++)
                {
                    for (int insertJ = insertI; insertJ > low; insertJ--)
                    {
                        if (dataArray[insertJ - 1] > dataArray[insertJ])
                        {
                            int temp = dataArray[insertJ - 1];
                            dataArray[insertJ - 1] = dataArray[insertJ];
                            dataArray[insertJ] = temp;
                        }
                    }
                }
            }
            return;
        }
        else
        {
            int i = low;
            int j = high + 1; // Partition starts here: start at the two ends
            int pivot = dataArray[low];
            // Start moving i and j values toward pivor to swap
            if (median == 'y' || median == 'Y') // Check for median of three
            {

                int k = (low + high) / 2; // get median

                int A = dataArray[low];
                int B = dataArray[k];
                int C = dataArray[high];
                int medianValue;

                if ((A >= B && A <= C) || (A >= C && A <= B))
                {
                    medianValue = low; // i.e., A is between B & C (BAC or CAB)
                }
                else if ((B >= A && B <= C) || (B >= C && B <= A))
                {
                    medianValue = k; // B between A & C (ABC CBA)
                }

                else
                {
                    medianValue = high; // C is all that's left!
                }

                // Swap for median of three
                int i = low;
                int j = high + 1;

                int pivot = dataArray[medianValue];
            }
            // Partition the array
            do
            {
                do
                {
                    i++;
                } while (dataArray[i] < pivot);

                do
                {
                    j--;
                } while (dataArray[j] > pivot);

                if (i < j)
                {
                    int temp = dataArray[i];
                    dataArray[i] = dataArray[j];
                    dataArray[j] = temp;
                }
                else
                    break;
            } while (true);
            // Swap pivot
            int temp = dataArray[low];
            dataArray[low] = dataArray[j];
            dataArray[j] = temp;

            if (low == j) //  don't bother calling sides that would run to base case to save stack space
            {
                quickSort(j + 1, high); // (recursively) QS the right side
                return;
            }
            else if (j == high)
            {
                quickSort(low, j - 1); // (recursively) QS the left side
                return;
            }

            quickSort(low, j - 1);  // (recursively) QS the left side
            quickSort(j + 1, high); // (recursively) QS the right side
        }
    }
}
// Checks to make sure data is sorted properly
bool isSorted()
{
    for (int i = 0; i < size - 1; i++)
        if (dataArray[i] < dataArray[i - 1]) // check the element before it to make sure it is smaller
            return 0;
    return 1;
}
