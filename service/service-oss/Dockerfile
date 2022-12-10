FROM java:8
EXPOSE 8083

VOLUME /tmp
ADD service-oss.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
