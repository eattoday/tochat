FROM anapsix/alpine-java:8_jdk_unlimited
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone
COPY ./target/*.jar app.jar
ENTRYPOINT java -jar app.jar