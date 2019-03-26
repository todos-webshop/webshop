window.onload = function() {
   fetchReports();

   function fetchReports() {
           var url ="/reports/reportone";
           fetch(url)
               .then(function(response) {
                   return response.json();
                   })
               .then(function(jsonData) {

                   console.log(jsonData);
                   showTables(jsonData);
               });}

 function showTables(jsonData) {
        tableMain = document.getElementById("report-table");
        for (var i = 0; i < jsonData.length; i++) {
        var oneRow = document.createElement("tr");
        //oneRow.setAttribute('class', 'product-div')


        var yearTd = document.createElement("td");

        yearTd.innerHTML = jsonData[i].;
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
