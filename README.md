# AdTech gRPC Project

This project demonstrates the use of gRPC in an AdTech platform, specifically for communication between a Supply-Side Platform (SSP) and an Ad Exchange. It showcases how gRPC can be used for efficient inter-service communication in a microservices architecture.

## Project Structure

```
grpcjava/
├── proto/
│   └── src/main/proto/bidding.proto
├── adexchange/
│   └── src/main/java/com.example.adexchange/
│       ├── service/
│       │   └── AdExchangeService.java
│       └── AdexchangeApplication.java
└── ssp/
    └── src/main/java/com.example.ssp/
        ├── controller/
        │   └── SspController.java
        ├── service/
        │   └── SspService.java
        └── SspApplication.java
```

## Setup and Configuration

### Proto

1. Navigate to the `proto` folder
2. Run the following commands:
   ```
   mvn clean install
   mvn clean generate-sources
   ```
3. Mark the generated Java files as Generated Sources Root in your IDE

### AdExchange and SSP

1. Navigate to each folder (`adexchange` and `ssp`)
2. Run the same Maven commands as above

## gRPC Setup

This project uses gRPC for communication between the SSP and Ad Exchange. Here's how it's set up:

1. **Proto Definition**: The `bidding.proto` file in the `proto` folder defines the service and message structures.

2. **Ad Exchange (gRPC Server)**:
   - Implements the gRPC service defined in the proto file
   - Uses `@GrpcService` annotation for service discovery
   - Starts a gRPC server on port 9090

3. **SSP (gRPC Client)**:
   - Uses `@GrpcClient` annotation to inject a gRPC client stub
   - Converts REST API requests to gRPC calls
   - Communicates with the Ad Exchange via gRPC

## Application in AdTech

This setup demonstrates how gRPC can be used in an AdTech platform:

1. **Inter-service Communication**: gRPC provides efficient, type-safe communication between the SSP and Ad Exchange.

2. **OpenRTB Mapping**: The DTO structures closely mirror OpenRTB bid request and response formats, allowing for easy mapping between OpenRTB and our internal structures.

3. **High Performance**: gRPC's use of Protocol Buffers and HTTP/2 ensures high-speed communication, crucial for real-time bidding scenarios.

4. **Scalability**: The use of gRPC facilitates easy scaling of services independently.

## Benefits of Using gRPC in AdTech

- **Low Latency**: Critical for real-time bidding scenarios
- **Strong Typing**: Reduces errors in communication between services
- **Bi-directional Streaming**: Useful for handling multiple bid requests/responses efficiently
- **Language Agnostic**: Allows different services to be written in different languages if needed

## Getting Started

1. Clone the repository
2. Follow the setup instructions for Proto, AdExchange, and SSP
3. Run the AdExchange application
4. Run the SSP application
5. The SSP will now be able to send bid requests to the Ad Exchange via gRPC

## Sample Request

Here's a sample cURL request to test the SSP endpoint:

```bash
curl --location 'http://localhost:8080/api/ssp/bidrequest' \
--header 'Content-Type: application/json' \
--data '{
    "id": "1234567890",
    "imp": {
        "id": "1",
        "banner": {
            "w": [300, 728],
            "h": [250, 90],
            "pos": 1
        },
        "video": {
            "mimes": ["video/mp4", "video/webm"],
            "minduration": 5,
            "maxduration": 30
        },
        "bidfloor": "1.0",
        "bidfloorcur": "USD"
    },
    "site": {
        "id": "site123",
        "domain": "example.com"
    },
    "device": {
        "ua": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.85 Safari/537.36",
        "ip": "192.168.1.1",
        "geo": {
            "lat": 37.7749,
            "lon": -122.4194,
            "country": "USA"
        }
    },
    "user": {
        "id": "user123",
        "buyeruid": "buyer456"
    }
}'
```

This request simulates a bid request from a publisher to the SSP, which then forwards it to the Ad Exchange via gRPC.

## Future Improvements

1. **Separate OpenRTB and gRPC Implementation**:
   - Create a new folder structure for OpenRTB implementation
   - Implement OpenRTB 2.5 or 3.0 specifications
   - Run OpenRTB as a separate microservice

2. **Enhanced gRPC Usage**:
   - Implement bi-directional streaming for handling multiple bid requests/responses
   - Add interceptors for logging, error handling, and metrics collection

3. **Security Enhancements**:
   - Implement SSL/TLS for gRPC connections
   - Add authentication mechanisms (e.g., JWT tokens)

4. **Performance Optimizations**:
   - Implement connection pooling for gRPC clients
   - Use asynchronous gRPC calls for non-blocking operations

5. **Monitoring and Observability**:
   - Integrate with monitoring tools (e.g., Prometheus, Grafana)
   - Implement distributed tracing (e.g., OpenTelemetry)

6. **Scalability Improvements**:
   - Implement service discovery (e.g., using Consul or etcd)
   - Set up load balancing for gRPC servers

7. **Testing**:
   - Implement comprehensive unit and integration tests
   - Set up performance benchmarking tests

8. **Documentation**:
   - Create detailed API documentation
   - Provide examples of different bid request scenarios

9. **Containerization**:
   - Dockerize each component (Proto, AdExchange, SSP)
   - Create docker-compose for easy local deployment

10. **CI/CD**:
   - Set up automated build and deployment pipelines
   - Implement automated testing in the CI/CD process

## Detailed Component Breakdown

### Proto

The `proto` folder contains the Protocol Buffers definitions for our gRPC services. This is where we define the structure of our messages and the RPC methods.

Key files:
- `bidding.proto`: Defines the BidRequest and BidResponse messages, as well as the BiddingService.

### AdExchange

The `adexchange` component acts as our gRPC server. It receives bid requests from the SSP and returns bid responses.

Key files:
- `AdExchangeService.java`: Implements the gRPC service defined in the proto file.
- `AdexchangeApplication.java`: Spring Boot application that sets up and runs the gRPC server.

### SSP

The `ssp` component acts as both a REST API server (receiving bid requests from publishers) and a gRPC client (forwarding requests to the AdExchange).

Key files:
- `SspController.java`: REST controller that receives HTTP POST requests.
- `SspService.java`: Service that converts REST requests to gRPC calls and communicates with the AdExchange.
- `SspApplication.java`: Spring Boot application that sets up the REST API and gRPC client.

## Contributing

Contributions to this project are welcome. Please follow these steps:

1. Fork the repository
2. Create a new branch for your feature
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

