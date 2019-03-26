fetchOrders();

function orderItems(){
    if (!confirm('Are you sure you want to place your order?')) {
      return;
    }

  var request = {

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
      if (jsonData.response == 'SUCCESS') {
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
  divMain = document.getElementById('main_div');
  divMain.innerHTML = '';
  var id = 0;
  for (var i = 0; i < jsonData.length; i++) {
  if (jsonData[i].id !== id){
      var divRow = document.createElement('div');
      divRow.setAttribute('id', jsonData[i].id);
      divRow.setAttribute('class', 'orders-div');

      var bold = document.createElement('b');
      bold.class = 'bold';
      divRow.appendChild(bold);

      var numberOfOrder = document.createElement('div');
      numberOfOrder.innerText = 'Order ' + jsonData[i].id + "\n";
      numberOfOrder.setAttribute('class', 'bold');
      bold.appendChild(numberOfOrder);
      divRow.appendChild(numberOfOrder);

      var date = document.createElement('div');
      date.innerText = 'Date of order: ' + jsonData[i].orderTime.split("T")[0] + " " + jsonData[i].orderTime.split("T")[1];
      divRow.appendChild(date);

      var totalOrder = document.createElement('div');
      totalOrder.innerText = 'Total purchase value: ' + jsonData[i].totalOrderPrice + "\n";
      divRow.appendChild(totalOrder);

      var codeDiv = document.createElement('div');
      codeDiv.innerText = 'Product code: ' + jsonData[i].orderItems[0].product.code;
      divRow.appendChild(codeDiv);

      var nameDiv = document.createElement('div');
      nameDiv.innerText = 'Product name: ' + jsonData[i].orderItems[0].product.name;
      divRow.appendChild(nameDiv);

      var manufacturerDiv = document.createElement('div');
      manufacturerDiv.innerText = 'Product manufacturer: ' + jsonData[i].orderItems[0].product.manufacturer;
      divRow.appendChild(manufacturerDiv);

      var pieceDiv = document.createElement('div');
      pieceDiv.innerText = 'Piece: ' + jsonData[i].orderItems[0].pieces;
      divRow.appendChild(pieceDiv);

      var priceDiv = document.createElement('div');
      priceDiv.innerText = 'Price: ' + jsonData[i].orderItems[0].product.price + ' Ft' +"\n";
      divRow.appendChild(priceDiv);

      divMain.appendChild(divRow)
      id = jsonData[i].id
  } else {
        var foundDivRow = document.getElementById(jsonData[i].id);

        foundDivRow.innerText = foundDivRow.innerText + "\n" + 'Product code: ' + jsonData[i].orderItems[0].product.code + "\n"
        +'Product name: ' + jsonData[i].orderItems[0].product.name + "\n"
        + 'Product manufacturer: ' + jsonData[i].orderItems[0].product.manufacturer + "\n"
        + 'Piece: ' + jsonData[i].orderItems[0].pieces + "\n" + 'Price: ' + jsonData[i].orderItems[0].product.price + " Ft \n"
        }
    }
}