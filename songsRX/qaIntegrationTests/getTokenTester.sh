#! /bin/sh

echo "--- REQUESTING USER TOKEN ------------"
curl -X GET \
     -v "http://localhost:8080/songsRX/rest/auth?userId=mmuster"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING USER TOKEN ------------"
curl -X GET \
     -v "http://localhost:8080/songsRX/rest/auth?userId=eschuler"
echo " "
echo "-------------------------------------------------------------------------------------------------"

echo "--- REQUESTING USERS ------------"
curl -X GET \
     -v "http://localhost:8080/songsRX/rest/auth/user"
echo " "
echo "-------------------------------------------------------------------------------------------------"

