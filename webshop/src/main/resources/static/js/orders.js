function orderItems(){
    if (!confirm('Are you sure you want to place your order?')) {
      return;
    }

  var request = {

  }

  fetch('/myorders', {
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
        fetchBasket();
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
      // console.log(jsonData);
      showBasket(jsonData);
    });
}