# Spring Microservices Tutorial

## Monolithic Repository (Monorepo)

Modules
- Eureka server - Discovery
- API gateway - Gateway
- User service - Simple Eureka client
- Order service - RestClient for blocking service communication techniques
- Reactive order service - WebClient, reactive approach to non-blocking communication
- Product service - RabbitMQ, event-driven approach to non-blocking communication (publisher)
- Email service - RabbitMQ, event-driven approach to non-blocking communication (subscriber)
- Inventory service - RabbitMQ, Pub/Sub vs Point-To-Point
- Activity service - Kafka, event streaming approach (producer)
- Audit service - Kafka (consumer - replayability)
- Analytic service - Kafka (consumer - aggregation - stream processing)

### RabbitMQ external dependency by Docker

- docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
- docker start rabbitmq

### Kafka external dependency by Docker

- In the project root folder: docker run --rm confluentinc/cp-kafka:7.5.0 kafka-storage random-uuid
    - See docker-compose.yaml for details
- docker compose up -d / docker compose down
