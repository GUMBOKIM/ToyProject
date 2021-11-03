package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.Company;
import com.union.placeorderAutomation.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartRepository extends JpaRepository<Part,Long> {
    Optional<Part> findByBwCode(String bwCode);

    List<Part> findByCompany(Company company);

}
