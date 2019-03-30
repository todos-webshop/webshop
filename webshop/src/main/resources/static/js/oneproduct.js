fetchProduct();



function fetchProduct() {
var address = (new URL(document.location)).searchParams.get("address");
        var url ="api/product/" + address;
        console.log(url );
        fetch(url)
            .then(function(response) {
                return response.json();
                })
            .then(function(jsonData) {

            if ( jsonData.response == 'FAILED'){
            showProductNotFound(jsonData);
            } else {
                showTable(jsonData);
                console.log(jsonData)
            }});
            return false;
            }

 function showTable(jsonData) {
    console.log(jsonData);

    var table = document.getElementById("product-table");

    var tbody = document.createElement('tbody');
    tbody.setAttribute('class', 'product-body');
    table.appendChild(tbody);

    var trDetail = document.createElement('tr');
    tbody.appendChild(trDetail);

    var tdLeft = document.createElement('td');
    trDetail.appendChild(tdLeft);
    tdLeft.setAttribute('class', 'td-left');

    var link = document.createElement('div');
    link.addEventListener('click', goBack);
    link.innerHTML = 'Back to main menu';
    link.setAttribute('class', 'product-category link');
    tdLeft.appendChild(link);

    var categoryDiv = document.createElement('div');
    categoryDiv.innerText = jsonData.categoryName;
    categoryDiv.setAttribute('class', 'product-category');
    tdLeft.appendChild(categoryDiv);

    var span = document.createElement('span');
    span.innerHTML = ' / ' + jsonData.products[0].name;
    categoryDiv.appendChild(span);

    var productImg = document.createElement('img');
    productImg.setAttribute('class', 'product-img')
    productImg.setAttribute('src', '/img/coming_soon.jpg')
    productImg.setAttribute('alt', '');
    tdLeft.appendChild(productImg);

    var tdRight = document.createElement('td');
    tdRight.setAttribute('class', 'td-right')
    trDetail.appendChild(tdRight);

    var inputField = document.createElement('input');
    inputField.setAttribute('class', 'purchase-quantity');
    inputField.setAttribute('type', 'number');
    inputField.setAttribute('name', 'quantity');
    inputField.setAttribute('id', 'quantity');
    inputField.setAttribute('min', '1');
    inputField.setAttribute('value', '1');
    tdRight.appendChild(inputField);

    var button = document.createElement('button');
    button.setAttribute('class', 'purchase add-to-cart');
    button.setAttribute('id', 'purchase');
    button.innerHTML = 'Add to cart';
    tdRight.appendChild(button);

    var nameDiv = document.createElement('div');
    nameDiv.innerText = jsonData.products[0].name;
    nameDiv.setAttribute('class', 'product-name');
    tdRight.appendChild(nameDiv);

    var categoryDiv = document.createElement('div');
    categoryDiv.innerText = jsonData.categoryName;
    categoryDiv.setAttribute('class', 'category');
    tdRight.appendChild(categoryDiv);

    var manufacturerDiv = document.createElement('div');
    manufacturerDiv.setAttribute('class', 'product-man');
    manufacturerDiv.innerText = 'by ' + jsonData.products[0].manufacturer;
    tdRight.appendChild(manufacturerDiv);

    var codeDiv = document.createElement('div');
    codeDiv.setAttribute('class', 'product-code');
    codeDiv.innerText = jsonData.products[0].code;
    tdRight.appendChild(codeDiv);

    var priceDiv = document.createElement('div');
    priceDiv.setAttribute('class', 'product-price');
    priceDiv.innerText = jsonData.products[0].price + ' Ft';
    tdRight.appendChild(priceDiv);

    document.querySelector('#purchase').addEventListener('click', function () {
            addToBasket(jsonData);
        });

}

function addToBasket(jsonData) {
  var quantity = document.getElementById('quantity').value;
  console.log(quantity);
  var code = jsonData.products[0].code;
  var request = {
    'productCode': code,
    'productPieces': quantity
  };
//   console.log(request);
  fetch('/basket', {
    method: 'POST',
    body: JSON.stringify(request),
    headers: {
      'Content-type': 'application/json'
    }
  })
    .then(function (response) {
            //  console.log(response);
             return response.json();
            }).then(function (jsonData) {
                // console.log(jsonData);
      if (jsonData.response === "SUCCESS") {
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
      document.getElementById('message-div').innerHTML = jsonData.message;
    });
}
function goBack() {
  window.history.back();
}
   // function getUrlParam(name) {
  //  var results = new RegExp('[\\?&]' + name + '=([^&#]*)').exec(window.location.href);
 //   return results;
//}
function showProductNotFound(jsonData){
   window.location.href = 'http://localhost:8080/error.html';
   // reviews.innerHTML = "";
}