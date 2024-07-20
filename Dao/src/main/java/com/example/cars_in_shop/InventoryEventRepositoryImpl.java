package com.example.cars_in_shop;

import com.example.cars_in_shop.inventory_event.InventoryEvent;
import com.example.cars_in_shop.inventory_event.InventoryEventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@AllArgsConstructor
public class InventoryEventRepositoryImpl implements InventoryEventRepository {

    private final InventoryEventJpaRepository jpaRepository;
    private final InventoryEventDaoMapper daoMapper;


    @Override
    public InventoryEvent saveInventoryEvent(InventoryEvent inventoryEvent) {
        InventoryEventDao dao = daoMapper.mapToDao(inventoryEvent);
        InventoryEventDao saved = jpaRepository.save(dao);
        return daoMapper.mapToModel(saved);
    }
}

