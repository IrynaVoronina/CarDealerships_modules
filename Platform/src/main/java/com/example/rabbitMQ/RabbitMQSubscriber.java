package com.example.rabbitMQ;

import com.example.cars_in_shop.inventory_event.InventoryEvent;

public interface RabbitMQSubscriber {

    void saveInventoryEvent(InventoryEvent inventoryEvent);
}
