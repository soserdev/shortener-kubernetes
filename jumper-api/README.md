# Jumper-Api

Start mongodb using docker.

```bash
docker run -d --name mongodb -p 27017:27017 -v  ~/tmp/mongodb/data:/data/db mongo
```

Build the docker image.

```bash
docker build -t somnidev/jumper-api:latest -t somnidev/jumper-api:0.1 -f Dockerfile .
```

Run the jumper-api using docker and link it to the mongodb docker container.

```bash
docker run --rm --name jumper-api -p 8080:8080 --link mongodb:mongodb somnidev/jumper-api:latest
```

Create a short url.

```bash
curl -v -H'Content-Type: application/json' -d'{"url": "http://www.google.com"}' http://localhost:8080/
```

And the result is.

```bash
% curl -v -H'Content-Type: application/json' -d'{"url": "http://www.google.com"}' http://localhost:8080/
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> POST / HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
> Content-Type: application/json
> Content-Length: 32
> 
* upload completely sent off: 32 out of 32 bytes
< HTTP/1.1 200 
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Wed, 24 Mar 2021 17:57:50 GMT
< 
* Connection #0 to host localhost left intact
{"url":"http://www.google.com","shortUrl":"aHR0cD"}*
```

Get the original url for the short url.

```bash
curl localhost:8080/aHR0cD
```

## Ingress

```bash
curl -v -H'Content-Type: application/json' -d'{"url": "http://www.google.com"}' http://localhost:80/api/
```

## ConfigurationProperties

```java
@Value("${jumper.test}")
private String test;
```