var allProducts;
   fetchProducts();




function fetchProducts() {
        var url ="/api/products";
        fetch(url)
            .then(function(response) {
                return response.json();
                })
            .then(function(jsonData) {
                console.log(jsonData);
                allProducts =jsonData;
                showDivs(jsonData);

            });}

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
//@Fanni
//ez a függvény készíti el a category select-et a gombokkal együtt
//szerintem a fetchProducts-ba kellene meghívni a függvényt jsonData változóval
function addFilterButtons(jsonData){

var select = document.createElement("select");
select.setAttribute('id','select-category');
for (var i = 0; i < jsonData.length; i++) {
var option = document.createElement('option');
option.value=jsonData[i]["categoryName"];
option.innerHtml = jsonData[i]["categoryName"];
select.appendChild(option);
}

var selectButton = document.createElement('button');
selectButton.setAttribute('id','select-button');

var selectAllButton = document.createElement('button');
selectAllButton.setAttribute('id','select-all-button');

//Neked ide kell beírni, mire "akasztjuk fel" a select-category id-jú selectet és a gombokat

//itt adom hozzá a függvényeket a gombokhoz
document.querySelector('#select-button').addEventListener('click', function () {
                        showCategory(document.getElementById("select-category").value);

                    });
document.querySelector('#select-all-button').addEventListener('click', function () {
                        showAllCategoryCategory();

                    });
}

function showAllCategory(){
for (var i=0; i < allProducts.length;i++){
var actCategory =document.getElementById(jsonData[i]["categoryName"]);
var actCategoryName =document.getElementById(jsonData[i]["categoryName"]+" Name" );
actCategory.setAttribute('visibility','visible');
actCategoryName.setAttribute('visibility','visible');
}

}


function showCategory(Category){

for (var i=0; i<jsonData.length;i++){
var actCategory =document.getElementById(jsonData[i]["categoryName"]);
var actCategoryName =document.getElementById(jsonData[i]["categoryName"]+" Name" );
if (jsonData[i]["categoryName"]===Category){
actCategory.setAttribute('visibility','visible');
actCategoryName.setAttribute('visibility','visible');
} else {
actCategory.setAttribute('visibility','hidden');
actCategoryName.setAttribute('visibility','hidden');

}
}
}
