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
    var divMain = document.getElementById('main_div');
    divMain.innerHTML = '';
    for (var i = 0; i < jsonData.length; i++) {
      var divRow = document.createElement('div');
      divRow.setAttribute('contenteditable', 'false');
      divRow.setAttribute('id', jsonData[i].product.id);
      divRow.setAttribute('class', 'admin-product-div');

      var codeDiv = document.createElement('div');
      codeDiv.innerHTML = jsonData[i].product.code;
      codeDiv.setAttribute('class', 'div_class_admin');
      divRow.appendChild(codeDiv);

      var nameDiv = document.createElement('div');
      nameDiv.innerHTML = jsonData[i].product.name;
      nameDiv.setAttribute('class', 'div_class_admin');
      divRow.appendChild(nameDiv);

      // var addressDiv = document.createElement('div');
      // addressDiv.innerHTML = jsonData[i].product.address;
      // addressDiv.setAttribute('class', 'div_class_admin');
      // divRow.appendChild(addressDiv);

      var manufacturerDiv = document.createElement('div');
      manufacturerDiv.innerHTML = jsonData[i].product.manufacturer;
      manufacturerDiv.setAttribute('class', 'div_class_admin');
      divRow.appendChild(manufacturerDiv);
      
      var quantityDiv = document.createElement('div');
      quantityDiv.innerHTML = `quantity: ${jsonData[i].pieces}`;
      quantityDiv.setAttribute('class', 'div_class_admin');
      divRow.appendChild(quantityDiv);

      var priceDiv = document.createElement('div');
      priceDiv.innerHTML = `${jsonData[i].product.price} Ft`;
      priceDiv.setAttribute('class', 'div_class_admin');
      divRow.appendChild(priceDiv);

      // var statusDiv = document.createElement('div');
      // statusDiv.innerHTML = jsonData[i].product.productStatus;
      // statusDiv.setAttribute('class', 'div_class_admin status-div');
      // divRow.appendChild(statusDiv);

      var buttonsDiv = document.createElement('div');
      buttonsDiv.setAttribute('class', 'div_class_admin admin-product-div');
      buttonsDiv.setAttribute('id', 'buttons-div');

      var deleteBtn = document.createElement('img');
      deleteBtn.setAttribute('src', '/img/delete-button.png');
      deleteBtn.setAttribute('class', 'button');
      // deleteBtn.setAttribute('id', jsonData[i].product.id);
      deleteBtn.setAttribute('data-address', jsonData[i].product.address);
      console.log(jsonData[i].product.address);
      deleteBtn.addEventListener('click', deleteItem);
      deleteBtn.setAttribute('class', 'button');

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

      divRow.appendChild(deleteBtn);
      // divRow.appendChild(editBtn);
      // divRow.appendChild(saveBtn);

      divMain.appendChild(divRow);
    }
  }


  function deleteItem() {
    var id = (new URL(document.location)).searchParams.get('id');
    var address = this.getAttribute('data-address');

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