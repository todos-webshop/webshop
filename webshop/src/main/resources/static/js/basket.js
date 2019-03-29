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
  //console.log(jsonData);
  var divBasket = document.getElementById('basket-div');
  divBasket.innerHTML = '';
  divBasket.setAttribute('class', 'container');

  var sumPieces = jsonData.sumPieces;
  var sumPrice = jsonData.sumPrice;

  var sumsDiv = document.querySelector('.sums-div');

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
    priceTh.innerHTML = 'Price';
    trHeading.appendChild(priceTh);

    var quantityTh = document.createElement('th');
    quantityTh.innerHTML = 'Quantity';
    trHeading.appendChild(quantityTh);




  for (var i = 0; i < jsonData.basket.basketItems.length; i++) {
    var trRow = document.createElement('tr');

    var codeTd = document.createElement('td');
    codeTd.innerHTML = jsonData.basket.basketItems[i].product.code;
    // codeTd.setAttribute('class', 'div_class');
    trRow.appendChild(codeTd);

    var nameTd = document.createElement('td');
    nameTd.innerHTML = jsonData.basket.basketItems[i].product.name;
    // nameTd.setAttribute('class', 'div_class');
    trRow.appendChild(nameTd);

    // var addressTd = document.createElement('td');
    // addressTd.innerHTML = jsonData.basket.basketItems[i].product.address;
    // addressTd.setAttribute('class', 'div_class');
    // trRow.appendChild(addressTd);

    var manufacturerTd = document.createElement('td');
    manufacturerTd.innerHTML = jsonData.basket.basketItems[i].product.manufacturer;
    // manufacturerTd.setAttribute('class', 'div_class');
    trRow.appendChild(manufacturerTd);

    var priceTd = document.createElement('td');
    priceTd.innerHTML = jsonData.basket.basketItems[i].product.price + ' Ft';
    // priceTd.setAttribute('class', 'div_class');
    trRow.appendChild(priceTd);

    var quantityTd = document.createElement('td');
    quantityTd.innerHTML = jsonData.basket.basketItems[i].pieces;
    // quantityTd.setAttribute('class', 'div_class');
    trRow.appendChild(quantityTd);

    var buttonTd = document.createElement('td');
    //buttonTd.innerHTML = 'delete';
    // buttonTd.setAttribute('class', 'div_class');
    var deleteButton = document.createElement("button");
    //buttonTd.setAttribute("onclick", function () { deleteFromBasket(jsonData);});
    deleteButton.innerHTML = "Delete product";
    deleteButton.onclick = deleteFromBasket;
    //deleteButton.setAttribute('id',jsonData[i].id);
    deleteButton["raw-data"] = jsonData.basket.basketItems[i];
    buttonTd.appendChild(deleteButton);
    trRow.appendChild(buttonTd);

    /*var imgDiv = document.createElement('div');
    imgDiv.innerHTML = '<img alt=' + jsonData.basket.basketItems[i].product.address + ' src=img\\products\\' + jsonData.basket.basketItems[i].product.address + '.png>';

    imgDiv.classList.add('div_class');

    trRow.appendChild(imgDiv);*/

    table.appendChild(trRow);
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

function deleteFromBasket() {
  console.log(this);
  console.log(this["raw-data"].product.id);
  id = this["raw-data"].product.id;

  if (!confirm("Are you sure to delete?")) {
    return;
  }

  fetch("/basketitem/" + id, {
      method: "DELETE",
    })
    .then(function (response) {
      document.getElementById("message-div").innerHTML = "Deleted";
      fetchBasket();
    });
}

function orderItems() {
  if (!confirm('Are you sure you want to continue?')) {
    return;
  }
}