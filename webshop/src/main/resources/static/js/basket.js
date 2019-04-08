window.onload = function () {
  fetchBasket();
};


document.querySelector('#clear-basket').addEventListener('click', function () {
  clearBasket();
});


function fetchBasket() {
  var url = '/basket';
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showBasket(jsonData);
    });
}


function showBasket(jsonData) {
  var divBasket = document.getElementById('basket-div');
  var sumsDiv = document.querySelector('.sums-div');
  divBasket.innerHTML = '';
  sumsDiv.innerHTML = '';
  divBasket.setAttribute('class', 'container');

  var sumPieces = jsonData.sumPieces;
  var sumPrice = jsonData.sumPrice;


  var sumPiecesDiv = document.createElement('div');
  var sumpriceDiv = document.createElement('div');
  sumPiecesDiv.innerText = `Items in basket: ${sumPieces}`;
  sumpriceDiv.innerText = `Total value: ${sumPrice} Ft`;


  sumPiecesDiv.setAttribute('class', 'div_class');
  sumpriceDiv.setAttribute('class', 'div_class');

  sumsDiv.appendChild(sumPiecesDiv);
  sumsDiv.appendChild(sumpriceDiv);

  var table = document.createElement('table');
  table.setAttribute('class', 'table table-striped');
  divBasket.appendChild(table);

  var trHeading = document.createElement('tr');
  trHeading.setAttribute('class', 'basket-head-row')
  table.appendChild(trHeading);

  var codeTh = document.createElement('th');
  codeTh.innerHTML = 'Product code';
  trHeading.appendChild(codeTh);

  var nameTh = document.createElement('th');
  nameTh.innerHTML = 'Name';
  trHeading.appendChild(nameTh);

  var manufacturerTh = document.createElement('th');
  manufacturerTh.innerHTML = 'Manufacturer';
  trHeading.appendChild(manufacturerTh);

  var priceTh = document.createElement('th');
  priceTh.innerHTML = 'Unit price';
  trHeading.appendChild(priceTh);

  var quantityTh = document.createElement('th');
  quantityTh.innerHTML = 'Quantity';
  trHeading.appendChild(quantityTh);


  for (var i = 0; i < jsonData.basket.basketItems.length; i++) {
    var trRow = document.createElement('tr');
    trRow.setAttribute('class', 'tr-row')

    var codeTd = document.createElement('td');
    codeTd.innerHTML = jsonData.basket.basketItems[i].product.code;
    trRow.appendChild(codeTd);

    var nameTd = document.createElement('td');
    nameTd.innerHTML = jsonData.basket.basketItems[i].product.name;
    trRow.appendChild(nameTd);

    var manufacturerTd = document.createElement('td');
    manufacturerTd.innerHTML = jsonData.basket.basketItems[i].product.manufacturer;
    trRow.appendChild(manufacturerTd);

    var priceTd = document.createElement('td');
    priceTd.innerHTML = jsonData.basket.basketItems[i].product.price + ' Ft';
    trRow.appendChild(priceTd);

    var quantityTd = document.createElement('td');
    quantityTd.innerHTML = jsonData.basket.basketItems[i].pieces;
    quantityTd.setAttribute('id', 'quantityTd' + jsonData.basket.basketItems[i].product.id);
    trRow.appendChild(quantityTd);


    var editSaveButtonTd = document.createElement('td');
    trRow.appendChild(editSaveButtonTd);

    var editBtn = document.createElement('img');
    editSaveButtonTd.appendChild(editBtn);
    editBtn.setAttribute('src', '/img/edit-button.png');
    editBtn['raw-data'] = jsonData.basket.basketItems[i];
    editBtn.addEventListener('click', editBasketItemQuantity);
    editBtn.setAttribute('class', 'button-enabled button edit-button edit-enabled');
    editBtn.setAttribute('id', 'edit' + jsonData.basket.basketItems[i].product.id);

    var saveBtn = document.createElement('img');
    editSaveButtonTd.appendChild(saveBtn);
    saveBtn.addEventListener('click', saveUpdatedItemQuantity);
    saveBtn['raw-data'] = jsonData.basket.basketItems[i];
    saveBtn.setAttribute('src', '/img/save-button.png');
    saveBtn.setAttribute('class', 'button-disabled button save-button save-disabled');
    saveBtn.setAttribute('id', 'save' + jsonData.basket.basketItems[i].product.id);

    var deleteButtonTd = document.createElement('td');
    var deleteButton = document.createElement('button');
    deleteButton.setAttribute('id', 'delete-product-basket');
    deleteButton.innerHTML = 'Delete product';
    deleteButton.onclick = deleteFromBasket;
    deleteButton['raw-data'] = jsonData.basket.basketItems[i];
    deleteButtonTd.appendChild(deleteButton);
    trRow.appendChild(deleteButtonTd);


    table.appendChild(trRow);
  }

  fetchFormerShippingAddressList();
}


