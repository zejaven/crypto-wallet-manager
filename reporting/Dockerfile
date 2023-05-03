FROM openjdk:17
WORKDIR /reporting
COPY ./target/*.jar reporting.jar
COPY ./wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
EXPOSE 8083
