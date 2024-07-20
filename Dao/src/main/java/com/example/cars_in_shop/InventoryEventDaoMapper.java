package com.example.cars_in_shop;

import com.example.cars_in_shop.inventory_event.InventoryEvent;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InventoryEventDaoMapper {

    InventoryEventDao mapToDao(InventoryEvent inventoryEvent);

    InventoryEvent mapToModel(InventoryEventDao inventoryEventDao);

}
