#!/bin/bash
docker pull postgres
docker run -d --name postgres \
  -e POSTGRES_USER=user \
  -e POSTGRES_PASSWORD=apollo \
  -e POSTGRES_DB=restaurante \
  -p 5432:5432 \
  -v pg_data:/var/lib/postgresql/data \
  postgres