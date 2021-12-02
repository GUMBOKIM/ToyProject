package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.dto.task.part.status.PartLogDetail;
import com.union.placeorderAutomation.entity.ModifyLog;
import com.union.placeorderAutomation.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModifyLogRepository extends JpaRepository<ModifyLog, Long> {

    @Query(value = "SELECT new com.union.placeorderAutomation.dto.task.part.status.PartLogDetail(a.orderSeq, SUM(a.amount)) "
            + "FROM ModifyLog a "
            + "WHERE a.part = :part And a.date = :date "
            + "GROUP BY a.orderSeq")
    List<PartLogDetail> sumQuantityByPartLog(@Param("part") Part part, @Param("date") String date);
}
