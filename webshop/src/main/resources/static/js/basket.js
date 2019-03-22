window.onload = function () {
  fetchBasket();
  console.log("Szia!");


}


function fetchBasket() {
  var url = "/basket";
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {

      console.log(jsonData);
      showBasket(jsonData);
    });
}

function showBasket(jsonData) {
  var divMain = document.getElementById('basket-div');

  for (var i = 0; i < jsonData.basket.basketItems.length; i++) {
    var divRow = document.createElement("div");

    var codeDiv = document.createElement("div");
    codeDiv.innerHTML = jsonData.basket.basketItems[i].product.code;
    codeDiv.setAttribute('class', 'div_class');
    divRow.appendChild(codeDiv);

    var nameDiv = document.createElement("div");
    nameDiv.innerHTML = jsonData.basket.basketItems[i].product.name;
    nameDiv.setAttribute('class', 'div_class');
    divRow.appendChild(nameDiv);

    var addressDiv = document.createElement("div");
    addressDiv.innerHTML = jsonData.basket.basketItems[i].product.address;
    addressDiv.setAttribute('class', 'div_class');
    divRow.appendChild(addressDiv);

    var manufacturerDiv = document.createElement("div");
    manufacturerDiv.innerHTML = jsonData.basket.basketItems[i].product.manufacturer;
    manufacturerDiv.setAttribute('class', 'div_class');
    divRow.appendChild(manufacturerDiv);

    var priceDiv = document.createElement("div");
    priceDiv.innerHTML = jsonData.basket.basketItems[i].product.price + " Ft";
    priceDiv.setAttribute('class', 'div_class');
    divRow.appendChild(priceDiv);

    var imgDiv = document.createElement("div");
    imgDiv.innerHTML = "<img alt=" + jsonData.basket.basketItems[i].product.address + " src=img\\products\\" + jsonData.basket.basketItems[i].product.address + ".png>";

    imgDiv.classList.add('div_class');

    divRow.appendChild(imgDiv);

    divMain.appendChild(divRow);
  }

}