fetchUsers();


function fetchUsers() {
  var url = "/api/users";
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showDivs(jsonData);
    });
}

function showDivs(jsonData) {
  divMain = document.getElementById('main_div');
  divMain.innerHTML = '';
  for (var i = 0; i < jsonData.length; i++) {
    var divRow = document.createElement('div');
    divRow.setAttribute('contenteditable', 'false');
    divRow.setAttribute('id', jsonData[i].id);
    divRow.setAttribute('class', 'admin-product-div');

    var firstNameDiv = document.createElement('div');
    firstNameDiv.innerHTML = jsonData[i].firstName;
    firstNameDiv.setAttribute('class', 'div_class_admin');
    divRow.appendChild(firstNameDiv);

    var lastNameDiv = document.createElement('div');
    lastNameDiv.innerHTML = jsonData[i].lastName;
    lastNameDiv.setAttribute('class', 'div_class_admin');
    divRow.appendChild(lastNameDiv);

    var usernameDiv = document.createElement('div');
    usernameDiv.innerHTML = jsonData[i].username;
    usernameDiv.setAttribute('class', 'div_class_admin');
    divRow.appendChild(usernameDiv);

    var passwordDiv = document.createElement('div');
    passwordDiv.innerHTML ="";
    passwordDiv.setAttribute('class', 'div_class_admin','placeholder');
    divRow.appendChild(passwordDiv);

 //  var enabledDiv = document.createElement('div');
 //   enabledDiv.innerHTML = jsonData[i].enabled;
 //   enabledDiv.setAttribute('class', 'div_class_admin');
 //    divRow.appendChild(enabledDiv);

    var userRoleDiv = document.createElement('div');
    userRoleDiv.innerHTML = jsonData[i].userRole;
    userRoleDiv.setAttribute('class', 'div_class_admin');
    divRow.appendChild(userRoleDiv);

    var buttonsDiv = document.createElement('div');
    buttonsDiv.setAttribute('class', 'div_class_admin admin-product-div')
    buttonsDiv.setAttribute('id', 'buttons-div')

    var deleteBtn = document.createElement('img');
    deleteBtn.setAttribute('src','/img/delete-button.png')
    deleteBtn.setAttribute('class', 'button')
    deleteBtn.setAttribute('id', jsonData[i].id)
    deleteBtn.addEventListener('click', deleteUser);
    deleteBtn.setAttribute('class', 'button');

    var editBtn = document.createElement('img');
    editBtn.setAttribute('src', '/img/edit-button.png');
    editBtn.setAttribute('class', 'button')
    editBtn.setAttribute('id', jsonData[i].id)
    editBtn.addEventListener('click', editUser);
    editBtn.setAttribute('class', 'button');

    var saveBtn = document.createElement('img');
    saveBtn.addEventListener('click', saveUpdatedUser);
    saveBtn.setAttribute('id', jsonData[i].id);
    saveBtn.setAttribute('src', '/img/save-button.png');
    var attribute = 'button-disabled button save-button' + jsonData[i].id;
    saveBtn.setAttribute('class', attribute);

    divRow.appendChild(deleteBtn);
    divRow.appendChild(editBtn);
    divRow.appendChild(saveBtn);

    divMain.appendChild(divRow);
  }
}

function deleteUser() {
  var id = this.id;

  if (!confirm('Are you sure to delete?')) {
    return;
  }

  fetch('/api/users/' + id, {
    method: 'DELETE'
  })
    .then(function (response) {
        return response.json();
        })
    .then(function(jsonData){
        if (jsonData.response == 'SUCCESS'){
            fetchUsers();
            document.getElementById('message-div').innerHTML = jsonData.message;
            document.getElementById('message-div').setAttribute('class', 'alert alert-success');
        }else{
            document.getElementById('message-div').innerHTML = "This user no longer exists.";
            document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
        }
    });
    return false;
}

    function editUser(){
        var attribute = '.save-button' + this.id;
        var saveBtn = document.querySelector(attribute);
        var newClassName = 'save-button' + this.id;
        var newAttribute = 'button-enabled button ' + newClassName;

        saveBtn.setAttribute('class', newAttribute);

        var row = document.getElementById(this.id);
        var c = row.childNodes;
        for (var i = 0; i < c.length; i++){
            c[i].setAttribute('contenteditable', 'true');
        }

    }

function saveUpdatedUser(){
      var row = document.getElementById(this.id);
      var childenOfRow = row.children;
      var id = this.id;

      var firstName = childenOfRow[0].innerHTML;
      var lastName = childenOfRow[1].innerHTML;
      var username = childenOfRow[2].innerHTML;
      var password = childenOfRow[3].innerHTML;
   //   var enabled = childenOfRow[4].innerHTML;
      var userRole = childenOfRow[4].innerHTML;



      var request = {
        'firstName': firstName,
        'lastName': lastName,
        'username': username,
        'password': password,
    //    'enabled': enabled,
        'userRole': userRole
      };

      fetch('/api/users/' + id, {
            method: 'POST',
            body: JSON.stringify(request),
            headers: {
                'Content-type': 'application/json'
            }
        })
        .then(function (response) {
            return response.json();
            })
        .then(function(jsonData){
            console.log(jsonData)
            if (jsonData.response == 'SUCCESS'){
            fetchUsers();
            document.getElementById('message-div').innerHTML = 'User updated!';
            document.getElementById('message-div').setAttribute('class', 'alert alert-success')
            row.setAttribute('contenteditable', 'false');
            var classAttribute = '.save-button' + id;
            var newClassName = 'save-button' + id;
            var newAttribute = 'button-disabled button ' + newClassName;
            document.querySelector(classAttribute).setAttribute('class', newAttribute);
            } else{
            fetchUsers();
            document.getElementById('message-div').innerHTML = jsonData.message;
            document.getElementById('message-div').setAttribute('class', 'alert alert-danger')
            row.setAttribute('contenteditable', 'false');
            var classAttribute = '.save-button' + id;
            var newClassName = 'save-button' + id;
            var newAttribute = 'button-disabled button ' + newClassName;
            document.querySelector(classAttribute).setAttribute('class', newAttribute);
                }
            })
        return false;
}
function showInputFields() {
  var formInput = document.querySelector('#form-input');
  if (formInput.getAttribute('class') == 'disabled'){
  formInput.setAttribute('class', 'enabled');
  } else {
  formInput.setAttribute('class', 'disabled')}
}