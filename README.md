# Build and Start the Docker Containers

Use Docker Compose to build and start the `chat-server` and `chat-client`.

```bash
docker-compose up --build
```

### This command will:
- Build the Docker images for `chat-server` and `chat-client`
- Start the `chat-server` container and a `chat-client` container connected to it

---

## Running Multiple Clients

To simulate multiple clients, open new terminals and use the following command for each new client instance:

```bash
docker-compose run -it chat-client
```

Each new terminal will start a new `chat-client` instance connected to the same `chat-server`.

---

## Interacting with the Application

Once a `chat-client` instance starts, you’ll see an interactive menu with the following options:

```
1. Create Room
2. List Rooms
3. Join Room
4. Leave Room
5. Send Message
6. View Messages
0. Exit
```

You can select options by entering the corresponding number.

When you're done testing, you can stop all containers by pressing `Ctrl + C` in the terminal where `docker-compose up` was run, or by executing:

```bash
docker-compose down
```

---

## To Run REST

Navigate to the root directory and run:

```bash
docker-compose up --build
```

Once that is done, open another terminal in the directory and run:

```bash
flutter run -d chrome
```

You may run this command as many times as needed to create multiple users.

---

# Experiment Setup

The goal of the project is to evaluate the performance of REST and gRPC protocols for a chatroom application.

The experiment setup consists of the following components:

---

## Load Generator

A Python-based testing script is used to simulate multiple clients sending requests to the chatroom server. It supports concurrency, arrival rate adjustments, and payload customization.

For gRPC, the `grpcio` library is used.  
For REST, the `requests` library is used.

Each script consists of the following functions:

- `create_chatroom(chatroom_name)`
- `send_message(payload)`
- `test_small_payload(num_requests)`
- `test_large_payload(num_requests)`
- `test_concurrent_requests(num_requests, num_threads)`
  - `simulate_client(client_id)`
- `send_message_with_retry(payload, retries=3)`
- `send_message_with_server_check(payload)`
- `test_arrival_rates(num_requests, rate)`
- `test_data_types()`
- `test_data_volume()`

---

## Server Implementation

The chatroom server is implemented separately for REST and gRPC protocols.

- The REST server was built with Flutter and uses HTTP-based endpoints to handle requests such as creating a chat room, sending messages, and retrieving chatroom data.
- The gRPC server uses Protocol Buffers to define the service and message structures. It supports bi-directional communication with a strongly-typed contract.

Both servers run on a local machine.

The testing script communicates with the servers through:

```
localhost:8080
```

For failure scenarios, the server is manually stopped or restarted to simulate node failures, while retry logic simulates network instability.

---

# Experiments

The experiments are designed to measure the performance and robustness of REST and gRPC under various conditions.

## Small Payloads
Measuring the latency of small text messages. This provides a baseline for the best-case scenario for both protocols.

## Large Payloads
Measuring the impact of larger payloads (10KB and 1MB) on latency and throughput. This evaluates how REST and gRPC handle serialization and deserialization overhead.

## Concurrent Requests
Measuring the scalability of the server under concurrent requests using 10 simulated clients sending 50 requests. This helps compare the threading efficiency of REST and gRPC servers.

## Varying Arrival Rates
Testing server performance under controlled arrival rates (1, 10, and 50 requests per second). This assesses the server's ability to handle sustained traffic and prevent request drops.

## Network Failures
Testing the resilience of REST and gRPC under transient network failures by introducing retries and exponential backoff. This measures recovery times and successful retries under poor network conditions.

## Node Failures
Simulating a server crash or downtime while clients are sending requests. This captures client behavior during server unavailability, including retry logic and error handling.

## Data Types
Evaluating how REST and gRPC handle different data formats (e.g., plain text, nested JSON, binary). This highlights protocol differences in serialization and performance.

---

# Data Interpretation

**X Axis – Request ID**  
Represents the sequence of requests sent to the server. Each request has a unique identifier (`request_id`) that tracks when it was sent during the test.

**Y Axis – Latency (s)**  
Measures the time taken in seconds for the server to respond to a specific request.

---

## Results

![REST Results](https://github.com/user-attachments/assets/f249d0ac-ce05-49ac-9804-fcccf31df280)
![Large Payload Latency](https://github.com/user-attachments/assets/4a02497c-4aae-4c04-aa23-f25bdb78460e)
![Small Payload Latency](https://github.com/user-attachments/assets/983abdb8-baf6-438a-97bd-1a330a5d35b2)
![Concurrent gRPC Latency](https://github.com/user-attachments/assets/1014edfb-2ee0-40fb-9ee5-dc95a36a5830)
![gRPC Results](https://github.com/user-attachments/assets/2b4a3ad7-f4db-46ee-900c-7f7499b8ffbd)

---

# Overall Results

The comparison between gRPC and REST demonstrates a performance advantage for gRPC in terms of latency, scalability, and throughput.

Across all test cases, gRPC consistently exhibited lower latency and better efficiency, particularly under concurrent loads. REST struggled to match gRPC's performance due to its reliance on JSON serialization and the additional overhead of HTTP-based communication.

gRPC achieved higher throughput due to its use of HTTP/2, which supports multiplexing and reduces connection overhead. The binary serialization of Protocol Buffers is lightweight and faster, minimizing serialization and deserialization overhead.

REST achieved lower throughput as each request incurs significant overhead from text-based JSON serialization and the stateless nature of HTTP/1.1. REST also experiences higher latency and more frequent spikes, especially under heavy loads or concurrent requests.

Higher peaks in REST are caused by the overhead of establishing new HTTP connections for each request, JSON serialization/deserialization, and thread contention under concurrent loads. gRPC spikes occur due to initial connection setup and increased server-side load during high concurrency or payload processing.

In both cases, resource contention and serialization overhead play significant roles in latency fluctuations. However, gRPC handles these better due to its binary format and persistent connections. In scenarios requiring high throughput and minimal overhead, gRPC is the better choice.

For both protocols, N clients were simulated using threads in the testing scripts. In gRPC, each thread creates a unique client connection using the `grpcio` library. HTTP/2’s support for multiplexing makes it more efficient in managing multiple connections.

For REST, each thread uses a `requests.Session` to represent an independent client. However, the stateless nature of REST requires creating a new connection for each request, leading to higher overhead. The ability to handle multiple clients concurrently was noticeably better in gRPC due to its efficient connection management and support for streaming.

gRPC shows its strength in low latency and high efficiency, especially for concurrent and large payloads. It also supports bi-directional streaming, making it ideal for real-time applications. However, it is more complex to set up due to the need for `.proto` files and a higher learning curve.

REST is very simple and widely used across platforms. The human-readable JSON format makes it easier for debugging and testing. Its statelessness makes it more scalable in certain scenarios, such as distributed systems where each request must be independent. However, it suffers from higher latency and lower throughput due to JSON serialization and HTTP/1.1 overhead.

gRPC is ideal for performance-intensive, real-time systems, while REST excels in flexibility, simplicity, and broad compatibility. The choice between them depends on application requirements and the balance between performance and development simplicity.
