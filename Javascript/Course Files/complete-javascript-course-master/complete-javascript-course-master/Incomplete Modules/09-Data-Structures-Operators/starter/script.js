'use strict';

// Data needed for a later exercise
const flights =
  '_Delayed_Departure;fao93766109;txl2133758440;11:25+_Arrival;bru0943384722;fao93766109;11:45+_Delayed_Arrival;hel7439299980;fao93766109;12:05+_Departure;fao93766109;lis2323639855;12:30';

// Data needed for first part of the section
const restaurant = {
  name: 'Classico Italiano',
  location: 'Via Angelo Tavanti 23, Firenze, Italy',
  categories: ['Italian', 'Pizzeria', 'Vegetarian', 'Organic'],
  starterMenu: ['Focaccia', 'Bruschetta', 'Garlic Bread', 'Caprese Salad'],
  mainMenu: ['Pizza', 'Pasta', 'Risotto'],

  order: function (starterIndex, mainIndex) {
    return [this.starterMenu[starterIndex], this.mainMenu[mainIndex]];
  },
  // Destructure object given as a param and default values can be given
  orderDelivery: function (
    starterIndex = 1,
    mainIndex = 0,
    time = '20:00',
    address
  ) {
    console.log(
      `Order Received! ${this.starterMenu[starterIndex]} and ${this.mainMenu[mainIndex]} will be delivered to ${this.address} at ${this.time}`
    );
  },

  orderPasta: function (ing1, ing2, ing3) {
    console.log(
      `Here is your delicious pasta with ${ing1}, ${ing2}, and ${ing3}`
    );
  },

  openingHours: {
    thu: {
      open: 12,
      close: 22,
    },
    fri: {
      open: 11,
      close: 23,
    },
    sat: {
      open: 0, // Open 24 hours
      close: 24,
    },
  },
};
// Passing object directly into method
/*
restaurant.orderDelivery({
  time: '22:30',
  address: 'Via del Sole, 21',
  mainIndex: 2,
  starterIndex: 2,
});
*/
// // Array Destructuring
// const arr = [2, 3, 4];
// const a = arr[0];
// const b = arr[1];
// const c = arr[2];

// const [x, y, z] = arr; // Actual Destructuring
// console.log(x, y, z);

// let [main, , secondary] = restaurant.categories; //takes first and third elements from catagories array
// console.log(main, secondary);

// // Swap values in array without temp variable using destructuring

// [main, secondary] = [secondary, main];
// console.log(main, secondary);

// // Get two return values from one function by destructuring the return of the function
// const [starter, mainCourse] = restaurant.order(2, 0);
// console.log(starter, mainCourse);

// const nested = [2, 4, [5, 6]];
// // Destructure inside an array inside an array
// const [i, , [j, k]] = nested;
// console.log(i, j, k);

console.log('');

// Object Destructuring - very useful when getting data from api's
/*
const { name, openingHours, categories } = restaurant;
console.log(name, openingHours, categories);
// Destructuing objects allows you to make new variable names for destructured objects
const {
  name: restaurantName,
  openingHours: hours,
  categories: tags,
} = restaurant;
console.log(restaurantName, hours, tags);
// Menu does not exist in the object so it will be created when we destructure, starterMenu's default value will not be applied since it already ahs one
const { menu = [], starterMenu: starters = [] } = restaurant;

console.log(menu, starters);

// Mutating variables

let a = 11;
let b = 999;

const obj = { a: 23, b: 7, c: 14 };

({ a, b } = obj);
console.log(a, b);

// nested objects

const {
  fri: { open: o, close: c },
} = openingHours;
console.log(o, c);
*/
console.log('');

// Spread operator - works on all iterables (arrays, strings, maps, sets) - Not Objects

// Bad way
const arr = [7, 8, 9];
const badNewArr = [1, 2, arr[0], arr[1], arr[2]];
console.log(badNewArr);

// Good Way

const newArr = [1, 2, ...arr];
console.log(newArr);

// Can use spread operator to get individual elements from array - can only be used when we would use a comma
console.log(...newArr);

const newMenu = [...restaurant.mainMenu, 'Gnocci'];
console.log(newMenu);

//Shallow copy using spread

const mainMenuCopy = [...restaurant.mainMenu];

// Join 2 arrays

const menu = [...restaurant.mainMenu, ...restaurant.starterMenu];
console.log(menu);

// Spread with strings - isolates each of the elements
const str = 'Jonas';
const letters = [...str, ' ', 'S'];
console.log(letters); // still in arr
//console.log(...str); // letters by themselves

// const ingredients = [
//   prompt("Let's make pasta! Ingredient 1?"),
//   prompt('Ingredient 2?'),
//   prompt('Ingredient 3?'),
// ];
// With spread operator, you don't need to input each element individually
restaurant.orderPasta(...ingredients);
