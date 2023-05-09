FROM openjdk:17
WORKDIR /wallet-management-view
COPY ./target/*.jar wallet-management-view.jar
COPY ./wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
EXPOSE 8083
