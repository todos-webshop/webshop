var modify = false;

fetchProducts();

var addButton = document.getElementById('add-btn');
addButton.onclick = addNewProduct;

function fetchProducts() {
  var url = '/api/products';
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showDivs(jsonData);
    });
}

function fetchCategories(e){
  var url = '/api/categories';
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      if (modify){
      editItem(jsonData, e.target.id);
      } else {
      showInputFields(jsonData);
      }
    });
}


function showDivs(jsonData) {
  var divMain = document.getElementById('main_div_adminproducts');
  divMain.innerHTML = '';
  for (var i = 0; i < jsonData.length; i++) {
    var categoryName = document.createElement('div');
    if (jsonData[i].products.length == 1){
    categoryName.innerHTML = jsonData[i].categoryName + '  (1 product)';
    } else{
    categoryName.innerHTML = jsonData[i].categoryName + '  (' + jsonData[i].products.length + ' products)';
    }
    categoryName.setAttribute('class', 'admin-category-names')
    divMain.appendChild(categoryName)

  for (var j = 0; j < jsonData[i].products.length; j++){
    var divRow = document.createElement('div');
    divRow.setAttribute('contenteditable', 'false');
    divRow.setAttribute('id', jsonData[i].products[j].id);
    divRow.setAttribute('class', 'admin-product-div');

    var codeDiv = document.createElement('div');
    codeDiv.innerHTML = jsonData[i].products[j].code;
    codeDiv.setAttribute('class', 'admin-product-code');
    divRow.appendChild(codeDiv);

    var nameDiv = document.createElement('div');
    nameDiv.innerHTML = jsonData[i].products[j].name;
    nameDiv.setAttribute('class', 'admin-product-name');
    divRow.appendChild(nameDiv);

    var addressDiv = document.createElement('div');
    addressDiv.innerHTML = jsonData[i].products[j].address;
    addressDiv.setAttribute('class', 'div_class_admin');
    divRow.appendChild(addressDiv);

    var manufacturerDiv = document.createElement('div');
    manufacturerDiv.innerHTML = jsonData[i].products[j].manufacturer;
    manufacturerDiv.setAttribute('class', 'div_class_admin');
    divRow.appendChild(manufacturerDiv);

    var priceDiv = document.createElement('div');
    priceDiv.innerHTML = jsonData[i].products[j].price + ' Ft';
    priceDiv.setAttribute('class', 'div_class_admin');
    divRow.appendChild(priceDiv);

    var statusDiv = document.createElement('div');
    statusDiv.innerHTML = jsonData[i].products[j].productStatus;
    statusDiv.setAttribute('class', 'admin-product-status status-div');
    divRow.appendChild(statusDiv);

    var categoryDiv = document.createElement('div');
    categoryDiv.innerHTML = jsonData[i].categoryName;
    categoryDiv.setAttribute('class', 'div_class_admin admin-category');
    divRow.appendChild(categoryDiv);

    var buttonsDiv = document.createElement('div');
    buttonsDiv.setAttribute('class', 'div_class_admin admin-product-div')
    buttonsDiv.setAttribute('id', 'buttons-div')

    var deleteBtn = document.createElement('img');
    deleteBtn.setAttribute('src','/img/delete-button.png')
    deleteBtn.setAttribute('id', jsonData[i].products[j].id)
    deleteBtn.addEventListener('click', deleteItem);
    deleteBtn.setAttribute('class', 'button');

    var editBtn = document.createElement('img');
    editBtn.setAttribute('src', '/img/edit-button.png');
    editBtn.setAttribute('id', jsonData[i].products[j].id)
    editBtn.addEventListener('click', (e) => {modify = true; fetchCategories(e)});
    editBtn.setAttribute('class', 'button');

    var saveBtn = document.createElement('img');
    saveBtn.addEventListener('click', saveUpdatedItem);
    saveBtn.setAttribute('id', jsonData[i].products[j].id);
    saveBtn.setAttribute('src', '/img/save-button.png');
    var attribute = 'button-disabled button save-button' + jsonData[i].products[j].id;
    saveBtn.setAttribute('class', attribute);

    divRow.appendChild(deleteBtn);
    divRow.appendChild(editBtn);
    divRow.appendChild(saveBtn);

    divMain.appendChild(divRow);
  }
  var clearerDiv = document.createElement('div');
  clearerDiv.setAttribute('class', 'clearer');
  divMain.appendChild(clearerDiv);
    }
}

function deleteItem() {
  var id = this.id;

  if (!confirm('Are you sure to delete?')) {
    return;
  }

  fetch('/api/product/' + id, {
    method: 'DELETE'
  })
    .then(function (response) {
        return response.json();
        })
    .then(function(jsonData){
        if (jsonData.response == 'SUCCESS'){
            fetchProducts();
            document.getElementById('message-div').innerHTML = jsonData.message;
            document.getElementById('message-div').setAttribute('class', 'alert alert-success');
        }else{
            document.getElementById('message-div').innerHTML = 'This product is already deleted.';
            document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
        }
    });
    return false;
}

