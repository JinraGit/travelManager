FROM mysql:8.0.33

ENV MYSQL_DATABASE=travelmanager_db
ENV MYSQL_USER=tm_user
ENV MYSQL_PASSWORD=secret123
ENV MYSQL_ROOT_PASSWORD=1234

EXPOSE 3306

COPY init.sql /docker-entrypoint-initdb.d/
