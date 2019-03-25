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

    var codeDiv = document.createElement('div');
    codeDiv.innerHTML = jsonData[i].code;
    codeDiv.setAttribute('class', 'div_class_admin');
    divRow.appendChild(codeDiv);

    var nameDiv = document.createElement('div');
    nameDiv.innerHTML = jsonData[i].name;
    nameDiv.setAttribute('class', 'div_class_admin');
    divRow.appendChild(nameDiv);

    var addressDiv = document.createElement('div');
    addressDiv.innerHTML = jsonData[i].address;
    addressDiv.setAttribute('class', 'div_class_admin');
    divRow.appendChild(addressDiv);

    var manufacturerDiv = document.createElement('div');
    manufacturerDiv.innerHTML = jsonData[i].manufacturer;
    manufacturerDiv.setAttribute('class', 'div_class_admin');
    divRow.appendChild(manufacturerDiv);

    var priceDiv = document.createElement('div');
    priceDiv.innerHTML = jsonData[i].price + ' Ft';
    priceDiv.setAttribute('class', 'div_class_admin');
    divRow.appendChild(priceDiv);

    var statusDiv = document.createElement('div');
    statusDiv.innerHTML = jsonData[i].productStatus;
    statusDiv.setAttribute('class', 'div_class_admin status-div');
    divRow.appendChild(statusDiv);

    var buttonsDiv = document.createElement('div');
    buttonsDiv.setAttribute('class', 'div_class_admin admin-product-div')
    buttonsDiv.setAttribute('id', 'buttons-div')

    var deleteBtn = document.createElement('img');
    deleteBtn.setAttribute('src','/img/delete-button.png')
    deleteBtn.setAttribute('id', jsonData[i].id)
    deleteBtn.addEventListener('click', deleteItem);
    deleteBtn.setAttribute('class', 'button');

    var editBtn = document.createElement('img');
    editBtn.setAttribute('src', '/img/edit-button.png');
    editBtn.setAttribute('id', jsonData[i].id)
    editBtn.addEventListener('click', editItem);
    editBtn.setAttribute('class', 'button');

    var saveBtn = document.createElement('img');
    saveBtn.addEventListener('click', saveUpdatedItem);
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

function deleteItem() {
  var id = this.id;

  if (!confirm('Are you sure to delete?')) {
    return;
  }

  fetch('/api/product/' + id, {
    method: 'DELETE'
  })
    .then(function (response) {
        return response.json();
        })
    .then(function(jsonData){
        if (jsonData.response == 'SUCCESS'){
            fetchProducts();
            document.getElementById('message-div').innerHTML = jsonData.message;
            document.getElementById('message-div').setAttribute('class', 'alert alert-success');
        }else{
            document.getElementById('message-div').innerHTML = 'This product is already deleted.';
            document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
        }
    });
    return false;
}

function editItem(){
    var attribute = '.save-button' + this.id;
    var saveBtn = document.querySelector(attribute);
    var newClassName = 'save-button' + this.id;
    var newAttribute = 'button-enabled button ' + newClassName;

    saveBtn.setAttribute('class', newAttribute);

    var row = document.getElementById(this.id);
    var c = row.childNodes;
    for (var i = 0; i < c.length; i++){
        if (i == 5){
            c[i].innerHTML = `<select class="select-element">
                <option value="ACTIVE">ACTIVE</option>
                <option value="DELETED">DELETED</option>
            </select>`
        }
        else {
        c[i].setAttribute('contenteditable', 'true');
        }
        }
}

function saveUpdatedItem(){
      var row = document.getElementById(this.id);
      var childenOfRow = row.children;
      var id = this.id;

      var code = childenOfRow[0].innerHTML;
      var name = childenOfRow[1].innerHTML;
      var address = childenOfRow[2].innerHTML;
      var manufacturer = childenOfRow[3].innerHTML;;
      var price = childenOfRow[4].innerHTML.split(" ");

      var selectElement = document.querySelector('.select-element');
      var value = selectElement.options[selectElement.selectedIndex].value;

      price = price[0];

      var request = {
        'code': code,
        'name': name,
        'address': address,
        'manufacturer': manufacturer,
        'price': price,
        'productStatus': value
      };
      fetch('/api/product/' + id, {
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
            if (jsonData.response == 'SUCCESS'){
            fetchProducts();
            document.getElementById('message-div').innerHTML = 'Updated!';
            document.getElementById('message-div').setAttribute('class', 'alert alert-success')

                        var row = document.getElementById(this.id);
                            var c = row.childNodes;
                            for (var i = 0; i < c.length; i++){
                                if (i == 5){
                                    c[i].innerHTML = `<div>$value</div>`
                                }
                                else {
                                c[i].setAttribute('contenteditable', 'false');
                                }
                                }

            var classAttribute = '.save-button' + id;
            var newClassName = 'save-button' + id;
            var newAttribute = 'button-disabled button ' + newClassName;
            document.querySelector(classAttribute).setAttribute('class', newAttribute);
            } else{
            fetchProducts();
            document.getElementById('message-div').innerHTML = jsonData.message;
            document.getElementById('message-div').setAttribute('class', 'alert alert-danger')

            var row = document.getElementById(this.id);
                var c = row.childNodes;
                for (var i = 0; i < c.length; i++){
                    if (i == 5){
                        c[i].innerHTML = `<div>$value</div>`
                    }
                    else {
                    c[i].setAttribute('contenteditable', 'false');
                    }
                    }
            //row.setAttribute('contenteditable', 'false');
            var classAttribute = '.save-button' + id;
            var newClassName = 'save-button' + id;
            var newAttribute = 'button-disabled button ' + newClassName;
            document.querySelector(classAttribute).setAttribute('class', newAttribute);
                }
            })
        return false;
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
    'price': priceInput,
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
  var formInput = document.querySelector('#form-input');
  if (formInput.getAttribute('class') == 'disabled'){
  formInput.setAttribute('class', 'enabled');
  } else {
  formInput.setAttribute('class', 'disabled')}
}
