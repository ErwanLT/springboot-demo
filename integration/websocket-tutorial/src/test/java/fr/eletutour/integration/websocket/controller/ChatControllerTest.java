package fr.eletutour.integration.websocket.controller;

import fr.eletutour.integration.websocket.model.ChatMessage;
import fr.eletutour.integration.websocket.model.MessageType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatControllerTest {

    @LocalServerPort
    private int port;

    private WebSocketStompClient stompClient;

    @BeforeEach
    public void setup() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);
        this.stompClient = new WebSocketStompClient(sockJsClient);
        this.stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    }

    @Test
    public void testSendMessage() throws Exception {
        final CompletableFuture<ChatMessage> completableFuture = new CompletableFuture<>();

        StompSession session = stompClient.connectAsync(String.format("ws://localhost:%d/ws", port), new StompSessionHandlerAdapter() {})
                .get(1, TimeUnit.SECONDS);

        session.subscribe("/topic/public", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                completableFuture.complete((ChatMessage) payload);
            }
        });

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(MessageType.CHAT);
        chatMessage.setSender("testUser");
        chatMessage.setContent("Hello, World!");

        session.send("/app/chat.sendMessage", chatMessage);

        ChatMessage receivedMessage = completableFuture.get(5, TimeUnit.SECONDS);

        assertThat(receivedMessage).isNotNull();
        assertThat(receivedMessage.getType()).isEqualTo(MessageType.CHAT);
        assertThat(receivedMessage.getSender()).isEqualTo("testUser");
        assertThat(receivedMessage.getContent()).isEqualTo("Hello, World!");
    }

    @Test
    public void testAddUser() throws Exception {
        final CompletableFuture<ChatMessage> completableFuture = new CompletableFuture<>();

        StompSession session = stompClient.connectAsync(String.format("ws://localhost:%d/ws", port), new StompSessionHandlerAdapter() {})
                .get(1, TimeUnit.SECONDS);

        session.subscribe("/topic/public", new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return ChatMessage.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                completableFuture.complete((ChatMessage) payload);
            }
        });

        ChatMessage joinMessage = new ChatMessage();
        joinMessage.setType(MessageType.JOIN);
        joinMessage.setSender("newUser");

        session.send("/app/chat.addUser", joinMessage);

        ChatMessage receivedMessage = completableFuture.get(5, TimeUnit.SECONDS);

        assertThat(receivedMessage).isNotNull();
        assertThat(receivedMessage.getType()).isEqualTo(MessageType.JOIN);
        assertThat(receivedMessage.getSender()).isEqualTo("newUser");
    }
}
