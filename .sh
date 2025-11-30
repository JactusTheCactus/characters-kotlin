#!/usr/bin/env bash
set -euo pipefail
flag() {
	for f in "$@"; do
		[[ -e ".flags/$f" ]] || return 1
	done
}
DIRS=(
	logs
	bin
)
for i in "${DIRS[@]}"; do
	rm -rf "$i" || :
	mkdir -p "$i"
done
exec > logs/app.log
find . -name "*.apk" -exec cp {} bin \;
find . -name "*.gradle" -exec echo {} \;