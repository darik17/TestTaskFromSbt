Реализовал 3 сценария тестирования заданного сайта:

1. Проверка работоспособности непосредственно конвертера валют, тестовые данные лежат в файле convertiontest.csv директории resources.

2. Проверка изменения графика отношения тестируемой валюты к рублю, тестовые данные лежат в файле viewcurrtest.csv директории resources.
 
 3. Изначально тест задумывался как проверка изменения графика валюты в зависимости от заданных дат, но возникли трудности с внесием даты в поле (не копируется). Поэтому переделан на изменение валюты и проверку окна распечатывания графика. Тестовые данные лежат: src\main\resources\currdatetest.csv
 
 Реализовано логгирование ошибок и некоторых сообщений на консоль и файл (LOGTest.log), настройку уровня логгирования можно изменять в файле настройки log4j.properties директории resources.
  
  Скрины отчета allure выложил в src\LastAllureReport или запустить в console: mvn clean test site jetty:run и перейти по адресу: http://localhost:8080/