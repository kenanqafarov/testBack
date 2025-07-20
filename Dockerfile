FROM alpine:3.18.0
RUN apk add --no-cache openjdk17
COPY build/libs/modern-dentistry-1.0.0.jar /app/app.jar
WORKDIR /app
CMD ["java", "-jar", "app.jar"]