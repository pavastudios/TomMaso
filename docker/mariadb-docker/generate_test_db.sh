#!/usr/bin/env sh
sed -e 's:tommaso:tommaso_test:g' ./db-entrypoint/001\ createDB.sql> ./db-entrypoint/002\ createTestDB.sql
