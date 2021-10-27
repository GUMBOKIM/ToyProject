package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part,Long> {
}
