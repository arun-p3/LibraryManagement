package com.archonite.librarymanagement.system.inventory.repository;

import com.archonite.librarymanagement.system.inventory.model.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InventoryRepository extends JpaRepository<OrderModel, UUID> {
}
