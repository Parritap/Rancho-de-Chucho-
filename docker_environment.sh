#!/bin/bash

declare -r network="my-net"
docker network create $network

#Cuando vayan a correr el Pgadmin, el hostname del servidor es postgres, esto sucede porque
# docker tiene un DNS Resolver integrado que cambia el nombre del contenedor por la IP dentro
# de la red bridged de docker. Dato curioso para que lo tengan en cuenta.
docker pull dpage/pgadmin4 && docker run \
 --name pgadmin \
 --network $network \
 -p 5050:80 \
 -e PGADMIN_DEFAULT_EMAIL=root@gmail.com \
 -e PGADMIN_DEFAULT_PASSWORD=root \
 -d dpage/pgadmin4

docker run -d --name pos-rest \
  --network $network \
  -e POSTGRES_USER=user \
  -e POSTGRES_PASSWORD=apollo \
  -e POSTGRES_DB=restaurante \
  -p 5555:5432 \
  -v pg_data_res:/var/lib/postgresql/data \
  postgres
