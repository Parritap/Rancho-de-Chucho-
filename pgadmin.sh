#!/usr/bin/env bash
#email is root@gmail.com
#password is root
docker pull dpage/pgadmin4 && docker run --name pgadmin -p 5050:80 -e PGADMIN_DEFAULT_EMAIL=root@gmail.com -e PGADMIN_DEFAULT_PASSWORD=root -d dpage/pgadmin4           ─╯
