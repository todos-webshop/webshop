getCategories();

function getCategories(){
    fetch("/api/categories")
    .then(response => response.json())
    .then(json => showCategories(json))
}

function showCategories(jsonData){
  var ul = document.getElementById("sortable");
  ul.innerHTML = "";
  for (const key in jsonData) {
    if (jsonData.hasOwnProperty(key)) {
      const e = jsonData[key];
      ul.innerHTML += `
      <li id="${e["id"]}">
      <img src="img/dragNdrop.png" alt="dragNdrop" class="handle" height="25">
      <label>${e["sequence"]}</label>
      <label contenteditable="true">${e["categoryName"]}</label>
      </li>
      `;
    }
  }
}

$('.sortable').sortable();
    $('.sortable').sortable({
    handle: '.handle'
});

function save(){
    var request = [];
    var lis = document.getElementById('sortable').getElementsByTagName('li');
    for (var i = 0; i < lis.length; i++) {
      const e = lis[i];
      request.push({
        "id": e.id,
        "categoryName": e.getElementsByTagName('label')[1].innerHTML.trim(),
        "sequence": i+1});
    }
    fetch("api/categories/update",{
      method: 'POST',
      body: JSON.stringify(request),
      headers: {
      'Content-type': 'application/json'
      }
    })
    .then(response => response.json())
    .then(r => {
      // document.getElementById('message-div').innerHTML = r.message;
      getCategories();
    })
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



