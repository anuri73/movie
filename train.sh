#!/bin/bash

export SPARK_MASTER_URL=spark://${SPARK_MASTER_NAME}:${SPARK_MASTER_PORT}
export SPARK_HOME=/spark
export SPARK_APPLICATION_JAR_LOCATION=/app/target/scala-2.12/root-assembly-0.1.0-SNAPSHOT.jar
export SPARK_APPLICATION_ARGS=1
export SPARK_APPLICATION_MAIN_CLASS=urmat.jenaliev.App

echo "Submit application ${SPARK_APPLICATION_JAR_LOCATION} with main class ${SPARK_APPLICATION_MAIN_CLASS} to Spark master ${SPARK_MASTER_URL}"
    echo "Passing arguments ${SPARK_APPLICATION_ARGS}"
    /${SPARK_HOME}/bin/spark-submit \
        --class "${SPARK_APPLICATION_MAIN_CLASS}" \
        --master "${SPARK_MASTER_URL}" \
        ${SPARK_SUBMIT_ARGS} \
        ${SPARK_APPLICATION_JAR_LOCATION} ${SPARK_APPLICATION_ARGS}