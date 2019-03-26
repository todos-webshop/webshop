document.getElementById('registration-form').onsubmit = createUser;

function createUser() {

  var firstName = document.getElementById('first-name-input').value;
  var lastName = document.getElementById('last-name-input').value;
  var username = document.getElementById('username-input').value;
  var pass1 = document.getElementById('pass-input1').value;
  var pass2 = document.getElementById('pass-input2').value;

  console.log(firstName);

  if (pass1 !== pass2) {
    document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
    document.getElementById('message-div').innerHTML = 'Password is not repeated correctly. Try it again.';
  } else {
    var request = {
      'firstName': firstName,
      'lastName': lastName,
      'username': username,
      'password': pass1
    };

    fetch('/users', {
      method: 'POST',
      body: JSON.stringify(request),
      headers: {
        'Content-type': 'application/json'
      }
    })
      .then(function (response) {
        return response.json();
      }).then(function (jsonData) {
        if (jsonData.response == 'SUCCESS') {
          document.getElementById('message-div').setAttribute('class', 'alert alert-success');
        } else {
          document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
        }
        document.getElementById('message-div').innerHTML = jsonData.message;
      });
  }
  return false;
}
var strength = {
  0: "Worst",
  1: "Bad",
  2: "Weak",
  3: "Good",
  4: "Strong"
}
var password = document.getElementById("pass-input1");
var meter = document.getElementById('password-strength-meter');
var text = document.getElementById('password-strength-text');

password.addEventListener('input', function() {
  var val = password.value;
  var result = zxcvbn(val);

  meter.value = result.score;


  if (val !== "") {
    text.innerHTML = "Strength: " + strength[result.score];
  } else {
    text.innerHTML = "";
  }
});