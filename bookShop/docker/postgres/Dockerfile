FROM postgres:14
ENV LANG C.UTF-8
RUN localedef -i ru_RU -c -f UTF-8 -A /usr/share/locale/locale.alias ru_RU.UTF-8 \
    && echo "ALTER SYSTEM SET max_prepared_transactions = 10;" > /docker-entrypoint-initdb.d/init.sql
