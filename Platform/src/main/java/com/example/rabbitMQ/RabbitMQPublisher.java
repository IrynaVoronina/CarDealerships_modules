package com.example.rabbitMQ;

import com.example.cars_in_shop.inventory_event.InventoryEvent;

public interface RabbitMQPublisher {

    void publishInventoryEvent(InventoryEvent inventoryEvent);
}
