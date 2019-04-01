
   fetchProducts();




function fetchProducts() {
        var url ="/api/products";
        fetch(url)
            .then(function(response) {
                return response.json();
                })
            .then(function(jsonData) {
                console.log(jsonData)
                showDivs(jsonData);

            });}

//function addFilterButtons(jsonData){
//for (var i = 0; i < jsonData.length; i++) {
//
//}
//}
   function showDivs(jsonData) {
       divMain = document.getElementById("main_div");
       for (var i = 0; i < jsonData.length; i++) {

       var divCategoryName =document.createElement("div");
       divCategoryName.innerHTML = jsonData[i]["categoryName"];
       divCategoryName.setAttribute('class', 'category-name-div');
       divCategoryName.setAttribute('id', jsonData[i]["categoryName"]+' Name');


       var divCategory =document.createElement("div");
        divCategory.setAttribute('class', 'category-div');
        divCategory.setAttribute('id', jsonData[i]["categoryName"]);

       for (var j = 0; j < jsonData[i].products.length; j++){
       if (jsonData[i].products[j].productStatus === "ACTIVE"){
       var divRow = document.createElement("div");
       divRow.setAttribute('class', 'product-div')

       var imgDiv = document.createElement('img');
       imgDiv.setAttribute('src', '/img/coming_soon.jpg');
       imgDiv.setAttribute('alt', '');
       imgDiv.setAttribute('class', 'products_img');
       imgDiv.setAttribute("onclick", `window.location.href="product.html?address=${jsonData[i].products[j].address}"`);


       divRow.appendChild(imgDiv);

       var nameDiv = document.createElement("div");

       nameDiv.innerHTML = jsonData[i].products[j].name;
       nameDiv.setAttribute('class', 'div_class_name');

       nameDiv.setAttribute("onclick", `window.location.href="product.html?address=${jsonData[i].products[j].address}"`);
       divRow.appendChild(nameDiv);


       var priceDiv = document.createElement("div");
       priceDiv.innerHTML = jsonData[i].products[j].price+ " Ft";

       priceDiv.setAttribute("onclick", `window.location.href="product.html?address=${jsonData[i].products[j].address}"`);
       divRow.appendChild(priceDiv);

            // var imgDiv = document.createElement("div");
            //                         imgDiv.innerHTML = "<img alt="+jsonData[i].address+" src=img\\products\\"+jsonData[i].address+".png>";
            //                        imgDiv.classList.add('div_class');
            //                        imgDiv.setAttribute("onclick", `window.location.href="product.html?address=${jsonData[i].address}"`);
            //                         divRow.appendChild(imgDiv);

           divCategory.appendChild(divRow);


      }
       divMain.appendChild(divCategoryName);
      divMain.appendChild(divCategory);
   }
}
        var clearerDiv = document.createElement('div');
        clearerDiv.setAttribute('class', 'clearer');
        divMain.appendChild(clearerDiv);
}

window.onscroll = function() {scrollFunction()};

function scrollFunction() {
  if (document.body.scrollTop > 250 || document.documentElement.scrollTop > 250) {
    document.getElementById("myBtn").style.display = "block";
  } else {
    document.getElementById("myBtn").style.display = "none";
  }
}


function topFunction() {
  document.documentElement.scrollTop = 0;
}

//function showCategories(jsonData){
//
//for (var i=0; i<jsonData.length;i++){
//
//jsonData[i].["categoryName"];
//}
//}
