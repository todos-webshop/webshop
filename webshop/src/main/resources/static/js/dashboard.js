load();

function load (){
    var url = "/dashboard";
    fetch(url)
        .then(function(response){
            return response.json();
        })
        .then(function(jsonData){
            showStatistics(jsonData);

        });
}

function showStatistics(jsonData){

    document.getElementById('users').innerHTML = jsonData["numOfUsers"];
    document.getElementById('orders').innerHTML = jsonData["numOfOrders"];
    var numberOfActiveProducts = jsonData["numOfActiveProducts"];
    var numberOfAllProducts = jsonData["numOfProducts"];
    var numberOfActiveOrders = jsonData["numOfActiveOrders"];
    var numberOfAllOrders = jsonData["numOfOrders"];
    var options = {responsive: true,
                       maintainAspectRatio:false,fontStyle: 'Arial',
                       };
if(numberOfAllProducts !==0){
    var ct1 = document.getElementById('chart1');
    var data = {
        labels: [`Active Products`, `Deleted Products`],
        datasets: [{
            data: [numberOfActiveProducts, numberOfAllProducts-numberOfActiveProducts],
            backgroundColor: [
                            '#006400',
                                                        '	#D3D3D3'
                            ],
            borderColor: [
                           '#006400',
                                                       'rgba(112, 99, 132, 1)'
                         ],
                         borderWidth:1

        }]
     };


    var myDoughnutChart = new Chart(ct1, {
        type: 'doughnut',
        data: data,
        options: options
    });
    } else {

        document.getElementById('message-product').innerHTML = "There are no products.";
    }

    if(numberOfAllOrders !==0){
    var ct2 = document.getElementById('chart2');

    var data2 = {
            labels:[`Pending Orders`, `Delivered Orders`],
            datasets: [{
                data: [numberOfActiveOrders, numberOfAllOrders-numberOfActiveOrders],
                backgroundColor: [
                                 '	#D3D3D3',
                                                             '#006400'
                                 ],
                borderColor: [
                               '#006400',
                                                           '#006400'

                             ],
                             borderWidth:1,
            }],
         };


       var myDoughnutChart = new Chart(ct2, {
           type: 'doughnut',
           data: data2,
           options: options
       });
        } else {
            document.getElementById('message-order').innerHTML="There are no orders.";
        }
}




