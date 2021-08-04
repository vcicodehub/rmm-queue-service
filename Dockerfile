# Dockerfile - Repair Harmonization POC
# OM SQS Processing
FROM adoptopenjdk/openjdk11
ARG JAR_FILE=target/*.jar
EXPOSE 80
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]