package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.Company;
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

    @Query(value = "SELECT * FROM part_inventory WHERE part_id = ?1 AND stock > 0 ORDER BY  IF(ASCII(SUBSTRING(lot, 1, 1)) < 65, ASCII(SUBSTRING(lot, 1, 1)) + 50, ASCII(SUBSTRING(lot, 1, 1))) * 10000\n" +
            "              + IF(ASCII(SUBSTRING(lot, 2, 1)) < 65, ASCII(SUBSTRING(lot, 2, 1)) + 50, ASCII(SUBSTRING(lot, 2, 1))) * 100\n" +
            "                  + IF(ASCII(SUBSTRING(lot, 3, 1)) < 65, ASCII(SUBSTRING(lot, 3, 1)) + 50, ASCII(SUBSTRING(lot, 3, 1))) * 1", nativeQuery = true)
    List<PartInventory> findInventoryListByPart(String bwCode);

    @Query(value = "SELECT * FROM part_inventory WHERE part_id = ?1 AND lot = ?2", nativeQuery = true)
    Optional<PartInventory> findByPartAndLot(String partBwCode, String lot);

    @Query(value = "SELECT i FROM PartInventory i INNER JOIN i.part p WHERE p.company = :company order by i.part.bwCode, i.lot")
    List<PartInventory> findPartInventoryByCompany(@Param("company") Company company);

}
