# HotelAPI

## Описание
RESTful API приложение для управления отелями. Позволяет просматривать, создавать, искать и добавлять удобства к отелям, а также строить гистограммы по различным параметрам.

## Технологии
- Java 17+
- Spring Boot 4.x
- Spring Data JPA
- H2 (в памяти)
- Liquibase
- Lombok
- SpringDoc OpenAPI (Swagger UI)
- Maven

## Запуск приложения
После клонирования, запуск приложения возможен с использованием команды
```bash
mvn spring-boot:run
```
Приложение будет доступно по адресу: http://localhost:8092

## Swagger UI
Документация к API доступна через Swagger UI по адресу:
```bash
http://localhost:8092/swagger-ui.html
```

## API Endpoints
### 1. Получить список всех отелей
- **GET** /property-view/hotels
### 2. Получить информацию о конкретном отеле
- **GET** /property-view/hotels/{id}
### 3. Поиск отелей по параметрам
- **GET** /property-view/search
  - Параметры:
    - name (опционально)
    - brand (опционально)
    - city (опционально)
    - country (опционально)
    - amenities (опционально, можно передавать несколько)
- Пример запроса:
  - /property-view/search?brand=hilton&city=minsk&amenities=amenities1&amenities=amenities2
### 4. Создать новый отель
- **POST** /property-view/hotels
  - Тело запроса: JSON с информацией об отеле
### 5. Добавить удобства к отелю
- **POST** /property-view/hotels/{id}/amenities
  - Тело запроса: массив строк с названиями удобств
### 6. Получить гистограмму по параметру
- **GET** /property-view/histogram/{param}
  - param может быть: brand, city, country, amenities

## База данных
По умолчанию используется **H2** (в памяти). Для переключения на другую **SQL-СУБД** (PostgreSQL, MySQL и т.д.):
- Замените зависимость в `pom.xml`
- Обновите настройки подключения в `application.yaml`
- Убедитесь, что Liquibase-скрипты совместимы с новой СУБД

Для **NoSQL баз данных** (MongoDB и т.д.) потребуется:
- Заменить Spring Data JPA на Spring Data MongoDB
- Переписать сущности и репозитории
- Обновить логику запросов