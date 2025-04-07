package com.ijse.bookstore.messagingRABBIT;

import com.ijse.bookstore.dto.OrderShippingConfirmation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingKey;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        System.out.println("Mensagem enviada: " + message);
    }

    public void sendOrderShippingConfirmation(OrderShippingConfirmation orderShippingConfirmation) {
        log.info("operation='sendOrderShippingConfirmation', orderShipping='{}'", orderShippingConfirmation);
        rabbitTemplate.convertAndSend(exchange, routingKey, orderShippingConfirmation);
    }
}