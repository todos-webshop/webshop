var user = getUser();
// getUser();
setMenu();
window.onload = function(){
    
}

function getUser(){
    var url = "/userdata";
    return fetch(url)
    .then(function(response){
        return response.json();
    })
    .then(function(jsonData){
        return jsonData;
    })
}

function setMenu(){
    var body = document.getElementsByTagName("body")[0];
    var div = document.createElement("div");
    div.setAttribute("id", "menu_div");
    div.setAttribute("class", "menu_div");

    div.appendChild(createLogoImg());
    // div.appendChild(createOrdersButton());
    // div.appendChild(createHistoryButton());
    // div.appendChild(createStatButton());
    div.appendChild(createLoginButton());
    div.appendChild(createSignUpButton());
    div.appendChild(createLogoutButton());
    console.log(user);
    // switch(user.role){
    //     case "NOT_AUTHENTICATED": break;
    //     case "USER": {
    //         createDropdownDivForUser(div);
    //         break;
    //     }
    //     case "ADMIN": {
    //         createDropdownDivForAdmin(div);
    //         break;
    //     }
    // }
    
    createDropdownDivForUser(div);
    createDropdownDivForAdmin(div);    
    div.appendChild(createCartButton());


    body.insertBefore(div, body.firstChild);
    setFavicon();
    insertMenuCss();
}

function classChanger() {
    document.getElementById("myDropdown").classList.toggle("show");
}

window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}

function setFavicon(){
    var head = document.getElementsByTagName("head")[0];

    var link = document.createElement("link");
    link.setAttribute("id", "favicon");
    link.setAttribute("rel", "shortcut icon");
    link.setAttribute("type", "image/png");
    link.setAttribute("href", "img/favicon.png");

    head.appendChild(link);
}

function insertMenuCss(){
    var head = document.getElementsByTagName("head")[0];

    var cssLink = document.createElement("link");
    cssLink.setAttribute("rel", "stylesheet");
    cssLink.setAttribute("type", "text/css");
    cssLink.setAttribute("href", "css/menu.css");

    head.appendChild(cssLink);
}

function createDropdownDivForUser(div){
    div.innerHTML += `
    <div class="dropdown">
        <button onclick="classChanger()" class="dropbtn">User</button>
        <div id="myDropdown" class="dropdown-content">
            <a href="/orders.html">Orders</a>
            <a href="/user.html">Profile</a>
            <a href="#contact">Contact</a>
        </div>
    </div>
    `;
}

function createDropdownDivForAdmin(div){
    div.innerHTML += `
    <div class="dropdown">
        <button onclick="classChanger()" class="dropbtn">Admin</button>
        <div id="myDropdown" class="dropdown-content">
            <a href="/history.html">Order history</a>
            <a href="/statistics.html">Statistics</a>
            <a href="/users.html">Users</a>
        </div>
    </div>
    `;
}



function createLogoImg(){
    var logoImg = document.createElement("img");
    logoImg.setAttribute("src", "img/logo.png");
    logoImg.setAttribute("alt", "Logo");
    logoImg.onclick = function(){
        window.location.href = "index.html";
    }
    logoImg.setAttribute("style","height: 80px");
    return logoImg;
}

// function createOrdersButton(){
//     var ordersBtn = document.createElement("button");
//     ordersBtn.innerText = "Orders";
//     ordersBtn.onclick = function(){
//         window.location.href = "orders.html";
//     }
//     return ordersBtn;
// }

// function createHistoryButton(){
//     var historyBtn = document.createElement("button");
//     historyBtn.innerText = "History";
//     historyBtn.onclick = function(){
//         window.location.href = "history.html";
//     }
//     return historyBtn;
// }

// function createStatButton(){
//     var statisticsBtn = document.createElement("button");
//     statisticsBtn.innerText = "Statistics";
//     statisticsBtn.onclick = function(){
//         window.location.href = "statistics.html";
//     }
//     return statisticsBtn;
// }

function createLoginButton(){
    var loginBtn = document.createElement("button");
    loginBtn.innerText = "Login";
    loginBtn.onclick = function(){
        window.location.href = "/login";
    }
    return loginBtn;
}

function createLogoutButton(){
    var logoutBtn = document.createElement("button");
    logoutBtn.innerText = "Logout";
    logoutBtn.onclick = function(){
        window.location.href = "/logout";
    }
    return logoutBtn;
}

function createSignUpButton(){
    var signUpBtn = document.createElement("button");
    signUpBtn.innerText = "Sign up";
    signUpBtn.onclick = function(){
        window.location.href = "/register.html";
    }
    return signUpBtn;
}

function createCartButton(){
    var cartBtn = document.createElement("button");
    cartBtn.innerText = "Cart";
    cartBtn.onclick = function(){
        window.location.href = "cart.html";
    }
    return cartBtn;
}