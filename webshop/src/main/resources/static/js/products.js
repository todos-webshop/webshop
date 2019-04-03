var allProducts;
var fetchedCategory;
var fetchedCategoryNames;

//var fetchedCategoryNames;
fetchCategoryNames();

setTimeout(fetchProducts, 1000);



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
      fetchedCategory = jsonData;
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
        priceDiv.innerHTML = jsonData[i].products[j].price + " Ft";
        priceDiv.setAttribute("onclick", `window.location.href="product.html?address=${jsonData[i].products[j].address}"`);
        divRow.appendChild(priceDiv);
      }
    }
    var clearerDiv = document.createElement('div');
    clearerDiv.setAttribute('class', 'clearer');
    divCategory.appendChild(clearerDiv);
  }

  var clearerDiv2 = document.createElement('div');
  clearerDiv2.setAttribute('class', 'clearer');
  mainDiv.appendChild(clearerDiv2);
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

  var selectButton = document.createElement('button');
  selectButton.innerHTML = 'Filter';
  selectButton.setAttribute('class', 'select-button');
  selectButton.addEventListener('click', function () {
    fetchCategory(document.querySelector(".select-category").value)
  });

  var selectAllButton = document.createElement('button');
  selectAllButton.innerHTML = 'Show All';
  selectAllButton.setAttribute('class', 'select-all-button');
  selectAllButton.addEventListener('click', function () {
    showDivs(allProducts)
  });

  var mainDiv = document.getElementById('main_div');
  mainDiv.prepend(selectAllButton);
  mainDiv.prepend(selectButton);
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

/*
function showAllCategory(){
for (var i=0; i < allProducts.length;i++){

var actCategory =document.getElementById(allProducts[i].categoryName);
var actCategoryName =document.getElementById(allProducts[i].categoryName + " Name" );
actCategory.setAttribute('class','enabled');
actCategoryName.setAttribute('class','enabled');
    }
}


function showCategory(Category){
for (var i=0; i<allProducts.length;i++){

var actCategory =document.getElementById(allProducts[i].categoryName);
var actCategoryName =document.getElementById(allProducts[i].categoryName + " Name" );

if (allProducts[i].categoryName === Category){
actCategory.setAttribute('class','enabled');
actCategoryName.setAttribute('class','enabled');
} else {
actCategory.setAttribute('class','disabled');
actCategoryName.setAttribute('class','disabled');
        }
    }
}*/