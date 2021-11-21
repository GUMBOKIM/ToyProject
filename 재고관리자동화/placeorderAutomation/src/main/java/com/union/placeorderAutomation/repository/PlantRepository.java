package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository extends JpaRepository<Plant, String> {
}
