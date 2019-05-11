FROM baseserver

VOLUME /tmp
COPY SudokuServer-1.0.war /usr/local/tomcat/webapps/app.war
RUN sh -c 'touch /usr/local/tomcat/webapps/app.war'

#CMD ["catalina.sh", "run"]

# u can run this container using something like this
# docker run --name server -it -p 8080:8080 -v "$(pwd)/../../target/SudokuServer-1.0:/usr/local/tomcat/webapps/app" sudokuserver