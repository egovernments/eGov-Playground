FROM egovio/maven:1.0.0

MAINTAINER Venki<venki@egovernments.org>

# advertise jboss service port
EXPOSE 8080 9990

# copy src code into image
COPY src /usr/local/src/egov-notifications/src

# copy src code into image
COPY pom.xml /usr/local/src/egov-notifications/

# set the context for the rest of the cmds
WORKDIR /usr/local/src/egov-notifications

# build artifacts using maven
RUN mvn clean package

# starting
CMD ["java", "-jar", "target/egov-notification-0.0.1-SNAPSHOT.jar"]

# NOTE: the two 'RUN' commands can probably be combined inside of a single
# script (i.e. RUN build-and-install-app.sh) so that we can also clean up the
# extra files created during the `mvn package' command. that step inflates the
# resultant image by almost 1.0GB.
