package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<Part,String> {

    Optional<Part> findByBwCode(String bwCode);

    @Query(value ="SELECT p " +
            "FROM Part p " +
            "INNER JOIN Company c " +
            "ON p.company = c.companyCode " +
            "WHERE c.companyCode = ?1 " +
            "AND p.selectYn = 'N' " +
            "AND p.useYn = 'Y' " +
            "ORDER BY p.bwCode")
    List<Part> findPartByCompany(String company);

    @Query(value ="SELECT p " +
            "FROM Part p " +
            "INNER JOIN Company c " +
            "ON p.company = c.companyCode " +
            "WHERE c.companyCode = ?1 " +
            "AND p.selectYn = 'Y' " +
            "AND p.useYn = 'Y' " +
            "ORDER BY p.bwCode")
    List<Part> findSelectPartByCompany(String company);

}
