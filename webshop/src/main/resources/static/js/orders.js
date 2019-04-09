
function orderItems() {
  if (!confirm('Are you sure you want to place your order?')) {
    return;
  }

  var shippingAddressField = document.querySelector('#shipping-address');
  var shippingAddressChooserDiv = document.querySelector('#choose-address-div');

  var shippingAddress;
  var url;
  if (shippingAddressField.getAttribute('class') === 'enabled') {
    shippingAddress = shippingAddressField.value;
    url = '/myorders';
  } else if (shippingAddressChooserDiv.getAttribute('class') === 'enabled') {
    var chosenAddress = document.forms.shippingAddressChoserForm.elements.oneFormerAddress.value;
    shippingAddress = chosenAddress;
    url = '/myorders/storedaddresses';
  }

  var request = {
    'shippingAddress': shippingAddress
  };


  fetch(url, {
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
        document.getElementById('shipping-address').value = '';
        document.getElementById('shipping-address').setAttribute('class', 'disabled');
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
    });
  return false;
}

function fetchBasket() {
  var url = '/basket';
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showBasket(jsonData);
    });
}