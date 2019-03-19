window.onload = function() {
   fetchProducts();
   }


function fetchProducts() {
        var url ="/products";
        fetch(url)
            .then(function(response) {
                return response.json();
                })
            .then(function(jsonData) {

                console.log(jsonData);
                showTable(jsonData);
            });}

function showTable(jsonData) {
    table = document.getElementById("product-table");
    table.innerHTML = "";

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