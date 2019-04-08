var user = getUser();


function getUser() {
  var url = '/userdata';
  return fetch(url)
    .then(response => response.json())
    .then(jsonData => user = jsonData)
    .then(setMenu);
}

function setMenu() {
  var body = document.getElementsByTagName('body')[0];
  var freeShippingDiv = document.createElement('div');
  freeShippingDiv.setAttribute('class', 'free-shipping-div');
  freeShippingDiv.innerHTML = 'FREE SHIPPING WORLDWIDE';

  var div = document.createElement('div');
  div.setAttribute('id', 'menu_div');
  div.setAttribute('class', 'menu_div');

  body.insertBefore(createMessageDiv(), body.firstChild);
  body.insertBefore(div, body.firstChild);
  body.insertBefore(freeShippingDiv, body.firstChild);


  switch (user.role) {
  case 'NOT_AUTHENTICATED':
    div.appendChild(createLoginButton());
    div.appendChild(createSignUpButton());
    addEventListenerToNotLoggedIn();
    break;
  case 'ROLE_USER':
  {
    createDropdownDivForUser(div);
    div.appendChild(createLogoutButton());
    div.appendChild(welcomeUser());
    div.appendChild(createCartButton());
    addEventListenerToLoggedIn();
    break;
  }
  case 'ROLE_ADMIN':
  {
    createDropdownDivForAdmin(div);
    div.appendChild(createLogoutButton());
    div.appendChild(createCartButton());
    div.appendChild(welcomeUser());
    addEventListenerToLoggedIn();
    break;
  }
  }

  div.appendChild(createLogoImg());

  setFavicon();
  insertMenuCss();
  addEventListenerToLogo();
}

function classChanger() {
  document.getElementById('myDropdown').classList.toggle('show');
}

window.onclick = function (event) {
  if (!event.target.matches('.dropbtn')) {
    var dropdowns = document.getElementsByClassName('dropdown-content');
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
};

function welcomeUser() {
  var welcomeLabel = document.createElement('label');
  welcomeLabel.setAttribute('class', 'welcome-div')
  welcomeLabel.innerHTML = ' Welcome ' + user.username + '!';
  return welcomeLabel;
}

function setFavicon() {
  var head = document.getElementsByTagName('head')[0];

  var link = document.createElement('link');
  link.setAttribute('id', 'favicon');
  link.setAttribute('rel', 'shortcut icon');
  link.setAttribute('type', 'image/png');
  link.setAttribute('href', 'img/favicon.png');

  head.appendChild(link);
}

function insertMenuCss() {
  var head = document.getElementsByTagName('head')[0];

  var cssLink = document.createElement('link');
  cssLink.setAttribute('rel', 'stylesheet');
  cssLink.setAttribute('type', 'text/css');
  cssLink.setAttribute('href', 'css/menu2.css');

  head.appendChild(cssLink);
}

function createDropdownDivForUser(div) {
  div.innerHTML += `
    <div class="dropdown">
        <button onmouseover="classChanger()" class="dropbtn">My Account</button>
        <div id="myDropdown" class="dropdown-content">
            <a href="/myorders.html">My Orders</a>
            <a href="/profile.html">Profile</a>
        </div>
    </div>
    `;
}
function createDropdownDivForAdmin(div) {
  div.innerHTML += `
    <div class="dropdown">
        <button onmouseover="classChanger()" class="dropbtn">Admin</button>
        <div id="myDropdown" class="dropdown-content">
            <a href="/orders.html">Order history</a>
            <a href="/dashboard.html">Dashboard</a>
            <a href="/users.html">Users</a>
            <a href="/adminproducts.html">Products</a>
            <a href="/reports.html">Reports</a>
            <a href="/categories.html">Categories</a>
        </div>
    </div>
    `;
}

function createLogoImg() {
  var logoImg = document.createElement('img');
  logoImg.setAttribute('src', 'img/logo3.png');
  logoImg.setAttribute('alt', 'Logo');
  logoImg.setAttribute('class', 'logo-img');
  logoImg.id = 'logo-img';
  return logoImg;
}

function createLoginButton() {
  var loginBtn = document.createElement('button');
  loginBtn.innerText = 'Login';
  loginBtn.className = 'menu-button';
  loginBtn.id = 'login-btn';
  return loginBtn;
}

function createLogoutButton() {
  var logoutBtn = document.createElement('button');
  logoutBtn.innerText = 'Logout';
  logoutBtn.className = 'menu-button';
  logoutBtn.id = 'logout-btn';
  return logoutBtn;
}

function createSignUpButton() {
  var signUpBtn = document.createElement('button');
  signUpBtn.innerText = 'Sign up';
  signUpBtn.className = 'menu-button';
  signUpBtn.id = 'signup-btn';
  return signUpBtn;
}

function createCartButton() {
  var cartBtn = document.createElement('img');
  cartBtn.setAttribute('src', '/img/cart.jpg')
  cartBtn.className = 'menu-button-cart';
  cartBtn.id = 'cart-btn';
  return cartBtn;
}

function createMessageDiv() {
  var messageDiv = document.createElement('div');
  messageDiv.setAttribute('id', 'message-div');
  messageDiv.setAttribute('class', 'message-div');
  messageDiv.addEventListener("DOMSubtreeModified", function(){
    document.body.scrollTop = document.documentElement.scrollTop = 0;
  })
  return messageDiv;
}

function addEventListenerToNotLoggedIn() {
  document.getElementById('login-btn').addEventListener('click', function () {
    window.location.href = '/login.html';
  });
  document.getElementById('signup-btn').addEventListener('click', function () {
    window.location.href = '/register.html';
  });
}

function addEventListenerToLoggedIn() {
  document.getElementById('logout-btn').addEventListener('click', function () {
    window.location.href = '/logout';
  });
  document.getElementById('cart-btn').addEventListener('click', function () {
    window.location.href = '/basket.html';
  });
}

function addEventListenerToLogo() {
  document.getElementById('logo-img').addEventListener('click', function () {
    window.location.href = 'index.html';
  });
}
