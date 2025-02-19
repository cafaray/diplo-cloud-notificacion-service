#!/bin/bash

# $1 Deployment name
# $2 Image

DEPLOYMENT_NAME="notificaciones-service"
IMAGE="docker.io/cafaray/notificaciones:V8"

echo "Working with values: DEPLOYMENT_NAME = $DEPLOYMENT_NAME & IMAGE = $IMAGE \n"
echo "starting deployment \n"
kubectl delete deployment $DEPLOYMENT_NAME
kubectl create deployment $DEPLOYMENT_NAME --replicas=2 --image=$IMAGE
echo "verifying deployment \n"
CURRENT_ALREADY_PODS=$(kubectl get deployments $DEPLOYMENT_NAME | awk 'NR==2{print $4}')
MINIMAL_AVAILABLE_PODS=2
COUNTER=1
while [ $((CURRENT_ALREADY_PODS)) -lt $((MINIMAL_AVAILABLE_PODS)) ]
  do
    echo "waiting for pods \n"
    CURRENT_ALREADY_PODS=$(kubectl get deployments $DEPLOYMENT_NAME | awk 'NR==2{print $4}')
    COUNTER=$(( $COUNTER + 1 ))
    if [ $COUNTER -gt 20 ]
    then
      echo "It looks something went wrong with the deployment $DEPLOYMENT_NAME. I can not wait more ... \n"
      exit 1
    else
      sleep 3
    fi
done;
echo "deployment ready, here the status: \n"
kubectl get deployment $DEPLOYMENT_NAME -o wide
