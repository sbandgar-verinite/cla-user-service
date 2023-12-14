#!/bin/bash
set -e

mysql -u "$MYSQL_ROOT_PASSWORD" -p"$MYSQL_ROOT_PASSWORD" <<-EOSQL
  GRANT ALL PRIVILEGES ON *.* TO 'admin'@'%';
EOSQL

