package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.PartLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PartLogRepository extends JpaRepository<PartLog, Long> {


    @Query(value =
            "SELECT log.part_id AS bwCode, DATE_FORMAT(log.date,'%d') AS day, log.division AS division, SUM(amount) AS amount, log.time AS time, log.factory AS factory" +
                    " FROM part_log log" +
                    " INNER JOIN part p" +
                    " ON p.bw_code = log.part_id" +
                    " INNER JOIN company c" +
                    " ON c.company_code = p.company_id" +
                    " WHERE log.date >= :startDate" +
                    " AND log.date < :endDate" +
                    " AND c.company_code = :companyCode" +
                    " GROUP BY day, log.division, log.part_id, log.time, log.factory;",
            nativeQuery = true)
    List<Object[]> findPartStatus(@Param("companyCode") String companyCode,
                                       @Param("startDate") String startDate,
                                       @Param("endDate") String endDate);

}
