FROM openjdk:8

RUN groupadd --gid 1000 beyond && \
    useradd --uid 1000 --gid 1000 beyond && \
        echo 'Asia/Shanghai' > /etc/timezone && \
    ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

ENV JAVA_OPTS="-server -Xms128m -Xmx512m" \
    JAVA_ARGS="" \
    SPRING_PROFILES_ACTIVE=default

ADD ./bca-svc-order/target/bca-svc-order-*.jar /bca-svc-order.jar
ADD ./docker/order/entrypoint.sh /

RUN chmod +x /entrypoint.sh

USER 1000

EXPOSE 10002

ENTRYPOINT ["./entrypoint.sh"]
