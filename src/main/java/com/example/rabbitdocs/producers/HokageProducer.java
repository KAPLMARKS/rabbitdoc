package com.example.rabbitdocs.producers;

import com.example.rabbitdocs.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class HokageProducer {
    private final static String EXCHANGE_NAME = "hokage_exchange";
    private final static String EXCHANGE_TYPE = "fanout";
    private final static String QUEUE_1 = "hokage_order_queue";
    private final static String QUEUE_2 = "be_hokage_queue";


    private final ConnectionFactory connectionFactory;
    private final ObjectMapper objectMapper;
    private Channel channel;

    public HokageProducer(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        this.connectionFactory = connectionFactory;
        this.objectMapper = objectMapper;
        try {
            Connection connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            channel.queueBind(QUEUE_1, EXCHANGE_NAME, "");
            channel.queueBind(QUEUE_2, EXCHANGE_NAME, "");
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void produce(User user) {
        try {
            String message = objectMapper.writeValueAsString(user);
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}