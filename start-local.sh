if [ ! -d "axon-server" ]; then
    wget "https://download.axoniq.io/axonserver/AxonServer.zip" -O "AxonServer.zip"
    unzip "AxonServer.zip" -d "axon-server"
    rm "AxonServer.zip"
fi

cd axon-server/AxonServer* || exit;
java -jar axonserver.jar &
cd ../..

mvn clean install -DskipTests=true

java -jar eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar &
sleep 10

java -jar wallet-management-view/target/wallet-management-view-0.0.1-SNAPSHOT.jar &
java -jar wallet-management/target/wallet-management-0.0.1-SNAPSHOT.jar &
java -jar transaction-management/target/transaction-management-0.0.1-SNAPSHOT.jar &
sleep 10

java -jar api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar &
read -p "Press Enter to continue..."

