# Spring boot service exposing REST API to submit Spark job on minikube cluster

Run [**`TelosMLTask`**](src/main/java/com/telos/spark/TelosMLTask.java) as Spring boot application.

> [!IMPORTANT]
> Set VM argument `--add-exports java.base/sun.nio.ch=ALL-UNNAMED`

## Requirements

This demo requires:

- JDK 17
- `maven`
- `docker`

## Build

```shell
mvn clean package

docker image build . -t spark-spring-boot-service:0.0.1  -f Dockerfile --no-cache
minikube image load spark-spring-boot-service:0.0.1
minikube image rm spark-spring-boot-service:0.0.1 

kubectl create serviceaccount spark
kubectl create clusterrolebinding spark-role --clusterrole=edit --serviceaccount=default:spark --namespace=default

kubectl apply -f fabric8-rbac.yaml
kubectl apply -f deployment.yaml

kubectl port-forward <pod name> 8090:8090

kubectl delete -f deployment.yaml
kubectl delete pods --all
kubectl delete pods --all -n default
kubectl delete pod <pod name>

kubectl delete -f  fabric8-rbac.yaml
kubectl delete -f deployment.yaml
```