package fr.eletutour.integration.grpc.service;


import fr.eletutour.integration.grpc.GreetingServiceGrpc;
import fr.eletutour.integration.grpc.HelloReply;
import fr.eletutour.integration.grpc.HelloRequest;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

/**
 * gRPC service that implements the GreetingService.
 */
@GrpcService
public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {

    /**
     * Implements the sayHello RPC method.
     * @param request the request from the client
     * @param responseObserver the observer to send the response to
     */
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        String name = request.getName();
        String message = "Hello, " + name;
        HelloReply reply = HelloReply.newBuilder().setMessage(message).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
