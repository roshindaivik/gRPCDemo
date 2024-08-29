package com.example.adexchange;

import com.example.adexchange.service.AdExchangeService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@Slf4j
@SpringBootApplication
public class AdexchangeApplication {


	private static void startGrpcServer() {
		try {
			Server server = ServerBuilder.forPort(9090)
					.addService(new AdExchangeService())
					.build()
					.start();
			log.info("gRPC server started, listening on port 9090");
			server.awaitTermination();
		} catch (IOException | InterruptedException e) {
			log.error("Error starting gRPC server", e);
		}
    }

	public static void main(String[] args) {
		SpringApplication.run(AdexchangeApplication.class, args);
		startGrpcServer();

	}

}
