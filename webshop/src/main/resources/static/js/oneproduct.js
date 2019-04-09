var actProduct;
var actRates;
fetchProduct();


function fetchProduct() {
  var address = (new URL(document.location)).searchParams.get("address");
  var url = "api/product/" + address;
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      if (jsonData.response == 'FAILED') {
        showProductNotFound(jsonData);
      } else {
        actProduct = jsonData;
        showTable(jsonData);
      }
    })
  return false;
}

function fetchRates() {
  var url = "/api/rating/list/" + actProduct["products"][0]["id"];
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      actRates = jsonData;
      showRates();
      fetchAvg();
      showRatesForUserIfExists();
    });
  return false;
}

function showRatesForUserIfExists() {
  var url = "/api/rating/" + actProduct["products"][0]["id"]
  fetch(url)
    .then(response => response.json())
    .then(jsonData => fillRate(jsonData))
}

function fillRate(jsonData) {
  createRatingDiv();
  var keys = Object.keys(jsonData);
  if (jsonData[keys[0]].response === "SUCCESS") {
    var stars = jsonData[keys[1]].stars;
    var message = jsonData[keys[1]].message;
    document.querySelector("#formId #star-" + stars).setAttribute("checked", "true");
    document.getElementById("message_text").value = message;
  }
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
  productImg.setAttribute('src', '/img/products/' + jsonData.products[0].address + '.jpg')
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

  var categoryDiv2 = document.createElement('div');
  categoryDiv2.innerText = jsonData.categoryName;
  categoryDiv2.setAttribute('class', 'category');
  tdRight.appendChild(categoryDiv2);

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

  document.querySelector('#purchase').addEventListener('click', function () {
    addToBasket(jsonData);
  });

  createRatingDiv(jsonData);

  fetchRates(jsonData);
}

function sendRate() {
  var numOfStars = 0;
  var stars = document.getElementById('formId').getElementsByTagName('input');
  for (let i = 0; i < stars.length; i++) {
    if (stars[i]["checked"]) {
      numOfStars = 5 - (i);
      break;
    }
  }
  if (numOfStars > 0) {
    var message = document.getElementById('message_text').value;
    var request = {
      'id': actProduct["id"],
      'stars': numOfStars,
      'message': message,
      'date': null,
      'user': null,
      'product': actProduct["products"][0]
    };

    fetch('/api/rating/userrating/' + actProduct["products"][0]["id"], {
        method: 'POST',
        body: JSON.stringify(request),
        headers: {
          'Content-type': 'application/json'
        }
      })
      .then(function (response) {
        return response.json();
      })
      .then(function (jsonData) {
        if (jsonData.response === "SUCCESS") {
          document.getElementById('message-div').setAttribute('class', 'alert alert-success');
          fetchRates();
        } else {
          document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
        }
        document.getElementById('message-div').innerHTML = jsonData.message;
      })
    document.getElementById('message_text').value = "";
  } else {
    document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
    document.getElementById('message-div').innerHTML = "Please select from the stars before rate!";
  }
}

function fetchAvg() {
  var url = "/api/rating/avg/" + actProduct["products"][0]["id"];
  fetch(url)
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      displayAvg(jsonData);
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
    })
    .then(function () {
      fetchRates();
    });
}

function goBack() {
  window.history.back();
}

function showProductNotFound() {
  window.location.href = 'http://localhost:8080/error.html';
}

function displayAvg(jsonData) {
  document.getElementById('avg_product').innerHTML = "Average rating: " + jsonData;
}

function showRates() {
  var div = document.getElementById('rating-div');
  div.innerHTML = "";
  for (var i = 0; i < actRates.length; i++) {

    var reviewDiv = document.createElement("div");
    reviewDiv.setAttribute('class', 'review-div')

    var numberOfRate = document.createElement("div");
    numberOfRate.className = "stars";

    var starForm = document.createElement("form");
    starForm.setAttribute("action", "");

    for (var j = 5; j > 0; j--) {
      if (j != actRates[i]["stars"]) {
        starForm.innerHTML += `
        <input class="starView starView-${j}" id="star-${j}" type="radio" name="star" disabled="disabled"/>
        <label class="starView starView-${j}" for="star-${j}"></label>`;
      } else {
        starForm.innerHTML += `
        <input class="starView starView-${j}" id="star-${j}" type="radio" name="star" disabled="disabled" checked="true"/>
        <label class="starView starView-${j}" for="star-${j}"></label>`;
      }
    }

    numberOfRate.appendChild(starForm);
    reviewDiv.appendChild(numberOfRate);

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

function createRatingDiv(jsonData) {
  var tdRight = document.querySelector('.td-right');

  var collectorChecker = document.getElementById('collector');
  if (tdRight.contains(collectorChecker)) {
    tdRight.removeChild(collectorChecker);
  }

  var collector = document.createElement('div');
  collector.id = 'collector';
  tdRight.appendChild(collector);

  var starDiv = document.createElement('div');
  starDiv.setAttribute('class', 'rating-div-main');
  starDiv.setAttribute('id', 'star');
  collector.appendChild(starDiv);

  var divForStars = document.createElement("div");
  divForStars.className = "stars";

  var starForm = document.createElement("form");
  starForm.id = "formId";
  starForm.setAttribute("action", "");

  for (var i = 5; i > 0; i--) {
    starForm.innerHTML += `<input class="star-rating star star-${i}" id="star-${i}" type="radio" name="star"/>
      <label class="star star-${i}" for="star-${i}"></label>`;
  }

  divForStars.appendChild(starForm);
  collector.appendChild(divForStars);

  var ratingMessage = document.createElement('div');
  ratingMessage.setAttribute('class', 'rating-message');
  ratingMessage.setAttribute('id', 'message');
  collector.appendChild(ratingMessage);

  var textArea = document.createElement('textarea');
  textArea.setAttribute('id', 'message_text');
  textArea.setAttribute('rows', '2');
  textArea.setAttribute('cols', '40');
  ratingMessage.appendChild(textArea);

  var ratingButtonDiv = document.createElement('div');
  ratingButtonDiv.setAttribute('id', 'button');
  collector.appendChild(ratingButtonDiv);

  var ratingButton = document.createElement('button');
  ratingButton.setAttribute('class', 'rating-button');
  ratingButton.setAttribute('id', 'rate_button');
  ratingButton.innerHTML = 'Rate';
  ratingButtonDiv.appendChild(ratingButton);

  var deleteButton = document.createElement('button');
  deleteButton.setAttribute('class', 'delete-rate');
  deleteButton.setAttribute('id', 'delete-rate');
  deleteButton.innerHTML = 'Delete my rate';
  ratingButtonDiv.appendChild(deleteButton);

  document.querySelector('#rate_button').addEventListener('click', function () {
    sendRate(jsonData);
  });

  document.querySelector('#delete-rate').addEventListener('click', function () {
    deleteRate();
  });
}

function deleteRate() {
  fetchRates();
  fetch('/api/rating/delete/' + actProduct["products"][0]["id"], {
      method: "DELETE"
    })
    .then(function (response) {
      return response.json();
    }).then(function (jsonData) {
      if (jsonData.response === 'SUCCESS') {
        if (!confirm("Are you sure to delete your review?")) {
          return;
        }
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
      document.getElementById('message-div').innerHTML = jsonData.message;
      fetchRates();
    });
}