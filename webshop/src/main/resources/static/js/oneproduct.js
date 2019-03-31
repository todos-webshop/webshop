var actProduct;
var actRate;
var actAvg;
var actRates;
fetchProduct();
setTimeout(fetchRate, 1000);
setTimeout(fetchRates, 1000);
setTimeout(fetchAvg, 1000);




function fetchProduct() {
  var address = (new URL(document.location)).searchParams.get("address");
        var url ="api/product/" + address;
        fetch(url)
            .then(function(response) {
                return response.json();
                })
            .then(function(jsonData) {

            if ( jsonData.response == 'FAILED'){
            showProductNotFound(jsonData);
            } else {
                showTable(jsonData);
                actProduct = jsonData;
            }});
            return false;
}

function fetchRate() {
        var url ="/api/rating/"+actProduct["products"][0]["id"] ;
        fetch(url)
            .then(function(response) {
                return response.json();
                })
            .then(function(jsonData) {

                actRate =jsonData;
            });
            return false;
}

function showTable(jsonData) {
    var table = document.getElementById("product-table");

    var tbody = document.createElement('tbody');
    tbody.setAttribute('class', 'product-body');
    table.appendChild(tbody);

    var trDetail = document.createElement('tr');
    tbody.appendChild(trDetail);

    var tdLeft = document.createElement('td');
    trDetail.appendChild(tdLeft);
    tdLeft.setAttribute('class', 'td-left');

    var link = document.createElement('div');
    link.addEventListener('click', goBack);
    link.innerHTML = 'Back to main menu';
    link.setAttribute('class', 'product-category link');
    tdLeft.appendChild(link);

    var categoryDiv = document.createElement('div');
    categoryDiv.innerText = jsonData.categoryName;
    categoryDiv.setAttribute('class', 'product-category');
    tdLeft.appendChild(categoryDiv);

    var span = document.createElement('span');
    span.innerHTML = ' / ' + jsonData.products[0].name;
    categoryDiv.appendChild(span);

    var productImg = document.createElement('img');
    productImg.setAttribute('class', 'product-img')
    productImg.setAttribute('src', '/img/coming_soon.jpg')
    productImg.setAttribute('alt', '');
    tdLeft.appendChild(productImg);

    var tdRight = document.createElement('td');
    tdRight.setAttribute('class', 'td-right')
    trDetail.appendChild(tdRight);

    var inputField = document.createElement('input');
    inputField.setAttribute('class', 'purchase-quantity');
    inputField.setAttribute('type', 'number');
    inputField.setAttribute('name', 'quantity');
    inputField.setAttribute('id', 'quantity');
    inputField.setAttribute('min', '1');
    inputField.setAttribute('value', '1');
    tdRight.appendChild(inputField);

    var button = document.createElement('button');
    button.setAttribute('class', 'purchase add-to-cart');
    button.setAttribute('id', 'purchase');
    button.innerHTML = 'Add to cart';
    tdRight.appendChild(button);

    var nameDiv = document.createElement('div');
    nameDiv.innerText = jsonData.products[0].name;
    nameDiv.setAttribute('class', 'product-name');
    tdRight.appendChild(nameDiv);

    var avgDiv = document.createElement('div');
    avgDiv.setAttribute('class', 'avg');
    avgDiv.setAttribute('id', 'avg_product');
    tdRight.appendChild(avgDiv);

    var categoryDiv = document.createElement('div');
    categoryDiv.innerText = jsonData.categoryName;
    categoryDiv.setAttribute('class', 'category');
    tdRight.appendChild(categoryDiv);

    var manufacturerDiv = document.createElement('div');
    manufacturerDiv.setAttribute('class', 'product-man');
    manufacturerDiv.innerText = 'by ' + jsonData.products[0].manufacturer;
    tdRight.appendChild(manufacturerDiv);

    var codeDiv = document.createElement('div');
    codeDiv.setAttribute('class', 'product-code');
    codeDiv.innerText = jsonData.products[0].code;
    tdRight.appendChild(codeDiv);

    var priceDiv = document.createElement('div');
    priceDiv.setAttribute('class', 'product-price');
    priceDiv.innerText = jsonData.products[0].price + ' Ft';
    tdRight.appendChild(priceDiv);


    createRatingDiv();

    document.querySelector('#purchase').addEventListener('click', function () {
            addToBasket(jsonData);
        });
    document.querySelector('#rate_button').addEventListener('click', function () {
                           sendRate(jsonData);
                           document.getElementById('message_text').value = "";
                           setTimeout(fetchRates, 100);
                           showRates();
                           fetchAvg();
                           displayAvg();
            });


}

