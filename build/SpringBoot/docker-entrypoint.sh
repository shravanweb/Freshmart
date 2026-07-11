#!/bin/sh
set -e

to_jdbc_url() {
  raw="$1"
  case "$raw" in
    jdbc:postgresql://*|jdbc:postgres://*)
      jdbc="$raw"
      ;;
    postgresql://*|postgres://*)
      jdbc="jdbc:$raw"
      ;;
    *)
      printf '%s' "$raw"
      return
      ;;
  esac

  # Credentials belong in SPRING_DATASOURCE_USERNAME/PASSWORD, not the JDBC URL.
  printf '%s' "$jdbc" | sed -E 's#^(jdbc:postgres(ql)?://)[^/@]+@#\1#'
}

if [ -n "$DATABASE_URL" ] && [ -z "$SPRING_DATASOURCE_URL" ]; then
  export SPRING_DATASOURCE_URL="$(to_jdbc_url "$DATABASE_URL")"
fi

if [ -n "$SPRING_DATASOURCE_URL" ]; then
  export SPRING_DATASOURCE_URL="$(to_jdbc_url "$SPRING_DATASOURCE_URL")"
fi

exec bin/inventory.management.system
