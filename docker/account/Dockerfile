FROM openjdk:8

RUN groupadd --gid 1000 beyond && \
    useradd --uid 1000 --gid 1000 beyond && \
        echo 'Asia/Shanghai' > /etc/timezone && \
    ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

ENV JAVA_OPTS="-server -Xms128m -Xmx512m" \
    JAVA_ARGS="" \
    SPRING_PROFILES_ACTIVE=default

ADD ./bca-svc-account/target/bca-svc-account-*.jar /bca-svc-account.jar
ADD ./docker/account/entrypoint.sh /

RUN chmod +x /entrypoint.sh

USER 1000

EXPOSE 10001

ENTRYPOINT ["./entrypoint.sh"]
