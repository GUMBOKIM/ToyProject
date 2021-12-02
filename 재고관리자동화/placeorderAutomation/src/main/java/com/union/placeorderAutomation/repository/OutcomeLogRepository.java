package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.dto.task.part.status.PartLogDetail;
import com.union.placeorderAutomation.entity.Company;
import com.union.placeorderAutomation.entity.OutcomeLog;
import com.union.placeorderAutomation.entity.Part;
import com.union.placeorderAutomation.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutcomeLogRepository extends JpaRepository<OutcomeLog, Long> {

    @Query(value = "SELECT new com.union.placeorderAutomation.dto.task.part.status.PartLogDetail(a.orderSeq, COALESCE(SUM(a.amount),0)) "
            + "FROM OutcomeLog a "
            + "WHERE a.part = :part "
            + "AND a.date = :date "
            + "AND a.plant = :plant "
            + "GROUP BY a.orderSeq")
    List<PartLogDetail> sumQuantityByPartLog(@Param("part") Part part, @Param("date") String date, @Param("plant") Plant plant);

    List<OutcomeLog> findOutcomeLogByCompanyAndPlantAndDateAndOrderSeq(Company company, Plant plant, String date, int orderSeq);

}


