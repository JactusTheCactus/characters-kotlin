#!/usr/bin/env bash
set -euo pipefail
flag() {
	for f in "$@"; do
		[[ -e ".flags/$f" ]] || return 1
	done
}
DIRS=(
	bin
	dist
	logs
)
for i in "${DIRS[@]}"; do
	rm -rf "$i" || : > /dev/null 2>& 1
	mkdir -p "$i"
done
exec > logs/app.log 2>& 1
find . \
	-name "*.apk" \
	-not -name "*-androidTest.apk" \
	-exec cp {} bin \; > /dev/null 2>& 1
for i in src/*.c; do
	if [[ ! $i = "src/*.c" ]]; then
		o="$(echo $i | perl -pe 's/src\/(.+)\.c/dist\/$1/g')"
		echo "$i -> $o"
		gcc "$i" -o "$o"
		./$o
	fi
done