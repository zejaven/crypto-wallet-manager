FROM openjdk:17
WORKDIR /transaction-management
COPY ./target/*.jar transaction-management.jar
COPY ./wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
EXPOSE 8081
