fetchProduct();



function fetchProduct() {
var address = (new URL(document.location)).searchParams.get("address");
        var url ="api/products/" + address;
        fetch(url)
            .then(function(response) {
                return response.json();
                })
            .then(function(jsonData) {

                showTable(jsonData);
            });}

function showTable(jsonData) {
    var table = document.getElementById("product-table");
    var tr = document.createElement("tr");
 
    var codeTd = document.createElement("td");
              codeTd.innerHTML = jsonData.code;
              tr.appendChild(codeTd);

    var nameTd = document.createElement("td");
             nameTd.innerHTML = jsonData.name;
             tr.appendChild(nameTd);

    var manufacturerTd = document.createElement("td");
                     manufacturerTd.innerHTML = jsonData.manufacturer;
                     tr.appendChild(manufacturerTd);

    var priceTd = document.createElement("td");
                     priceTd.innerHTML = jsonData.price;
                     tr.appendChild(priceTd);

    table.appendChild(tr);
 
}

function addToBasket(){
    console.log("baszki");
}