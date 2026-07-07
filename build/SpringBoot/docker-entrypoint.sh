#!/bin/sh
set -e

if [ -n "$DATABASE_URL" ] && [ -z "$SPRING_DATASOURCE_URL" ]; then
  case "$DATABASE_URL" in
    postgresql://*|postgres://*)
      export SPRING_DATASOURCE_URL="jdbc:$DATABASE_URL"
      ;;
  esac
fi

if [ -n "$SPRING_DATASOURCE_URL" ]; then
  case "$SPRING_DATASOURCE_URL" in
    postgresql://*|postgres://*)
      export SPRING_DATASOURCE_URL="jdbc:$SPRING_DATASOURCE_URL"
      ;;
  esac
fi

exec bin/inventory.management.system
