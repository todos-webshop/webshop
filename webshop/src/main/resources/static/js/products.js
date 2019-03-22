window.onload = function() {
   fetchProducts();
   }


function fetchProducts() {
        var url ="/api/products";
        fetch(url)
            .then(function(response) {
                return response.json();
                })
            .then(function(jsonData) {

                console.log(jsonData);
                showDivs(jsonData);
            });}


   function showDivs(jsonData) {
       divMain = document.getElementById("main_div");
       for (var i = 0; i < jsonData.length; i++) {
       var divRow = document.createElement("div");

       var codeDiv = document.createElement("div");
       codeDiv.innerHTML = jsonData[i].code;
       codeDiv.setAttribute('class', 'div_class');
       divRow.appendChild(codeDiv);

       var nameDiv = document.createElement("div");
       nameDiv.innerHTML = jsonData[i].name;
       nameDiv.setAttribute('class', 'div_class');
       divRow.appendChild(nameDiv);

       var addressDiv = document.createElement("div");
       addressDiv.innerHTML = jsonData[i].address;
       addressDiv.setAttribute('class', 'div_class');
       divRow.appendChild(addressDiv);

       var manufacturerDiv = document.createElement("div");
       manufacturerDiv.innerHTML = jsonData[i].manufacturer;
       manufacturerDiv.setAttribute('class', 'div_class');
       divRow.appendChild(manufacturerDiv);

       var priceDiv = document.createElement("div");
       priceDiv.innerHTML = jsonData[i].price+ " Ft";
       priceDiv.setAttribute('class', 'div_class');
       divRow.appendChild(priceDiv);

       var statusDiv = document.createElement('div');
       statusDiv.innerHTML = jsonData[i].productStatus;
       statusDiv.setAttribute('class', 'div_class');
       divRow.appendChild(statusDiv);

       divRow.appendChild(imgDiv);

       divMain.appendChild(divRow);

   }



}