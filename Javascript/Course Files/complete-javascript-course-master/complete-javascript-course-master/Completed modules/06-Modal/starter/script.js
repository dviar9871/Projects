'use strict';

const modal = document.querySelector('.modal');
const overlay = document.querySelector('.overlay');
// Get all elements in HTML class and it is treated as an array
const buttonsOpenModal = document.querySelectorAll('.show-modal');
const buttonCloseModal = document.querySelector('.close-modal');

for (let i = 0; i < buttonsOpenModal.length; i++) {
  // adding event listener to check for clicks to each button in class
  buttonsOpenModal[i].addEventListener('click', function () {
    // removing hidden from class
    modal.classList.remove('hidden');
    overlay.classList.remove('hidden');
  });
}
// closeModal function to be called by event listener
const closeModal = function () {
  modal.classList.add('hidden');
  overlay.classList.add('hidden');
};

// Closes modal when x is clicked
buttonCloseModal.addEventListener('click', closeModal);
// Closes modal when overlay is cliced
overlay.addEventListener('click', closeModal);

// Keyboard events are global events
// parameter is the key being pressed
document.addEventListener('keydown', function (e) {
  // .key selects the key from the parameter list of given parameter
  if (e.key === 'Escape') {
    if (!modal.classList.contains('hidden')) closeModal(); // use actual function here rather than value because we are calling it not using it as a parameter
  }
});
