# gca projekt
## Nutzung
### Vorbereitung
1. Minikube installieren
   - Minicube starten
   - ingress addon aktivieren
2. Helm installieren

### Deployment
```
helm install <name> ./gca-chart
```


## Builden
Um alle Anwendungen builden zu können wird ein Postgresql server auf localhost benötigt, der eine Datenbank namens gca und einen entsprechenden Nutzer namens psql mit Password pswl_pw gibt.

## Resilience Pattern
 - Timeout (http://www.vinsguru.com/resilient-microservice-design-timeout-pattern-2/) 
 - Retry (http://www.vinsguru.com/resilient-microservice-design-retry-pattern/) 
 - Circuit Breaker (http://www.vinsguru.com/resilient-microservice-design-circuit-breaker-pattern/) 
 - Bulkhead (https://www.vinsguru.com/resilient-microservice-design-bulkhead-pattern/) 
 - Rate Limiter (http://www.vinsguru.com/resilient-microservice-design-rate-limiter-pattern/) 
