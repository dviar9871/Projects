// Remember, we're gonna use strict mode in all scripts now!
// Installed Prettier plug in
"use strict";

///////////////////////////////////////
// Using Google, StackOverflow and MDN

// PROBLEM 1:
// We work for a company building a smart home thermometer. Our most recent task is this: "Given an array of temperatures of one day, calculate the temperature amplitude. Keep in mind that sometimes there might be a sensor error."

// function calcAmplitude(tempArray) {
//   // Get max and min at the same time
//   let max = tempArray[0];
//   let min = tempArray[0];

//   // Look and update max and min at the same time to reduce loops
//   for (let i = 1; i < tempArray.length; i++) {
//     if (typeof tempArray[i] !== "number") continue;
//     else if (max < tempArray[i]) max = tempArray[i];
//     else if (min > tempArray[i]) min = tempArray[i];
//   }

//   return max - min;
// }

// const temperatures = [3, -2, -6, -1, "error", 9, 13, 17, 15, 14, 9, 5];

// console.log(calcAmplitude(temperatures));

// // now there are two temp arrays how to solve ?
// // Merge arrays and run function

// const temperatures2 = [4, -5, -1, 10, "error", 9, "Error", 17, 15, 20, 9, 5];

// const temperatureCombined = temperatures.concat(temperatures2);

// console.log(temperatureCombined);

// console.log(calcAmplitude(temperatureCombined));

// Challenge 1

/*
Given an array of forecasted maximum temperatures, the thermometer displays a string with these temperatures.

Example: [17, 21, 23] will print "... 17ºC in 1 days ... 21ºC in 2 days ... 23ºC in 3 days ..."

Create a function 'printForecast' which takes in an array 'arr' and logs a string like the above to the console.

Use the problem-solving framework: Understand the problem and break it up into sub-problems!

TEST DATA 1: [17, 21, 23]
TEST DATA 2: [12, 5, -5, 0, 4]
*/

// take in array
const printForecast = function (tempArray) {
  // print each day with counter variable for the day
  let tempString = "";
  for (let i = 0; i < tempArray.length; i++) {
    tempString += `${tempArray[i]}ºC in ${i + 1} days ...`;
  }

  console.log("..." + tempString);
};

const tempArr1 = [17, 21, 23];
const tempArr2 = [12, 5, -5, 0, 4];

printForecast(tempArr1);
printForecast(tempArr2);
