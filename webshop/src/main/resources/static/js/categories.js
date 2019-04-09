getCategories();

function getCategories() {
  fetch('/api/categories')
    .then(response => response.json())
    .then(json => showCategories(json));
}

function showCategories(jsonData) {
  var ul = document.getElementById('sortable');
  ul.innerHTML = '';
  for (const key in jsonData) {
    if (jsonData.hasOwnProperty(key)) {
      const e = jsonData[key];
      var id = jsonData[key].id;
      var liId = 'li-' + id;
      ul.innerHTML += `
      <li id=${liId}>
      <img src="img/dragNdrop.png" alt="dragNdrop" class="handle" height="25">
      <label>${e.sequence}</label>
      <label contenteditable="true">${e.categoryName}</label>
      <img id=${id} src="/img/delete-button.png" onclick="deleteCategory()" class='category-delete'>
      </li>
      `;
    }
  }
}

$('.sortable').sortable();
$('.sortable').sortable({
  handle: '.handle'
});

function save() {
  var request = [];
  var lis = document.getElementById('sortable').getElementsByTagName('li');
  for (var i = 0; i < lis.length; i++) {
    const e = lis[i];
    request.push({
      'id': e.id.split("-")[1],
      'categoryName': e.getElementsByTagName('label')[1].innerHTML.trim(),
      'sequence': i + 1
    });
  }
  fetch('api/categories/update', {
    method: 'POST',
    body: JSON.stringify(request),
    headers: {
      'Content-type': 'application/json'
    }
  })
    .then(response => response.json())
    .then(r => {
      getCategories();
    });
}

function deleteCategory() {
  var id = this.event.target.id

  if (!confirm('Are you sure to delete?')) {
    return;
  }

  fetch('/api/category/' + id, {
    method: 'DELETE'
  })
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      if (jsonData.response == 'SUCCESS') {
        getCategories();
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
    });
  return false;
}

function addNewProduct() {
  var nameInput = document.getElementById('name').value;
  var sequenceInput = document.getElementById('sequence').value;

  var request = {
     "categoryName": nameInput,
     "sequence" : sequenceInput
  };

  fetch('/api/categories', {
    method: 'POST',
    body: JSON.stringify(request),
    headers: {
      'Content-type': 'application/json'
    }
  })
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      if (jsonData.response == 'SUCCESS') {
        document.getElementById('name').value = '';
        document.getElementById('sequence').value = '';
        getCategories();
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
    });
  return false;
}

var addButton = document.getElementById('add-btn');
addButton.onclick = addNewProduct;

var newProductButton = document.getElementById('new-product-btn');
newProductButton.onclick = function () {
  showInputFields();
};

function showInputFields() {
  var formInput = document.querySelector('#form-input');
  if (formInput.getAttribute('class') == 'disabled'){
  formInput.setAttribute('class', 'enabled');
  } else {
  formInput.setAttribute('class', 'disabled')
  }
}
