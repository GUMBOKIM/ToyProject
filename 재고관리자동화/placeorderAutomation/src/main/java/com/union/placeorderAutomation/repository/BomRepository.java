package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.Bom;
import com.union.placeorderAutomation.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BomRepository extends JpaRepository<Bom, String> {

    Bom findByBwCode(String bwCode);

    @Query(value = "select b from Bom b " +
            "inner join BomPart bp " +
            "on b = bp.bom " +
            "inner join Part p " +
            "on bp.part = p " +
            "where p.company = ?1 " +
            "and p.selectYn = 'N' " +
            "GROUP BY b.bwCode " +
            "order by b.bwCode ")
    List<Bom> findByCompanyCode(Company company);
}
