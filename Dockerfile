FROM maven:3.9.11
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY testng.xml .
RUN mvn dependency:go-offline
CMD ["mvn", "clean", "test"]