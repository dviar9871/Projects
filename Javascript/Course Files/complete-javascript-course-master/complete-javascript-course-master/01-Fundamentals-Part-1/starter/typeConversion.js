// Type Conversion
const inputYear = '1991'

console.log(typeof Number(inputYear));

console.log(typeof String(50));


// Type Coersion
console.log(`I am ` + 23 + "years old");
console.log('23' - 10 + '20'); // will do normal math unless the + operator is used



// 5 Falsy Values: 0, '', undefined, null, NaN 
//(Will be converted to false when converted to boolean)
// Anything else will be true

console.log(Boolean(0));
console.log(Boolean(10));


// == vs ===
// Strict equality === - no type coersion exactly the same
// Loose equality == - Type coersion is done (Don't use often manually convert)
const age = 18;

if (age === 18) console.log('You are an adult');

console.log('18' == 18);


let favNum = Number(prompt("What's your favorite number?")); // prompt gives strings
console.log(favNum);

// There is also strict and loose non equals !== vs !=



// Logical Operators
// && - And  || - Or ! - Not

// Switch Statement

switch (age) {
    case 10:
        console.log("Too Young");
        break;
    case 18:
        console.log("Adult");
    default:

}


// Ternary Operator

const bool = true;
bool ? console.log("Ternary is true") : console.log("Ternary is false");

















