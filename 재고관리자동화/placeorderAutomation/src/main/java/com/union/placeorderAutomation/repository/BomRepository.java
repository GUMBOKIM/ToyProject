package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.Bom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BomRepository extends JpaRepository<Bom, Long> {

    Bom findByBwCode(String bwCode);
}
