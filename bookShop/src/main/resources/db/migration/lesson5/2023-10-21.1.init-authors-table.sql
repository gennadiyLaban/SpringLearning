--liquibase formatted sql

--changeset 2023-10-21.1.init-authors-table runOnChange:false
--preconditions onFail:MARK_RAN
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM current_schema as sch where sch = 'shop'
do '
DECLARE
    dbname      varchar(100) := current_database();
    username    varchar(100) := current_user;
    schema      varchar(100) := ''${bookshop.db.schema}'';
    searchpath  varchar(100) := schema || '',public'';
BEGIN
    EXECUTE ''CREATE SCHEMA IF NOT EXISTS '' || schema;
    EXECUTE ''ALTER ROLE '' || username || '' IN DATABASE '' || dbname || '' SET SEARCH_PATH='' || searchpath;
    EXECUTE ''SET SEARCH_PATH='' || searchpath;
    RAISE NOTICE ''db=%; usr=%; current_schema=%'', current_database(), current_user, current_schema;
END;
' language plpgsql;
