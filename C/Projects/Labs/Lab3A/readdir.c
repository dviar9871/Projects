/*
Author: Daniel Viar
Professor: Dr. Thomas
EECS 3540
readdir- This project is a recreation of the dir command in windows on a fat32 directory
*/

#include "helper.h"

int main(int argc, char *argv[], char *env[])
{
    // Check for right amount of arguements
    if (argc == 2)
        printf("%s \n", argv[1]);
    else
    {
        printf("Invalid Arguement Passed\n");
        return -1;
    }

    // union longAndShort* segmentHold = (union longAndShort*) malloc(sizeof(union longAndShort) * 255);
    union longAndShort testRead;
    FILE *fp;

    // open file (may need to change address in the future)
    fp = fopen(argv[1], "r"); // Change to argv[1]
    unsigned long totalFilesize = 0;
    unsigned int totalNumFiles = 0;
    // Ensure file has been opened properly
    if (fp == NULL)
    {
        printf("Could not open file \n");
        return -1;
    }
    else // read file if it opened
    {

        struct longFileName lfnList[11]; // max size of a file name
        int num = 1;
        int spot = 0;

        while (fread(&testRead, sizeof(struct shortFileName), 1, fp) != 0)
        {
            // Read in next 32 byte segment

            if (testRead.sf.attribute == 0x08)
            {
                printf("Directory: EECS 3540\n");
                continue;
            }
            // Skip non directories and files that are empty
            if (testRead.sf.name[0] == 0x02)
                continue;
            else if (testRead.sf.name[0] == 0XE5)
                continue;
            else if (testRead.sf.name[0] == 0X00)
                continue;

            // Read lfns and keep track until next sfn
            if (testRead.sf.attribute == 0x0F)
            {
                lfnList[spot] = testRead.lf;
                spot++;
            }
            else if (testRead.sf.attribute != 0x0F) // sfn
            {

                // Date last modified
                // Bits 0–4: Day of month, valid value range 1-31 inclusive.
                // Bits 5–8: Month of year, 1 = January, valid value range 1–12 inclusive.
                // Bits 9–15: Count of years from 1980, valid value range 0–127 inclusive (1980–2107).

                // Mask and bit shift to get bits listed above
                unsigned int day = (testRead.sf.lastWriteDate & 0x1F);
                unsigned int month = (testRead.sf.lastWriteDate >> 5) & 0x0F;
                unsigned int year = (testRead.sf.lastWriteDate >> 9) & 0x7F;

                // Time last modified
                // Bits 0–4: 2-second count, valid value range 0–29 inclusive (0 – 58 seconds).
                // Bits 5–10: Minutes, valid value range 0–59 inclusive.
                // Bits 11–15: Hours, valid value range 0–23 inclusive.

                // Mask and bitshift to get bits listed above
                unsigned int minutes = testRead.sf.lastWriteTime >> 5 & 0x1F;
                unsigned int hours = testRead.sf.lastWriteTime >> 11 & 0x1F;

                // Make hours and minutes into strings to
                unsigned char minutesString[3];
                unsigned char hoursString[3];

                // Make char array to support am vs pm without excessive logic
                unsigned char amPm[3];
                amPm[1] = 'M';
                amPm[2] = '\0'; // Null terminate string for %s print

                if (minutes / 10 == 0) // See if minute is less than 10
                {
                    // Convert to string and put zero in front of single digit
                    sprintf(minutesString, "%d", minutes);
                    char temp = minutesString[0];
                    minutesString[0] = '0';
                    minutesString[1] = temp;
                    minutesString[2] = '\0';
                }
                else
                {
                    // Convert to minutes to string
                    sprintf(minutesString, "%d", minutes);
                }
                // fix edge case for 12 AM
                if (hours / 10 == 0 || (hours - 12) / 10 == 0) // Check if there is a single digit
                {
                    // Check for AM or PM
                    if (hours > 12)
                    {
                        hours -= 12;
                        amPm[0] = 'P';
                    }
                    else
                        amPm[0] = 'A';
                    // Add 0 infront of digit
                    if (hours == 12 && amPm[0] == 'A')
                        hours = 12;
                    sprintf(hoursString, "%d", hours);
                    char temp = hoursString[0];
                    hoursString[0] = '0';
                    hoursString[1] = temp;
                    hoursString[2] = '\0';
                }
                else
                {
                    // Convert if no digit in front is needed
                    sprintf(hoursString, "%d", hours);
                }

                // Get length of the filezize integer
                int tempFileSize = testRead.sf.fileSize;
                int lengthOfFileSize = 0;
                while (tempFileSize != 0)
                {
                    tempFileSize /= 10;
                    lengthOfFileSize++;
                }
                // Make string to hold file size and convert
                char *fileSize = (char *)malloc(sizeof(char) * lengthOfFileSize);
                sprintf(fileSize, "%d", testRead.sf.fileSize);
                // Copy name to new array to put in null terminator
                unsigned char sfNameTerm[9];
                strncpy(sfNameTerm, testRead.sf.name, 8);
                // Copy name to new array to put in null terminator
                unsigned char sfExt[4];
                strncpy(sfExt, testRead.sf.extention, 3);

                if (month / 10 == 0 && day / 10 == 0) // Print Date
                    printf("0%1d/0%1d/%2d  ", month, day, year + 1980);
                else if (month / 10 == 0)
                    printf("0%1d/%1d/%2d  ", month, day, year + 1980);
                else if (day / 10 == 0)
                    printf("%1d/0%1d/%2d  ", month, day, year + 1980);
                else
                    printf("%2d/%2d/%2d  ", month, day, year + 1980);

                if (hours >= 12) // Print time
                {

                    printf("%s:%s %2s  ", hoursString, minutesString, amPm);
                }
                else
                {

                    printf("%s:%s %2s  ", hoursString, minutesString, amPm);
                }

                printf("%u ", ((testRead.sf.highOrder << 16) | testRead.sf.lowOrder)); // put in commas
                if (testRead.sf.attribute == 16)
                {
                    printf("<dir> ");
                }
                else
                {

                    if (lengthOfFileSize > 3) // Print File Size
                    {
                        insertCommas(fileSize, lengthOfFileSize);
                    }
                    else
                        printf(" %d ", testRead.sf.fileSize);
                }
                free(fileSize);

                // If just SFN
                if (spot == 0 && lfnList[0].attribute == 0)
                {

                    // Print File Name
                    for (int i = 0; i < 9; i++)
                    {
                        if (sfNameTerm[i] != ' ')
                            printf("%c", sfNameTerm[i]);
                        else
                            continue;
                    }

                    printf(".%3s\n", sfExt); // Print File Extension
                    totalNumFiles++;
                    totalFilesize += testRead.sf.fileSize;
                }
                // Sfn that follows lfn
                else
                {
                    // Print lfn name from list starting from last lfn added to list
                    for (int i = spot; i >= 0; i--)
                    {
                        for (int j = 0; j < 10; j += 2)
                        {
                            if (lfnList[i].name1[j] == '\0' || lfnList[i].name1[j] == 255)
                                break;
                            printf("%c", lfnList[i].name1[j]);
                        }

                        for (int j = 0; j < 11; j += 2)
                        {
                            if (lfnList[i].name2[j] == '\0' || lfnList[i].name2[j] == 255)
                                break;
                            printf("%c", lfnList[i].name2[j]);
                        }

                        for (int j = 0; j < 4; j += 2)
                        {
                            if (lfnList[i].name3[j] == '\0' || lfnList[i].name3[j] == 255)
                                break;
                            printf("%c", lfnList[i].name3[j]);
                        }
                    }
                    printf("\n");
                    // Keep track of number of files and filesize
                    totalNumFiles++;
                    totalFilesize += testRead.sf.fileSize;
                }
                // Clear lfn list for new entries

                spot = 0;
                memset(lfnList, 0, 11 * sizeof(struct longFileName));
            }
        }
        // Get total size and number of files
        int lengthOfTotalFileSize = 0;
        int tempTotalFileSize = totalFilesize;
        while (tempTotalFileSize != 0)
        {
            tempTotalFileSize /= 10;
            lengthOfTotalFileSize++;
        }
        printf("\n%10d Files  ", totalNumFiles);

        char totalFilesizeString[20] = {};
        sprintf(totalFilesizeString, "%ld", totalFilesize);
        insertCommas(totalFilesizeString, lengthOfTotalFileSize);
        printf(" Bytes Used\n");
        // close file
        fclose(fp);
    }
    return 0;
}