package com.example.rabbitdocs.consumers;

import com.example.rabbitdocs.models.User;
import com.example.rabbitdocs.utils.PdfGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Component
public class NarutoConsumer {

    private final String QUEUE = "naruto_queue";
    private ObjectMapper objectMapper;
    private PdfGenerator pdfGenerator;
    private ConnectionFactory connectionFactory;

    public NarutoConsumer(ObjectMapper objectMapper, ConnectionFactory connectionFactory, PdfGenerator pdfGenerator) {
        this.objectMapper = objectMapper;
        this.pdfGenerator = pdfGenerator;
        this.connectionFactory = connectionFactory;
        consume();
    }

    public void consume() {
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.basicQos(3);
            channel.queueDeclare(QUEUE, true, false, true, null);
            DeliverCallback deliverCallback = (consumerTag, message) -> {
                User user = objectMapper.readValue(message.getBody(), User.class);
                System.out.println("DOING " + user.getFirstName());

                pdfGenerator.createPdf(user, "Naruto");
                System.out.println("Created naruto document for " + user.getLastName());
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            };
            channel.basicConsume(QUEUE, false, deliverCallback, (consumerTag, message) -> {
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
}