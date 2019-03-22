
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


            var nameDiv = document.createElement("div");

                   nameDiv.innerHTML = jsonData[i].name;
                    nameDiv.setAttribute('class', 'div_class');

                     nameDiv.setAttribute("onclick", `window.location.href="product.html?address=${jsonData[i].address}"`);
                   divRow.appendChild(nameDiv);


           var priceDiv = document.createElement("div");
                           priceDiv.innerHTML = jsonData[i].price+ " Ft";
                            priceDiv.setAttribute('class', 'div_class');

                            priceDiv.setAttribute("onclick", `window.location.href="product.html?address=${jsonData[i].address}"`);
                           divRow.appendChild(priceDiv);

            // var imgDiv = document.createElement("div");
            //                         imgDiv.innerHTML = "<img alt="+jsonData[i].address+" src=img\\products\\"+jsonData[i].address+".png>";
            //                        imgDiv.classList.add('div_class');
            //                        imgDiv.setAttribute("onclick", `window.location.href="product.html?address=${jsonData[i].address}"`);
            //                         divRow.appendChild(imgDiv);

           divMain.appendChild(divRow);

   }
}