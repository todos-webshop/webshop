window.onload = function () {
  loadErrorMessage();
};

function loadErrorMessage() {
  var errorDiv = document.getElementById('error-div');

  var params = new URLSearchParams(window.location.search);

  if (params.has('error')) {
    errorDiv.innerHTML = '<p>Bad credentials. Take a deep breath and try it again. You will remember. Dont worry. Trust yourself.<p>';
    errorDiv.setAttribute('class', 'alert alert-danger');
  } else {
    errorDiv.innerHTML = '';
    errorDiv.removeAttribute('class', 'alert alert-danger');
  }
}
