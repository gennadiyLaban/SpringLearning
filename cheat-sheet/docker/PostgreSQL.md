DockerFile:
```dockerfile
FROM postgres:14
ENV LANG C.UTF-8
RUN localedef -i ru_RU -c -f UTF-8 -A /usr/share/locale/locale.alias ru_RU.UTF-8
```

docker-compose.yml:
```yaml
version: "3.9"
services:
  database:
    build: docker/postgres
    environment:
      POSTGRES_DB: lesson3
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: admin
      PGPORT: 5433
    ports:
      - "5433:5433"
    logging:
      options:
        max-size: 100m
```