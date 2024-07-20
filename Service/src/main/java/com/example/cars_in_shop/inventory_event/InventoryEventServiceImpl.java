package com.example.cars_in_shop.inventory_event;


import org.springframework.stereotype.Service;

@Service
public class InventoryEventServiceImpl implements InventoryEventService {

    InventoryEventRepository inventoryEventRepository;

    public InventoryEventServiceImpl(InventoryEventRepository inventoryEventRepository) {
        this.inventoryEventRepository = inventoryEventRepository;
    }

    @Override
    public InventoryEvent saveInventoryEvent(InventoryEvent inventoryEvent) {
        return inventoryEventRepository.saveInventoryEvent(inventoryEvent);
    }
}
