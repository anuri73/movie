#!/bin/bash

export SPARK_MASTER_URL=spark://${SPARK_MASTER_NAME}:${SPARK_MASTER_PORT}
export SPARK_HOME=/spark
export SPARK_APPLICATION_JAR_LOCATION=/app/cli/target/scala-2.12/app-cli.jar
export SPARK_APPLICATION_ARGS="train -m 71 -m 1 -m 22"
export SPARK_APPLICATION_MAIN_CLASS=urmat.jenaliev.cli.App

echo "Submit application ${SPARK_APPLICATION_JAR_LOCATION} with main class ${SPARK_APPLICATION_MAIN_CLASS} to Spark master ${SPARK_MASTER_URL}"
echo "Passing arguments ${SPARK_APPLICATION_ARGS}"
/${SPARK_HOME}/bin/spark-submit \
  --class "${SPARK_APPLICATION_MAIN_CLASS}" \
  --master "${SPARK_MASTER_URL}" \
  ${SPARK_SUBMIT_ARGS} \
  ${SPARK_APPLICATION_JAR_LOCATION} ${SPARK_APPLICATION_ARGS}
