# TaskManager Backend

## Описание проекта

Этот проект представляет собой backend-приложение на Java с использованием Spring Boot, Maven и интеграцией с Keycloak для управления аутентификацией и авторизацией.

## Содержание

- [Установка и запуск](#установка-и-запуск)
- [Использование](#использование)
- [Стек приложения](#стек-приложения)

## Установка и запуск

1. Склонируйте репозиторий.
2. Соберите проект с помощью Maven:
   ```bash
   mvn clean package
   ```
3. Запустите контейнеры через Docker Compose:
   ```bash
   docker-compose up --build
   ```
4. Экспортируйте realm keycloak из файла `src/main/resources/keycloak/realm-export.json` в Keycloak (user - admin, password - admin).

## Использование

После успешного запуска проверьте работу backend, перейдя по адресу: [http://localhost:8081/test](http://localhost:8081/test)

**Ожидаемый ответ:** `Hello!!!`

Также есть документация Swagger по адресу: [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

## Стек приложения
- Java 23
- Spring Boot
- Spring Security
- Spring Data JPA
- Spring Web
- Spring DevTools
- OpenAPI
- Lombok
- Keycloak
- Docker
- PostgreSQL
## Идея для улучшения
- Написать тесты
- Написать фронтенд
- ПЕРЕПИСАТЬ ЭТО ГОВНО НА PHP
- Настроить CI/CD и поставить на свой сервер
