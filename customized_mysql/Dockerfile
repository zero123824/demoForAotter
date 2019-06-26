FROM mysql
ENV MYSQL_ROOT_PASSWORD 123456
ENV MYSQL_DATABASE demo
ADD createtable.sql /docker-entrypoint-initdb.d
EXPOSE 3306