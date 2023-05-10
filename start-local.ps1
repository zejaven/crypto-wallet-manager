if (-not (Test-Path "axon-server")) {
    Invoke-WebRequest -Uri "https://download.axoniq.io/axonserver/AxonServer.zip" -OutFile ".\AxonServer.zip"
    Expand-Archive -Path ".\AxonServer.zip" -DestinationPath ".\axon-server"
    Remove-Item -Path ".\AxonServer.zip"
}
cd "axon-server\AxonServer*";
Start-Process -FilePath "java" -ArgumentList "-jar axonserver.jar" -PassThru
cd "..\.."

mvn clean install -DskipTests=true

Start-Process -FilePath "java" -ArgumentList "-jar eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar" -PassThru

Start-Sleep -Seconds 10

Start-Process -FilePath "java" -ArgumentList "-jar wallet-management-view/target/wallet-management-view-0.0.1-SNAPSHOT.jar" -PassThru
Start-Process -FilePath "java" -ArgumentList "-jar wallet-management/target/wallet-management-0.0.1-SNAPSHOT.jar" -PassThru
Start-Process -FilePath "java" -ArgumentList "-jar transaction-management/target/transaction-management-0.0.1-SNAPSHOT.jar" -PassThru

Start-Sleep -Seconds 10

Start-Process -FilePath "java" -ArgumentList "-jar api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar" -PassThru
