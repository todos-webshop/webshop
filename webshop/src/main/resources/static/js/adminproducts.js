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

    var imgDiv = document.createElement('div');
    imgDiv.innerHTML = '<img alt=' + jsonData[i].address + ' src=img\\products\\' + jsonData[i].address + '.png>';

    var statusDiv = document.createElement('div');
    statusDiv.innerHTML = jsonData[i].productStatus;
    statusDiv.setAttribute('class', 'div_class');
    divRow.appendChild(statusDiv);

    imgDiv.classList.add('div_class');

    divRow.appendChild(imgDiv);

    divMain.appendChild(divRow);
  }
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
