window.onload = function() {
  // Get reference to the login form
  var loginForm = document.getElementById('loginForm');

  // Attach event listener to the form submit event
  loginForm.addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the form from submitting

    // Get the username and password values
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    // Perform validation and login logic here
    // You can use AJAX requests to communicate with a server-side API for authentication
    // For simplicity, we'll just do a basic check here

    if (username === 'admin' && password === 'password') {
      alert('Login successful!');
      // Redirect to another activity or perform desired action
    } else {
      alert('Invalid username or password. Please try again.');
    }
  });
};