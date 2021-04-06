
const createShortUrl = async (url) => {
    const data = {
        originalUrl: url
    }
    console.log(`Hello: ${JSON.stringify(data)}`);
    const response = await fetch('http://localhost:8081/shorturl', {
        method: 'POST',
        mode: 'cors', // cors, no-cors, *cors, same-origin);
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
    if (response.status !== 200) {
        throw new Error('Unable to get message')
    }
    // return response.json(); // parses JSON response into native JavaScript objects
    const shortUrl = await response.json();
    return shortUrl;
}
createShortUrl().then((shortUrl) => {
    console.log('ShortUrl: ${shortUrl} ')
    // document.querySelector('#title').innerHTML = data.title;
    // document.querySelector('#body').innerHTML = data.body;
}).catch((err) => {
    console.log(`Error: ${err}`);
})