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
  tableBody.innerHTML = '';
  tableBody.setAttribute('class', 'table table-striped admin-orders');

  for (var i = 0; i < jsonData.length; i++) {
    var tr = document.createElement('tr');

    var idTd = document.createElement('td');
    idTd.textContent = jsonData[i].orderId;
    tr.appendChild(idTd);

    var usernameTd = document.createElement('td');
    usernameTd.innerHTML = jsonData[i].username;
    tr.appendChild(usernameTd);

    var orderTimeTd = document.createElement('td');
    orderTimeTd.innerHTML = `${jsonData[i].orderTime.split('T')[0]} ${jsonData[i].orderTime.split('T')[1]}`;
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

    var shippingAddress = document.createElement('td');
    shippingAddress.innerHTML = jsonData[i].shippingAddress;
    tr.appendChild(shippingAddress);

    var deleteButtonTd = document.createElement('td');
    var deleteButton = document.createElement('button');
    deleteButton.innerHTML = 'Delete order';
    deleteButton.setAttribute('data-id', jsonData[i].orderId);
    deleteButton.addEventListener('click', deleteOrder, true);
    deleteButtonTd.appendChild(deleteButton);
    tr.appendChild(deleteButtonTd);

    var updateStatusButtonTd = document.createElement('td');
    var updateStatusButton = document.createElement('button');
    updateStatusButton.innerHTML = 'Delivered';
    updateStatusButton.setAttribute('data-id', jsonData[i].orderId);
    updateStatusButton.addEventListener('click', updateOrderStatus, true);
    updateStatusButtonTd.appendChild(updateStatusButton);
    tr.appendChild(updateStatusButtonTd);

    tableBody.appendChild(tr);
    tr.setAttribute('onclick', `window.location.href="/order.html?id=${jsonData[i].orderId}"`);

  }
}

function showOrder(jsonData) {
  var code = jsonData.code;
  var request = {
    'productCode': code
  };
  fetch('/basket', {
      method: 'POST',
      body: JSON.stringify(request),
      headers: {
        'Content-type': 'application/json'
      }
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
    });
}

function deleteOrder(event) {
  event.stopPropagation();
  var id = this.getAttribute('data-id');

  var url = `/orders/${id}`;

  if (!confirm('Are you sure to delete?')) {
    return;
  }

  fetch(url, {
      method: 'DELETE'
    })
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      if (jsonData.response === 'SUCCESS') {
        fetchAllOrders();
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
    });
  return false;
}


function updateOrderStatus(event) {
  event.stopPropagation();
  var id = this.getAttribute('data-id');

  var url = `/orders/${id}/status`;

  if (!confirm('Are you sure to change order status?')) {
    return;
  }

  fetch(url, {
      method: 'POST'
    })
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      if (jsonData.response === 'SUCCESS') {
        fetchAllOrders();
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
    });
  return false;
}


function filterByOrderStatus() {
  var filter = document.getElementById('order-filter').value;
  if (filter === '') {
    fetchAllOrders();
  } else {
    fetchOrdersFilteredByStatus(filter);
  }
}

function fetchOrdersFilteredByStatus(filter) {
  var url = `/orders/filtered/${filter}`;
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showTable(jsonData);
    });
}
