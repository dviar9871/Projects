'use strict';

const diceEl = document.querySelector('.dice');
const player0El = document.querySelector('.player--0');
const player1El = document.querySelector('.player--1');
const btnList = document.querySelectorAll('.btn');
const newGameBtn = btnList[0];
const rollBtn = btnList[1];
const holdBtn = btnList[2];
// Which player's turn it is
let playerTurn, playing;

// Object that keeps track of the score and objects
const playerScores = {
  // use hash to select id from class
  score0El: document.querySelector('#score--0'),
  currentScore0El: document.querySelector('#current--0'),
  // This does not need the hash
  score1El: document.getElementById('score--1'),
  currentScore1El: document.querySelector('#current--1'),

  currentScore0: 0,
  totalScore0: 0,
  currentScore1: 0,
  totalScore1: 0,
};

const init = function () {
  // starting conditions
  playerTurn = 0;
  playing = true;
  playerScores['score0El'].textContent = 0;
  playerScores['score1El'].textContent = 0;
  playerScores['currentScore0'] = 0;
  playerScores['currentScore1'] = 0;

  playerScores['totalScore0'] = 0;
  playerScores['totalScore1'] = 0;

  diceEl.classList.add('hidden');
  player0El.classList.remove('player--winner');
  player1El.classList.remove('player--winner');
  player0El.classList.add('player--active');
  player1El.classList.remove('player--active');
};
init();
function switchPlayer() {
  // Update current element
  playerScores[`currentScore${playerTurn}El`].textContent =
    playerScores[`currentScore${playerTurn}`];

  player0El.classList.toggle('player--active');
  player1El.classList.toggle('player--active');
  if (playerTurn === 0) {
    playerTurn++;
  } else {
    playerTurn--;
  }
}
// Generate dice roll
rollBtn.addEventListener('click', function () {
  if (playing) {
    // Get dice roll
    let roll = Math.trunc(Math.random() * 5) + 1;

    // Switch dice picture
    diceEl.classList.remove('hidden');
    diceEl.src = `dice-${roll}.png`;

    // Update current value
    if (roll > 1) {
      playerScores[`currentScore${playerTurn}`] += roll;
      // Update current element
      playerScores[`currentScore${playerTurn}El`].textContent =
        playerScores[`currentScore${playerTurn}`];
    } else {
      playerScores[`currentScore${playerTurn}`] = 0;
      switchPlayer();
    }
  }
});

holdBtn.addEventListener('click', function () {
  // Add current to the total
  playerScores[`totalScore${playerTurn}`] +=
    playerScores[`currentScore${playerTurn}`];
  // update the total score element
  playerScores[`score${playerTurn}El`].textContent =
    playerScores[`totalScore${playerTurn}`];
  // Clear out current variable and element
  playerScores[`currentScore${playerTurn}`] = 0;
  playerScores[`currentScore${playerTurn}El`].textContent = 0;

  if (playerScores[`totalScore${playerTurn}`] >= 100) {
    document
      .querySelector(`.player--${playerTurn}`)
      .classList.remove('player--active');
    document
      .querySelector(`.player--${playerTurn}`)
      .classList.add('player--winner');
  } else {
    switchPlayer();
  }
});

newGameBtn.addEventListener('click', init);
