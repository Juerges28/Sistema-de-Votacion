# Imagen base con Java 21
FROM eclipse-temurin:21-jdk

# Carpeta de trabajo
WORKDIR /app

# Copiamos Maven y dependencias
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copiamos el código fuente
COPY src ./src

# Compilamos sin tests
RUN ./mvnw clean package -DskipTests

# Ejecutamos el JAR generado
CMD ["java", "-jar", "target/sistemavotos-0.0.1-SNAPSHOT.jar"]
