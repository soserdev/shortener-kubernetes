document.querySelector('#shorten-form').addEventListener('submit', function(e) {
    e.preventDefault()
    const originalUrl = e.target.elements.originalUrl.value;
    console.log(e.target.elements.originalUrl.value)
    e.target.elements.originalUrl.value = ''


    const spanOriginalUrlElement = document.createElement("span");
    spanOriginalUrlElement.setAttribute("class", "link-original-url");
    spanOriginalUrlElement.textContent = originalUrl;
    spanOriginalUrlElement.setAttribute("class", "link-original-url");

    const spanShortUrlElement = document.createElement("span");
    spanShortUrlElement.setAttribute("class", "link-short-url");
    spanShortUrlElement.textContent = 'jumbr.io/bG3eNf';

    const italicElement = document.createElement("i");
    italicElement.setAttribute("data-feather", "copy")
    italicElement.setAttribute("class", "feather-12rem")

    const buttonElement = document.createElement("button");
    buttonElement.setAttribute("class", "link-copy-button");
    buttonElement.appendChild(italicElement);


    const newDiv = document.createElement('div');
    newDiv.setAttribute('class', 'link');
    newDiv.appendChild(spanOriginalUrlElement);
    newDiv.appendChild(spanShortUrlElement);
    newDiv.appendChild(buttonElement);

    const links = document.querySelector("#links");
    links.prepend(newDiv)
    feather.replace();
    console.log('Appended new child div')
})