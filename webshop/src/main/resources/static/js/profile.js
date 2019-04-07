
// document.forms["user-information-form"].onsubmit = updateUser;
// document.getElementById('registration-form').onsubmit = updateUser;
document.getElementById('submit-button').addEventListener('click', function () {updateUser();});
getUser();
var name = '';
var id = 0;
function getUser() {
  fetch('/currentuserdata')
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      loadData(jsonData);
      name = jsonData.username;
      id = jsonData.id;
    });
}
function loadData(jsonData) {
  var firstNameInput = document.getElementById('first-name-input');
  firstNameInput.value = jsonData.firstName;
  var lastNameInput = document.getElementById('last-name-input');
  lastNameInput.value = jsonData.lastName;
  var userNameInput = document.getElementById('username-input');
  userNameInput.value = jsonData.username;
  var submitInput = document.getElementById('submit-button');
  submitInput['raw-data'] = jsonData;
}

function updateUser() {
  var id = document.getElementById('submit-button')['raw-data'].id;

  var firstName = document.getElementById('first-name-input').value;
  var lastName = document.getElementById('last-name-input').value;
  var userName = document.getElementById('username-input').value;

  var password = document.getElementById('pass-input1').value;
  var pass2 = document.getElementById('pass-input2').value;
  // if (password !== pass2){
  //        document.getElementById('message-div').setAttribute('class', 'alert-danger');
  //        document.getElementById('message-div').innerHTML = 'Passwords do not match!';
  //          } else {
  var request;
  if (password == '********' || password == '') {
    request = { 'id': id,
      'firstName': firstName,
      'lastName': lastName,
      'username': userName
    };
} else if (password.match(/^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])([a-zA-Z0-9]{8,})$/)) {
 request = {
    'id': id,
    'firstName': firstName,
    'lastName': lastName,
    'username': userName,
    'password': password
  }
;}

            fetch('/api/user/' + id,{
    method: 'POST',
    body: JSON.stringify(request),
    headers: {
      'Content-type': 'application/json'
    }
  }).then(function (response) {
    return response.json();
  }).then(function (jsonData) {
    if (jsonData.response == 'SUCCESS') {
      // StartTimers();
      var timeLeft = 5;
      var elem = document.getElementById('message-div');

      var timerId = setInterval(countdown, 1000);
      function countdown() {
        if (timeLeft == 0) {
          clearTimeout(timerId);
          Timeout();
        } else {
          var elem = document.getElementById('message-div');
          elem.innerHTML = 'You will be logged out in:' + ' ' + timeLeft + ' ' + ' seconds.';
          timeLeft--;
        }
      }


      document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      // document.getElementById('message-div').innerHTML =  elem.innerHTML = timeLeft + ' seconds remaining';
    } else {
      document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      elem.innerHTML = jsonData.message;
    }
   // document.getElementById('message-div').innerHTML = 'User password invalid.';
  });

  // loadData(request);
  //  getUser();
  return false;
}
// function StartTimers() {
//   window.setTimeout("Timeout()", 10000);
// }
function Timeout() {
  window.location.href = '/logout';
}


var myInput = document.getElementById('pass-input1');
var letter = document.getElementById('letter');
var capital = document.getElementById('capital');
var number = document.getElementById('number');
var length = document.getElementById('length');

myInput.onfocus = function () {
  document.getElementById('message').style.display = 'block';
};
myInput.onblur = function () {
  document.getElementById('message').style.display = 'none';
};
myInput.onkeyup = function () {
  var lowerCaseLetters = /[a-z]/g;
  if (myInput.value.match(lowerCaseLetters)) {
    letter.classList.remove('invalid');
    letter.classList.add('valid');
  } else {
    letter.classList.remove('valid');
    letter.classList.add('invalid');
  }


  var upperCaseLetters = /[A-Z]/g;
  if (myInput.value.match(upperCaseLetters)) {
    capital.classList.remove('invalid');
    capital.classList.add('valid');
  } else {
    capital.classList.remove('valid');
    capital.classList.add('invalid');
  }


  var numbers = /[0-9]/g;
  if (myInput.value.match(numbers)) {
    number.classList.remove('invalid');
    number.classList.add('valid');
  } else {
    number.classList.remove('valid');
    number.classList.add('invalid');
  }

  if (myInput.value.length >= 8) {
    length.classList.remove('invalid');
    length.classList.add('valid');
  } else {
    length.classList.remove('valid');
    length.classList.add('invalid');
  }
};

var strength = {
  0: 'Worst',
  1: 'Bad',
  2: 'Weak',
  3: 'Good',
  4: 'Strong'
};
var password = document.getElementById('pass-input1');
var meter = document.getElementById('password-strength-meter');
var text = document.getElementById('password-strength-text');

password.addEventListener('input', function () {
  var val = password.value;
  var result = zxcvbn(val);

  // meter.value = result.score;


  if (val !== '') {
    text.innerHTML = 'Strength: ' + strength[result.score];
  } else {
    text.innerHTML = '';
  }
});
