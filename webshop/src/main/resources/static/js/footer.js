function createFooter(){
var body = document.querySelector('body');

var footer = document.createElement('footer');
footer.setAttribute('class', 'footer')

var div = document.createElement('div');
div.setAttribute = ('class', 'footer');

var b = document.createElement('b');
b.innerHTML = 'GREENHUB © 2019 All rights reserved';

var p = document.createElement('p');
p.innerHTML = 'Follow us!'

var facebookLink = document.createElement('a');
facebookLink.setAttribute('href', 'http://facebook.com');

var facebookImg = document.createElement('img');
facebookImg.setAttribute('src', '/img/facebook_icon.png');
facebookImg.setAttribute('alt', '');
facebookLink.appendChild(facebookImg);

var instagramLink = document.createElement('a');
instagramLink.setAttribute('href', 'http://instagram.com');

var instagramImg = document.createElement('img');
instagramImg.setAttribute('src', '/img/instagram_icon.png');
instagramImg.setAttribute('alt', '');
instagramLink.appendChild(instagramImg);

var youtubeLink = document.createElement('a');
youtubeLink.setAttribute('href', 'http://youtube.com');

var youtubeImg = document.createElement('img');
youtubeImg.setAttribute('src', '/img/youtube2.png');
youtubeImg.setAttribute('alt', '');
youtubeLink.appendChild(youtubeImg);

var twitterLink = document.createElement('a');
twitterLink.setAttribute('href', 'http://twitter.com');

var twitterImg = document.createElement('img');
twitterImg.setAttribute('src', '/img/twitter.svg');
twitterImg.setAttribute('alt', '');
twitterLink.appendChild(twitterImg);

div.appendChild(b);
div.appendChild(p);
div.appendChild(facebookLink);
div.appendChild(instagramLink);
div.appendChild(youtubeLink)
div.appendChild(twitterLink)

footer.appendChild(div);
body.appendChild(footer);
}

createFooter();
