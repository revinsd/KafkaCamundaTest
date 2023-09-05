
# Camunda and kafka test app


## Get Started
#### Project can be started with the following command
```
docker-compose up -d
```
#### It will launch four containers:

Container Name  | Port
-------------   | -------------
Kafka           | 29092
Zookeeper       | 2181
App             | 8010
Kafka - UI      | 8090




## API Reference


#### Send message to main topic

```http
  POST /message/send/main

  {
    "messageText": "message",
    "uuid": "8e215f5d-47be-42a7-a2b1-541e9a708634"
  }
```
#### Ready request available [here](https://github.com/revinsd/KafkaCamundaTest/blob/master/src/main/resources/send-message.http)

