/* EECS 3540
   Author: Daniel Viar
   Professor: Dr. Thomas
   Project 1
*/

#include <sys/time.h>
#include <sys/times.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>


int main(int argc, char* argv[], char* env[]){

    // Define structs to hold times and gettimeofday
    struct timeval startTotalTime, endTotalTime, elapsedTotalTime;
    struct tms end_program_tms;
    // Define child pid
    pid_t pid;
    // Get start time
    int cycles = sysconf(_SC_CLK_TCK);
    gettimeofday(&startTotalTime, NULL);
    // Enter loop to loop through terminal arguements
    for(int i = 1; i < argc; i++){
        // Define grandchild pid 
  
        pid_t grandchild_pid;

        pid = fork(); // Fork child process and store pids 

        if (pid < 0) { // error trying to fork?
            fprintf(stderr, "Fork Failed");
            return 1;
        }else if (pid == 0) {               // The child process will enter here
           // Define structs to get times for the grandchild processes
           struct tms end_tms; 
           struct timeval startTime, endTime, elapsedTime;
           // Get start time of grandchild process
           gettimeofday(&startTime,NULL);

           grandchild_pid = fork();
            
            if (grandchild_pid == 0) {  
                char* args[4] = {"/bin/bash","-c", argv[i], NULL}; 

                // Do the process from the terminal arguements     
                execve("/bin/bash", args, env);
                // Exit process so it doesn't bleed through
            
                exit(grandchild_pid);
            }else{
                
                wait(NULL); // wait for grandchild to end
               
                gettimeofday(&endTime, NULL); // Get end time
             
                timersub(&endTime, &startTime, &elapsedTime); // Subtract times of grandchild to get real time
               
                times(&end_tms); // Get cpu cycles to print

                float real_time_for_child = (elapsedTime.tv_usec / (1.0e6))+ elapsedTime.tv_sec;
                
                printf("%s %s %s", "Executing: ", argv[i], "\n");
                printf("%s %1.3lf %s" ,"Real:", real_time_for_child, "s\n");
                printf("%s %1.3Lf %s" ,"Usr:", (long double)end_tms.tms_cutime / cycles, "s\n");
                printf("%s %1.3Lf %s" ,"Sys:", (long double)end_tms.tms_cstime / cycles, "s\n\n");

                exit(pid); // Exit child process
            }

         }else {                           
            wait(NULL);                   // parent waits for the child to complete
        }
    }

    times(&end_program_tms); // Get times for whole program
    gettimeofday(&endTotalTime, NULL);
    timersub(&endTotalTime, &startTotalTime, &elapsedTotalTime);

    float real_time_for_program = (elapsedTotalTime.tv_usec / (1.0e6))+ elapsedTotalTime.tv_sec;

    // Print statistics
    printf("Summary Statistics: \n");
    printf("%s %1.3lf %s" ,"Real:", real_time_for_program, "s\n");
    printf("%s %1.3Lf %s" ,"Usr:", (long double)end_program_tms.tms_cutime / cycles, "s\n");
    printf("%s %1.3Lf %s", "Sys:", (long double)end_program_tms.tms_cstime / cycles, "s\n\n");

    return 0;
}




