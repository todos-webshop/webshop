window.onload = function() {
console.log("csoki");
  fetchProduct();

}

function fetchProduct() {
console.log("csoki2");
var address = (new URL(document.location)).searchParams.get("address");
console.log(address);
        var url ="product/" + address;
        fetch(url)
            .then(function(response) {
                return response.json();
                })
            .then(function(jsonData) {

                showDivs(jsonData);
            });}

function showDivs(jsonData) {
    divMain = document.getElementById("main_div");
    var divRow = document.createElement("div");

       var codeDiv = document.createElement("div");
                 codeDiv.innerHTML = jsonData.code;
                 codeDiv.setAttribute('class', 'div_class');
                 divRow.appendChild(codeDiv);

         var nameDiv = document.createElement("div");
                nameDiv.innerHTML = jsonData.name;
                 nameDiv.setAttribute('class', 'div_class');
                divRow.appendChild(nameDiv);

        var addressDiv = document.createElement("div");
                        addressDiv.innerHTML = jsonData.address;
                         addressDiv.setAttribute('class', 'div_class');
                        divRow.appendChild(addressDiv);

        var manufacturerDiv = document.createElement("div");
                        manufacturerDiv.innerHTML = jsonData.manufacturer;
                         manufacturerDiv.setAttribute('class', 'div_class');
                        divRow.appendChild(manufacturerDiv);

        var priceDiv = document.createElement("div");
                        priceDiv.innerHTML = jsonData.price+ " Ft";
                         priceDiv.setAttribute('class', 'div_class');
                        divRow.appendChild(priceDiv);

         var imgDiv = document.createElement("div");
                                 imgDiv.innerHTML = "<img alt="+jsonData.address+" src=img\\products\\"+jsonData.address+".png>";

                                imgDiv.classList.add('div_class');
                                 //imgDiv.classList.add('div_img');
                                 divRow.appendChild(imgDiv);

        divMain.appendChild(divRow);

}