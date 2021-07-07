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

## Ingress rules

Ingress has the ability to rewrite the target uri - see the [Official Documentation](https://kubernetes.github.io/ingress-nginx/examples/rewrite/#rewrite-target). In order to activate this annotation add `nginx.ingress.kubernetes.io/rewrite-target` and the rule.

The following example shows how to create an Ingress rule with a rewrite annotation - see the official documentation.

```yaml
apiVersion: networking.k8s.io/v1beta1
kind: Ingress
metadata:
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /$2
  name: rewrite
  namespace: default
spec:
  rules:
  - host: rewrite.bar.com
    http:
      paths:
      - backend:
          serviceName: http-svc
          servicePort: 80
        path: /something(/|$)(.*)
```

The ingress definition above will result in the following rewrites.

- `rewrite.bar.com/something` rewrites to `rewrite.bar.com/`
- `rewrite.bar.com/something/` rewrites to `rewrite.bar.com/`
- `rewrite.bar.com/something/new` rewrites to `rewrite.bar.com/new`


## CORS

```yaml
Access to fetch at 'http://jumper.io/api/shorturl' from origin 'http://localhost' has 
been blocked by CORS policy: Response to preflight request doesn't pass access 
control check: No 'Access-Control-Allow-Origin' header is present on the requested 
resource. If an opaque response serves your needs, set the request's mode to 
'no-cors' to fetch the resource with CORS disabled.
```

## Kubectl 

```bash
me@MBP-von-Simon kubernetes % kubectl get all          
NAME                                 READY   STATUS    RESTARTS   AGE
pod/jumper-api-5846cd4c6b-9dp9n      1/1     Running   0          19h
pod/jumper-frontend-b784cd56-2z9d9   1/1     Running   0          24h
pod/mongodb-5b4859859c-87kzk         1/1     Running   0          24h

NAME                      TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)     AGE
service/jumper-api        ClusterIP   10.103.168.172   <none>        80/TCP      19h
service/jumper-frontend   ClusterIP   10.109.59.203    <none>        80/TCP      24h
service/jumper-mongodb    ClusterIP   10.100.23.3      <none>        27017/TCP   24h
service/kubernetes        ClusterIP   10.96.0.1        <none>        443/TCP     26d

NAME                              READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/jumper-api        1/1     1            1           19h
deployment.apps/jumper-frontend   1/1     1            1           24h
deployment.apps/mongodb           1/1     1            1           24h

NAME                                       DESIRED   CURRENT   READY   AGE
replicaset.apps/jumper-api-5846cd4c6b      1         1         1       19h
replicaset.apps/jumper-frontend-b784cd56   1         1         1       24h
replicaset.apps/mongodb-5b4859859c         1         1         1       24h
me@MBP-von-Simon kubernetes % kubectl exec --stdin --tty pod/mongodb-5b4859859c-87kzk -- /bin/bash
root@mongodb-5b4859859c-87kzk:/# mongo
MongoDB shell version v3.6.5
connecting to: mongodb://127.0.0.1:27017
MongoDB server version: 3.6.5
Welcome to the MongoDB shell.
For interactive help, type "help".
For more comprehensive documentation, see
	http://docs.mongodb.org/
Questions? Try the support group
	http://groups.google.com/group/mongodb-user
Server has startup warnings: 
2021-04-12T09:43:43.484+0000 I CONTROL  [initandlisten] 
2021-04-12T09:43:43.484+0000 I CONTROL  [initandlisten] ** WARNING: Access control is not enabled for the database.
2021-04-12T09:43:43.484+0000 I CONTROL  [initandlisten] **          Read and write access to data and configuration is unrestricted.
2021-04-12T09:43:43.484+0000 I CONTROL  [initandlisten] 
> show dbs
admin   0.000GB
config  0.000GB
jumper  0.000GB
local   0.000GB
> use jumper
switched to db jumper
> show collections
urls
> db.urls.find()
{ "_id" : ObjectId("60741959ee53f01f6ab0309d"), "shortUrl" : "YWMzMG", "originalUrl" : "http://www.google.com", "_class" : "io.jumper.api.model.ShortUrl" }
{ "_id" : ObjectId("60741adcee53f01f6ab0309e"), "shortUrl" : "ODRlY2", "originalUrl" : "http://www.google.com", "_class" : "io.jumper.api.model.ShortUrl" }
{ "_id" : ObjectId("60741c9eee53f01f6ab0309f"), "shortUrl" : "Nzc4ZT", "originalUrl" : "http://swr3.de", "_class" : "io.jumper.api.model.ShortUrl" }
{ "_id" : ObjectId("607460287a23f35c285da9b8"), "shortUrl" : "ZTZjOT", "originalUrl" : "https://www.bbc.com/news/uk-56721559", "_class" : "io.jumper.api.model.ShortUrl" }
> db.urls.drop()
true
> db show collections
2021-04-13T14:22:18.804+0000 E QUERY    [thread1] SyntaxError: missing ; before statement @(shell):1:3
> show collections
> %                                                                           me@MBP-von-Simon kubernetes % ```

