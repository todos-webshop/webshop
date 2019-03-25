window.onload = function () {
  fetchOrderItems();
};


function fetchOrderItems() {
  var id = (new URL(document.location)).searchParams.get("id");
  var url = '/orders/' + id;
  console.log(url);
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      console.log(jsonData);
      // showTable(jsonData);
    });
}