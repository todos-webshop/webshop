fetchProducts();

var addButton = document.getElementById('add-btn');
addButton.onclick = addNewProduct;

function fetchProducts() {
  var url = '/api/products';
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      console.log(jsonData);
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

    var codeDiv = document.createElement('div');
    codeDiv.innerHTML = jsonData[i].code;
    codeDiv.setAttribute('class', 'div_class');
    divRow.appendChild(codeDiv);

    var nameDiv = document.createElement('div');
    nameDiv.innerHTML = jsonData[i].name;
    nameDiv.setAttribute('class', 'div_class');
    divRow.appendChild(nameDiv);

    var addressDiv = document.createElement('div');
    addressDiv.innerHTML = jsonData[i].address;
    addressDiv.setAttribute('class', 'div_class');
    divRow.appendChild(addressDiv);

    var manufacturerDiv = document.createElement('div');
    manufacturerDiv.innerHTML = jsonData[i].manufacturer;
    manufacturerDiv.setAttribute('class', 'div_class');
    divRow.appendChild(manufacturerDiv);

    var priceDiv = document.createElement('div');
    priceDiv.innerHTML = jsonData[i].price + ' Ft';
    priceDiv.setAttribute('class', 'div_class');
    divRow.appendChild(priceDiv);

    var statusDiv = document.createElement('div');
    statusDiv.innerHTML = jsonData[i].productStatus;
    statusDiv.setAttribute('class', 'div_class');
    divRow.appendChild(statusDiv);

    var buttonsDiv = document.createElement('div');
    buttonsDiv.setAttribute('class', 'div_class')
    buttonsDiv.setAttribute('id', 'buttons-div')

    var deleteBtn = document.createElement('input');
    deleteBtn.setAttribute('type', 'button');
    deleteBtn.value = 'Delete';
    deleteBtn.setAttribute('id', jsonData[i].id)
    deleteBtn.addEventListener('click', deleteItem);
    deleteBtn.setAttribute('class', 'delete-button');

    var editBtn = document.createElement('input');
    editBtn.setAttribute('type', 'button');
    editBtn.value = 'Edit';
    editBtn.setAttribute('id', jsonData[i].id)
    editBtn.addEventListener('click', editItem);
    editBtn.setAttribute('class', 'delete-button');

    var saveBtn = document.createElement('input');
    saveBtn.value = 'Save';
    saveBtn.addEventListener('click', saveUpdatedItem);
    saveBtn.setAttribute('id', jsonData[i].id);
    saveBtn.setAttribute('type', 'button');
    saveBtn.setAttribute('class', 'disabled delete-button edit-button');

    buttonsDiv.appendChild(deleteBtn);
    buttonsDiv.appendChild(editBtn);
    buttonsDiv.appendChild(saveBtn);

    divRow.appendChild(buttonsDiv);

    divMain.appendChild(divRow);
  }
}

function deleteItem() {
  var id = this.id;
  console.log(id);

  if (!confirm('Are you sure to delete?')) {
    return;
  }

  fetch('/api/products/' + id, {
    method: 'DELETE'

  })
    .then(function (response) {
      document.getElementById('message-div').innerHTML = 'Deleted!';
      fetchProducts();
    });
}

function editItem(){
    var editBtn = document.querySelector('.edit-button');
    editBtn.setAttribute('class', 'enabled');
    var row = document.getElementById(this.id);
    row.setAttribute('contenteditable', 'true');
}

function saveUpdatedItem(){

      var codeInput = document.getElementById('code').value;
      var nameInput = document.getElementById('name').value;
      var addressInput = document.getElementById('address').value;
      var manufacturerInput = document.getElementById('manufacturer').value;
      var priceInput = document.getElementById('price').value.split(" ");
      priceInput = priceInput[0];
      var statusInput = document.getElementById('status').value;
      var request = {
        'code': codeInput,
        'name': nameInput,
        'address': addressInput,
        'manufacturer': manufacturerInput,
        'price': priceInput,
        'status': statusInput
      };

      fetch('/api/products/' + this.id, {
        method: 'POST',
        body: JSON.stringify(request),
        headers: {
          'Content-type': 'application/json'
        }
      })
        .then(function (response) {
          return response.json();
        })
        .then(function (jsonData){
        console.log('idáig eljutottam!')
        })
        return false;

        var row = document.getElementById(this.id);
        row.setAttribute('contenteditable', 'false')
}


function addNewProduct() {
  var codeInput = document.getElementById('code').value;
  var nameInput = document.getElementById('name').value;
  var manufacturerInput = document.getElementById('manufacturer').value;
  var priceInput = document.getElementById('price').value;
  var request = {
    'code': codeInput,
    'name': nameInput,
    'manufacturer': manufacturerInput,
    'price': priceInput
  };

  fetch('/api/products', {
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
        document.getElementById('code').value = '';
        document.getElementById('name').value = '';
        document.getElementById('manufacturer').value = '';
        document.getElementById('price').value = '';
        fetchProducts();
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
    });
  return false;
}

var newProductButton = document.getElementById('new-product-btn');
newProductButton.onclick = function () {
  showInputFields();
};

function showInputFields() {
  var formInput = document.querySelector('.disabled');
  formInput.setAttribute('class', 'enabled');
}
