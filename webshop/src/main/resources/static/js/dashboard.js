load();

function load (){
    var url = "/dashboard";
    fetch(url)
        .then(function(response){
            return response.json();
        })
        .then(function(jsonData){
            showTable(jsonData);
        })
}

function showTable(jsonData){
    var tableBody = document.getElementById("stat-table-body");
    var tr = document.createElement("tr");
    for (const key in jsonData) {
        if (jsonData.hasOwnProperty(key)) {
            const element = jsonData[key];
            var td = document.createElement("td");
            td.innerHTML = element;
            tr.appendChild(td);
        }
    }
    tableBody.appendChild(tr);
}