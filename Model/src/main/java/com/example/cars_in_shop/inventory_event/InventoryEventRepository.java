package com.example.cars_in_shop.inventory_event;

import com.example.cars_in_shop.inventory_event.InventoryEvent;

public interface InventoryEventRepository {

    InventoryEvent saveInventoryEvent(InventoryEvent inventoryEvent);
}
