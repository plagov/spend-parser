FROM bellsoft/liberica-runtime-container:jre-25_37-musl
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
