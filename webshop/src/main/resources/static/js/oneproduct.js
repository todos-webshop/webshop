window.onload = function() {
console.log("csoki");
  fetchProduct();

}

function fetchProduct() {
console.log("csoki2");
var address = (new URL(document.location)).searchParams.get("address");
console.log(address);
        var url ="/product/" + address;
        fetch(url)
            .then(function(response) {
                return response.json();
                })
            .then(function(jsonData) {

                showTable(jsonData);
            });}

function showTable(jsonData) {
    table = document.getElementById("product-table");
    var tr = document.createElement("tr");

       var codeTd = document.createElement("td");
                 codeTd.innerHTML = jsonData.code;
                 tr.appendChild(codeTd);

         var nameTd = document.createElement("td");
                nameTd.innerHTML = jsonData.name;
                tr.appendChild(nameTd);

        var addressTd = document.createElement("td");
                        addressTd.innerHTML = jsonData.address;
                        tr.appendChild(addressTd);

        var manufacturerTd = document.createElement("td");
                        manufacturerTd.innerHTML = jsonData.manufacturer;
                        tr.appendChild(manufacturerTd);

        var priceTd = document.createElement("td");
                        priceTd.innerHTML = jsonData.price;
                        tr.appendChild(priceTd);

        table.appendChild(tr);

}