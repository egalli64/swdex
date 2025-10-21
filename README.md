# Spring Microservices Tutorial

## Monolithic Repository (Monorepo)

Modules
- Eureka server - Discovery
- User service - Simple Eureka client
- Config server - Configuration
- API gateway - Gateway
- Order service - RestClient for blocking service communication techniques
- Random service - Simple Dockerized RestClient
- Refresh User service - Dynamic configuration with @RefreshScope
- Reactive Order service - WebClient, reactive approach to non-blocking communication
- Product service - RabbitMQ, event-driven approach to non-blocking communication (publisher)
- Email service - RabbitMQ, event-driven approach to non-blocking communication (subscriber)
- Inventory service - RabbitMQ, Pub/Sub vs Point-To-Point
- Activity service - Kafka, event streaming approach (producer)
- Audit service - Kafka (consumer - replayability)
- Analytic service - Kafka (consumer - aggregation - stream processing)
- Resilient Order service - Resilience4j (timeout and retry)
- Breaker Order service - Resilience4j (add fail fast by circuit breaker)
- Fallback Order service - Resilience4j (add fallback strategy)
- Bulkhead Order service - Resilience4j (add bulkhead support)
- Tracing User service - Micrometer Tracing
- Tracing Order service - Micrometer Tracing and Zipkin

## Requirements
- Docker
- RabbitMQ: docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
- Kafka: docker run --rm confluentinc/cp-kafka:7.5.0 kafka-storage random-uuid
    - See docker-compose.yaml for details
- Zipkin: docker run -d --name zipkin -p 9411:9411 openzipkin/zipkin

## Startup
- Start Docker Desktop (if on Windows/MacOS)
- Start RabbitMQ: docker start rabbitmq
- Start Kafka: docker compose up -d (in the repository root)
- Start the Spring Services:
    - eureka-server
    - config-server
    - all the services _(the dockerized ones should start in Docker!)_
    - api-gateway

## Shutdown
- Stop the services
- Stop Kafka: docker compose down
- Stop RabbitMQ and Zipkin: docker stop <name>
- Stop Docker Desktop
