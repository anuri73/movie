FROM bde2020/spark-master:3.1.2-hadoop3.2

ENV SPARK_MASTER_NAME spark-master
ENV SPARK_MASTER_PORT 7077

ARG SBT_VERSION
ENV SBT_VERSION=${SBT_VERSION:-1.8.2}

RUN wget -O - https://github.com/sbt/sbt/releases/download/v${SBT_VERSION}/sbt-${SBT_VERSION}.tgz | gunzip | tar -x -C /usr/local

ENV PATH /usr/local/sbt/bin:${PATH}

WORKDIR /app

ADD build.sbt /app/
ADD project /app/project
ADD src /app/src
ADD train.sh /app/train.sh
ADD recomend.sh /app/recomend.sh

CMD ["/bin/bash", "/master.sh"]