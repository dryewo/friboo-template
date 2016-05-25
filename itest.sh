#!/usr/bin/env bash
set -euo pipefail
IFS=$'\t\n'
set -x

lein do clean, install

cd target
lein new friboo com.example.bar/foo-bar
cd foo-bar
lein test
