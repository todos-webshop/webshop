getCategories();

function getCategories() {
  fetch('/api/categories')
    .then(response => response.json())
    .then(json => showCategories(json));
}

function showCategories(jsonData) {
  var ul = document.getElementById('sortable');
  ul.innerHTML = '';
  for (const key in jsonData) {
    if (jsonData.hasOwnProperty(key)) {
      const e = jsonData[key];
      var id = jsonData[key].id;
      var liId = 'li-' + id;
      ul.innerHTML += `
      <li id=${liId}>
      <img src="img/dragNdrop.png" alt="dragNdrop" class="handle" height="25">
      <label>${e.sequence}</label>
      <label contenteditable="true">${e.categoryName}</label>
      <img id=${id} src="/img/delete-button.png" onclick="deleteCategory()" class='category-delete'>
      </li>
      `;
    }
  }
}

$('.sortable').sortable();
$('.sortable').sortable({
  handle: '.handle'
});

function save() {
  var request = [];
  var lis = document.getElementById('sortable').getElementsByTagName('li');
  for (var i = 0; i < lis.length; i++) {
    const e = lis[i];
    request.push({
      'id': e.id.split("-")[1],
      'categoryName': e.getElementsByTagName('label')[1].innerHTML.trim(),
      'sequence': i + 1
    });
  }
  fetch('api/categories/update', {
    method: 'POST',
    body: JSON.stringify(request),
    headers: {
      'Content-type': 'application/json'
    }
  })
    .then(response => response.json())
    .then(r => {
      //document.getElementById('message-div').innerHTML = r.message;
      getCategories();
    });
}

function deleteCategory() {
  var id = this.event.target.id
  console.log(this.event.target.id);

  if (!confirm('Are you sure to delete?')) {
    return;
  }

  fetch('/api/category/' + id, {
    method: 'DELETE'
  })
    .then(function (response) {
      return response.json();
    })
    .then(function (jsonData) {
      if (jsonData.response == 'SUCCESS') {
        getCategories();
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
    });
  return false;
}

function addNewProduct() {
  var nameInput = document.getElementById('name').value;
  var sequenceInput = document.getElementById('sequence').value;

  var request = {
     "categoryName": nameInput,
     "sequence" : sequenceInput
  };

  fetch('/api/categories', {
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
      if (jsonData.response == 'SUCCESS') {
        document.getElementById('name').value = '';
        document.getElementById('sequence').value = '';
        getCategories();
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-success');
      } else {
        document.getElementById('message-div').innerHTML = jsonData.message;
        document.getElementById('message-div').setAttribute('class', 'alert alert-danger');
      }
    });
  return false;
}

var addButton = document.getElementById('add-btn');
addButton.onclick = addNewProduct;

var newProductButton = document.getElementById('new-product-btn');
newProductButton.onclick = function () {
  showInputFields();
};

function showInputFields(jsonData) {
  var formInput = document.querySelector('#form-input');
  if (formInput.getAttribute('class') == 'disabled'){
  formInput.setAttribute('class', 'enabled');
  } else {
  formInput.setAttribute('class', 'disabled')
  }
  }

// function setCat(jsonData){
//     var ul = document.getElementById("columns");
//     var sequenceUL = document.getElementById("sequence");
//     ul.innerHTML = "";
//     sequenceUL.innerHTML = "";
//     console.log(jsonData);
//     for (const key in jsonData) {
//         if (jsonData.hasOwnProperty(key)) {
//             const element = jsonData[key];
//             ul.innerHTML +=
//             `<li class="column"><header><div class="drag-box" id="${element["id"]}">
//                 ${element["categoryName"]}
//             </div></header></li>`;
//             sequenceUL.innerHTML +=
//             `<li class="sequence-column"><header><div class="drag-box">
//             ${element["sequence"]}
//             </div></header></li>`
//         }
//     }
// }

// function changeSequence(){
//     document.querySelectorAll('#columns .column').forEach(function(e){
//         e.setAttribute("class","edit-column");
//         e.setAttribute("draggable", "true")
//     });
//     var cols = document.querySelectorAll('#columns .edit-column');
//     [].forEach.call(cols, addDnDHandlers);
// }

// var dragSrcEl = null;

// function handleDragStart(e) {
//   dragSrcEl = this;

//   e.dataTransfer.effectAllowed = 'move';
//   e.dataTransfer.setData('text/html', this.outerHTML);

//   this.classList.add('dragElem');
// }
// function handleDragOver(e) {
//   if (e.preventDefault) {
//     e.preventDefault();
//   }
//   this.classList.add('over');

//   e.dataTransfer.dropEffect = 'move';

//   return false;
// }

// function handleDragEnter(e) {
// }

// function handleDragLeave(e) {
//   this.classList.remove('over');
// }

// function handleDrop(e) {

//   if (e.stopPropagation) {
//     e.stopPropagation();
//   }

//   if (dragSrcEl != this) {
//     this.parentNode.removeChild(dragSrcEl);
//     var dropHTML = e.dataTransfer.getData('text/html');
//     this.insertAdjacentHTML('beforebegin',dropHTML);
//     var dropElem = this.previousSibling;
//     addDnDHandlers(dropElem);

//   }
//   this.classList.remove('over');
//   return false;
// }

// function handleDragEnd(e) {
//   this.classList.remove('over');
// }

// function addDnDHandlers(elem) {
//   elem.addEventListener('dragstart', handleDragStart, false);
//   elem.addEventListener('dragenter', handleDragEnter, false)
//   elem.addEventListener('dragover', handleDragOver, false);
//   elem.addEventListener('dragleave', handleDragLeave, false);
//   elem.addEventListener('drop', handleDrop, false);
//   elem.addEventListener('dragend', handleDragEnd, false);
// }
