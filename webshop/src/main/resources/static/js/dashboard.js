
load();

function load() {
  var url = '/dashboard';
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      showStatistics(jsonData);
    });
}

function showStatistics(jsonData) {
  document.getElementById('user').innerHTML = 'We have ' + jsonData.numOfUsers + ' registered users!';
  var numberOfActiveProducts = jsonData.numOfActiveProducts;
  var numberOfAllProducts = jsonData.numOfProducts;
  var numberOfActiveOrders = jsonData.numOfActiveOrders;
  var numberOfAllOrders = jsonData.numOfOrders;
  var options = {responsive: true,
    maintainAspectRatio: false, fontStyle: 'Arial'
  };
  if (numberOfAllProducts !== 0) {
    var ct1 = document.getElementById('chart1');
    var data = {
      labels: ['All Products', 'Active Products', 'Deleted Products'],
      datasets: [{
        data: [numberOfAllProducts, numberOfActiveProducts, numberOfAllProducts - numberOfActiveProducts],
        backgroundColor: [
       'rgb(172, 196, 177)',
          'rgba(212,183, 145,1)','rgba(169,179, 188,1)'
        ],
        borderColor: [
          '',
          '',''
        ],
        borderWidth: 0.6

      }]
    };


    var myDoughnutChart = new Chart(ct1, {
      type: 'pie',
      data: data,
      options: options
    });
  } else {
    document.getElementById('message-product').innerHTML = 'There are no products.';
  }

  if (numberOfAllOrders !== 0) {
    var ct2 = document.getElementById('chart2');

    var data2 = {
      labels: ['All Orders', 'Pending Orders', 'Delivered Orders'],
      datasets: [{
        data: [numberOfAllOrders, numberOfActiveOrders, numberOfAllOrders - numberOfActiveOrders],
        backgroundColor: [
          'rgb(172, 196, 177)',
          'rgba(212, 183, 145,1)','rgba(169,179,188,1)'
        ],
        borderColor: [
          '',
          '',''

        ],
        borderWidth: 0.6
      }]
    };


    var myDoughnutChart = new Chart(ct2, {
      type: 'pie',
      data: data2,
      options: options
    });
  } else {
    document.getElementById('message-order').innerHTML = 'There are no orders.';
  }
}

