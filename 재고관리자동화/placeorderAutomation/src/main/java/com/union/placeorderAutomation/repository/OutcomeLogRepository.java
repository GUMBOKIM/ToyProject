package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.dto.task.part.status.PartLogDetail;
import com.union.placeorderAutomation.entity.Company;
import com.union.placeorderAutomation.entity.OutcomeLog;
import com.union.placeorderAutomation.entity.PartLog;
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
            + "WHERE a.partLog = :partLog "
            + "AND a.plant = :plant "
            + "GROUP BY a.orderSeq")
    List<PartLogDetail> sumQuantityByPartLog(@Param("partLog") PartLog partLog, @Param("plant") Plant plant);
}


