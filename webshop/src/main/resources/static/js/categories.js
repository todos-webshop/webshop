getCategories();

function getCategories(){
    fetch("/api/categories")
    .then(response => response.json())
    .then(json => setCat(json))
}

function setCat(jsonData){
    var ul = document.getElementById("columns");
    var sequenceUL = document.getElementById("sequence");

    for (const key in jsonData) {
        if (jsonData.hasOwnProperty(key)) {
            const element = jsonData[key];
            ul.innerHTML += 
            `<li class="column" draggable="true"><header><div class="drag-box" id="id-${element["sequence"]}">
                ${element["categoryName"]}
            </div></header></li>`;
            sequenceUL.innerHTML += 
            `<li class="sequence-column"><header><div class="drag-box">
            ${element["sequence"]}
            </div></header></li>`
        }
    }
    var cols = document.querySelectorAll('#columns .column');
    [].forEach.call(cols, addDnDHandlers);
}

function save(){
    //ide jöhet a fetch
    console.log("fetchke");
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



