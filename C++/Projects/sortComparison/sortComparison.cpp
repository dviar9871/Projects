#include <iostream>
#include <vector>
#include <cmath>  
#include <chrono>
using std::vector;
using std::cout;
using std::endl;
using namespace std::chrono;
using Duration = std::chrono::microseconds;
void insertionSort(std::vector<int>& numbers) {
	int j;
	int temp;
	for (int i = 1; i < numbers.size(); i++) {
		j = i;
		while (j > 0 && numbers[j] < numbers[j - 1]) {
			temp = numbers[j];
			numbers[j] = numbers[j - 1];
			numbers[j - 1] = temp;
			j--;
		}
	}




}



void bucketSort(std::vector<int>& numbers, int numBuckets) {

	if (numbers.size() < 1) {
		return;
	}
	vector<vector<int>> buckets;
	int index;
	for (int i = 0; i < numBuckets; i++) {
		vector<int> bucket = {};
		buckets.push_back(bucket);
	}

	int max = 0;
	for (int i = 1; i < numbers.size(); i++) {
		if (numbers.at(i) > max)
			max = numbers.at(i);
	}


	for (int i = 0; i < numbers.size(); i++) {

		index = numbers.at(i) * numBuckets / (max + 1);
		if (index >= buckets.size())
			break;
		buckets.at(index).push_back(numbers.at(i));

	}

	// Sort each bucket

	for (int i = 0; i < buckets.size(); i++) {
		insertionSort(buckets.at(i));
	}
	numbers.clear();

	for (int i = 0; i < buckets.size(); i++) {
		for (int j = 0; j < buckets.at(i).size(); j++) {
			numbers.push_back(buckets.at(i).at(j));
		}
	}

}


int radixGetLength(int value) {
	if (value == 0) {
		return 1;
	}

	int digits = 0;
	while (value != 0) {
		digits++;
		value /= 10;
	}
	return digits;
}

int radixGetMaxLength(int numbers[], int arr_size) {
	int maxDigits = 0;
	for (int i = 0; i < arr_size; i++) {
		int digitCount = radixGetLength(numbers[i]);
		if (digitCount > maxDigits) {
			maxDigits = digitCount;
		}
	}
	return maxDigits;
}


void radixSort(std::vector<int>& numbers) {
	//cout << "1";

	vector<vector<int>> buckets;
	for (int i = 0; i < 10; i++) {
		vector<int> bucket;
		buckets.push_back(bucket);
	}
	//cout << "2";
	int maxDigits = radixGetMaxLength(numbers.data(), numbers.size());
	int bucketIndex;
	//cout << "3" << endl;
	int pow10 = 1;
	for (int digitIndex = 0; digitIndex < maxDigits; digitIndex++) {
		for (int i = 0; i < numbers.size(); i++) {
			bucketIndex = abs(numbers.at(i) / pow10) % 10;
			buckets.at(bucketIndex).push_back(numbers.at(i));

		}
		//cout << "4";
		int arrayIndex = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < buckets.at(i).size(); j++) {
				numbers.at(arrayIndex) = buckets.at(i).at(j);
				arrayIndex++;
			}
			buckets.at(i).clear();
		}
	
		pow10 = 10 * pow10;
		
	}
	
}


int main() {
	vector<int> list;
	vector<int> copy_list;
	vector<int> insertion_times;
	vector<int> bucket_times;
	vector<int> radix_times;
	int values_of_ten[] = { 10,100,1000,10000 };
	int insertion_seconds = 0;
	int bucket_seconds = 0;
	int radix_seconds = 0;
	for (int i = 0; i < 4; i++) {
		insertion_seconds = 0;
		bucket_seconds = 0;
		radix_seconds = 0;
		cout << "Array Size: " << values_of_ten[i] << endl;
		for (int j = 0; j < 10; j++) {
			for (int arr_limit = 0; arr_limit < values_of_ten[i]; arr_limit++) {
				list.push_back(rand() % 1000);

			}

			copy_list = list;


			auto start = high_resolution_clock::now();
			insertionSort(list);
			auto stop = high_resolution_clock::now();
			long duration = duration_cast<milliseconds>(stop - start).count();
			insertion_seconds += duration;
			insertion_times.push_back(duration);
			list = copy_list;


			start = high_resolution_clock::now();
			bucketSort(list, 10);
			stop = high_resolution_clock::now();
			duration = duration_cast<milliseconds>(stop - start).count();
			//cout << "Bucket Sort time:" << duration.count() << " nanoseconds" << endl;
			bucket_seconds += duration;
			bucket_times.push_back(duration);

			list = copy_list;
			start = high_resolution_clock::now();
			radixSort(list);
			stop = high_resolution_clock::now();
			duration = duration_cast<milliseconds>(stop - start).count();
			//cout << "Radix Sort time:" << duration.count() << " nanoseconds" << endl;
			radix_times.push_back(duration);
			radix_seconds += duration;

			list.clear();
			copy_list.clear();
		}
		list.clear();
		copy_list.clear();





		cout << "Insertion average: " << (insertion_seconds / 10.0) << " Milliseconds" << endl;
		cout << "Bucket average: " << (bucket_seconds / 10.0) << " Milliseconds" << endl;
		cout << "Radix average: " << (radix_seconds / 10.0) << " Milliseconds" << endl << endl;


	}
	cout << "Completed" << endl;
	return 0;
}