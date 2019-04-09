document.getElementById('registration-form').onsubmit = createUser;

function createUser() {

  var firstName = document.getElementById('first-name-input').value;
  var lastName = document.getElementById('last-name-input').value;
  var username = document.getElementById('username-input').value;
  var pass1 = document.getElementById('pass-input1').value;
  var pass2 = document.getElementById('pass-input2').value;


  if (pass1 !== pass2) {
    document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
    document.getElementById('message-div').innerHTML = 'Password is not repeated correctly. Try it again.';
  } else {
    var request = {
      'firstName': firstName,
      'lastName': lastName,
      'username': username,
      'password': pass1,
      'enabled' : 1
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
var myInput = document.getElementById("pass-input1");
var letter = document.getElementById("letter");
var capital = document.getElementById("capital");
var number = document.getElementById("number");
var length = document.getElementById("length");

myInput.onfocus = function() {
  document.getElementById("message").style.display = "block";
}
myInput.onblur = function() {
  document.getElementById("message").style.display = "none";
}
myInput.onkeyup = function() {

  var lowerCaseLetters = /[a-z]/g;
  if(myInput.value.match(lowerCaseLetters)) {
    letter.classList.remove("invalid");
    letter.classList.add("valid");
  } else {
    letter.classList.remove("valid");
    letter.classList.add("invalid");
}


  var upperCaseLetters = /[A-Z]/g;
  if(myInput.value.match(upperCaseLetters)) {
    capital.classList.remove("invalid");
    capital.classList.add("valid");
  } else {
    capital.classList.remove("valid");
    capital.classList.add("invalid");
  }


  var numbers = /[0-9]/g;
  if(myInput.value.match(numbers)) {
    number.classList.remove("invalid");
    number.classList.add("valid");
  } else {
    number.classList.remove("valid");
    number.classList.add("invalid");
  }

  if(myInput.value.length >= 8) {
    length.classList.remove("invalid");
    length.classList.add("valid");
  } else {
    length.classList.remove("valid");
    length.classList.add("invalid");
  }
}