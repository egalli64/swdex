# Spring Microservices Tutorial

## Monolithic Repository (Monorepo)

Modules
- Eureka server - Discovery
- API gateway
- User service - A Eureka client
- Order service - More advanced, but always synchronous, service communication techniques
- Reactive order service - WebClient, reactive approach to non-blocking communication
- Product service - RabbitMQ, Event-Driven approach to non-blocking communication (publisher)
- Email service - RabbitMQ, Event-Driven approach to non-blocking communication (consumer)

### RabbitMQ external dependency by Docker

- docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
- docker start rabbitmq
