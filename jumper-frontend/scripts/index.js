document.querySelector('#shorten-form').addEventListener('submit', function(e) {
    e.preventDefault()
    const originalUrl = e.target.elements.originalUrl.value;
    if (originalUrl === "") {
        return
    }
    console.log(e.target.elements.originalUrl.value)
    e.target.elements.originalUrl.value = ''

    const h3Element = document.getElementById("shortened-urls");
    h3Element.style.display = "block";

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
    console.log("#childnodes: " + links.childElementCount)
    if (links.childElementCount > 3) {
        const lastElement = links.lastElementChild;
        links.removeChild(lastElement);
    }
    
    feather.replace();
    console.log('Appended new child div')
})

const h3Element = document.getElementById("shortened-urls");
h3Element.style.display = "none";