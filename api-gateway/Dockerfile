FROM openjdk:17
WORKDIR /api-gateway
COPY ./target/*.jar api-gateway.jar
COPY ./wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
EXPOSE 8765
