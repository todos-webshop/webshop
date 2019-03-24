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
      // console.log(jsonData);
      showBasket(jsonData);
    });
}


function showBasket(jsonData) {
  var divMain = document.getElementById('basket-div');
  divMain.innerHTML = '';
  divMain.setAttribute('class', 'container');

  var sumPieces = jsonData.sumPieces;
  var sumPrice = jsonData.sumPrice;

  var sumsDiv = document.createElement('div');
  sumsDiv.setAttribute('class', 'div_class');
  divMain.appendChild(sumsDiv);

  var sumPiecesDiv = document.createElement('div');
  var sumPriceDiv = document.createElement('div');
  sumPiecesDiv.innerText = `Items in basket: ${sumPieces}`;
  sumPriceDiv.innerText = `Total value: ${sumPrice} Ft`;


  sumPiecesDiv.setAttribute('class', 'div_class');
  sumPriceDiv.setAttribute('class', 'div_class');
  sumsDiv.appendChild(sumPiecesDiv);
  sumsDiv.appendChild(sumPriceDiv);


  for (var i = 0; i < jsonData.basket.basketItems.length; i++) {
    var divRow = document.createElement('div');

    var codeDiv = document.createElement('div');
    codeDiv.innerHTML = jsonData.basket.basketItems[i].product.code;
    codeDiv.setAttribute('class', 'div_class');
    divRow.appendChild(codeDiv);

    var nameDiv = document.createElement('div');
    nameDiv.innerHTML = jsonData.basket.basketItems[i].product.name;
    nameDiv.setAttribute('class', 'div_class');
    divRow.appendChild(nameDiv);

    var addressDiv = document.createElement('div');
    addressDiv.innerHTML = jsonData.basket.basketItems[i].product.address;
    addressDiv.setAttribute('class', 'div_class');
    divRow.appendChild(addressDiv);

    var manufacturerDiv = document.createElement('div');
    manufacturerDiv.innerHTML = jsonData.basket.basketItems[i].product.manufacturer;
    manufacturerDiv.setAttribute('class', 'div_class');
    divRow.appendChild(manufacturerDiv);

    var priceDiv = document.createElement('div');
    priceDiv.innerHTML = jsonData.basket.basketItems[i].product.price + ' Ft';
    priceDiv.setAttribute('class', 'div_class');
    divRow.appendChild(priceDiv);

    var quantityDiv = document.createElement('div');
    quantityDiv.innerHTML = 'Quantity: ' + jsonData.basket.basketItems[i].pieces;
    quantityDiv.setAttribute('class', 'div_class');
    divRow.appendChild(quantityDiv);

    var imgDiv = document.createElement('div');
    imgDiv.innerHTML = '<img alt=' + jsonData.basket.basketItems[i].product.address + ' src=img\\products\\' + jsonData.basket.basketItems[i].product.address + '.png>';

    imgDiv.classList.add('div_class');

    divRow.appendChild(imgDiv);

    divMain.appendChild(divRow);
  }
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

function orderItems(){
  if (!confirm('Are you sure you want to continue?')) {
    return;
  }
}