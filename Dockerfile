# Використовуємо офіційний образ OpenJDK 17
FROM openjdk:17-jdk-slim

# Встановлюємо робочу директорію в контейнері
WORKDIR /app

# Копіюємо jar-файл у контейнер
COPY target/*.jar app.jar/

# Відкриваємо порт (зазвичай 8080 для Spring Boot)
EXPOSE 8080

# Вказуємо команду для запуску додатку
ENTRYPOINT ["java", "-jar", "/app.jar"]
