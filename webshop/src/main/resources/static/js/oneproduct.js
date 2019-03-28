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
            }});
            return false;
            }

function showTable(jsonData) {
    console.log(jsonData);

    var productImg = document.querySelector('.product-img');
    productImg.setAttribute('src', '/img/coming_soon.jpg')
    productImg.setAttribute('alt', '');

    var table = document.getElementById("product-table");
    var tr = document.createElement("tr");
 
    var codeTd = document.createElement("td");
              codeTd.innerHTML = jsonData.code;
              tr.appendChild(codeTd);

    var nameTd = document.createElement("td");
             nameTd.innerHTML = jsonData.name;
             tr.appendChild(nameTd);

    var manufacturerTd = document.createElement("td");
                     manufacturerTd.innerHTML = jsonData.manufacturer;
                     tr.appendChild(manufacturerTd);

    var priceTd = document.createElement("td");
                     priceTd.innerHTML = jsonData.price;
                     tr.appendChild(priceTd);

    table.appendChild(tr);

    document.querySelector('#purchase').addEventListener('click', function () {
            addToBasket(jsonData);
        });
 
}

function addToBasket(jsonData) {
  var quantity = document.getElementById('quantity').value;
  console.log(quantity);
  var code = jsonData.code;
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