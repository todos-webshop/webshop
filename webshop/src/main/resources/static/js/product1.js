window.onload = function() {
console.log("csoki");
  fetchProduct();

}

function fetchProduct() {
console.log("csoki2");
var address = (new URL(document.product)).searchParams.get("address");

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


    for (var i = 0; i < jsonData.length; i++) {
            var tr = document.createElement("tr");

       var codeTd = document.createElement("td");
                 codeTd.innerHTML = jsonData[i].code;
                 tr.appendChild(codeTd);

         var nameTd = document.createElement("td");
                nameTd.innerHTML = jsonData[i].name;
                tr.appendChild(nameTd);

        var addressTd = document.createElement("td");
                        addressTd.innerHTML = jsonData[i].address;
                        tr.appendChild(addressTd);

        var manufacturerTd = document.createElement("td");
                        manufacturerTd.innerHTML = jsonData[i].manufacturer;
                        tr.appendChild(manufacturerTd);

        var priceTd = document.createElement("td");
                        priceTd.innerHTML = jsonData[i].price;
                        tr.appendChild(priceTd);

        table.appendChild(tr);
    }
}