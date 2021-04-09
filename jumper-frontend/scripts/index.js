const createShortUrl = async (originalUrl) => {
    const data = {
        url: originalUrl
    }
    const response = await fetch('http://jumper.io/api/shorturl', {
        method: 'POST',
        // mode: 'cors', // cors, no-cors, *cors, same-origin);
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
    if (response.status !== 200) {
        throw new Error('Unable to get message')
    }
    const shortUrl = await response.json();
    return shortUrl;
}

document.querySelector('#shorten-form').addEventListener('submit', function(e) {
    e.preventDefault()
    const originalUrl = e.target.elements.originalUrl.value;
    if (originalUrl === "") {
        return
    }
 
    let shortUrl = "";
    createShortUrl(originalUrl).then((url) => {
        const linkDiv = createLinkDiv(originalUrl, url.shortUrl);
        const links = document.querySelector("#links");
        if (links.childElementCount > 2) {
            const lastElement = links.lastElementChild;
            links.removeChild(lastElement);
        }
        links.prepend(linkDiv)
        feather.replace();
        e.target.elements.originalUrl.value = ''
    }).catch((err) => {
        console.log(`Error: ${err}`);
        return;
    })
})

const createLinkDiv = (originalUrl, shortUrl) => {
    const h3Element = document.getElementById("shortened-urls");
    h3Element.style.display = "block";

    const spanOriginalUrlElement = document.createElement("span");
    spanOriginalUrlElement.setAttribute("class", "link-original-url");
    spanOriginalUrlElement.textContent = originalUrl;
    spanOriginalUrlElement.setAttribute("class", "link-original-url");

    const anchorShortUrlElement = document.createElement("a");
    const shortUrlLink = "jumper.io" + "/" + shortUrl;
    anchorShortUrlElement.setAttribute("href", "http://" + shortUrlLink);
    anchorShortUrlElement.setAttribute("class", "link-short-url-a");
    anchorShortUrlElement.textContent = shortUrlLink;

    const spanShortUrlElement = document.createElement("span");
    spanShortUrlElement.setAttribute("class", "link-short-url");
    // const hostname = window.location.hostname;
    spanShortUrlElement.appendChild(anchorShortUrlElement);
    // spanShortUrlElement.textContent = "jumper.io" + "/" + shortUrl; //'jumbr.io/bG3eNf';

    // const italicElement = document.createElement("i");
    // italicElement.setAttribute("data-feather", "copy")
    // italicElement.setAttribute("class", "feather-12rem")

    const buttonElement = document.createElement("button");
    buttonElement.setAttribute("class", "link-copy-button");
    // buttonElement.appendChild(italicElement);

    const newDiv = document.createElement('div');
    newDiv.setAttribute('class', 'link');
    newDiv.appendChild(spanOriginalUrlElement);
    newDiv.appendChild(spanShortUrlElement);
    newDiv.appendChild(buttonElement);
    return newDiv;
}

const h3Element = document.getElementById("shortened-urls");
h3Element.style.display = "none";