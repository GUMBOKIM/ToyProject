package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.Part;
import com.union.placeorderAutomation.entity.PartInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartInventoryRepository extends JpaRepository<PartInventory, Long> {

    // 파트 재고 조회에서 사용
    // 부품별 파트 재고 - 전체
    @Query(value =
            "SELECT c.company_name, b.bw_code, b.sp_code, SUM(STOCK)  AS STOCK " +
                    "FROM part_inventory a " +
                    "INNER JOIN part b on a.part_id = b.bw_code " +
                    "INNER JOIN company c on b.company_id = c.company_code " +
                    "WHERE STOCK > 0 " +
                    "GROUP BY a.part_id " +
                    "ORDER BY b.bw_code",
            nativeQuery = true)
    List<Object[]> findPartStockInventoryList();

    // 부품별 파트 재고 - 회사
    @Query(value =
            "SELECT c.company_name, b.bw_code, b.sp_code, SUM(STOCK) AS STOCK " +
                    "FROM part_inventory a " +
                    "INNER JOIN part b on a.part_id = b.bw_code " +
                    "INNER JOIN company c on b.company_id = c.company_code " +
                    "WHERE b.company_id = ?1 " +
                    "AND STOCK > 0 " +
                    "GROUP BY a.part_id " +
                    "ORDER BY b.bw_code",
            nativeQuery = true)
    List<Object[]> findPartStockInventoryListByCompanyCode(String companyCode);

    @Query(value =
            "SELECT SUM(STOCK)  AS STOCK " +
                    "FROM part_inventory " +
                    "WHERE part_id = ?1 ",
            nativeQuery = true)
    Long sumPartStock(String bwCode);

    @Query(value = "SELECT * FROM part_inventory WHERE part_id = ?1 AND stock > 0 ORDER BY IF(lot RLIKE '[A-Z][A-Z][A-Z]', 1, 2), lot", nativeQuery = true)
    List<PartInventory> findInventoryListByPart(String bwCode);

    @Query(value = "SELECT * FROM part_inventory WHERE part_id = ?1 AND lot = ?2", nativeQuery = true)
    Optional<PartInventory> findByPartAndLot(String partBwCode, String lot);



}
