fetchOrders();

function orderItems(){
    if (!confirm('Are you sure you want to place your order?')) {
      return;
    }

    var shippingAddress = document.getElementById('shipping-address').value;
    console.log(shippingAddress);

  var request = {
        'shippingAddress' : shippingAddress
  }

  fetch('/myorders', {
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
        console.log(jsonData)
      if (jsonData.response == 'SUCCESS') {
        document.getElementById('shipping-address').value  = '';
        document.getElementById('shipping-address').setAttribute('class', 'disabled');
        fetchBasket();
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
    });
  return false;
}

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

function fetchOrders() {
  var url = '/myorders';
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
        console.log(jsonData)
      showDivs(jsonData);
    });
}

function showDivs(jsonData) {

  var id = 0;

  for (var i = 0; i < jsonData.length; i++) {
  for (var j = 0; j < jsonData[i].orderItems.length; j++){
      if (jsonData[i].id !== id){

      var divMain = document.getElementById('main_div_orders');

      var tableMain = document.createElement('table');
      tableMain.setAttribute('class', 'orders-table');
      divMain.appendChild(tableMain);

      var tbodyMain = document.createElement('tbody');
      tbodyMain.setAttribute('class', 'orders-tbody');
      tableMain.appendChild(tbodyMain);

      var trMain = document.createElement('tr');
      trMain.setAttribute('id', jsonData[i].id);
      tbodyMain.appendChild(trMain);

      var tdMain = document.createElement('td');
      tdMain.setAttribute('class', 'orders-list-summary');
      trMain.appendChild(tdMain);

      var pFirst = document.createElement('p');
      pFirst.setAttribute('class', 'orders-list-order');
      pFirst.innerText = 'Order ' + jsonData[i].id;
      tdMain.appendChild(pFirst);

      var span = document.createElement('span');
      span.setAttribute('class', 'orders-list-span')
      span.innerText = '– ' + jsonData[i].orderStatus;
      pFirst.appendChild(span);

      var pSecond = document.createElement('p');
      pSecond.setAttribute('class', 'orders-list-date');
      pSecond.innerText = jsonData[i].orderTime.split("T")[0] + " " + jsonData[i].orderTime.split("T")[1];
      tdMain.appendChild(pSecond);

      var pThird = document.createElement('p');
      pThird.setAttribute('class', 'orders-list-amount');
      pThird.innerText =  jsonData[i].totalOrderPrice + " Ft";
      tdMain.appendChild(pThird);

      var tdButton = document.createElement('td');
      tdButton.setAttribute('class', 'orders-list-actions');
      trMain.appendChild(tdButton);

      var button = document.createElement('button');
      button.setAttribute('id', jsonData[i].id)
      button.setAttribute('class', 'orders-button')
      button.innerHTML = 'SEE DETAILS';
      button.addEventListener('click', showDetails);
      tdButton.appendChild(button);

      var tableDetail = document.createElement('table');
      var attribute = 'button' + jsonData[i].id;
      tableDetail.setAttribute('class', 'disabled');
      tableDetail.setAttribute('id', attribute);
      divMain.appendChild(tableDetail);

      var tbodyDetail = document.createElement('tbody');
      tbodyDetail.setAttribute('class', 'orders-list-summary order-products');
      tableDetail.appendChild(tbodyDetail);

      var trDetail = document.createElement('tr');
      var id = 'order-' + jsonData[i].id;
      trDetail.setAttribute('id', id);
      trDetail.setAttribute('class', 'tr-detail');
      tbodyDetail.appendChild(trDetail);

      var tdDetail = document.createElement('div');
      tdDetail.setAttribute('class', 'orders-list-summary tr-div');
      trDetail.appendChild(tdDetail);

      var productNumber = document.createElement('div');
      productNumber.innerText = 'Product ' + (parseInt(j) + 1)
      productNumber.setAttribute('class', 'myorders-div product-number');
      tdDetail.appendChild(productNumber);

      var nameDiv = document.createElement('div');
      nameDiv.innerText =  jsonData[i].orderItems[j].product.name;
      nameDiv.setAttribute('class', 'myorders-div name-product');
      tdDetail.appendChild(nameDiv);

      var codeDiv = document.createElement('div');
      codeDiv.setAttribute('class', 'myorders-div');
      codeDiv.innerText =  jsonData[i].orderItems[j].product.code;
      tdDetail.appendChild(codeDiv);

      var manufacturerDiv = document.createElement('div');
      manufacturerDiv.setAttribute('class', 'myorders-div');
      manufacturerDiv.innerText = jsonData[i].orderItems[j].product.manufacturer;
      tdDetail.appendChild(manufacturerDiv);

      var pieceDiv = document.createElement('div');
      pieceDiv.setAttribute('class', 'myorders-div');
      pieceDiv.innerText = 'Quantity: ' + jsonData[i].orderItems[j].pieces;
      tdDetail.appendChild(pieceDiv);

      var priceDiv = document.createElement('div');
      priceDiv.setAttribute('class', 'myorders-div');
      priceDiv.innerText = jsonData[i].orderItems[j].product.price + ' Ft';
      tdDetail.appendChild(priceDiv);

      id = jsonData[i].id;

  } else {
      var idToFind = 'order-' + jsonData[i].id;
      var trToAppendIn = document.getElementById(idToFind);

      var selector = '#' + idToFind;
      var tdInFoundTr = document.querySelector(selector);
      console.log(tdInFoundTr.children.length);


      var tdDetail = document.createElement('div');
      tdDetail.setAttribute('class', 'orders-list-summary tr-div');
      trToAppendIn.appendChild(tdDetail);

      var productNumber = document.createElement('div');
      productNumber.innerText = 'Product ' + (parseInt(j) + 1)
      productNumber.setAttribute('class', 'myorders-div product-number');
      tdDetail.appendChild(productNumber);

      var nameDiv = document.createElement('div');
      nameDiv.innerText = jsonData[i].orderItems[j].product.name;
      nameDiv.setAttribute('class', 'myorders-div name-product');
      tdDetail.appendChild(nameDiv);

      var codeDiv = document.createElement('div');
      codeDiv.setAttribute('class', 'myorders-div');
      codeDiv.innerText = jsonData[i].orderItems[j].product.code;
      tdDetail.appendChild(codeDiv);

      var manufacturerDiv = document.createElement('div');
      manufacturerDiv.setAttribute('class', 'myorders-div');
      manufacturerDiv.innerText = jsonData[i].orderItems[j].product.manufacturer;
      tdDetail.appendChild(manufacturerDiv);

      var pieceDiv = document.createElement('div');
      pieceDiv.setAttribute('class', 'myorders-div');
      pieceDiv.innerText = 'Quantity: ' + jsonData[i].orderItems[j].pieces;
      tdDetail.appendChild(pieceDiv);

      var priceDiv = document.createElement('div');
      priceDiv.setAttribute('class', 'myorders-div');
      priceDiv.innerText = jsonData[i].orderItems[j].product.price + ' Ft';
      tdDetail.appendChild(priceDiv);
           }
        }
    }
}

function showDetails() {
  var id = this.id;
  var attribute = 'button' + id;
  var tableDetails = document.getElementById(attribute);
  if (tableDetails.getAttribute('class') == 'disabled'){
  tableDetails.setAttribute('class', 'enabled');
  } else {
  tableDetails.setAttribute('class', 'disabled')
  }
}