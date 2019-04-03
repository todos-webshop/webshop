window.onload = function() {
  fetchUsers();
};

function fetchUsers() {
  fetch("/api/users")
    .then(function(response) {
      return response.json();
    })
    .then(function(jsonData) {
      showTable(jsonData);
    });
}

function showTable(jsonData) {
  var table = document.querySelector("#users-table");

  table.innerHTML = "";
  for (var i = 0; i < jsonData.length; i++) {
    var tr = document.createElement("tr");
    tr["raw-data"] = jsonData[i];

    var idTd = document.createElement("td");
    idTd.innerHTML = jsonData[i].id;
    var idTdId = "idTd" + i;
    idTd.setAttribute("id", idTdId);
    tr.appendChild(idTd);

    var firstNameTd = document.createElement("td");
    firstNameTd.innerHTML = jsonData[i].firstName;
    var firstNameTdId = "firstNameTd" + i;
    firstNameTd.setAttribute("id", firstNameTdId);
    tr.appendChild(firstNameTd);

    var lastNameTd = document.createElement("td");
    lastNameTd.innerHTML = jsonData[i].lastName;
    var lastNameTdId = "lastNameTd" + i;
    lastNameTd.setAttribute("id", lastNameTdId);
    tr.appendChild(lastNameTd);

    var usernameTd = document.createElement("td");
    usernameTd.innerHTML = jsonData[i].username;
    var usernameTdId = "usernameTd" + i;
    usernameTd.setAttribute("id", usernameTdId);
    tr.appendChild(usernameTd);

    var passwordTd = document.createElement("td");
   passwordTd.innerHTML = "********";
    var passwordTdId = "passwordTd" + i;
    passwordTd.setAttribute("id", passwordTdId);
  //  passwordTd.setAttribute("style", );

    tr.appendChild(passwordTd);

var roleTd = document.createElement("td");
    roleTd.innerHTML = jsonData[i].userRole;
    var roleTdId = "roleTd" + i;
    roleTd.setAttribute("id", roleTdId);
    tr.appendChild(roleTd);



    var editButtonTd = document.createElement("td");
    var editButton = document.createElement("button");
    var editButtonId = 'editbutton' + i;
    editButton.setAttribute('id', editButtonId);
    editButton.setAttribute('class', 'btn');
    editButton.setAttribute('onclick', `editTds(${i})`);
    editButton.innerHTML = `<i class="fas fa-edit"></i>Edit`;
    tr.appendChild(editButtonTd);
    editButtonTd.appendChild(editButton);

     var saveButton = document.createElement("button");
                var saveButtonId = 'savebutton' + i;
                saveButton.innerHTML = `<i class="fa fa-save"></i>Save `;
                saveButton.setAttribute('id', saveButtonId);
                saveButton.setAttribute('class', 'btn');
                saveButton.setAttribute('onclick', `saveTds(${i})`);
                saveButton.style.display = 'none';
                editButtonTd.appendChild(saveButton);


    var deleteButtonTd = document.createElement("td");
            var deleteButton = document.createElement("button");
            var deleteButtonId = 'deletebutton' + i;
            deleteButton.setAttribute('id', deleteButtonId);
            deleteButton.setAttribute('class', 'btn');
            deleteButton.setAttribute('onclick', `deleteUser(${i})`);
            deleteButton['raw-data'] = jsonData[i];

            deleteButton.innerHTML = `<i class="fas fa-trash-alt"></i>Delete`;

            deleteButtonTd.appendChild(deleteButton);
            tr.appendChild(deleteButtonTd);

            table.appendChild(tr);

  }
  }
  function deleteUser(num){

          var id = document.getElementById(`deletebutton${num}`)['raw-data'].id;
          var name = document.getElementById(`deletebutton${num}`)['raw-data'].name;

          if (!confirm("Are you sure to delete user?")) {
              return;
          }

           fetch("/api/users/" + id, {
                          method: "DELETE",
                      })
                      .then(function (response) {
                          document.getElementById("message-div").setAttribute("class", "alert alert-success");
                          document.querySelector("#message-div").innerHTML = name + " User Deleted!"
                          fetchUsers();
                          });
}
function editTds(num){

        var firstName = document.getElementById(`firstNameTd${num}`);
         var lastName = document.getElementById(`lastNameTd${num}`);
         var username = document.getElementById(`usernameTd${num}`);
        var password = document.getElementById(`passwordTd${num}`);



        var firstNameData = firstName.innerHTML;
        var lastNameData = lastName.innerHTML;
        var usernameData = username.innerHTML;
      //  var passwordData = password.innerHTML;

        firstName.innerHTML = `<input id="firstNameInput${num}" type='text' minLength='1' maxLength='255' class='input-box'  value = '${firstNameData}' required>`
        lastName.innerHTML = `<input id="lastNameInput${num}" type='text' minLength='1' maxLength='255' class='input-box'  value = '${lastNameData}' required>`
        username.innerHTML = `<input id="usernameInput${num}" type='text' minLength='1' maxLength='255' class='input-box'  value = '${usernameData}' required>`
        password.innerHTML = `<input id="passwordInput${num}" type="password"   value='********' placeholder="Password" required>`

        var edit = document.getElementById(`editbutton${num}`);
        edit.style.display = 'none';
        var save = document.getElementById(`savebutton${num}`);
        save.style.display = 'inline';
    }

    function saveTds(num){

            var id = document.getElementById(`savebutton${num}`).parentElement.parentElement['raw-data'].id;
            var firstName = document.getElementById(`firstNameInput${num}`).value;
            var lastName = document.getElementById(`lastNameInput${num}`).value;
            var username = document.getElementById(`usernameInput${num}`).value;
            var password = document.getElementById(`passwordInput${num}`).value;
            var userRole = document.getElementById(`savebutton${num}`).parentElement.parentElement['raw-data'].userRole;

 var request;
 if(password == null || password == '********' || password == '') {
             request =
                    {
                            "id": id,
                            "firstName": firstName,
                            "lastName": lastName,
                            "username": username,
                         //    'enabled': 1,
                            'userRole': userRole

                        };
                        }
                       if (check(password)) {
                        request = {
                         "id": id,
                    "firstName": firstName,
                  "lastName": lastName,
                   "username": username,
                   "password": password,
          //       'enabled': 1,
                    'userRole': userRole
                        };}

            fetch("/api/users/" + id, {

                    method: "POST",
                    body: JSON.stringify(request),
                    headers: {
                        "Content-type": "application/json"
                    }
                })
                .then(function (response) {
                    return response.json();
                }).
            then(function (jsonData) {

                if (jsonData.response == 'SUCCESS') {

                   document.getElementById(`firstNameTd${num}`).innerHTML = firstName;
                   document.getElementById(`lastNameTd${num}`).innerHTML = lastName;
                   document.getElementById(`usernameInput${num}`).innerHTML = username;
                  document.getElementById(`passwordTd${num}`).innerHTML = password;


                    fetchUsers();
                                    document.getElementById("message-div").setAttribute("class", "alert alert-success");
                                   document.getElementById("message-div").innerHTML = jsonData.message;
                             }

                        });
                        return false;
                    }
window.onscroll = function() {scrollFunction()};

function scrollFunction() {
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    document.getElementById("myBtn").style.display = "block";
  } else {
    document.getElementById("myBtn").style.display = "none";
  }
}


function topFunction() {
  document.documentElement.scrollTop = 0;
}
function check(pass) {
   var msgdiv = document.getElementById("message-div");
    msgdiv.setAttribute("class", "alert alert-danger");
    if (pass.length < 8) {
        document.getElementById("message-div").innerHTML = "Password must be at least 8 characters long!!";
        return false;
    } else if (pass.search(/\d/) == -1) {
        document.getElementById("message-div").innerHTML = "Password must have a number in it!";
        return false;
    } else if (pass.search(/[a-zA-Z]/) == -1) {
        document.getElementById("message-div").innerHTML = "Password requires a letter!";
        return false;
    } else if (pass.search(/[A-Z]/) == -1) {
        document.getElementById("message-div").innerHTML = "Password must have an upper case letter!";
        return false;
    }return true;}