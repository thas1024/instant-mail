FROM maven as maven-package

MAINTAINER thas thascc1024@gmail.com

RUN git clone https://github.com/thascc/instant-mail.git

RUN cd /instant-mail && mvn install

FROM java:8

COPY --from=maven-package /instant-mail/spring-boot-parent/spring-boot-sample/target/instant-mail-spring-boot-sample*.jar app.jar

RUN mkdir config

ENTRYPOINT ["java","-jar","app.jar"]

CMD ["--Dspring.config.location=/config/"]
