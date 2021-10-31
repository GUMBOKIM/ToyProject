package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.Part;
import com.union.placeorderAutomation.entity.PartInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartInventoryRepository extends JpaRepository<PartInventory,Long> {

    Optional<PartInventory> findByPartAndLot(Part part, String lot);

}
