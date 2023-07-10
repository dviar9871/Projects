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
	cout << "1" << endl;
	if (numbers.size() < 1) {
		return;
	}
	vector<vector<int>> buckets;
	int index;
	for (int i = 0; i < numBuckets; i++) {
		vector<int> bucket = {};
		buckets.push_back(bucket);
	}
	cout << "2" << endl;
	int max = 0;
	for (int i = 1; i < numbers.size(); i++) {
		if (numbers.at(i) > max)
			max = numbers.at(i);
	}

	cout << "3" << endl;
	for (int i = 0; i < numbers.size(); i++) {
		
		index = numbers.at(i) * numBuckets / (max + 1);
		if (index >= numbers.size())
			break;
		buckets.at(index).push_back(numbers.at(i));

	}

	// Sort each bucket
	cout << "4" << endl;
	for (int i = 0; i < buckets.size(); i++) {
		insertionSort(buckets.at(i));
	}
	numbers.clear();
	cout << "5" << endl;
	for (int i = 0; i < buckets.size(); i++) {
		for (int j = 0; j < buckets.at(i).size(); j++) {
			numbers.push_back(buckets.at(i).at(j));
		}
	}
	cout << "6" << endl;
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
	
	vector<vector<int>> buckets;
	for (int i = 0; i < 10; i++) {
		vector<int> bucket;
		buckets.push_back(bucket);
	}
	
	int copyBackIndex = 0;

	// Find the max length, in number of digits
	int maxDigits = radixGetMaxLength(numbers.data(), numbers.size());

	int pow10 = 1;
	for (int digitIndex = 0; digitIndex < maxDigits; digitIndex++) {
		
		for (int i = 0; i < numbers.size(); i++) {
			int num = numbers[i];
			int bucketIndex = (abs(num) / pow10) % 10;
			
			buckets.at(bucketIndex).push_back(num);
		}

		// Copy buckets back into numbers array
		copyBackIndex = 0;
		
		for (int i = 0; i < 10; i++) {
			vector<int> bucket = buckets.at(i);
			for (int j = 0; j < bucket.size(); j++) {
				
				if (copyBackIndex == numbers.size()) {
					break;
				}
				numbers.at(copyBackIndex) = bucket.at(j);
				
				copyBackIndex++;
				
			}
			bucket.clear();
		}
		
		pow10 *= 10;
	}
	
	
}

int main() {
	vector<int> list;
	vector<int> copy_list;
	vector<Duration> insertion_times;
	vector<Duration> bucket_times;
	vector<Duration> radix_times;
	int values_of_ten[] = { 10,100,1000,10000 };
	microseconds insertion_seconds = microseconds(0);
	microseconds bucket_seconds = microseconds(0);
	microseconds radix_seconds = microseconds(0);
	for (int i = 0; i < 4; i++) {
		cout << "Array Size: " << values_of_ten[i] << endl;
		for (int j = 0; j < 10; j++) {
			for (int arr_limit = 0; arr_limit < values_of_ten[i]; arr_limit++) {
				list.push_back(rand());

			}

			copy_list = list;


			auto start = high_resolution_clock::now();
			insertionSort(list);
			auto stop = high_resolution_clock::now();
			Duration duration = duration_cast<microseconds>(stop - start);
			cout << "Insertion Sort time:" << duration.count() << " microseconds" << endl;
			insertion_times.push_back(duration);
			list = copy_list;


			start = high_resolution_clock::now();
			bucketSort(list, 2);
			stop = high_resolution_clock::now();
			duration = duration_cast<microseconds>(stop - start);
			cout << "Bucket Sort time:" << duration.count() << " microseconds" << endl;
			bucket_times.push_back(duration);

			list = copy_list;
			start = high_resolution_clock::now();
			radixSort(list);
			stop = high_resolution_clock::now();
			duration = duration_cast<microseconds>(stop - start);
			cout << "Radix Sort time:" << duration.count() << " microseconds" << endl;
			radix_times.push_back(duration);
			cout << endl;
			list.clear();
			copy_list.clear();
		}  
		for (int times = 0; times < values_of_ten[i]; times++) {
			insertion_seconds += insertion_times.at(times);
			bucket_seconds += bucket_times.at(times);
			radix_seconds += radix_times.at(times);
		}
		cout << "Insertion average" << insertion_seconds.count() / insertion_times.size() << endl;
		
	}
	return 0;
}