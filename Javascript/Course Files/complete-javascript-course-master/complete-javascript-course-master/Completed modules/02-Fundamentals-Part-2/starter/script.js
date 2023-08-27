// Adding strict mode - Must be first line
// Shows certain errors non strict mode will not
'use strict';

let hasDriversLicense = false;
const passTest = true;

// With strict mode off, it does not catch this mispelled variable name
//if (passTest) hasDriverLicense = true;
if (hasDriversLicense) console.log('I can drive !');


// Functions - Can be called before intitialization
// All functions are values
logger()
function logger() {
    console.log('My name is Daniel');
}



function fruitProcessor(apples, oranges) {
    console.log(apples, oranges);
    const juice = `Juice with ${apples} apples and ${oranges} oranges`;
    return juice
}

console.log(fruitProcessor(5, 0));


function calcAge(birthYear) {
    return 2023 - birthYear;
}
// Function Expression - cannot be called before intitialization
// calcAge2(1991); - ERROR
const calcAge2 = function (birthYear) {
    return 2023 - birthYear;
}
const age = calcAge(1991);
const age2 = calcAge2(1991);

console.log(age, age2);


// Arrow Functions - Faster way to write functions
const calcAge3 = subtract => 2023 - subtract; // implicit return

// For more complex functions

const yearUntilRetire = (birthYear, firstName) => {
    const age = 2023 - birthYear;
    const retirment = 65 - age;
    return `You have ${retirment} years until retirement ${firstName} !`;
}

console.log(yearUntilRetire(1991, 'Daniel'));


// Arrays

const friends = ['Michael', 'Steven', 'Peter'];

console.log(friends);

const years = new Array(1991, 1992, 1993);

console.log(years.length);

friends[2] = 'John';

// arrays can be of mixed values

const arr = new Array(123, 'five', years);

console.log(arr);

// Array Methods

// Add element to end of the array
friends.push("Jay"); // Returns length of new array

// Add element to the beginning of array
friends.unshift("Emma");

// Remove Elements from array
// remove last element of the array
friends.pop() // Returns removed element

// Remove first element
friends.shift()

// Find index of element
friends.indexOf("Steven");

// Returns true if element is in the array
friends.includes("Steven");

console.log(friends, " ", friends.includes("Steven"));


// Objects - no need to make classes to create objects

const jonas = {
    firstName: "Jonas",
    lastName: "Schmedtman",
    birthYear: 1991,
    job: "Teacher",
    friends: ['Michael', 'Steven', 'Peter'],
    hasDriversLicense: true,

    calcAge: function () { // need to use an expression for this to work
        this.age = 2037 - this.birthYear;
        return this.age;
    }
};

// Dot vs Bracket notation

console.log(jonas.lastName);
console.log(jonas['lastName']);

const nameKey = 'Name';

console.log(jonas['first' + nameKey]);
console.log(jonas['last' + nameKey]);

const interested = prompt("Ask for Jonas firstName, lastName, age, job, and friends");
if (jonas[interested])
    console.log(jonas[interested]);
else
    console.log("Info not available for that request");

// Add properties using dot and bracket notation
jonas.location = 'Portugal';
jonas['twitter'] = '@user';

// length stays as int
console.log(jonas.firstName, "has", jonas.friends.length, "and his best friend is", jonas.friends[0]);
// Using a template literal makes all string
console.log(`${jonas.firstName} has ${jonas.friends.length} and his best friend is ${jonas.friends[0]}`);

// Object Methods
jonas.calcAge();
console.log(jonas);


// For loop

for (let i = 0; i <= 10; i++) {
    console.log(i);
}


// While loop

let j = 0;
while (j <= 10) {

    console.log(j);
    j++;
}
