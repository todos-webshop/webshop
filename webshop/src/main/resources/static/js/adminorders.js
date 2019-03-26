window.onload = function () {
  fetchAllOrders();
};



function fetchAllOrders() {
  var url = '/orders';
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showTable(jsonData);
    });
}

function showTable(jsonData) {
  var tableBody = document.getElementById('orders-table');
  tableBody.setAttribute('class', 'table table-striped');

  for (var i = 0; i < jsonData.length; i++) {
    var tr = document.createElement('tr');

    var idTd = document.createElement('td');
    idTd.textContent = jsonData[i].orderId;
    tr.appendChild(idTd);

    var usernameTd = document.createElement('td');
    usernameTd.innerHTML = jsonData[i].username;
    tr.appendChild(usernameTd);

    var orderTimeTd = document.createElement('td');
    orderTimeTd.innerHTML = jsonData[i].orderTime;
    tr.appendChild(orderTimeTd);

    var orderStatusTd = document.createElement('td');
    orderStatusTd.innerHTML = jsonData[i].orderStatus;
    tr.appendChild(orderStatusTd);

    var sumOrderPriceTd = document.createElement('td');
    sumOrderPriceTd.innerHTML = jsonData[i].sumOrderPrice;
    tr.appendChild(sumOrderPriceTd);

    var sumOrderPiecesTd = document.createElement('td');
    sumOrderPiecesTd.innerHTML = jsonData[i].sumOrderPieces;
    tr.appendChild(sumOrderPiecesTd);


    tableBody.appendChild(tr);
    tr.setAttribute('onclick', `window.location.href="/order.html?id=${jsonData[i].orderId}"`);


    // document.querySelector('#purchase').addEventListener('click', function () {
    //   showOrder(jsonData);
    // });

  }
}

function showOrder(jsonData) {
  var code = jsonData.code;
  var request = {
    'productCode': code
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
      if (jsonData.response === 'SUCCESS') {
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
      document.getElementById('message-div').innerHTML = jsonData.message;
    });
}
window.onscroll = function() {scrollFunction()};

function scrollFunction() {
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    document.getElementById("myBtn").style.display = "block";
  } else {
    document.getElementById("myBtn").style.display = "none";
  }
}


function topFunction() {
  document.documentElement.scrollTop = 0;
}