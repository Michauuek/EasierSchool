FROM openjdk:17
ADD target/EasierSchool-0.0.1-SNAPSHOT.jar EasierSchool-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "EasierSchool-0.0.1-SNAPSHOT.jar"]