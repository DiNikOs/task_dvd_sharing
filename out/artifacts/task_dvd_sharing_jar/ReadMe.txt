Application stack:

Java.
Spring boot.
Hibernate.
FlyWay.
MySQL BD.
Thymeleaf.
Maven.
Lombok.
REST API.
JWT Bearer token.

Задание:

Написание автоматизации ресурса по обмену DVD-дисками, веб-приложение - только REST API (UI не надо).

Стек технологий:
Java 8+
Spring Framework (Spring Boot)
ORM (Hibernate)
СУБД (на выбор)
Maven/Gradle

Функциональные требования:

Есть коллекция дисков у каждого участника. 
Диски можно брать и отдавать. 
В системе три сущности: Disk (DVD-диск), User, TakenItem (связка User-Disk).

В системе пять экранов (экраны не выполнены(только бэкэнд), реализовано REST API для их обслуживания):
- авторизация
- список собственных дисков у каждого пользователя
- список свободных дисков (у всех пользователей не взятые)
- список дисков, взятых пользователем
- список дисков, взятых у пользователя (с указанием, кто взял)

Диск можно взять и отдать (без денежных расчётов).
СУБД - MySQL .
Работа с СУБД – Hibernate.
Для организации работы с ORM и в качестве «клея» для слоев приложения используется Spring Boot.
Сборка проекта производится при помощи Maven.
Запуск приложения в Windows out/artifacts/task_dvd_sharing_jar/run.bat.
 
Оформление результата:

Проект на github содержащий исходники, файлы сборки, библиотеки, SQL-скрипты создания базы и заполнения ее начальными данными.
Краткая инструкция по установке и проверки работы.

To install in Windows run the file run.bat

Address in the browser for the test:
http://localhost:8188/api/v0/start

Other test addresses can be used: 
1. Postman 
2. Using requests.http (located in the project) in the development environment of IntelliJ IDEA.

Для установки приложения запустить файл run.bat.

Для тестовой проверки работы приложения в браузере перейти по адресу:
http://localhost:8188/api/v0/start

Дальнейшая проверка возможна через:
1. Postman
2. По средствам файла requests.http(находится в корне проекта). Файл открывается в IntelliJ IDEA и запускается каждый запрос через Run.

#MySQL settings
url=jdbc:mysql://localhost:3306/db_dvd
username=root
password=root
