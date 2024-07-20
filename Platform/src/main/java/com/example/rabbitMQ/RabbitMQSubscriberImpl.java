package com.example.rabbitMQ;

import com.example.cars_in_shop.inventory_event.InventoryEvent;
import com.example.cars_in_shop.inventory_event.InventoryEventService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSubscriberImpl implements RabbitMQSubscriber {

    private final String queueName = "InventoryEvent";

    private final InventoryEventService inventoryEventService;

    public RabbitMQSubscriberImpl(InventoryEventService inventoryEventService) {
        this.inventoryEventService = inventoryEventService;
    }

    @Override
    @RabbitListener(queues = queueName)
    public void saveInventoryEvent(InventoryEvent InventoryEvent) {
        inventoryEventService.saveInventoryEvent(InventoryEvent);
    }
}
