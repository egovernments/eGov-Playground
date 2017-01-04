FROM egovdevops/egov-jboss:latest

MAINTAINER Venki<venki@egovernments.org>

# copy src code into image
COPY src /usr/local/src/egov-notifications/src

# copy src code into image
COPY pom.xml /usr/local/src/egov-notifications/

# set the context for the rest of the cmds
WORKDIR /usr/local/src/egov-notifications

# build artifacts using maven
RUN mvn package -DskipTests

# deploy application into jboss
RUN DEPLOY_DIR=$JBOSS_HOME/standalone/deployments \
 && cp target/egov-notification.war $DEPLOY_DIR \
 && cd $DEPLOY_DIR \
 && touch $(ls egov-notification.war).dodeploy

CMD $JBOSS_HOME/bin/standalone.sh --debug --server-config=standalone.xml -Dspring.profiles.active=$ENV -b 0.0.0.0 -bmanagement=0.0.0.0

# NOTE: the two 'RUN' commands can probably be combined inside of a single
# script (i.e. RUN build-and-install-app.sh) so that we can also clean up the
# extra files created during the `mvn package' command. that step inflates the
# resultant image by almost 1.0GB.