function clearBasket() {
  if (!confirm('Are you sure to empty your basket?')) {
    return;
  }
  fetch('/basket', {
      method: 'DELETE'
    })
    .then(function (response) {
      return response.json();
    }).then(function (jsonData) {
      if (jsonData.response === 'SUCCESS') {
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
      document.getElementById('message-div').innerHTML = jsonData.message;
      fetchBasket();
    });
}

function deleteFromBasket() {
  var id = this["raw-data"].product.id;

  if (!confirm("Are you sure to delete?")) {
    return;
  }

  fetch("/basketitem/" + id, {
      method: "DELETE"
    })
    .then(function (response) {
      return response.json();
    }).then(function (jsonData) {
      if (jsonData.response === 'SUCCESS') {
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
      document.getElementById('message-div').innerHTML = jsonData.message;
      fetchBasket();
    });
}


function editBasketItemQuantity() {
  if (document.querySelectorAll('.save-enabled').length < 1) {
    var data = this['raw-data'];
    var id = data.product.id;

    var editBtn = document.getElementById('edit' + id);
    editBtn.setAttribute('class', 'button-disabled button edit-button edit-disabled');

    var saveBtn = document.getElementById('save' + id);
    saveBtn.setAttribute('class', 'button-enabled button save-button save-enabled');


    var quantityTd = document.getElementById('quantityTd' + id);
    quantityTd.innerHTML = `
                        <div><input class="quantity-input" type="number" name="quantityinput" id="quantityinput${id}" min="1" value="${data.pieces}"></div>
  `;
  }
}


function saveUpdatedItemQuantity() {
  var data = this['raw-data'];
  var id = data.product.id;
  var code = data.product.code;

  var quantity = document.getElementById('quantityinput' + id).value;

  var saveBtn = document.getElementById('save' + id);
  saveBtn.setAttribute('class', 'button-disabled button save-button save-disabled');

  var editBtn = document.getElementById('edit' + id);
  editBtn.setAttribute('class', 'button-enabled button edit-button edit-enabled');

  var quantityTd = document.getElementById('quantityTd' + id);
  quantityTd.innerHTML = quantity;

  var request = {
    'productCode': code,
    'productPieces': quantity
  };
  fetch('/basket/update', {
      method: 'POST',
      body: JSON.stringify(request),
      headers: {
        'Content-type': 'application/json'
      }
    })
    .then(function (response) {
      return response.json();
    }).then(function (jsonData) {
      if (jsonData.response === 'SUCCESS') {
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
      document.getElementById('message-div').innerHTML = jsonData.message;
      fetchBasket();
    });
}

function showShippingAddressInputField() {
  var shippinAddressButton = document.querySelector('#add-shipping-address');
  var shippingAddressField = document.querySelector('#shipping-address');
  var shippingAddressChooserDiv = document.querySelector('#choose-address-div');
  if (shippingAddressField.getAttribute('class') === 'disabled') {
    shippingAddressField.setAttribute('class', 'enabled');
    shippingAddressChooserDiv.setAttribute('class', 'disabled');
    shippinAddressButton.innerHTML = 'Choose address';
  } else {
    shippingAddressField.setAttribute('class', 'disabled');
    shippingAddressChooserDiv.setAttribute('class', 'enabled');
    shippinAddressButton.innerHTML = 'Add address';
  }
}

function fetchFormerShippingAddressList() {
  var url = '/orders/shippingaddresses';
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showFormerShippingAddressList(jsonData);
    });
}

function showFormerShippingAddressList(jsonData) {
  var shippingAddressChooserDiv = document.querySelector('#choose-address-div');
  shippingAddressChooserDiv.innerHTML = '';
  var shippingAddressField = document.querySelector('#shipping-address');

  if (jsonData.length > 0) {
    shippingAddressField.setAttribute('class', 'disabled');
    shippingAddressChooserDiv.setAttribute('class', 'enabled');
    var chooseHeaderDiv = document.createElement('div');
    chooseHeaderDiv.setAttribute('class', 'choose-header');
    chooseHeaderDiv.innerText = 'Choose shipping address';
    shippingAddressChooserDiv.appendChild(chooseHeaderDiv);

    for (var i = 0; i < jsonData.length; i++) {
      var addressChooserInput = document.createElement('input');
      var id = 'oneFormerAddress' + (i + 1);
      addressChooserInput.setAttribute('id', id);
      addressChooserInput.setAttribute('name', 'oneFormerAddress');
      addressChooserInput.setAttribute('type', 'radio');
      addressChooserInput.setAttribute('class', 'address-input-radio');
      var oneAddress = jsonData[i].shippingAddress;
      addressChooserInput.value = oneAddress;
      var labelElement = document.createElement('label');
      labelElement.setAttribute('for', id);
      labelElement.innerHTML = oneAddress;
      var oneInputDiv = document.createElement('div');
      oneInputDiv.setAttribute('class', 'address-chooser');
      oneInputDiv.appendChild(addressChooserInput);
      oneInputDiv.appendChild(labelElement);
      shippingAddressChooserDiv.appendChild(oneInputDiv);
    }
    document.getElementById('oneFormerAddress1').checked = true;
  }
}


function orderItems() {
  if (!confirm('Are you sure you want to place your order?')) {
    return;
  }

  var shippingAddressField = document.querySelector('#shipping-address');
  var shippingAddressChooserDiv = document.querySelector('#choose-address-div');

  var shippingAddress;
  var url;
  if (shippingAddressField.getAttribute('class') === 'enabled') {
    shippingAddress = shippingAddressField.value;
    url = '/myorders';
  } else if (shippingAddressChooserDiv.getAttribute('class') === 'enabled') {
    if (typeof document.forms.shippingAddressChoserForm.elements.oneFormerAddress === 'undefined') {
      chosenAddress = '';
    } else {
      var chosenAddress = document.forms.shippingAddressChoserForm.elements.oneFormerAddress.value;
    }
    shippingAddress = chosenAddress;
    url = '/myorders/storedaddresses';
  }

  var request = {
    'shippingAddress': shippingAddress
  };

  fetch(url, {
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
      if (jsonData.response === 'SUCCESS') {
        document.getElementById('shipping-address').value = '';
        document.getElementById('shipping-address').setAttribute('class', 'disabled');
        fetchBasket();
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
        if (jsonData.message === 'Shipping address already stored. Please chose from the list below.') {
          showShippingAddressInputField();
        }
      }
    });
}


