FROM openjdk:17
WORKDIR /wallet-management
COPY ./target/*.jar wallet-management.jar
COPY ./wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
EXPOSE 8082
