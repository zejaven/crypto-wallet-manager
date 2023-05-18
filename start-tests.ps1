if (-not (Test-Path "axon-server")) {
    Invoke-WebRequest -Uri "https://download.axoniq.io/axonserver/AxonServer.zip" -OutFile ".\AxonServer.zip"
    Expand-Archive -Path ".\AxonServer.zip" -DestinationPath ".\axon-server"
    Remove-Item -Path ".\AxonServer.zip"
}
cd "axon-server\AxonServer*";
$axonProcess = Start-Process -FilePath "java" -ArgumentList "-jar axonserver.jar" -PassThru
cd "..\.."

Start-Sleep -Seconds 20

mvn test
Stop-Process -Id $axonProcess.Id
