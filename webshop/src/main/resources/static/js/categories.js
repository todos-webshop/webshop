getCategories();

function getCategories(){
    fetch("/api/categories")
    .then(response => response.json())
    .then(json => setCat(json))
}

function setCat(jsonData){
    var ul = document.getElementById("columns");
    var sequenceUL = document.getElementById("sequence");
    ul.innerHTML = "";
    sequenceUL.innerHTML = "";
    console.log(jsonData);
    for (const key in jsonData) {
        if (jsonData.hasOwnProperty(key)) {
            const element = jsonData[key];
            ul.innerHTML += 
            `<li class="column"><header><div class="drag-box" id="${element["id"]}">
                ${element["categoryName"]}
            </div></header></li>`;
            sequenceUL.innerHTML += 
            `<li class="sequence-column"><header><div class="drag-box">
            ${element["sequence"]}
            </div></header></li>`
        }
    }
}

function changeSequence(){
    document.querySelectorAll('#columns .column').forEach(function(e){
        e.setAttribute("class","edit-column");
        e.setAttribute("draggable", "true")
    });
    var cols = document.querySelectorAll('#columns .edit-column');
    [].forEach.call(cols, addDnDHandlers);
}

function save(){
    var request = [];
    var sequence = 0;
    document.querySelectorAll('#columns .edit-column').forEach(function(e){
        e.setAttribute("class","column");
        request.push({"id": e.firstChild.firstChild.id,
        "categoryName": e.firstChild.firstChild.innerHTML.trim(),
        "sequence": sequence});
        sequence++;
    });
    fetch("api/categories/update",{
      method: 'POST',
      body: JSON.stringify(request),
      headers: {
      'Content-type': 'application/json'
      }
    })
    .then(response => response.json())
    .then(responseJSON => console.log(responseJSON))
    .then(r => getCategories())
}





var dragSrcEl = null;

function handleDragStart(e) {
  dragSrcEl = this;

  e.dataTransfer.effectAllowed = 'move';
  e.dataTransfer.setData('text/html', this.outerHTML);

  this.classList.add('dragElem');
}
function handleDragOver(e) {
  if (e.preventDefault) {
    e.preventDefault(); 
  }
  this.classList.add('over');

  e.dataTransfer.dropEffect = 'move';  

  return false;
}

function handleDragEnter(e) {
}

function handleDragLeave(e) {
  this.classList.remove('over');  
}

function handleDrop(e) {

  if (e.stopPropagation) {
    e.stopPropagation(); 
  }

  if (dragSrcEl != this) {
    this.parentNode.removeChild(dragSrcEl);
    var dropHTML = e.dataTransfer.getData('text/html');
    this.insertAdjacentHTML('beforebegin',dropHTML);
    var dropElem = this.previousSibling;
    addDnDHandlers(dropElem);
    
  }
  this.classList.remove('over');
  return false;
}

function handleDragEnd(e) {
  this.classList.remove('over');
}

function addDnDHandlers(elem) {
  elem.addEventListener('dragstart', handleDragStart, false);
  elem.addEventListener('dragenter', handleDragEnter, false)
  elem.addEventListener('dragover', handleDragOver, false);
  elem.addEventListener('dragleave', handleDragLeave, false);
  elem.addEventListener('drop', handleDrop, false);
  elem.addEventListener('dragend', handleDragEnd, false);
}



