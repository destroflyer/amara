#!/usr/bin/env bash
cd /var/www/amara/downloads/
rm -rf Amara
mkdir -p Amara/assets/Interface/client
cp /root/amara/update/Amara.jar Amara/Amara.jar
cp /root/amara/update/Amara.bat Amara/Amara.bat
cp -r /root/amara/update/assets/Interface/client Amara/assets/Interface/
echo "./assets/" > Amara/assets.ini
rm -f Amara.zip
zip -r Amara.zip Amara