function sendRate(jsonData) {
  var numOfStars = 0;
  var stars = document.getElementById('formId').getElementsByTagName('input');
  for (let i = 0; i < stars.length; i++) {
    if(stars[i]["checked"]){
      numOfStars=5-(i);
      break;
    }
  }
  var message = document.getElementById('message_text').value;
    var request = {
    'id':actProduct["id"],
    'stars': numOfStars,
    'message': message,
    'date':null,
    'user':null,
    'product':actProduct["products"][0]
  };

  fetch('/api/rating/userrating/'+actProduct["products"][0]["id"], {
    method: 'POST',
    body: JSON.stringify(request),
    headers: {
      'Content-type': 'application/json'
    }
  })
    .then(function (response) {
             return response.json();
            }).then(function (jsonData) {

      if (jsonData.response === "SUCCESS") {
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
      document.getElementById('message-div').innerHTML = jsonData.message;
    });

}
function fetchAvg() {

        var url ="/api/rating/avg/"+actProduct["products"][0]["id"] ;
        fetch(url)
            .then(function(response) {
                return response.json();
                })
            .then(function(jsonData) {
                actAvg =jsonData;
                displayAvg();

            });
            return actAvg;

}
function fetchRates() {

        var url ="/api/rating/list/"+actProduct["products"][0]["id"];
        fetch(url)
            .then(function(response) {
                return response.json();
                })
            .then(function(jsonData) {
                actRates =jsonData;
                showRates();
            });
            return false;

}


function addToBasket(jsonData) {
  var quantity = document.getElementById('quantity').value;
  var code = jsonData.products[0].code;
  var request = {
    'productCode': code,
    'productPieces': quantity
  };
  fetch('/basket', {
    method: 'POST',
    body: JSON.stringify(request),
    headers: {
      'Content-type': 'application/json'
    }
  })
    .then(function (response) {
             return response.json();
            }).then(function (jsonData) {
      if (jsonData.response === "SUCCESS") {
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
      document.getElementById('message-div').innerHTML = jsonData.message;
    }
    .then(function(){
      fetchRates();
    }));
}
function goBack() {
  window.history.back();
}

function showProductNotFound(jsonData){
   window.location.href = 'http://localhost:8080/error.html';
}


function displayAvg(){
document.getElementById('avg_product').innerHTML = "Average rating: "+actAvg;
}


function showRates() {

var div = document.getElementById('rating-div');
div.innerHTML = "";
  for (var i=0;i<actRates.length;i++ ){

    var reviewDiv = document.createElement("div");
    reviewDiv.setAttribute('class', 'review-div')

    var numberOfRate = document.createElement("div");
    numberOfRate.className = "stars";

    var starForm = document.createElement("form");
    starForm.setAttribute("action","");

    for (var j = 5; j > 0; j--){
      if(j != actRates[i]["stars"]){
        starForm.innerHTML+= `
        <input class="starView starView-${j}" id="star-${j}" type="radio" name="star" disabled="disabled"/>
        <label class="starView starView-${j}" for="star-${j}"></label>`;
      } else {
        starForm.innerHTML+= `
        <input class="starView starView-${j}" id="star-${j}" type="radio" name="star" disabled="disabled" checked="true"/>
        <label class="starView starView-${j}" for="star-${j}"></label>`;
      }
    }

    numberOfRate.appendChild(starForm);
    reviewDiv.appendChild(numberOfRate);
    // var numberOfRate = document.createElement("div");
    // numberOfRate.setAttribute('class', 'rate-number');
    // numberOfRate.innerHTML = "Rate: " + actRates[i]["stars"];
    // reviewDiv.appendChild(numberOfRate);

    var reviewDate = document.createElement('div');
    reviewDate.setAttribute('class', 'rate-date');
    reviewDate.innerHTML = actRates[i]["date"];
    reviewDiv.appendChild(reviewDate);

    var reviewComment = document.createElement('div');
    reviewComment.setAttribute('class', 'rate-comment');
    reviewComment.innerHTML = actRates[i]["message"];
    reviewDiv.appendChild(reviewComment);

    var reviewName = document.createElement('div');
    reviewName.setAttribute('class', 'rate-name');
    reviewName.innerHTML = actRates[i]["user"]["username"];
    reviewDiv.appendChild(reviewName);

    div.appendChild(reviewDiv);
  }
}

function createRatingDiv(){
    var tdRight = document.querySelector('.td-right');

    var starDiv = document.createElement('div');
    starDiv.setAttribute('class', 'rating-div-main');
    starDiv.setAttribute('id', 'star');
    tdRight.appendChild(starDiv);

    var divForStars = document.createElement("div");
    divForStars.className = "stars";

    var starForm = document.createElement("form");
    starForm.id = "formId";
    starForm.setAttribute("action","");

    for (var i = 5; i>0;i--){
      starForm.innerHTML+= `<input class="star-rating star star-${i}" id="star-${i}" type="radio" name="star"/>
      <label class="star star-${i}" for="star-${i}"></label>`;
    }

    divForStars.appendChild(starForm);
    tdRight.appendChild(divForStars);

    // var selectStar = document.createElement('select');
    // selectStar.setAttribute('id', 'select_star');
    // starDiv.appendChild(selectStar);

    // var option5 = document.createElement('option');
    // option5.setAttribute('value', "5");
    // option5.innerHTML = 5;
    // selectStar.appendChild(option5)

    // var option4 = document.createElement('option');
    // option4.setAttribute('value', "4");
    // option4.innerHTML = 4;
    // selectStar.appendChild(option4)

    // var option3 = document.createElement('option');
    // option3.setAttribute('value', "3");
    // option3.innerHTML = 3;
    // selectStar.appendChild(option3)

    // var option2 = document.createElement('option');
    // option2.setAttribute('value', "2");
    // option2.innerHTML = 2;
    // selectStar.appendChild(option2)

    // var option1 = document.createElement('option');
    // option1.setAttribute('value', "1");
    // option1.innerHTML = 1;
    // selectStar.appendChild(option1);

    var ratingMessage = document.createElement('div');
    ratingMessage.setAttribute('class', 'rating-message');
    ratingMessage.setAttribute('id', 'message');
    tdRight.appendChild(ratingMessage);

    var textArea = document.createElement('textarea');
    textArea.setAttribute('id', 'message_text');
    textArea.setAttribute('rows', '2');
    textArea.setAttribute('cols', '40');
    ratingMessage.appendChild(textArea);

    var ratingButtonDiv = document.createElement('div');
    ratingButtonDiv.setAttribute('id', 'button');
    tdRight.appendChild(ratingButtonDiv);

    var ratingButton = document.createElement('button');
    ratingButton.setAttribute('class', 'rating-button');
    ratingButton.setAttribute('id', 'rate_button');
    ratingButton.innerHTML = 'Rate';
    ratingButtonDiv.appendChild(ratingButton);
}
