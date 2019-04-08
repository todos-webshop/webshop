fetchRating();
var starsArray = [];
 function fetchRating() {

               showRatings();


               }


function showRatings(){
var stars =document.querySelector('#stars');

for (var i=1;i<6;i++){
star = document.createElement("img");
star.setAttribute('src','/img/Grey_Star.gif');
star.setAttribute('width','50');
star.setAttribute('id','star'+i);

stars.appendChild(star);
}

star1 = document.getElementById('star1');
star1.addEventListener('mouseover', function () {
                  document.getElementById('star1').setAttribute('src','/img/Gold_Star.gif');
                   document.getElementById('star1').setAttribute('width','50');

                      });
star1.addEventListener('mouseout', function () {
                  document.getElementById('star1').setAttribute('src','/img/Grey_Star.gif');
                   document.getElementById('star1').setAttribute('width','50');

                      });
 star2 = document.getElementById('star2');
 star2.addEventListener('mouseover', function () {
                   document.getElementById('star1').setAttribute('src','/img/Gold_Star.gif');
                    document.getElementById('star1').setAttribute('width','50');
                    document.getElementById('star2').setAttribute('src','/img/Gold_Star.gif');
                                        document.getElementById('star2').setAttribute('width','50');

                       });
 star2.addEventListener('mouseout', function () {
                   document.getElementById('star1').setAttribute('src','/img/Grey_Star.gif');
                    document.getElementById('star1').setAttribute('width','50');
                    document.getElementById('star2').setAttribute('src','/img/Grey_Star.gif');
                                        document.getElementById('star2').setAttribute('width','50');

                       });
 star3 = document.getElementById('star3');
  star3.addEventListener('mouseover', function () {
                    document.getElementById('star1').setAttribute('src','/img/Gold_Star.gif');
                     document.getElementById('star1').setAttribute('width','50');
                     document.getElementById('star2').setAttribute('src','/img/Gold_Star.gif');
                   document.getElementById('star2').setAttribute('width','50');
                    document.getElementById('star3').setAttribute('src','/img/Gold_Star.gif');
                                      document.getElementById('star3').setAttribute('width','50');

                        });
  star3.addEventListener('mouseout', function () {
                    document.getElementById('star1').setAttribute('src','/img/Grey_Star.gif');
                     document.getElementById('star1').setAttribute('width','50');
                     document.getElementById('star2').setAttribute('src','/img/Grey_Star.gif');
                       document.getElementById('star2').setAttribute('width','50');
                         document.getElementById('star3').setAttribute('src','/img/Grey_Star.gif');
                                              document.getElementById('star3').setAttribute('width','50');

                        });
 star4 = document.getElementById('star4');
  star4.addEventListener('mouseover', function () {
                    document.getElementById('star1').setAttribute('src','/img/Gold_Star.gif');
                     document.getElementById('star1').setAttribute('width','50');
                     document.getElementById('star2').setAttribute('src','/img/Gold_Star.gif');
                   document.getElementById('star2').setAttribute('width','50');
                    document.getElementById('star3').setAttribute('src','/img/Gold_Star.gif');
                      document.getElementById('star3').setAttribute('width','50');
                       document.getElementById('star4').setAttribute('src','/img/Gold_Star.gif');
                                            document.getElementById('star4').setAttribute('width','50');


                        });
  star4.addEventListener('mouseout', function () {
                    document.getElementById('star1').setAttribute('src','/img/Grey_Star.gif');
                     document.getElementById('star1').setAttribute('width','50');
                     document.getElementById('star2').setAttribute('src','/img/Grey_Star.gif');
                       document.getElementById('star2').setAttribute('width','50');
                         document.getElementById('star3').setAttribute('src','/img/Grey_Star.gif');
                            document.getElementById('star3').setAttribute('width','50');
                              document.getElementById('star4').setAttribute('src','/img/Grey_Star.gif');
                                                        document.getElementById('star4').setAttribute('width','50');

                        });
star5 = document.getElementById('star5');
  star5.addEventListener('mouseover', function () {
                    document.getElementById('star1').setAttribute('src','/img/Gold_Star.gif');
                     document.getElementById('star1').setAttribute('width','50');
                     document.getElementById('star2').setAttribute('src','/img/Gold_Star.gif');
                   document.getElementById('star2').setAttribute('width','50');
                    document.getElementById('star3').setAttribute('src','/img/Gold_Star.gif');
                      document.getElementById('star3').setAttribute('width','50');
                       document.getElementById('star4').setAttribute('src','/img/Gold_Star.gif');
                                            document.getElementById('star4').setAttribute('width','50');
document.getElementById('star5').setAttribute('src','/img/Gold_Star.gif');
                                            document.getElementById('star5').setAttribute('width','50');

                        });
  star5.addEventListener('mouseout', function () {
                    document.getElementById('star1').setAttribute('src','/img/Grey_Star.gif');
                     document.getElementById('star1').setAttribute('width','50');
                     document.getElementById('star2').setAttribute('src','/img/Grey_Star.gif');
                       document.getElementById('star2').setAttribute('width','50');
                         document.getElementById('star3').setAttribute('src','/img/Grey_Star.gif');
                            document.getElementById('star3').setAttribute('width','50');
                              document.getElementById('star4').setAttribute('src','/img/Grey_Star.gif');
                                                        document.getElementById('star4').setAttribute('width','50');
document.getElementById('star5').setAttribute('src','/img/Grey_Star.gif');
                                                        document.getElementById('star5').setAttribute('width','50');

                        });


}







