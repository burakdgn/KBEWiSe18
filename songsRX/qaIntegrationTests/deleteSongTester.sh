#! /bin/sh

echo "--- DELETING ONE SONG ------------"
curl -X DELETE \
     -v "http://localhost:8080/songsRX/rest/songs/2"
echo " "
echo "-------------------------------------------------------------------------------------------------"


echo "--- REQUESTING ALL XML CONTACT--------"
curl -X GET \
     -H "Accept: application/xml" \
     -v "http://localhost:8080/songsRX/rest/songs"
echo " "
echo "-------------------------------------------------------------------------------------------------"
