package fr.eletutour.integration.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the GreetingService.
 */
@SpringBootTest(properties = {
        "grpc.server.inProcessName=test", // Run the server in-process
        "grpc.server.port=-1", // Disable external port
        "grpc.client.inProcess.address=in-process:test" // Configure the client to connect to the in-process server
})
@DirtiesContext // Ensures the context is reset between tests
public class GreetingServiceImplTest {

    @GrpcClient("inProcess")
    private GreetingServiceGrpc.GreetingServiceBlockingStub greetingServiceBlockingStub;

    /**
     * Test the sayHello RPC method.
     */
    @Test
    public void testSayHello() {
        String name = "TestUser";
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response = greetingServiceBlockingStub.sayHello(request);

        assertThat(response.getMessage()).isEqualTo("Hello, " + name);
    }
}
