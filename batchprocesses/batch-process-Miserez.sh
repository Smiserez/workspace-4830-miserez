#!/bin/sh
  for f in "$1"/*; do
    if [ -f "$f" ]; then
	FILE_NAME="$(basename "${f}")"
	WORD_COUNT="$(wc -w "${f}" | cut -d' ' -f1)"
	SIZE="$(du -sh "${f}" | cut -f1)"
	echo "Word Count: $WORD_COUNT"
	echo "File size: $SIZE"
    fi
done

