package com.example.rabbitMQ;

import com.example.cars_in_shop.inventory_event.InventoryEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQPublisherImpl implements RabbitMQPublisher {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    RabbitTemplate rabbitTemplate;

    public RabbitMQPublisherImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishInventoryEvent(InventoryEvent inventoryEvent) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, inventoryEvent);
    }
}
