FROM openjdk:8-jre-stretch

RUN apt-get update -y
RUN apt-get install -y build-essential
RUN apt-get install -y cmake git libgtk2.0-dev pkg-config libavcodec-dev libavformat-dev libswscale-dev
RUN apt-get install -y python-dev python-numpy libtbb2 libtbb-dev libjpeg-dev libpng-dev libtiff5-dev libdc1394-22-dev

RUN cd /tmp && wget https://github.com/opencv/opencv/archive/4.0.1.tar.gz && \
    tar xvzf 4.0.1.tar.gz && rm -f 4.0.1.tar.gz && cd opencv-4.0.1 && mkdir build && \
    cd build/ && cmake -D CMAKE_BUILD_TYPE=Release .. && make -j4 && make install && cd / && \
	rm -rf /tmp/opencv-4.0.1
	


#FROM tomcat:8.5-jre8
#VOLUME /tmp
#COPY SudokuServer-1.0.war /usr/local/tomcat/webapps/app.war
#RUN sh -c 'touch /usr/local/tomcat/webapps/app.war'
