# Jumper-Kubernetes

## Create docker images

Create the frontend image after `cd jumper-frontend`.

```bash
docker build -t somnidev/jumper-frontend:latest -t somnidev/jumper-frontend:0.1 -f Dockerfile .
```

Create the image for the api after `cd jumper-api`.

```bash
docker build -t somnidev/jumper-api:latest -t somnidev/jumper-api:0.1 -f Dockerfile .
```

## Create and get short url using api

Create a short url.

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

Run the docker container.

```bash
 % docker run -d --name mongodb -p 27017:27017 -v  ~/tmp/mongodb/data:/data/db mongo
b2db2808bd5ab242bdf78a6121b33813edaab090edeb2b19e657100983f926a7
```

Connect to mongo.

```bash
% mongo
MongoDB shell version v4.4.3
connecting to: mongodb://127.0.0.1:27017/?compressors=disabled&gssapiServiceName=mongodb
Implicit session: session { "id" : UUID("9ae8372c-ada9-4e51-8289-f28d983c881d") }
MongoDB server version: 4.4.4
---
The server generated these startup warnings when booting: 
        2021-04-06T10:30:15.251+00:00: Access control is not enabled for the database. Read and write access to data and configuration is unrestricted
---
---
        Enable MongoDB's free cloud-based monitoring service, which will then receive and display
        metrics about your deployment (disk utilization, CPU, operation statistics, etc).

        The monitoring data will be available on a MongoDB website with a unique URL accessible to you
        and anyone you share the URL with. MongoDB may use this information to make product
        improvements and to suggest MongoDB products and deployment options to you.

        To enable free monitoring, run the following command: db.enableFreeMonitoring()
        To permanently disable this reminder, run the following command: db.disableFreeMonitoring()
---
```

Show databases, collections, and find documents.

```bash
> show dbs
admin   0.000GB
config  0.000GB
jumper  0.000GB
local   0.000GB
test    0.000GB
> use jumper
switched to db jumper
> show collections
urls
> db.urls.find()
{ "_id" : ObjectId("605cb7e386b0962bc9cc55dd"), "shortUrl" : "YjkyNz", "originalUrl" : "http://www.google.com", "_class" : "io.jumper.backend.model.ShortUrl" }
{ "_id" : ObjectId("605cb81086b0962bc9cc55de"), "shortUrl" : "MGY2Mj", "originalUrl" : "http://www.google.com", "_class" : "io.jumper.backend.model.ShortUrl" }
```

## Kubernetetes exec into pod

```bash
kubectl exec --stdin --tty mongodb-5b4859859c-6l7kn -- /bin/bash
```

##