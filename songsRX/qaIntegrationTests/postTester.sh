#! /bin/sh

echo "--- POSTING A JSON CONTACT ------------------"
curl -X POST \
     -H "Content-Type: application/json" \
     -H "Accept: text/plain" \
     -d "@oneSong.json" \
     -v "http://localhost:8080/songsRX/rest/songs"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- POSTING A XML CONTACT ------------------"
curl -X POST \
     -H "Content-Type: application/xml" \
     -H "Accept: text/plain" \
     -d "@oneSong.xml" \
     -v "http://localhost:8080/songsRX/rest/songs"
echo " "
echo "-------------------------------------------------------------------------------------------------"
