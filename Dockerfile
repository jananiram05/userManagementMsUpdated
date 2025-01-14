FROM openjdk:17
COPY ./build/libs/userManagementMs-0.0.1-SNAPSHOT.jar userManagementMs.jar
EXPOSE 8081
CMD ["java","-jar","userManagementMs.jar"]