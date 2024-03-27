#!/bin/bash
set -e

# Create admin role if it doesn't exist
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    DO \$\$
    BEGIN
        IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'admin') THEN
            CREATE ROLE admin WITH LOGIN PASSWORD 'admin';
        END IF;
    END
    \$\$;
EOSQL

# Create tour_service_db if it doesn't exist
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE IF NOT EXISTS tour_service_db;
    GRANT ALL PRIVILEGES ON DATABASE tour_service_db TO admin;
EOSQL

# Create tour_attractions_db if it doesn't exist
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE DATABASE IF NOT EXISTS tour_attractions_db;
    GRANT ALL PRIVILEGES ON DATABASE tour_attractions_db TO admin;
EOSQL
