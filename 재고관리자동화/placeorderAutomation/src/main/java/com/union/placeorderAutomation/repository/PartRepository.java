package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PartRepository extends JpaRepository<Part,Long> {

    Optional<Part> findByBwCode(String bwCode);

    @Query(value ="SELECT p " +
            "FROM Part p " +
            "INNER JOIN Company c " +
            "ON p.company = c.companyCode " +
            "WHERE c.companyCode = ?1 " +
            "ORDER BY p.bwCode")
    List<Part> findByCompany(String company);

}
