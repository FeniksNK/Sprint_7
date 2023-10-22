
# Тестирование API сервиса Яндекс.Самокат

Этот проект содержит автотесты для **API сервиса Яндекс.Самокат**.

## Описание проекта

- Тестируемое API: [https://qa-scooter.praktikum-services.ru/api/v1](https://qa-scooter.praktikum-services.ru/docs/)

- Используемые технологии:

    - `Java11`
    - `Junit4`
    - `REST Assured`
    - `Allure`
    - `Maven`

- Структура проекта:

    - `src/test/java` - пакеты с тестами
    - `src/test/resources` - вспомогательные файлы для тестов
    - `pom.xml` - зависимости, настройки сборки и запуска тестов

## Запуск тестов

Для запуска всех тестов:

```bash
mvn clean test
```

После запуска тестов отчет о результатах можно посмотреть в **Allure Report**:

```bash
allure serve build/allure-results
```


