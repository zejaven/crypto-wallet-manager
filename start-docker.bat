mvn clean install -DskipTests=true ^
    && docker build -t eureka-server ./eureka-server ^
    && docker build -t wallet-management-view ./wallet-management-view ^
    && docker build -t wallet-management ./wallet-management ^
    && docker build -t transaction-management ./transaction-management ^
    && docker build -t api-gateway ./api-gateway ^
    && docker-compose up
