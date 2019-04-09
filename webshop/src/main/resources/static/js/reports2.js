window.onload = function () {
    fetchReportsTwo();

    function fetchReportsTwo() {
        var url = "/reports/products";
        fetch(url)
            .then(function (response) {
                return response.json();
            })
            .then(function (jsonData) {
                showTables(jsonData);
            });
    }

    function showTables(jsonData) {
        tableMain = document.getElementById("report-table_prod");
        for (var i = 0; i < jsonData.length; i++) {
            var oneRow = document.createElement("tr");

            var yearTd = document.createElement("td");
            yearTd.innerHTML = jsonData[i].year;
            oneRow.appendChild(yearTd);

            var monthTd = document.createElement("td");
            monthTd.innerHTML = jsonData[i].month;
            oneRow.appendChild(monthTd);
            var productTd = document.createElement("td");
            productTd.innerHTML = jsonData[i].productName;
            oneRow.appendChild(productTd);

            var priceTd = document.createElement("td");
            priceTd.innerHTML = jsonData[i].productPrice;
            oneRow.appendChild(priceTd);

            var countTd = document.createElement("td");
            countTd.innerHTML = jsonData[i].productCounter;
            oneRow.appendChild(countTd);

            var amountTd = document.createElement("td");
            amountTd.innerHTML = jsonData[i].amount;
            oneRow.appendChild(amountTd);

            tableMain.appendChild(oneRow);

        }
    }
}