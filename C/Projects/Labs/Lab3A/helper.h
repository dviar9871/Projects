#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#pragma pack(push,1)
struct shortFileName
{
    unsigned char name [8];
    unsigned char extention[3];
    unsigned char attribute;
    unsigned char windowNT; // Must be 0
    unsigned char fractSeconds;
    unsigned short timeCreated;
    unsigned short dateCreated;
    unsigned short dateAccessed; 
    unsigned short highOrder;
    unsigned short lastWriteTime;
    unsigned short lastWriteDate;
    unsigned short lowOrder;
    unsigned int fileSize;
};

struct longFileName
{
    unsigned char ord;
    unsigned char name1[10];
    unsigned char attribute;
    unsigned char type;
    unsigned char checkSum;
    unsigned char name2[12];
    unsigned short clusLo; // Must be 0
    unsigned char name3[4];
};

union longAndShort{
    struct shortFileName sf;
    struct longFileName lf;

};

#pragma pack(pop)

void insertCommas(char *arr, size_t arrSize)
{
    int firstSegOffset = 0;
    int spotForCommas = 0;

    if (arrSize % 3 != 0)
    {
        firstSegOffset = (arrSize % 3);
    }
    for (int i = 0; i < arrSize; i++)
    {
        if ((spotForCommas == 3 || i == firstSegOffset) && i != 0)
        {
            printf(",");
            spotForCommas = 0;
        }
        printf("%c", arr[i]);
        spotForCommas++;
    }

    printf(" ");
    return;
}

void displaySector(unsigned char* sector)
{
// Display the contents of sector[] as 16 rows of 32 bytes each. Each row is shown as 16 bytes,
// a "-", and then 16 more bytes. The left part of the display is in hex; the right part is in
// ASCII (if the character is printable; otherwise we display ".".
//
for (int i = 0; i < 16; i++) // for each of 16 rows
{ //
for (int j = 0; j < 32; j++) // for each of 32 values per row
{ //
printf("%02X ", sector[i * 32 + j]); // Display the byte in hex
if (j % 32 == 15) printf("- "); // At the half-way point? Show divider
}
printf(" "); // Separator space between hex & ASCII
for (int j = 0; j < 32; j++) // For each of those same 32 bytes
{ //
if (j == 16) printf(" "); // Halfway? Add a space for clarity
int c = (unsigned int)sector[i * 32 + j]; // What IS this char's ASCII value?
if (c >= 32 && c <= 127) printf("%1c", c); // IF printable, display it
else printf("."); // Otherwise, display a "."
} //
printf("\n"); // That’s the end of this row
}
}
