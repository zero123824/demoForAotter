FROM openjdk
ADD target/usercenter-0.0.1-SNAPSHOT.jar usercenter-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/usercenter-0.0.1-SNAPSHOT.jar"]
EXPOSE 8081