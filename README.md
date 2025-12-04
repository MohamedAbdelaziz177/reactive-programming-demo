<img src="https://projectreactor.io/img/logo.png" width="120"/><br/>Reactive Programming Demo (Spring WebFlux)
🚀 Overview

This is a simple Reactive Programming demo using Spring WebFlux and Project Reactor.
The goal is to show how WebFlux handles requests asynchronously and how reactive streams behave when returning data using Flux.

⚡ What is Reactive Programming?

Reactive Programming is a programming paradigm based on asynchronous, non-blocking streams of data.
Instead of requesting data now and waiting (blocking), the application says:

“When the data is ready, push it to me.”

Key characteristics:

Non-blocking I/O

Backpressure (the ability to control data flow)

Event-driven pipelines

Better scalability under high traffic

Spring WebFlux uses Project Reactor under the hood to build these pipelines.

🌐 How WebFlux Handles Requests Asynchronously

Unlike traditional Spring MVC (thread-per-request model), WebFlux uses:

Netty or servlet containers in non-blocking mode

A small, fixed thread pool

Event-loop architecture similar to Node.js

When a request arrives:

WebFlux does not block a thread waiting for the DB.

It registers callbacks.

When data becomes available, WebFlux resumes the pipeline and streams the response to the client.

This allows WebFlux to handle thousands of concurrent connections efficiently.

📘 Example: Returning a Flux With Delayed Elements
public Flux<Student> findAll() {
    return studentRepository.findAll()
            .delayElements(Duration.ofSeconds(1));
}

✅ What this code means

findAll() returns a Flux<Student> — a reactive stream of students.

.delayElements(Duration.ofSeconds(1)) tells Reactor:

Emit each student one by one, waiting 1 second between emissions.

⏱ Example Output Behavior

If you have 3 students in the DB, the client receives them like this:

Student #1  (after 1 second)
Student #2  (after 2 seconds)
Student #3  (after 3 seconds)


Important:

The server never blocks.

WebFlux streams data to the client in real time.

The consumer sees each element as it becomes available — perfect for data streaming, live feeds, logs, etc.

📦 Tech Stack

Spring WebFlux

Project Reactor (Flux, Mono)

Reactive Repositories

Netty Server

🎯 Purpose of This Demo

This project helps beginners understand:

What reactive programming is

How WebFlux processes requests asynchronously

How Flux streams data live instead of returning everything at once