function editItem(jsonData, id){
    var attribute = '.save-button' + id;
    var saveBtn = document.querySelector(attribute);
    var newClassName = 'save-button' + id;
    var newAttribute = 'button-enabled button ' + newClassName;

    saveBtn.setAttribute('class', newAttribute);

    var row = document.getElementById(id);
    var c = row.childNodes;

    var select = document.createElement('select');
    select.setAttribute('class', 'select-element-category');
    c[6].appendChild(select);

    for (var i = 0; i < c.length; i++){
        if (i == 5){
            c[i].innerHTML = `<select class="select-element">
                <option value="ACTIVE">ACTIVE</option>
                <option value="DELETED">DELETED</option>
            </select>`
        }
        if (i == 6){
        c[i].innerHTML = "";
        for (var j = 0; j < jsonData.length; j++){
            var option = document.createElement('option');
            option.innerHTML = jsonData[j].categoryName;
            select.appendChild(option)
        }
        c[6].appendChild(select);
        }
        else {
        c[i].setAttribute('contenteditable', 'true');
                }
        }
}

function saveUpdatedItem(){
      var row = document.getElementById(this.id);
      var childenOfRow = row.children;
      var id = this.id;

      var code = childenOfRow[0].innerHTML;
      var name = childenOfRow[1].innerHTML;
      var address = childenOfRow[2].innerHTML;
      var manufacturer = childenOfRow[3].innerHTML;
      var price = childenOfRow[4].innerHTML.split(" ");

      var selectElement = document.querySelector('.select-element');
      var value = selectElement.options[selectElement.selectedIndex].value;

      var selectElementCategory = document.querySelector('.select-element-category');
      var valueCategory = selectElementCategory.options[selectElementCategory.selectedIndex].value;

      price = price[0];

      var request = {
        'categoryName' : valueCategory,
        'products': [{
                'code': code,
                'name': name,
                'address': address,
                'manufacturer': manufacturer,
                'price': price,
                'productStatus': value
        }]
      };
      fetch('/api/product/' + id, {
            method: 'POST',
            body: JSON.stringify(request),
            headers: {
                'Content-type': 'application/json'
            }
        })
        .then(function (response) {
            return response.json();
            })
        .then(function(jsonData){
            if (jsonData.response == 'SUCCESS'){
            fetchProducts();
            document.getElementById('message-div').innerHTML = 'Updated!';
            document.getElementById('message-div').setAttribute('class', 'alert alert-success')

                        var row = document.getElementById(id);
                            var c = row.childNodes;
                            for (var i = 0; i < c.length; i++){
                                if (i == 5){
                                    c[i].innerHTML = `<div>$value</div>`
                                }
                                else {
                                c[i].setAttribute('contenteditable', 'false');
                                }
                                }

            var classAttribute = '.save-button' + id;
            var newClassName = 'save-button' + id;
            var newAttribute = 'button-disabled button ' + newClassName;
            document.querySelector(classAttribute).setAttribute('class', newAttribute);
            } else{
            fetchProducts();
            document.getElementById('message-div').innerHTML = jsonData.message;
            document.getElementById('message-div').setAttribute('class', 'alert alert-danger')

            var row = document.getElementById(id);
                var c = row.childNodes;
                for (var i = 0; i < c.length; i++){
                    if (i == 5){
                        c[i].innerHTML = `<div>$value</div>`
                    }
                    else {
                    c[i].setAttribute('contenteditable', 'false');
                    }
                    }
            var classAttribute = '.save-button' + id;
            var newClassName = 'save-button' + id;
            var newAttribute = 'button-disabled button ' + newClassName;
            document.querySelector(classAttribute).setAttribute('class', newAttribute);
                }
            })
        return false;
}


function addNewProduct() {
  var codeInput = document.getElementById('code').value;
  var nameInput = document.getElementById('name').value;
  var manufacturerInput = document.getElementById('manufacturer').value;
  var priceInput = document.getElementById('price').value;
  var categoryInput = document.getElementById('select-category');

  var valueSelectCategory = categoryInput.options[categoryInput.selectedIndex].value;

  var request = {
    'categoryName': valueSelectCategory,
    'products': [{
        'code': codeInput,
        'name': nameInput,
        'manufacturer': manufacturerInput,
        'price': priceInput
    }]
  };

  fetch('/api/products', {
    method: 'POST',
    body: JSON.stringify(request),
    headers: {
      'Content-type': 'application/json'
    }
  })
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      if (jsonData.response == 'SUCCESS') {
        document.getElementById('code').value = '';
        document.getElementById('name').value = '';
        document.getElementById('manufacturer').value = '';
        document.getElementById('price').value = '';
        fetchProducts();
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
    });
  return false;
}

var newProductButton = document.getElementById('new-product-btn');
newProductButton.onclick = function () {
  modify = false;
  fetchCategories();
};

function showInputFields(jsonData) {

  var select = document.getElementById('select-category');

  for (var i = 0; i < jsonData.length; i++){
        var option = document.createElement('option');
        option.innerHTML = jsonData[i].categoryName;
        select.appendChild(option);
  }


  var mainDiv = document.querySelector('#main_div_adminproducts');
  var formInput = document.querySelector('#form-input');
  if (formInput.getAttribute('class') == 'disabled'){
  formInput.setAttribute('class', 'enabled');
  mainDiv.setAttribute('class', 'main_div_adminproducts_modified')
  } else {
  formInput.setAttribute('class', 'disabled')
  mainDiv.setAttribute('class', 'main_div_adminproducts')
  }
}

window.onscroll = function() {scrollFunction()};

function scrollFunction() {
  if (document.body.scrollTop > 250 || document.documentElement.scrollTop > 250) {
    document.getElementById("myBtn").style.display = "block";
  } else {
    document.getElementById("myBtn").style.display = "none";
  }
}


function topFunction() {
  document.documentElement.scrollTop = 0;
}
