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
public class WarProducer {
    private final static String EXCHANGE_NAME = "war_exchange";
    private final static String EXCHANGE_TYPE = "topic";
    private final static String BINDING_KEY = "war.#";
    private final static String QUEUE = "war_queue";

    private final ConnectionFactory connectionFactory;
    private final ObjectMapper objectMapper;
    private Channel channel;

    public WarProducer(ConnectionFactory connectionFactory, ObjectMapper objectMapper) {
        this.connectionFactory = connectionFactory;
        this.objectMapper = objectMapper;
        try {
            Connection connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            channel.queueBind(QUEUE, EXCHANGE_NAME, BINDING_KEY);
        } catch (IOException | TimeoutException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void produce(User user, String routingKey) {
        try {
            String message = objectMapper.writeValueAsString(user);
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}