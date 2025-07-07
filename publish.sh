#!/bin/bash

set -e

if [ -z "$DISCORD_WEBHOOK" ]; then
  echo "no discord webhook env var set"
  exit 1
fi

./build_plugin.sh

PLUGIN_VERSION=$(mvn help:evaluate -Dexpression=SurvivalUHC.version -q -DforceStdout)
PLUGIN_LOCATION="./build/SurvivalUHC-${PLUGIN_VERSION}.jar"
COMMIT_MSG=${COMMIT_MSG:-$(git log -1 --pretty=format:"%s")}
COMMIT_SHA=${COMMIT_SHA:-$(git rev-parse HEAD)}

# if commit details is multiline, only use first line
COMMIT_MSG=$(echo "${COMMIT_MSG}" | head -1)

curl --fail-with-body \
  -H "Content-Type: multipart/form-data" \
  -X POST \
  -F "payload_json={ \"content\":\"**SurvivalUHC ${PLUGIN_VERSION}** is now available!\nCommit: ${COMMIT_MSG}\nView changes: <https://github.com/stefan-cooper/SurvivalUHC/commit/${COMMIT_SHA}> \" }" \
  -F "file1=@${PLUGIN_LOCATION}" \
  "${DISCORD_WEBHOOK}"

