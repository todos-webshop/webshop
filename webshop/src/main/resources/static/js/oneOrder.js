window.onload = function () {
  fetchOrderItems();
};


function fetchOrderItems() {
  var id = (new URL(document.location)).searchParams.get('id');
  var url = '/orders/' + id;
  console.log(url);
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showDivs(jsonData);
    });


  function showDivs(jsonData) {
    // console.log(jsonData);

    var divMain = document.getElementById('main_div_one_order');
    divMain.innerHTML = '';

    var table = document.createElement('table');
    table.setAttribute('class', 'table table-striped');
    divMain.appendChild(table);

      var trHeading = document.createElement('tr');
      trHeading.setAttribute('class', 'basket-head-row')
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

      var quantityTh = document.createElement('th');
      quantityTh.innerHTML = 'Quantity';
      trHeading.appendChild(quantityTh);

      var priceTh = document.createElement('th');
      priceTh.innerHTML = 'Total price';
      trHeading.appendChild(priceTh);

    for (var i = 0; i < jsonData.length; i++) {

var trRow = document.createElement('tr');
    trRow.setAttribute('class', 'tr-row')
        trRow.setAttribute('class', 'tr-row')


    var codeTd = document.createElement('td');
      codeTd.innerHTML = jsonData[i].product.code;
      codeTd.setAttribute('class', 'div_class_admin');
      trRow.appendChild(codeTd);

    var nameTd = document.createElement('td');
    nameTd.innerHTML = jsonData[i].product.name;
          nameTd.setAttribute('class', 'div_class_admin');
          trRow.appendChild(nameTd);

    var manufacturerTd = document.createElement('td');
      manufacturerTd.innerHTML = jsonData[i].product.manufacturer;
      manufacturerTd.setAttribute('class', 'div_class_admin');
      trRow.appendChild(manufacturerTd);


    var quantityTd = document.createElement('td');
      quantityTd.innerHTML = jsonData[i].pieces;
      quantityTd.setAttribute('class', 'div_class_admin');
      trRow.appendChild(quantityTd);

    var priceTd = document.createElement('td');
    priceTd.innerHTML = jsonData[i].product.price + 'Ft';
      priceTd.setAttribute('class', 'div_class_admin');
      trRow.appendChild(priceTd);




      // var statusDiv = document.createElement('div');
      // statusDiv.innerHTML = jsonData[i].product.productStatus;
      // statusDiv.setAttribute('class', 'div_class_admin status-div');
      // divRow.appendChild(statusDiv);

/*      var buttonsDiv = document.createElement('div');
      buttonsDiv.setAttribute('class', 'div_class_admin admin-product-div');
      buttonsDiv.setAttribute('id', 'buttons-div');*/

      var deleteBtn = document.createElement('img');
      deleteBtn.setAttribute('src', '/img/delete-button.png');
      deleteBtn.setAttribute('class', 'order-delete-button');
      //deleteBtn.setAttribute('id', jsonData[i].product.id);
      deleteBtn.setAttribute('data-address', jsonData[i].product.address);
      console.log(jsonData[i].product.address);
      console.log(jsonData);
      console.log(jsonData[i].product.address);
      deleteBtn.addEventListener('click', deleteItem);

      // var editBtn = document.createElement('img');
      // editBtn.setAttribute('src', '/img/edit-button.png');
      // editBtn.setAttribute('class', 'button')
      // editBtn.setAttribute('id', jsonData[i].product.id)
      // editBtn.addEventListener('click', editItem);
      // editBtn.setAttribute('class', 'button');

      // var saveBtn = document.createElement('img');
      // saveBtn.addEventListener('click', saveUpdatedItem);
      // saveBtn.setAttribute('id', jsonData[i].product.id);
      // saveBtn.setAttribute('src', '/img/save-button.png');
      // var attribute = 'button-disabled button save-button' + jsonData[i].product.id;
      // saveBtn.setAttribute('class', attribute);

      trRow.appendChild(deleteBtn);
      // divRow.appendChild(editBtn);
      // divRow.appendChild(saveBtn);

      table.appendChild(trRow);
    }
  }


  function deleteItem() {
    var id = (new URL(document.location)).searchParams.get('id');
    var address = this.getAttribute('data-address');
    console.log(id)
    console.log(address)

    var url = `/orders/${id}/${address}`;
    console.log(url);

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
          fetchOrderItems();
          document.getElementById('message-div').innerHTML = jsonData.message;
          document.getElementById('message-div').setAttribute('class', 'alert alert-success');
        } else {
          document.getElementById('message-div').innerHTML = jsonData.message;
          document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
        }
      });
    return false;
  }




}