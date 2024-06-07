FROM openjdk:8-jdk
WORKDIR /app
EXPOSE 21013
COPY ./target/*.jar /dashboard.jar
ENV TZ="Asia/Shanghai"
ENV SKIP_TESTS=true
#RUN bash -c 'touch /dashboard.jar'
ENTRYPOINT ["java","-jar","/dashboard.jar"]