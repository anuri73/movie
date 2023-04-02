FROM bde2020/spark-submit:3.2.0-hadoop3.2

ARG SBT_VERSION
ENV SBT_VERSION=${SBT_VERSION:-1.8.2}

RUN wget -O - https://github.com/sbt/sbt/releases/download/v${SBT_VERSION}/sbt-${SBT_VERSION}.tgz | gunzip | tar -x -C /usr/local

ENV PATH /usr/local/sbt/bin:${PATH}

WORKDIR /app

# Pre-install base libraries
ADD build.sbt /app/
ADD project /app/project
ADD src /app/src
RUN sbt update

COPY template.sh /

ENV SPARK_APPLICATION_MAIN_CLASS "urmat.jenaliev.App"

# Copy the build.sbt first, for separate dependency resolving and downloading
ONBUILD COPY build.sbt /app/
ONBUILD COPY project /app/project
ONBUILD RUN sbt update

# Copy the source code and build the application
ONBUILD COPY src /app/app
ONBUILD RUN sbt clean assembly

#CMD ["/template.sh"]
ENTRYPOINT ["tail", "-f", "/dev/null"]