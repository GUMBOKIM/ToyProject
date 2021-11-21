package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.Bom;
import com.union.placeorderAutomation.entity.BomPart;
import com.union.placeorderAutomation.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BomPartRepository extends JpaRepository<BomPart, String> {

    BomPart findByBomAndPart(Bom bom, Part part);
}
