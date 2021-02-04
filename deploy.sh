#!/bin/bash

VERSION=$1
SERVER=$2
CLIENT=$3

# Checkout
git clone https://github.com/destroflyer/amara.git
if [ -n "$VERSION" ]; then
  git checkout "$VERSION"
fi

# Build
mkdir workspace
echo -n "../assets/" > workspace/assets.ini
mvn clean install

# Deploy (Client)
rm -rf "${CLIENT}"*
mv assets "${CLIENT}"
mv client/master-client-application/target/libs "${CLIENT}"
mv client/master-client-application/target/master-client-application-0.8.jar "${CLIENT}Amara.jar"
echo -n "./assets/" > "${CLIENT}assets.ini"
curl https://destrostudios.com:8080/apps/1/updateFiles

# Deploy (Server)
mv server/master-server-application/target/master-server-application-0.8-jar-with-dependencies.jar "${SERVER}amara.jar"
sh "${SERVER}control.sh" restart