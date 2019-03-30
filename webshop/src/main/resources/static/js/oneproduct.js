var actProduct;
var actRate;
var actAvg;
var actRates;
fetchProduct();
setTimeout(fetchRate, 1000);
setTimeout(fetchRates, 1000);
setTimeout(fetchAvg, 1000);



function fetchProduct() {
var address = (new URL(document.location)).searchParams.get("address");
        var url ="api/product/" + address;
        //console.log(url );
        fetch(url)
            .then(function(response) {
                return response.json();
                })
            .then(function(jsonData) {

            if ( jsonData.response == 'FAILED'){
            showProductNotFound(jsonData);
            } else {
                showTable(jsonData);
                actProduct = jsonData;
               // fetchRate(actProduct);
            }});
            return false;
            }
function fetchRate() {
//console.log(actProduct["id"]);
        var url ="/api/rating/"+actProduct["id"] ;
        //console.log(url );
        fetch(url)
            .then(function(response) {
                return response.json();
                })
            .then(function(jsonData) {

//console.log("fetchRate url"+url);
                actRate =jsonData;
                //console.log("fetchRate"+jsonData);
            });
            return false;
            }
 function showTable(jsonData) {
   // console.log(jsonData);

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
            addToBasket(jsonData);});
    document.querySelector('#rate_button').addEventListener('click', function () {
                       // console.log("hello");
                       sendRate(jsonData);
                       document.getElementById('message_text').value = "";
                       setTimeout(fetchRates, 100);
                       showRates();
                       fetchAvg();
                       displayAvg();
        });

}

function addToBasket(jsonData) {
  var quantity = document.getElementById('quantity').value;
  var code = jsonData.code;
  var request = {
    'productCode': code,
    'productPieces': quantity
  };
//   console.log(request);
  fetch('/api/rating/userrating', {
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


function sendRate(jsonData) {
  var stars = document.getElementById('select_star').value;
  var message = document.getElementById('message_text').value;
    var request = {
    'id':actProduct["id"],
    'stars': stars,
    'message': message,
    'date':null,
    'user':null,
    'product':actProduct
  };
//   console.log(request);
  fetch('/api/rating/userrating/'+actProduct["id"], {
    method: 'POST',
    body: JSON.stringify(request),
    headers: {
      'Content-type': 'application/json'
    }
  })
    .then(function (response) {
            //  console.log(response);
          //console.log( "textarea"+ document.getElementById('message_text'));
          //  console.log( "message"+message);
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

function fetchAvg() {

        var url ="/api/rating/avg/"+actProduct["id"] ;
       // console.log(url );
        fetch(url)
            .then(function(response) {
                return response.json();
                })
            .then(function(jsonData) {
                actAvg =jsonData;
                displayAvg();

                //console.log("fetchAvg");
            });
            return false;

}
function fetchRates() {

        var url ="/api/rating/list/"+actProduct["id"];
        //console.log(url );
        fetch(url)
            .then(function(response) {
                return response.json();
                })
            .then(function(jsonData) {
                actRates =jsonData;
                console.log(actRates);
                showRates();


              //  console.log("fetchRates");
            });
            return false;

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

function displayAvg(){
//console.log("display avg");
//console.log(actAvg);
//if(actAvg!==undefined){

document.getElementById('avg_product').innerHTML = "Average "+actAvg;

}



function showRates() {
var table=document.getElementById('product-rate-table')  ;
table.innerHTML="";
for (var i=0;i<actRates.length;i++ ){
 var tr = document.createElement("tr");
 var userTd = document.createElement("td");
    userTd.innerHTML = actRates[i]["user"]["username"];
    var timeTd = document.createElement("td");
        timeTd.innerHTML = actRates[i]["date"];
 var starTd = document.createElement("td");
 starTd.innerHTML = actRates[i]["stars"];
 var messageTd = document.createElement("td");
  messageTd.innerHTML = actRates[i]["message"];
 tr.appendChild(userTd);
 tr.appendChild(timeTd);
tr.appendChild(starTd);
tr.appendChild(messageTd);
table.appendChild(tr);

}


}