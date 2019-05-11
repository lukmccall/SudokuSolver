FROM serverbase

VOLUME /tmp
COPY SudokuServer-1.0.war /usr/local/tomcat/webapps/app.war
RUN sh -c 'touch /usr/local/tomcat/webapps/app.war'

#CMD ["catalina.sh", "run"]