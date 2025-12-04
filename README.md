# 🌊 Reactive Programming with Spring WebFlux

![Project Reactor](https://projectreactor.io/docs/core/release/api/reactor-core.png)

## What is Reactive Programming?

Reactive programming is a **non-blocking, asynchronous** programming paradigm focused on data streams and change propagation. Instead of waiting for operations to complete (blocking), reactive systems react to events as they occur, allowing efficient resource utilization.

### Key Principles:
- **Non-blocking I/O**: Threads aren't waiting idle for responses
- **Backpressure**: Consumers control data flow rate from producers
- **Event-driven**: React to data as it arrives
- **Asynchronous**: Operations don't block the calling thread

---

## How WebFlux Handles Requests Asynchronously

Traditional Spring MVC uses **one thread per request**. If you have 200 concurrent requests, you need 200 threads—even if they're just waiting for database/API responses.

**Spring WebFlux** uses a small, fixed thread pool (typically CPU cores × 2) and handles thousands of concurrent requests through **event loop** mechanisms:

```
Request 1 → Event Loop → Database (non-blocking)
Request 2 → Event Loop → External API (non-blocking)  
Request 3 → Event Loop → File I/O (non-blocking)
         ↓
    [Small Thread Pool]
         ↓
   Responses streamed back as data becomes available
```

Threads are **never blocked**—when waiting for I/O, they handle other requests.

---

## Code Example Explained

```java
public Flux<Student> findAll() {
    return studentRepository.findAll()
            .delayElements(Duration.ofSeconds(1));
}
```

### What's Happening Here:

1. **`Flux<Student>`**: A reactive stream that emits 0 to N `Student` objects over time
2. **`studentRepository.findAll()`**: Fetches students asynchronously from the database
3. **`delayElements(Duration.ofSeconds(1))`**: Simulates streaming—each student is emitted with a 1-second delay

### Output Behavior:

If the database has 5 students:

```
Time 0s: Request received → Controller returns immediately (non-blocking)
Time 1s: Student 1 → { "id": 1, "name": "Alice" }
Time 2s: Student 2 → { "id": 2, "name": "Bob" }
Time 3s: Student 3 → { "id": 3, "name": "Charlie" }
Time 4s: Student 4 → { "id": 4, "name": "Diana" }
Time 5s: Student 5 → { "id": 5, "name": "Eve" }
```

**Critical Point**: The server thread handling this request is **free to process other requests** during those 1-second delays. The response is streamed back chunk-by-chunk as data becomes available.

---

## Why This Matters

**Traditional Blocking Approach:**
```java
// Thread is BLOCKED for entire database query duration
List<Student> students = studentRepository.findAll(); 
return students; // Thread tied up even if waiting on I/O
```

**Reactive Approach:**
```java
// Thread is RELEASED immediately after initiating the request
Flux<Student> students = studentRepository.findAll();
return students; // Thread processes other work while waiting
```

### Real-World Benefits:
- **Higher throughput**: Handle 10,000+ concurrent requests with ~20 threads
- **Better resource utilization**: CPU and memory are used efficiently
- **Scalability**: Gracefully handle traffic spikes without exhausting thread pools
- **Streaming**: Large datasets can be processed incrementally

---

## Running the Demo

1. Clone the repository
2. Ensure you have a reactive database configured (R2DBC)
3. Run the application: `./mvnw spring-boot:run`
4. Access the endpoint: `GET http://localhost:8080/students`
5. Watch the response stream in real-time!

---

## Tech Stack

- **Spring Boot 3.x**
- **Spring WebFlux** (Reactive web framework)
- **Project Reactor** (Reactive library with Flux/Mono)
- **R2DBC** (Reactive database driver)

---

## Learn More

- [Project Reactor Documentation](https://projectreactor.io/docs)
- [Spring WebFlux Reference](https://docs.spring.io/spring-framework/reference/web/webflux.html)
- [Reactive Streams Specification](https://www.reactive-streams.org/)

---

**Built with ❤️ using Spring WebFlux**
