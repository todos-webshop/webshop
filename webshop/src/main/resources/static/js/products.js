var allProducts;
var fetchedCategoryNames;

fetchCategoryNames();

setTimeout(fetchProducts, 1000);

fetchRecommendations();


function fetchProducts() {
  var url = "/api/products";
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      allProducts = jsonData;
      showDivs(jsonData);
      addFilterButtons(fetchedCategoryNames);
    });
}


function fetchCategory(categoryName) {
  var categoryUrl = categoryName.replace(/ /g, '_');

  var url = "/api/category/" + categoryUrl;
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      var allProductsDiv = document.querySelector('.all-products-div');
      allProductsDiv.innerHTML = '';
      showCategoryDivs(jsonData);
    });
}

function fetchCategoryNames() {
  var url = "/api/categories";
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      fetchedCategoryNames = jsonData;
    });
}



function showCategoryDivs(category) {
  var allProductsDiv = document.querySelector('.all-products-div');
  allProductsDiv.innerHTML = "";
  var categoryArray = [];
  categoryArray.push(category);
  showDivs(categoryArray);
}


function showDivs(jsonData) {
  var mainDiv = document.getElementById('main_div');


  var allProductsDiv = document.querySelector('.all-products-div');
  allProductsDiv.innerHTML = '';

  for (var i = 0; i < jsonData.length; i++) {

    var divCategory = document.createElement("div");
    divCategory.setAttribute('class', 'category-div');
    divCategory.setAttribute('id', jsonData[i].categoryName);
    allProductsDiv.appendChild(divCategory);

    var divCategoryName = document.createElement("div");
    divCategoryName.innerHTML = '–––––––– ' + jsonData[i].categoryName + ' ––––––––';
    divCategoryName.setAttribute('class', 'category-name-div anchor');
    divCategoryName.setAttribute('id', jsonData[i].categoryName + ' Name');
    divCategory.appendChild(divCategoryName);

    for (var j = 0; j < jsonData[i].products.length; j++) {
      if (jsonData[i].products[j].productStatus === "ACTIVE") {
        var divRow = document.createElement("div");
        divRow.setAttribute('class', 'index-product-div')
        divCategory.appendChild(divRow);

        var imgDiv = document.createElement('img');
        imgDiv.setAttribute('src', '/img/products/' + jsonData[i].products[j].address + '.jpg');
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
        priceDiv.innerHTML = jsonData[i].products[j].price + " Ft";
        priceDiv.setAttribute("onclick", `window.location.href="product.html?address=${jsonData[i].products[j].address}"`);
        divRow.appendChild(priceDiv);
      }
    }
    var clearerDiv = document.createElement('div');
    clearerDiv.setAttribute('class', 'clearer');
    divCategory.appendChild(clearerDiv);
  }

       var clearerDiv = document.createElement('div');
       clearerDiv.setAttribute('class', 'clearer');
       mainDiv.appendChild(clearerDiv);

}


function addFilterButtons(jsonData) {

  var select = document.createElement("select");
  select.setAttribute('class', 'select-category');

  for (var i = 0; i < jsonData.length; i++) {

    var option = document.createElement('option');
    option.value = jsonData[i].categoryName;
    option.innerHTML = jsonData[i].categoryName;
    select.appendChild(option);
  }

select.onchange = function(){fetchCategory(document.querySelector(".select-category").value)};

  var selectAllButton = document.createElement('button');
  selectAllButton.innerHTML = 'Show All';
  selectAllButton.setAttribute('class', 'select-all-button');
  selectAllButton.addEventListener('click', function () {
    showDivs(allProducts)
  });

var mainDiv = document.getElementById('main_div');
mainDiv.prepend(selectAllButton);
mainDiv.prepend(select);
}


window.onscroll = function () {
  scrollFunction()
};

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


function showRecommendations(jsonData){
var divMain = document.getElementById("main_div");

var divRec = document.createElement('div');
divRec.setAttribute('id',"recommends");
divMain.appendChild(divRec);

for(var i = 0; i < jsonData.length; i++){

var divProd = document.createElement('div');
divProd.setAttribute('class', 'recommend-product-div');

var nameDiv = document.createElement('div');
nameDiv.setAttribute('class', 'name-div-reco');
nameDiv.innerHTML = jsonData[i].name;
divProd.appendChild(nameDiv);

var imgDiv = document.createElement('img');
imgDiv.setAttribute('src', '/img/products/' + jsonData[i].address + '.jpg');
imgDiv.setAttribute('alt', '');
imgDiv.setAttribute('class', 'products_img_reco');

var variant = jsonData[i].address;
divProd.setAttribute("onclick", `window.location.href="product.html?address=${jsonData[i].address}"`);
divProd.appendChild(imgDiv);

divRec.appendChild(divProd);
    }
}



function fetchRecommendations(){
 var url ="/api/product/recommend";
        fetch(url)
            .then(function(response) {
                return response.json();
                })
            .then(function(jsonData) {
                showRecommendations(jsonData);
            });
}
