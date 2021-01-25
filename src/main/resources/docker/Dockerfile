FROM java:8

VOLUME /tmp

ADD /healthautocommit-0.0.1-SNAPSHOT.jar autocommit.jar

EXPOSE 8001

ENTRYPOINT ["java","-jar","/autocommit.jar"]