// Template Literals

const firstName = "Daniel";
const job = "Student";
const birthYear = 2001;
const year = 2023

const daniel = "I'm " + firstName + ', a ' + (year - birthYear) + " years old " + job

console.log(daniel);


const danielNew = `I'm ${firstName}, a ${year - birthYear} years old ${job}` // use upper left quote mark/ backticks
console.log(danielNew);
// You can use back ticks for any string or multiline string
console.log(`Line 1
Line 2`);