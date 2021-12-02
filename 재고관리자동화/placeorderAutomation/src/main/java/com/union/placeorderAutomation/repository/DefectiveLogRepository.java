package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.DefectiveLog;
import com.union.placeorderAutomation.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface DefectiveLogRepository extends JpaRepository<DefectiveLog, Long> {
    @Query(value = "SELECT COALESCE(SUM(dl.amount),0) " +
            "FROM DefectiveLog dl " +
            "WHERE dl.part = :part AND dl.date = :date"
    )
    int sumQuantityByPartLog(@Param("part") Part part, @Param("date") String date);
}
