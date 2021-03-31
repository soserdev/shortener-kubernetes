document.querySelector('#url-form').addEventListener('submit', function(e) {
    e.preventDefault()
    console.log(e.target.elements.originalUrl.value)
    e.target.elements.originalUrl.value = ''
})