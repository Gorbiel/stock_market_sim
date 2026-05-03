#!/bin/sh
set -eu

mkdir -p /backups/hourly /backups/daily /backups/weekly

backup() {
  TYPE="$1"
  FILE="/backups/$TYPE/stock_sim-$TYPE-$(date +%Y-%m-%d_%H-%M-%S).sql"

  pg_dump \
    -h postgres \
    -U "$POSTGRES_USER" \
    -d "$POSTGRES_DB" \
    > "$FILE"

  echo "Created backup: $FILE"
}

cleanup() {
  DIR="$1"
  KEEP="$2"

  ls -1t "$DIR"/*.sql 2>/dev/null | tail -n +"$((KEEP + 1))" | xargs -r rm --
}

while true; do
  HOUR="$(date +%H)"
  DAY_OF_WEEK="$(date +%u)"

  backup hourly
  cleanup /backups/hourly 24

  if [ "$HOUR" = "00" ]; then
    backup daily
    cleanup /backups/daily 7
  fi

  if [ "$HOUR" = "00" ] && [ "$DAY_OF_WEEK" = "1" ]; then
    backup weekly
    cleanup /backups/weekly 4
  fi

  sleep 3600
done