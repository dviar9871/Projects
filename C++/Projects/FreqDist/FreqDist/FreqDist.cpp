/*
	FreqDist/ Lab1
	Author: Daniel Viar
	Professor: Dr.Thomas
	Class: EECS 4760
	Description: This project focuses on creating histograms of provided files based on the frequency of the character count whether the file is in plain text or
	encrypted.
*/

#include <iostream>
#include <fstream>
#include <string>
const int NumberOfBytes = 256;
using namespace std;
// Get how many decmials are in front of the decimal
int precision(float num) {
	int precisionCount = 0;
	while (num >= 1) {
		num /= 10;
		precisionCount++;
	}
	return precisionCount;
}
//Xnormalized = (X - xmin / xmax - xmin) * height
// Scales function using normalization formula
int scale(int charPoint, int charMax, int charMin, int height, bool logOn) {
	// we are unconcerned with points that do not occur
	if (charPoint == 0) {
		return 0;
	}
	if (charPoint == charMax) {
		if (1) {
			cout << "";
		}
	}

	float scaledValue = 0;
	// If log is applied, log the values during scaling

	if (logOn)
		scaledValue = log(float(charPoint - charMin)) / log(float(charMax - charMin));
	else {
		scaledValue = float(charPoint - charMin) / float(charMax - charMin);
	}
	scaledValue *= height;
	// return rounded up scaled value
	return int(ceil(scaledValue));
}


int main(int argc, char* argv[])
{
	// charCount holds number of occurances
	unsigned int charCount[NumberOfBytes] = {};
	ifstream file;


	// Settings from command line arguements
	bool optA = false;
	bool optN = false;
	bool optRnn = false;
	bool optL = false;
	bool optG = false;
	int height = 20;
	// read in command line arguements
	for (int i = 1; i < argc; i++) {
		// cout << argv[i] << endl;
		if (argv[i][1] == 'a') {
			optA = true;
		}
		else if (argv[i][1] == 'n') {
			optN = true;
		}
		else if (argv[i][1] == 'r') {
			optRnn = true;
			// take in value of rxx
			string heightString = argv[i];
			int indexOfEnd = heightString.find("]");
			// Get number out of parameter
			heightString = heightString.substr(heightString.find("r") + 1);
			height = stoi(heightString);
		}
		else if (argv[i][1] == 'l') {
			optL = true;
		}
		else if (argv[i][1] == 'g') {
			optG = true;
		}
	}
	// Open file and check to ensure that it is opened properly
	file.open(argv[argc - 1], ios::binary);
	//file.open("C:\\Users\\dxvia\\Downloads\\hamlet.txt", ios::binary);
	if (file.is_open() == 0) {
		cout << "Input file not found" << endl;
		return 1;
	}

	// Current Character being read from file
	char currentChar;

	// Read in information from file and add it to char array
	while (file.get(currentChar)) {
		charCount[unsigned char(currentChar)]++;
	}

	// Set up values for statistics and read in from file
	int maxCharValue = 0;
	int minCharValue = 10;
	unsigned char maxChar;
	unsigned char minChar;
	double avgChar = 0;
	double stdChar = 0;
	double sum = 0;
	double sumSquared = 0;
	// Get statistics from data
	for (int i = 0; i < NumberOfBytes; i++) {
		sum += charCount[i];
		sumSquared += (charCount[i] * charCount[i]);
		//cout << char(i) << ": " << charCount[i] << endl;

		if (charCount[i] > maxCharValue) {
			maxChar = i;
			maxCharValue = charCount[i];
		}
		if (charCount[i] < minCharValue && charCount[i] != 0) {
			minChar = i;
			minCharValue = charCount[i];
		}


	}
	avgChar = sum / NumberOfBytes;
	stdChar = sqrt(sumSquared - ((sum * sum) / NumberOfBytes) / NumberOfBytes);

	file.close();

	// Statistics print out
	if (optG) {
		cout << endl;
		printf("Min :%9d Max  (%03d) :%9d AVG :%.*f DEV :%.*f   ", minCharValue, maxChar, maxCharValue, 8 - precision(avgChar), avgChar, 8 - precision(stdChar), stdChar);
	}
	// Build Histogram
	else {
		// Build the border and put in the astricks
		cout << "+";
		for (int i = 0; i < NumberOfBytes; i++) {
			cout << "-";
		}
		cout << "+" << endl;
		// Go from the top down and input the astrick if it is less than i
		for (int i = height; i >= 0; i--) {
			cout << "|";
			for (int j = 0; j < NumberOfBytes; j++) {
				int scaledValue = scale(charCount[j], maxCharValue, minCharValue, height, optL);
				if (scaledValue > i || scaledValue == height)
					cout << "*";
				else
					cout << " ";
			}
			cout << "|" << endl;
		}
		// Finish building the histogram outline
		cout << "+";
		for (int i = 0; i < NumberOfBytes; i++) {
			cout << "-";
		}
		cout << "+" << endl;


		if (optN) {
			cout << "|";
			// First row - isolate the first digit
			for (int i = 0; i < NumberOfBytes; i++) {
				if (i > 99)
					cout << i / 100;
				else
					cout << " ";
			}
			cout << "|" << endl << "|";

			// Second Row - isolate the middle digit
			for (int i = 0; i < NumberOfBytes; i++) {
				if (i > 9 && i <= 99)
					cout << i / 10;
				else if (i > 99) {
					cout << (i / 10) % 10;
				}
				else
					cout << " ";
			}

			cout << "|" << endl << "|";

			// Third Row - isolate the last digit
			for (int i = 0; i < NumberOfBytes; i++) {
				if (i < 10)
					cout << i;
				else
					cout << i % 10;
			}
			cout << "|" << endl;

			// Print the ascii characters inline with the  0 - 255 values
			if (optA) {
				cout << "|";
				for (int i = 0; i < NumberOfBytes; i++) {
					if (i >= 33 && i <= 126)
						cout << char(i);

					else
						cout << " ";
				}
			}
			cout << "|" << endl;
		}
		cout << endl;
		printf("Min :%9d Max  (%03d) :%9d AVG :%.*f DEV :%.*f   ", minCharValue, maxChar, maxCharValue, 8 - precision(avgChar), avgChar, 8 - precision(stdChar), stdChar);

	}
	return 0;
}

