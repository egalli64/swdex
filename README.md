# Spring Microservices Tutorial

## Monolithic Repository (Monorepo)

Modules
- Eureka server - Discovery
- API gateway - Gateway
- User service - Simple Eureka client
- Order service - More advanced, but always synchronous, service communication techniques
- Reactive order service - WebClient, reactive approach to non-blocking communication
- Product service - RabbitMQ, event-driven approach to non-blocking communication (publisher)
- Email service - RabbitMQ, event-driven approach to non-blocking communication (subscriber)

### RabbitMQ external dependency by Docker

- docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
- docker start rabbitmq
