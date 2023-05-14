DC=docker-compose
UP=@$(DC) -f docker-compose.yml up -d
BASH=/bin/bash
SH=/bin/sh

assembly:
	@$(DC) exec spark-master sbt assembly

build-and-up:
	@$(RUN) make copy-env
	@$(RUN) make create-volume
	@$(UP) --build --remove-orphans

copy-env:
	@$(RUN) cp hadoop.env.dist .hadoop.env 2>/dev/null

create-volume:
	@$(RUN) docker volume create --name=hadoop.namenode
	@$(RUN) docker volume create --name=hadoop.datanode
	@$(RUN) docker volume create --name=hadoop.historyserver

hdfs:
	@$(RUN) docker exec -it namenode bash

import:
	@$(RUN) docker exec namenode hdfs dfs -mkdir -p /data/ml-100k
	@$(RUN) docker exec namenode hdfs dfs -put /data/hadoop/namenode/ml-100k /data

install:
	make build-and-up
	sleep 10
	make import

ps:
	@$(DC) ps

stop:
	@$(DC) stop

down:
	@$(DC) down

sbt:
	@$(DC) exec spark-master sbt

ssh:
	@$(DC) exec spark-master bash

recomend:
	@$(DC) exec spark-master rm -rf /app/target
	make assembly
	@$(DC) exec spark-master /app/recomend.sh

remove:
	@$(DC) down -v --rmi all --remove-orphans
	make remove-volume

remove-volume:
	@$(RUN) docker volume rm hadoop.namenode -f
	@$(RUN) docker volume rm hadoop.datanode -f
	@$(RUN) docker volume rm hadoop.historyserver -f

test:
	@$(DC) exec spark-master sbt test

train:
	@$(DC) exec spark-master rm -rf /app/target
	make assembly
	@$(DC) exec spark-master /app/train.sh

up:
	@$(UP)