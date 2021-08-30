# Ingress

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