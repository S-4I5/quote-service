# Quote-service

Spring boot сервис, цитирующий отправленные сообщения группе в вк

## 1. ENVs

| Name                    | Type   | Default value                             | Description                 |
|-------------------------|--------|-------------------------------------------|-----------------------------|
| PORT                    | String | 8080                                      | Servet port                 |
| REDIS_HOST              | String | localhost                                 | Redis host                  |
| REDIS_PORT              | String | 6379                                      | Redis port                  |
| DATABASE_URL            | String | jdbc:postgresql://localhost:5432/postgres | DB connection url           |
| DATABASE_USERNAME       | String | postgres                                  | DB username                 |
| DATABASE_PASSWORD       | String | password                                  | DB password                 |
| LOGGING_LEVEL           | String | trace                                     | Logging level               |
| INIT_GROUP_API_VERSION  | String | 5.199                                     | Default source api version  |
| INIT_GROUP_ACCESS_TOKEN | String | token                                     | Default source access token |
| INIT_GROUP_SECRET       | String | secret                                    | Default source secret       |

## 2. Deployment via docker-compose

```
docker-compose up
```

## 3. Swagger documentation

```
http://localhost:8080/api/v1/swagger-ui/index.html#/
```

## 4. Vk Setup

Для работы сервиса необходимо подключить `Callback Api`, ибо именно через него он узнаёт о поступлении сообщений.
Подробнее об его работе можно прочитать в [документации](https://dev.vk.com/ru/api/callback/getting-started).
Необходимо настроить отправку callback'ов на отправку сообщений. Сервис может работать сразу с несколькими группами, и
для того, чтобы сервис обрабатывал входящие сообщения от источника, его нужно "зарегистрировать" в application.yaml. Это
можно сделать либо изменив данные "базового" источника, указав нужные через [env](##ENVs), либо же добавить туда новый.

* Пример:

```
vk:
  ....
  sources:
    testGroup:                        //'Id' группы, который нужно будет указаывать в url
      version: someVersion(ex: 5.199) //Версия
      access-token: someToken         //Токен группы (Необходимы права на управление группой и доступ к сообещниям)
      secret: someSecret              //Код api, который будет присылаться для верификации
```

## 5. Quick workflow explanation

P.S. Подробные Json-shemas можно найти в [сваггере](##Swagger documentation)
или [документации vk api](https://dev.vk.com/ru/reference/json-schema)

Для отправки callback'а источнику необходимо обратиться к POST http://{someHost}/api/v1/callback/{groupId}

При приходе любого callback'а перед его обработкой проверяется secret источника. Если строки не совпадают - вылетает

403.

Далее обрабатываются два типо сообщений: "confirmation" и "message_new"

* "Type `"confirmation"`:

  Запрашивает код для подтверждения, после подтверждения которого callback api начинает присылать сервису сообщения. Для
  его обработки сервис обращается к vk api и получает необходимый код по
  uri : `/method/groups.getCallbackConfirmationCode`, после чего возвращает его.

* Type `"message_new"`:

  Сообщает сервису о поступлении нового сообщения. Для его обработки сервис обращается к vk api
  uri : `/method/users.get` для получения инициалов отправителя, чтобы использовать их в ответном сообщении. Для
  оптимизации этой операции есть redis cache, в котором сервис перед запросом проверяет наличие данной информации. После
  получения сервис инициирует отправку кастомизированного сообщения пользователю используя
  uri : `/method/messages.send`, после чего сохраняет отправленное сообщение в PostgreSql.