# API для создания опросов

---

### Инструкция по запуску

Для работы приложения потребуется PostgreSQL. В файле `application.properties` требуется изменить следующие данные:

````
spring.datasource.url=jdbc:postgresql://localhost:5432/(название базы данных)
spring.datasource.username=(имя пользователя)
spring.datasource.password=(пароль пользователя)
````

После запуска программы любым удобным способом (создание fat-jar с помощью `mvn package` или запуском из IDE) и автогенерации таблиц требуется выполнить следующие шаги:

1) Создать в таблице `users` администратора следующим запросом:
````
INSERT INTO users (id, username, password) VALUES (1, 'admin', '$2y$12$dAK5qlqoH2mliOPxUZRCFeVIq3.eZYZX0iS9JYcKdI0yeHMulI1Aq')
````
(Пример с данными: логин - admin, пароль - admin в bcrypt. В качестве данных можно использовать что угодно)

2) Дать администратору роль администратора командой:
````
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1)
````

### Краткая сводка
- Создавать опросы и вопросы может только администратор
- Отвечать может кто угодно
- Минимизированные данные - это данные, в которых вместо объекта (опроса, вопроса) хранится id этого объекта


### Описание работы

Ниже будут приведены примеры запросов и алгоритм работы администратора. (Все примеры для программы Postman)

#### Аутентификация
Делается на адрес - `localhost:8080/api/v1/auth/login`

Тело запроса:
````
{
    "username": "admin",
    "password": "admin"
}
````

В ответ приходит ключ, который нужен для всех запросов создания опросов и вопросов

Пример ключа:
`eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNjI0NjU0NzY3LCJleHAiOjE2MjQ2NTgzNjd9.Sxox5BEgcqBl6OrB2oA-iI4ICzqToufztVbo3y2hBlY`

Его нужно вставить в раздел Headers, где ключом будет Authorization, а значением `Bearer_` + ключ

Пример:
`Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaWF0IjoxNjI0NjU0NzY3LCJleHAiOjE2MjQ2NTgzNjd9.Sxox5BEgcqBl6OrB2oA-iI4ICzqToufztVbo3y2hBlY`

#### Создание опроса

Делается на адрес - `localhost:8080/api/v1/admin/create`

Тело запроса:
````
{
    "name": "test survey",
    "startSurvey": "2021-07-31",
    "endSurvey": "2021-08-15",
    "description": "test desc for test survey"
}
````

#### Создание даты старта

Делается на адрес - `localhost:8080/api/v1/admin/create/startdate`

Тело запроса:
````
{
    "surveyName": "test survey",
    "newDate": "2021-08-15"
}
````

#### Изменение названия опроса

Делается на адрес - `localhost:8080/api/v1/admin/update/name`

Тело запроса:
````
{
    "currentName": "test",
    "newName": "updatedTest"
}
````

#### Изменение описания

Делается на адрес - `localhost:8080/api/v1/admin/update/description`

Тело запроса:
````
{
    "currentName": "test servey",
    "newDescription": "new test desc"
}
````

#### Изменение даты окончания

Делается на адрес - `localhost:8080/api/v1/admin/update/end`

Тело запроса:
````
{
    "surveyName": "updatedTest",
    "newDate": "2021-07-01"
}
````

#### Удаление опроса

Делается на адрес - `localhost:8080/api/v1/admin/delete/{name}`

`{name}` - название опроса

#### Получение всех клиентов данного опроса

Делается на адрес - `localhost:8080/api/v1/admin/get/{id}/customers`

`{id}` - id опроса

#### Создание обычного вопроса

Делается на адрес - `localhost:8080/api/v1/admin/create/defaultquestion`

Тело запроса:
````
{
    "surveyName": "test survey",
    "questionText": "first default question"
}
````

#### Создание вопроса с радио-кнопками

Делается на адрес - `localhost:8080/api/v1/admin/create/radioquestion`

Тело запроса:
````
{
    "surveyName": "test survey",
    "questionText": "second radio question",
    "answers": [
        "first answer",
        "second answer",
        "third answer"
    ]
}
````

#### Создание вопроса с выбором нескольких вариантов

Делается на адрес - `localhost:8080/api/v1/admin/create/checkboxquestion`

Тело запроса:
````
{
    "surveyName": "test survey",
    "questionText": "second radio question",
    "answers": [
        "first answer",
        "second answer"
    ]
}
````

#### Изменение обычного вопроса

Делается на адрес - `localhost:8080/api/v1/admin/update/defaultquestion`

Тело запроса:
````
{
    "surveyName": "updatedTest",
    "questionText": "This is a test question text",
    "newQuestionText": "This is updated text for deafult question"
}
````

#### Изменение вопроса с радио-кнопками

Делается на адрес - `localhost:8080/api/v1/admin/update/radioquestion`

Тело запроса:
````
{
    "surveyName": "updatedTest",
    "questionText": "This is a test question text for radio",
    "answers": [
        "first answer",
        "second answer",
        "third answer"
    ],
    "newQuestionText": "this is updated radio",
    "newAnswers": [
        "first",
        "second"
    ]
}
````

#### Изменение вопроса с выбором нескольких ответов

Делается на адрес - `localhost:8080/api/v1/admin/update/checkboxquestion`

Тело запроса:
````
{
    "surveyName": "updatedTest",
    "questionText": "This is a test question text for checkbox",
    "answers": [
        "first",
        "second"
    ],
    "newQuestionText": "this is updated checkbox",
    "newAnswers": [
        "first",
        "second",
        "third"
    ]
}
````

#### Удаление обычного вопроса

Делается на адрес - `localhost:8080/api/v1/admin/delete/defaultquestion`

Тело запроса:
````
{
    "surveyName": "test servey",
    "questionText": "Test default question"
}
````

#### Удаление вопроса с вариантами ответа

Делается на адрес - `localhost:8080/api/v1/admin/delete/answeroptions`

Тело запроса:
````
{
    "surveyName": "test survey",
    "questionText": "second radio question",
    "answers": [
        "first answer",
        "second answer",
        "third answer"
    ]
}
````

#### Получение всех активных опросов

Делается на адрес - `localhost:8080/api/v1/customer/getall`


#### Получение опроса

Делается на адрес - `localhost:8080/api/v1/customer/get/{id}`

`{id}` - id опроса

#### Отправление ответа

Делается на адрес - `localhost:8080/api/v1/customer/answer`

Тело запроса:
````
{
    "surveyId": "1",
    "questionId": "1",
    "answer": "test answer",
    "customerId": "1"
}
````

В случае отправки анонимного ответа в поле `customerId` передать `0`

#### Сохранение данных клиента для текущего опроса

Делается на адрес - `localhost:8080/api/v1/customer/save/{id}`

`{id]` - id опроса

Тело запроса:
````
{
    "uniqueUserData": "nikitagrudinin01@gmail.com"
}
````

#### Получение всех ответов клиента для конкретного опроса

Делается на адрес - `localhost:8080/api/v1/customer/get/answers/{id}`

Тело запроса:
````
{
    "surveyId": "1",
    "questionId": "2",
    "answer": "test answer",
    "customerId": "1"
}
````

#### Получение списка всех ответов клиента

Делается на адрес - `localhost:8080/api/v1/customer/get/answers`

Тело запроса:
````
{
    "customerId": "1"
}
````