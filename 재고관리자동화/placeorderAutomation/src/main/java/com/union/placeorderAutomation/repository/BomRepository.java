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
    @Query(value = "select b.bw_code from bom b " +
            "inner join bom_part bp " +
            "on b.bw_code = bp.bom_bw_code " +
            "inner join part p " +
            "on bp.part_bw_code = p.bw_code " +
            "where p.company_id = ?1 " +
            "GROUP BY b.bw_code " +
            "order by b.bw_code ",
            nativeQuery = true)
    List<Bom> findByCompanyCode(String companyCode);
}
