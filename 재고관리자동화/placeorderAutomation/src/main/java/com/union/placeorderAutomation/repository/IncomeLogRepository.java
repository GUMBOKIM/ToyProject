package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.dto.task.part.status.PartLogDetail;
import com.union.placeorderAutomation.entity.IncomeLog;
import com.union.placeorderAutomation.entity.PartLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncomeLogRepository extends JpaRepository<IncomeLog, Long> {

    @Query(value = "SELECT new com.union.placeorderAutomation.dto.task.part.status.PartLogDetail(a.orderSeq, COALESCE(SUM(a.amount),0)) "
    + "FROM IncomeLog a "
    + "WHERE a.partLog = :partLog "
    + "GROUP BY a.orderSeq")
    List<PartLogDetail> sumQuantityByPartLog(@Param("partLog") PartLog partLog);
}
