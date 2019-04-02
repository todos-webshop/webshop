fetchOrders();

function fetchOrders() {
    var url = '/myorders';
    fetch(url)
      .then(function (response) {
        return response.json();
      })
      .then(function (jsonData) {
          console.log(jsonData);
        showDivs(jsonData);
      });
}

  function showDivs(jsonData){

    var divMain = document.getElementById('main_div_orders');

    for (let i = 0; i < jsonData.length; i++) {
        var divMain = document.getElementById('main_div_orders');
  
                var tableMain = document.createElement('table');
                tableMain.setAttribute('class', 'orders-table');
                divMain.appendChild(tableMain);
        
                var tbodyMain = document.createElement('tbody');
                tbodyMain.setAttribute('class', 'orders-tbody');
                tableMain.appendChild(tbodyMain);
        
                var trMain = document.createElement('tr');
                trMain.setAttribute('id', jsonData[i].id);
                tbodyMain.appendChild(trMain);
        
                var tdMain = document.createElement('td');
                tdMain.setAttribute('class', 'orders-list-summary');
                trMain.appendChild(tdMain);
        
                var pFirst = document.createElement('p');
                pFirst.setAttribute('class', 'orders-list-order');
                pFirst.innerText = 'Order ' + jsonData[i].id;
                tdMain.appendChild(pFirst);
        
                var span = document.createElement('span');
                span.setAttribute('class', 'orders-list-span')
                span.innerText = '– ' + jsonData[i].orderStatus;
                pFirst.appendChild(span);
        
                var pSecond = document.createElement('p');
                pSecond.setAttribute('class', 'orders-list-date');
                pSecond.innerText = jsonData[i].orderTime.split("T")[0] + " " + jsonData[i].orderTime.split("T")[1];
                tdMain.appendChild(pSecond);
        
                var pThird = document.createElement('p');
                pThird.setAttribute('class', 'orders-list-address');
                pThird.innerText =  jsonData[i].shippingAddress;
                tdMain.appendChild(pThird);
        
                var pFourth = document.createElement('p');
                pFourth.setAttribute('class', 'orders-list-amount');
                pFourth.innerText =  jsonData[i].totalOrderPrice + " Ft";
                tdMain.appendChild(pFourth);
        
                var tdButton = document.createElement('td');
                tdButton.setAttribute('class', 'orders-list-actions');
                trMain.appendChild(tdButton);
        
                var button = document.createElement('button');
                button.setAttribute('id', jsonData[i].id)
                button.setAttribute('class', 'orders-button')
                button.innerHTML = 'SEE DETAILS';
                button.addEventListener('click', showDetails);
                tdButton.appendChild(button);
        
                var tableDetail = document.createElement('table');
                var attribute = 'button' + jsonData[i].id;
                tableDetail.setAttribute('class', 'disabled');
                tableDetail.setAttribute('id', attribute);
                divMain.appendChild(tableDetail);
        
                var tbodyDetail = document.createElement('tbody');
                tbodyDetail.setAttribute('class', 'orders-list-summary order-products');
                tableDetail.appendChild(tbodyDetail);
        
            for (let j = 0; j < jsonData[i].orderItems.length; j++) {

                var trDetail = document.createElement('tr');
                var id = 'order-' + jsonData[i].id;
                trDetail.setAttribute('id', id);
                trDetail.setAttribute('class', 'tr-detail');
                tbodyDetail.appendChild(trDetail);
        
                var tdDetail = document.createElement('div');
                tdDetail.setAttribute('class', 'orders-list-summary tr-div');
                trDetail.appendChild(tdDetail);
        
                var productNumber = document.createElement('div');
                productNumber.innerText = 'Product ' + (parseInt(j) + 1)
                productNumber.setAttribute('class', 'myorders-div product-number');
                tdDetail.appendChild(productNumber);
        
                var nameDiv = document.createElement('div');
                nameDiv.innerText = jsonData[i].orderItems[j].product.name;
                nameDiv.setAttribute('class', 'myorders-div name-product');
                tdDetail.appendChild(nameDiv);
        
                var codeDiv = document.createElement('div');
                codeDiv.setAttribute('class', 'myorders-div');
                codeDiv.innerText = jsonData[i].orderItems[j].product.code;
                tdDetail.appendChild(codeDiv);
        
                var manufacturerDiv = document.createElement('div');
                manufacturerDiv.setAttribute('class', 'myorders-div');
                manufacturerDiv.innerText = jsonData[i].orderItems[j].product.manufacturer;
                tdDetail.appendChild(manufacturerDiv);
        
                var pieceDiv = document.createElement('div');
                pieceDiv.setAttribute('class', 'myorders-div');
                pieceDiv.innerText = 'Quantity: ' + jsonData[i].orderItems[j].pieces;
                tdDetail.appendChild(pieceDiv);
        
                var priceDiv = document.createElement('div');
                priceDiv.setAttribute('class', 'myorders-div');
                priceDiv.innerText = jsonData[i].orderItems[j].product.price + ' Ft';
                tdDetail.appendChild(priceDiv);                      
            }      
    }

  
    // for (const key in jsonData) {
    //   if (jsonData.hasOwnProperty(key)) {
    //     const e = jsonData[key];
    //     divMain.innerHTML += `
    //       <table class="orders-table">
    //         <tbody class="orders-tbody">
    //           <p id = "${e.id}">
    //             <td class="orders-list-summary">
    //               <p class="orders-list-order">
    //                 "Order " + ${e.id}
    //                 <span class="orders-list-span"> 
    //                 '– ' + ${e.orderStatus}
    //                 </span>
    //               </p>
    //               <p class="orders-list-date"> 
    //                 ${e.orderTime.split("T")[0] + " " + e.orderTime.split("T")[1]}
    //               </p>
    //               <p class="orders-list-address">
    //                 ${e.shippingAddress}
    //               </p>
    //               <p class="orders-list-amount">
    //                 ${e.totalOrderPrice + " Ft"}
    //               </p>
    //             </td>
    //             <td id="${e.id}" class="orders-list-actions">
    //               <button id="${e.id}" class="orders-button" onclick="showDetails(${e.id})">SEE DETAILS</button>
    //             </td>
    //           </tr>
    //         </tbody>
    //       </table>
    //       <table id="button+${e.id}">
    //         <tbody class="orders-list-summary order-products">
    //             <tr id="order-${e.id}" class="tr-detail">
    //             ${createInnerDiv(e.orderItems, e)}
                
    //     `;
        
    //     divMain.innerHTML += ``
    //   }
    // }
  }

//   function createInnerDiv(products, e){
//     var result = "";
//     var j = 1;
//     console.log(products);
//     for (const key in products) {
//         if (products.hasOwnProperty(key)) {
//             const ee = products[key].product;
//             console.log(ee);
//             result += `
//             <div class="orders-list-summary tr-div">
//                 <div class="myorders-div product-number">${'Product ' + j}</div>
//                 <div class="myorders-div name-product">${ee.name}</div>
//                 <div class="myorders-div">${ee.code}</div>
//                 <div class="myorders-div">${ee.manufacturer}</div>
//                 <div class="myorders-div">${e.pieces}</div>
//                 <div class="myorders-div">${ee.price + ' Ft'}</div>
//             </div>
//             `;
//             j++;
//         }
//     }
//     result += `
//             </tr>
//         </tbody>
//     </table>`
//     return result;
//   }
  
  function showDetails() {
    var id = this.id;
    var attribute = 'button' + id;
    var tableDetails = document.getElementById(attribute);
    if (tableDetails.getAttribute('class') == 'disabled') {
      tableDetails.setAttribute('class', 'enabled');
    } else {
      tableDetails.setAttribute('class', 'disabled')
    }
  }