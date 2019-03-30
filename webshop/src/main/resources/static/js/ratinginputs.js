fetchRating();

 function fetchRating() {

               showRatings();


               }


function showRatings(){
//var stars =document.querySelector('#stars');
//
//
//select = document.createElement("select");
//star.setAttribute('src','/img/Grey_Star.gif');
//star.setAttribute('width','50');
//star.setAttribute('id','star'+i);
//
//stars.appendChild(star);


//document.querySelector('#purchase').addEventListener('click', function () {
//            addToBasket(jsonData);
//        });
}


function sendRate(jsonData) {
  var stars = document.getElementById('select_star').value;
  console.log(stars);
  var code = jsonData.code;
  var request = {
    'productCode': code,
    'productPieces': quantity
  };
//   console.log(request);
  fetch('/basket', {
    method: 'POST',
    body: JSON.stringify(request),
    headers: {
      'Content-type': 'application/json'
    }
  })
    .then(function (response) {
            //  console.log(response);
             return response.json();
            }).then(function (jsonData) {
                // console.log(jsonData);
      if (jsonData.response === "SUCCESS") {
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
      document.getElementById('message-div').innerHTML = jsonData.message;
    });
}
