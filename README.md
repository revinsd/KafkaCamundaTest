
# Camunda and kafka test app


## Get Started
To start project run the following commands:
```
mvn install
docker-compose up -d
```

Container Name  | Port
-------------   | -------------
Kafka           | 29092
Zookeeper       | 2181
App             | 8010
Kafka - UI      | 8090




## API Reference


#### Send message to main topic

``
  POST /message/send/main
``
```
  {
    "messageText": "message",
    "uuid": "8e215f5d-47be-42a7-a2b1-541e9a708634"
  }
```
Ready request available [here](https://github.com/revinsd/KafkaCamundaTest/blob/master/src/main/resources/send-message.http)

