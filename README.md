# SudokuSolver #

###### Projekt został stworzony przez:
* Daniel Dobrowolski
* Małgorzata Dymek
* Tomasz Janik
* Łukasz Kosmaty
* Nikodem Kwaśniak
* Dawid Szczerba

## Krótki opis aplikacji ##

Aplikacja jest połączeniem sudoku grabbera, który jest w stanie przeczytać łamigłówkę ze zdjęcia oraz sudoku solvera, który rozwiązuje podane przez użytkownika sudoku.
Aplikacja została podzielona na dwa główne moduły.
* Web - część odpowiedzialna za serwer oraz proces rozwiązywania/rozpoznawania sudoku. Moduł ten również został podzielony na pod moduły.
    * RecognizerLib - część odpowiedzialna ze rozpoznanie sudoku.
    * Server - część odpowiedzialna za obsługę zapytań http.
    * Solver - algorytm rozwiązujący łamigłówkę.
* App - część odpowiedzialna za dostarczenie przyjaznego gui do porozumiewania się z serwerem.

Dzięki modułowemu podejściu zyskaliśmy dużą swobodę w wymianie poszczególnych elementów oraz ułatwiło to zrozumienie kodu, ponieważ każda część działa od siebie nie zależnie. Ułatwiło to również testowanie całej aplikacji.
## Użyte technologie ##

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

## Dokumentacja kodu ##

Kod jest udokumentowany w postaci _JavaDoc_, które znajdują się [tutaj](https://github.com/lukmccall/SudokuSolver/tree/master/Doc/JavaDoc).

## Materiały użyte podczas procesu tworzenia programy ##

* http://www.aishack.in/tutorials/sudoku-grabber-opencv-detection/
* https://github.com/joseluisdiaz/sudoku-solver
* http://neuralnetworksanddeeplearning.com/chap1.html
* http://yann.lecun.com/exdb/mnist/
* http://emaraic.com/blog/realtime-sudoku-solver

### Licencja ###
[Apache License 2.0](https://github.com/lukmccall/SudokuSolver/blob/master/LICENSE)
