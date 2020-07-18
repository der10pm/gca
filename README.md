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
