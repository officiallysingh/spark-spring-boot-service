#!/bin/bash

# Check if JOB_NAME is not set or is empty
if [ -z "${JOB_NAME}" ]; then
  echo "ERROR: JOB_NAME environment variable is not set."
  exit 1
fi
# Check if JOB_CLASS_NAME is not set or is empty
if [ -z "${JOB_CLASS_NAME}" ]; then
  echo "ERROR: JOB_CLASS_NAME environment variable is not set."
  exit 1
fi
# Check if JOB_IMAGE_TAG is not set or is empty
if [ -z "${JOB_IMAGE_TAG}" ]; then
  echo "ERROR: JOB_IMAGE_TAG environment variable is not set."
  exit 1
fi

# Take defaults if following not set or is empty
if [ -z "${MASTER_URL}" ]; then
  echo "WARN: MASTER_URL environment variable is not set so taking default value: https://kubernetes.default.svc"
fi
if [ -z "${DEPLOY_MODE}" ]; then
  echo "WARN: DEPLOY_MODE environment variable is not set so taking default value: cluster"
fi
if [ -z "${SERVICE_ACCOUNT_NAME}" ]; then
  echo "WARN: SERVICE_ACCOUNT_NAME environment variable is not set so taking default value: spark"
fi
if [ -z "${SPARK_USER}" ]; then
  echo "WARN: SPARK_USER environment variable is not set so taking default value: spark"
fi
if [ -z "${EXECUTOR_INSTANCES}" ]; then
  echo "WARN: EXECUTOR_INSTANCES environment variable is not set so taking default value: 2"
fi

# Define the Spark submit command
SPARK_SUBMIT_CMD="./bin/spark-submit \
--master k8s://$MASTER_URL \
--deploy-mode ${DEPLOY_MODE:=cluster} \
--name $JOB_NAME \
--class $JOB_CLASS_NAME \
--conf spark.kubernetes.container.image=$JOB_NAME:$JOB_IMAGE_TAG \
--conf spark.kubernetes.authenticate.driver.serviceAccountName=${SERVICE_ACCOUNT_NAME:=spark} \
--conf spark.kubernetes.driverEnv.SPARK_USER=${SPARK_USER:=spark} \
--conf spark.executor.instances=${EXECUTOR_INSTANCES:=2} \
local:///opt/spark/job-apps/$JOB_NAME.jar"

# Execute the Spark submit command
echo "Executing Spark submit command..."
echo "SPARK_SUBMIT_CMD is: $SPARK_SUBMIT_CMD"

eval $SPARK_SUBMIT_CMD

# Check the exit status of the Spark submit command
if [ $? -eq 0 ]; then
  echo "Spark submit command executed successfully."
else
  echo "Spark submit command failed."
  exit 1
fi