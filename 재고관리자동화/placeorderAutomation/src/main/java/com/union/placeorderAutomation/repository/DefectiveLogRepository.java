package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.DefectiveLog;
import com.union.placeorderAutomation.entity.PartLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface DefectiveLogRepository extends JpaRepository<DefectiveLog, Long> {
    @Query(value = "SELECT COALESCE(SUM(dl.amount),0) " +
            "FROM DefectiveLog dl " +
            "WHERE dl.partLog = :partLog"
    )
    int sumQuantityByPartLog(@Param("partLog") PartLog partLog);
}
