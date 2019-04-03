fetchOrders();
var json;
function fetchOrders() {
  var url = '/myorders';
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showDivs(jsonData);
    });
}


function showDivs(jsonData){

  var mainDivOrders = document.getElementById('main_div_orders');

  for (let i = 0; i < jsonData.length; i++) {

    var tableMain = document.createElement('table');
    tableMain.setAttribute('class', 'orders-table');
    mainDivOrders.appendChild(tableMain); 

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
    pThird.setAttribute('class', 'orders-list-address');
    pThird.innerText = jsonData[i].shippingAddress;
    tdMain.appendChild(pThird);

    var pFourth = document.createElement('p');
    pFourth.setAttribute('class', 'orders-list-amount');
    pFourth.innerText = jsonData[i].totalOrderPrice + " Ft";
    tdMain.appendChild(pFourth);

    var tdButton = document.createElement('td');
    tdButton.setAttribute('class', 'orders-list-actions');
    trMain.appendChild(tdButton);

    var button = document.createElement('button');
    button.setAttribute('id', jsonData[i].id)
    button.setAttribute('class', 'orders-button')
    button.innerHTML = 'SEE DETAILS';
    button.addEventListener('click', showDetails);
    tdButton.appendChild(button);

    var mainDiv = document.createElement('div');
    var attribute = 'button' + jsonData[i].id;
    mainDiv.setAttribute('id', attribute);
    mainDivOrders.appendChild(mainDiv);
    
  }
  var body = document.getElementsByTagName('body')[0];
  var scriptN = document.createElement('script');
  scriptN.setAttribute('src',"https://unpkg.com/flickity@2/dist/flickity.pkgd.min.js");
  body.appendChild(scriptN);
  json = jsonData;
}


function showDetails(){
  var rowId = this.id;
  var div = document.getElementById('button'+rowId);

  if(div.children.length > 0){
    div.innerHTML="";

  } else {
    var index = json.findIndex(i => i.id == rowId);

  div.innerHTML="";
  var div2 = document.createElement('div');
  div.appendChild(div2);

//                              ------------------------------- FAAAAANNNNNNNIIIIIIII !!!!!!! -------------------------------
//                                      itt hozom létre a kis diveket amikben az adatok vannak! köszike :)

  for (let j = 0; j < json[index].orderItems.length; j++) {
    var div1 = document.createElement('div');
    div1.className = "carousel-cell";
    div2.appendChild(div1);

    // var productNumDiv = document.createElement('div');
    // productNumDiv.innerText = 'Product ' + (parseInt(j) + 1);
    // productNumDiv.className="mydiv";
    // div1.appendChild(productNumDiv);

    var productNameDiv = document.createElement('div');
    productNameDiv.innerText = 'Name: ' + json[index].orderItems[j].product.name;
    productNameDiv.className="mydiv";
    div1.appendChild(productNameDiv);

    var productCodeDiv = document.createElement('div');
    productCodeDiv.innerText = "Code: " + json[index].orderItems[j].product.code;
    productCodeDiv.className="mydiv";
    div1.appendChild(productCodeDiv);

    var productManufacturerDiv = document.createElement('div');
    productManufacturerDiv.innerText = json[index].orderItems[j].product.manufacturer;    
    productManufacturerDiv.className="mydiv";
    div1.appendChild(productManufacturerDiv);

    var quantityDiv = document.createElement('div');
    quantityDiv.innerText = 'Quantity: ' + json[index].orderItems[j].pieces;
    quantityDiv.className="mydiv";
    div1.appendChild(quantityDiv);

    var sumDiv = document.createElement('div');
    sumDiv.innerText = 'Total price: ' + json[index].orderItems[j].product.price + ' Ft';
    sumDiv.className="mydiv";
    div1.appendChild(sumDiv);
  }

  var flkty = new Flickity( div2, {
    // options
    cellAlign: 'center',
    contain: true,
    wrapAround: true,
    groupCells: 1
  });
  }

}
