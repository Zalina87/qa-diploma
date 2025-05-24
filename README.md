# Процедура запуска автотестов

1. Перейти в директорию проекта

2. Развернуть Docker compose при помощи команды docker-compose up

3. В новом терминале из корневой директории проекта перейдите в artifacts при помощи команды cd .\artifacts\

4. a) запустить сервис командой java -jar aqa-shop.jar, на стандартной базе данных (MySQL), подключение которой указано в файле application.properties

   б) при необходимости запустить сервис на PosgreSQL, для запуска использовать комманду java -jar aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app --spring.datasource.username=postgres --spring.datasource.password=postgres

5. Запустить тесты в отдельном терминале с помощью команды .\gradlew test

6. Убедитесь, что все тесты завершены

# Процедура формирования отчетов Allure

Сгенерированные Allure - отчёты можно посмотреть в директории allureReport, открыв в браузере файл index.html, для генерации новых отчетов, следуйте алгоритму:

1. Запустите Allure с помощью команды .\gradlew allureServe    

2. Убедитесь, что Allure запустился, в логе появлась строка: Server started at ....

3. В новом терминале выполните команду .\gradlew allureReport

4. Перейдите в директорию build/reports/allure-report/allureReport

5. Откройте в брайзере отчет, запустив /index.html
