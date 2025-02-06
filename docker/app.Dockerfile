FROM eclipse-temurin:22-jre-alpine
WORKDIR /home
COPY target/master-server-application-0.8-jar-with-dependencies.jar public.pem ./
COPY assets assets
ARG DB_ROOT_PASSWORD
RUN echo //db:3306/amara > db_key_to_the_city.ini && \
    echo root >> db_key_to_the_city.ini && \
    echo -n $DB_ROOT_PASSWORD >> db_key_to_the_city.ini && \
    echo -n ./assets/ > assets.ini && \
    echo -n ./public.pem > public_auth_key_to_the_city.ini
ENTRYPOINT ["java", "-jar", "master-server-application-0.8-jar-with-dependencies.jar"]