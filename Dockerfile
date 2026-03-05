FROM maven:3.9.6-eclipse-temurin-17

WORKDIR /app

# Кэширование зависимостей
COPY pom.xml .
RUN mvn -B -q -e -DskipTests dependency:go-offline

# Копируем весь проект
COPY . .

CMD ["mvn", "clean", "test"]