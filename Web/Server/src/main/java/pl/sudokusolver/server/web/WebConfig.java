package pl.sudokusolver.server.web;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.sudokusolver.recognizerlib.Init;
import pl.sudokusolver.server.bean.DigitRecognizer;
import pl.sudokusolver.solver.ISolver;
import pl.sudokusolver.solver.SmartSolver;
/*

README możesz znaleźć również pod adresem https://github.com/lukmccall/SudokuSolver/blob/master/README.md.

# SudokuSolver #
----
###### Projekt został stworzony przez:
* Daniel Dobrowolski
* Małgorzata Dymek
* Tomasz Janik
* Łukasz Kosmaty
* Nikodem Kwaśniak
* Dawid Szczerba

## Krótki opis aplikacji ##
------
Aplikacja jest połączeniem sudoku grabbera, który jest w stanie przeczytać łamigłówkę ze zdjęcia oraz sudoku solvera, który rozwiązuje podane przez użytkownika sudoku.
Aplikacja została podzielona na dwa główne moduły.
* Web - część odpowiedzialna za serwer oraz proces rozwiązywania/rozpoznawania sudoku. Moduł ten również został podzielony na pod moduły.
    * RecognizerLib - część odpowiedzialna ze rozpoznanie sudoku.
    * Server - część odpowiedzialna za obsługę zapytań http.
    * Solver - algorytm rozwiązujący łamigłówkę.
* App - część odpowiedzialna za dostarczenie przyjaznego gui do porozumiewania się z serwerem.

Dzięki modułowemu podejściu zyskaliśmy dużą swobodę w wymianie poszczególnych elementów oraz ułatwiło to zrozumienie kodu, ponieważ każda część działa od siebie nie zależnie. Ułatwiło to również testowanie całej aplikacji.
## Użyte technologie ##
----
* Web
    * [OpenCV 4.0.1](https://docs.opencv.org/4.0.1/)
    * [Googlw Guava 16.0.1](https://github.com/google/guava/tree/v16.0.1)
    * [Tess4j 3.5.3](https://github.com/nguyenq/tess4j/tree/tess4j-3.5.3)
    * [Spring MVC 5.1.6](https://github.com/spring-projects/spring-framework)
    * [Commons-fileupload 1.4](https://commons.apache.org/proper/commons-fileupload/)
    * [Gson 2.8.5](https://github.com/google/gson/tree/gson-parent-2.8.5)
* App
    * [JavaFX 8](https://openjfx.io/)
    * [OkHttp 3.14.0](https://square.github.io/okhttp/)

Wszystkie komponenty są kompatybilne z Javą 8 oraz zostały napisane z myślą o tej wersji.

Do testowania używaliśmy [junit 5.4.2](https://junit.org/junit5/) oraz [mokito 2.27.0](https://site.mockito.org/).

## Kompilacja ##
----
Proces kompilacji przy użyciu IDE został opisany w dokumencie _Instrukcja kompilacji.pdf_.

Istnieje również możliwość kompilacji serwera oraz aplikacji bez użycia środkownika.
Aby to zrobić należy najpierw zmienić pliki _config.properies_ znajdujące się w _Server/src/main/resources/_ oraz _RecognizerLib/src/test/resources/_. Po zmianie ścieżki do openCV możemy uruchomić wiersz poleceń w katalogu _Web_. Następnie należy wpisać następujące komendy:
```
mvn clean install [opcjonalnie z flagą -DskipTests]
mvn tomcat7:run
```

Jeśli chodzi o aplikację kliencką sprawa wygląda podobnie. Wystarczy wpisać komendy
```
mvn clean install [opcjonalnie z flagą -DskipTests]
mvn clean compile assembly:single
```
w katalogu _App_.

Aby uruchomić aplikację należy w folderze _target_ oraz wykonać komendę
```
java -jar app-1.0-jar-with-dependencies.jar
```

Proces uruchomienia serwera w pojemniku dockera został również opisany w pliku _Instrukcja kompilacji.pdf_.

### Dokumentacja kodu ###
-----
Kod jest udokumentowany w postaci _JavaDoc_, które znajdują się [tutaj](http://ns3102827.ip-54-37-129.eu:10021/).

### Materiały użyte podczas procesu tworzenia programy ###
----
* http://www.aishack.in/tutorials/sudoku-grabber-opencv-detection/
* https://github.com/joseluisdiaz/sudoku-solver
* http://neuralnetworksanddeeplearning.com/chap1.html
* http://yann.lecun.com/exdb/mnist/
* http://emaraic.com/blog/realtime-sudoku-solver

### Licencja ###
-------
[Apache License 2.0](https://github.com/lukmccall/SudokuSolver/blob/master/LICENSE)

 */
/**
 * Main config file.<br>
 * Components are located in <code>pl.sudokusolver.server</code>.<br>
 * Have one properties file.
 */
@Configuration
@EnableWebMvc
@ComponentScan("pl.sudokusolver.server") //default packet for this project
@PropertySource("classpath:config.properties")
public class WebConfig implements WebMvcConfigurer{

    /**
     * openCV path when you are using windows. Loaded form properties file.
     */
    @Value("${openCVUrlWin}")
    private String openCVUrlWin;

    /**
     * openCV path when you are using linux. Loaded form properties file.
     */
    @Value("${openCVUrlLinux}")
    private String openCVUrlLinux;

    /**
     * Adding jsp support.
     * @param registry global registry
     */
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // directory to views folder
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    /**
     * Adding assets (css and js).
     * @param registry global registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
    }

    /**
     * Accept <= 4 MB files.
     * @return bean which will be able to process multi part request.
     */
    @Bean(name="multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multi = new CommonsMultipartResolver();
        multi.setMaxUploadSize(4000000); //4 MB
        return multi;
    }

    /**
     * @return digit recognizer been (singleton).
     */
    @Bean
    public DigitRecognizer digitRecognizer(){
        if(Init.getOperatingSystemType() == Init.OSType.Linux)
            return new DigitRecognizer(openCVUrlLinux);
        else
            return new DigitRecognizer(openCVUrlWin);
    }

    /**
     * @return solving algorithm (singleton).
     */
    @Bean
    public ISolver solver(){
        return new SmartSolver();
    }


    /**
     * @param injectionPoint injectionPoint.
     * @return logger.
     */
    @Bean
    @Scope("prototype")
    public Logger logger(InjectionPoint injectionPoint) {
        return LogManager.getLogger(injectionPoint.getMember().getDeclaringClass());
    }
}
