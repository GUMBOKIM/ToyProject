package com.union.placeorderAutomation.repository;

import com.union.placeorderAutomation.entity.Part;
import com.union.placeorderAutomation.entity.PartInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PartInventoryRepository extends JpaRepository<PartInventory, Long> {

    @Query(value =
            "SELECT c.company_name, b.bw_code, b.po_code, b.sp_code, SUM(STOCK)  AS STOCK " +
                    "FROM part_inventory a " +
                    "INNER JOIN part b on a.part_id = b.bw_code " +
                    "INNER JOIN company c on b.company_id = c.company_code " +
                    "GROUP BY a.part_id " +
                    "ORDER BY b.bw_code",
            nativeQuery = true)
    List<Object[]> findPartStockInventoryList();

    @Query(value =
            "SELECT c.company_name, b.bw_code, b.po_code, b.sp_code, SUM(STOCK)  AS STOCK " +
                    "FROM part_inventory a " +
                    "INNER JOIN part b on a.part_id = b.bw_code " +
                    "INNER JOIN company c on b.company_id = c.company_code " +
                    "WHERE b.company_id = ?1 " +
                    "GROUP BY a.part_id " +
                    "ORDER BY b.bw_code",
            nativeQuery = true)
    List<Object[]> findPartStockInventoryListByCompanyCode(String companyCode);

    @Query(value = "SELECT * FROM part_inventory WHERE part_id = ?1 ORDER BY lot * 1 ASC", nativeQuery = true)
    List<PartInventory> findInventoryListByPart(String partBwCode);

    Optional<PartInventory> findByPartAndLot(Part part, String lot);

    List<PartInventory> findByPart(Part findPart);


}
