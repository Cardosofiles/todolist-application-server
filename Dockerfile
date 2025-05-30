# Etapa 1 - Build da aplicação (caso queira buildar direto aqui)
# FROM maven:3.9.6-eclipse-temurin-17 AS build
# COPY . .
# RUN mvn clean package -DskipTests

# Etapa 2 - Execução do JAR
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copia o JAR gerado localmente
COPY target/*.jar app.jar

# Cria o diretório de logs
RUN mkdir -p /logs

# Exponha a porta 8080
EXPOSE 8080

# Inicia a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
