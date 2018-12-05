#! /bin/sh

echo "--- POSTING A JSON CONTACT ------------------"
curl -X PUT \
     -H "Content-Type: application/json" \
     -H "Accept: text/plain" \
     -d "@oneSong2.json" \
     -v "http://localhost:8080/songsRX/rest/songs/7"
echo " "
echo "-------------------------------------------------------------------------------------------------"

