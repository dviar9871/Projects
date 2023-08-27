'use strict';

// Selecting an element in javascript

// console.log(document.querySelector('.message')); // Selecting .message class

// console.log(document.querySelector('.message').textContent); // gives text of element selected

// /*
//     What is DOM (Document Object Model)? It is the structured representation of HTML
//     documents. Allows for Javascript to access HTML elements and style to manipulate them
// */

// document.querySelector('.message').textContent = 'Correct Number !';
// // Get value from input field
// console.log(document.querySelector('.guess').value);

// Event listener for button click

// Check value of guess
let correctNumber = Math.floor(Math.random() * 20) + 1;
let score = 20;
let highscore = 0;
let winFlag = false;
console.log(correctNumber);

document.querySelector('.btn.check').addEventListener('click', function () {
  const guess = document.querySelector('.guess').value;
  // Check to see if guess is valid or the game has been won
  if (guess !== '' && !winFlag) {
    // too high
    if (score <= 1) {
      document.querySelector('.message').textContent = 'You Lose :(';
      score--;
      document.querySelector('.score').textContent = score;
      winFlag = true;
      // too low
    } else if (guess < correctNumber) {
      score--;
      document.querySelector('.message').textContent = 'Too Low !';
      document.querySelector('.score').textContent = score;
      // Winning number
    } else if (guess > correctNumber) {
      score--;
      document.querySelector('.message').textContent = 'Too High !';
      document.querySelector('.score').textContent = score;
    } else {
      document.querySelector('body').style.backgroundColor = '#60b347';

      document.querySelector('.number').style.width = '30rem';
      document.querySelector('.number').textContent = correctNumber;

      document.querySelector('.message').textContent = 'You Win !';
      if (score > highscore) {
        highscore = score;
        document.querySelector('.highscore').textContent = highscore;
      }

      winFlag = true;
    }
  } else {
    if (winFlag) {
    } else
      document.querySelector('.message').textContent = 'Please Enter a Number';
  }
});

// Handle reset of game
document.querySelector('.btn.again').addEventListener('click', function () {
  correctNumber = Math.floor(Math.random() * 20) + 1;
  score = 20;
  document.querySelector('.message').textContent = 'Please Enter a Number';
  document.querySelector('.score').textContent = score;
  document.querySelector('.guess').value = '';
  document.querySelector('body').style.backgroundColor = '#222';
  document.querySelector('.number').style.width = '15rem';
  document.querySelector('.number').textContent = '?';

  winFlag = false;
  console.log(correctNumber);
});
