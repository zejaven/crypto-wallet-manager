if [ ! -d "axon-server" ]; then
    wget "https://download.axoniq.io/axonserver/AxonServer.zip" -O "AxonServer.zip"
    unzip "AxonServer.zip" -d "axon-server"
    rm "AxonServer.zip"
fi

cd axon-server/AxonServer* || exit;
java -jar axonserver.jar &
axonProcess=$!
cd ../..

sleep 20

mvn test

kill $axonProcess
